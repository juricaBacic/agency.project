package agency.services.implementations;


import java.time.LocalDateTime;

import agency.entity.Heist;
import agency.entity.HeistMember;
import agency.entity.HeistSkill;
import agency.entity.MemberSkill;
import agency.enumeration.Status;
import agency.repository.HeistRepository;
import agency.repository.HeistSkillRepository;
import agency.repository.MemberSkillRepository;
import agency.services.interfaces.AutomaticHeistStartService;
import agency.services.interfaces.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.ZoneOffset;
import java.util.*;

@PropertySource("classpath:application.properties")
@EnableScheduling
@Configuration
@Service
public class AutomaticHeistStartServiceImpl implements AutomaticHeistStartService {

    private HeistRepository heistRepository;
    private CurrentDateTime currentDateTime;
    private EmailService emailService;
    private MemberSkillRepository memberSkillRepository;
    private HeistSkillRepository heistSkillRepository;
    private Environment env;


    private Long levelUPTime;

    public AutomaticHeistStartServiceImpl(HeistRepository heistRepository, CurrentDateTime currentDateTime, EmailService emailService, MemberSkillRepository memberSkillRepository, HeistSkillRepository heistSkillRepository, Environment env) {
        this.heistRepository = heistRepository;
        this.currentDateTime = currentDateTime;
        this.emailService = emailService;
        this.memberSkillRepository = memberSkillRepository;
        this.heistSkillRepository = heistSkillRepository;
        this.env = env;
        levelUPTime=Long.parseLong(env.getProperty("app.levelUpTime", "86400"));
    }

    @Override
    @Scheduled(cron = "0 * * * * *")
    public void startHeistStatusAutomatically() {

        LocalDateTime localDateTime = LocalDateTime.now();

        List<Heist> findAllHeist = heistRepository.findAll();

        if (!findAllHeist.isEmpty()) {

            for (Heist heist : findAllHeist) {

                if (localDateTime.isAfter(heist.getStartTime()) && localDateTime.isBefore(heist.getEndTime()) && heist.getStatus().equals(Status.PLANNING)) {

                    heist.setStatus(Status.IN_PROGRESS);
                    Set<HeistMember> heistMembers = heist.getHeistMembers();
                    for (HeistMember heistMembersForHeist : heistMembers) {

                        emailService.sendSimpleMessage(heistMembersForHeist.getEmail());

                    }
                    heistRepository.save(heist);

                } else if (localDateTime.isAfter(heist.getEndTime()) && heist.getStatus().equals(Status.IN_PROGRESS)) {
                    levelUpMemberSkill(heist);
                    heist.setStatus(Status.FINISHED);
                    Set<HeistMember> heistMembers = heist.getHeistMembers();
                    for (HeistMember heistMembersForHeist : heistMembers) {
                        emailService.sendSimpleMessage(heistMembersForHeist.getEmail());
                    }
                    heistRepository.save(heist);
                }
            }
        }

    }

    void levelUpMemberSkill(Heist heist) {


        Set<HeistMember> heistMembers = heist.getHeistMembers();

        long spentTime = heist.getEndTime().toEpochSecond(ZoneOffset.UTC) - heist.getStartTime().toEpochSecond(ZoneOffset.UTC);

        long numberOfLevelUps = spentTime / levelUPTime;

        long restOfTimeSpent = spentTime % levelUPTime;

        Set<HeistSkill> heistSkillByHeist = heistSkillRepository.findHeistSkillByHeist(heist);

        for (HeistMember heistMember : heistMembers) {

            Set<MemberSkill> memberSkillByMember = memberSkillRepository.findMemberSkillsByMember(heistMember);

            for (HeistSkill heistSkill : heistSkillByHeist) {

                for (MemberSkill memberSkill : memberSkillByMember) {

                    if (heistSkill.getSkill().equals(memberSkill.getSkill()) && heistSkill.getLevel().length() <= memberSkill.getLevel().length()) {

                        memberSkill.setTime(restOfTimeSpent);

                        String levels = "";

                        for (int i = 0; i < numberOfLevelUps; i++) {

                            levels += "*";
                        }

                        String newLevel = (memberSkill.getLevel() + levels).length() > 10 ? "**********" : memberSkill.getLevel() + levels;

                        memberSkill.setLevel(newLevel);

                        memberSkillRepository.save(memberSkill);

                        break;
                    }

                }

            }
        }

    }
}
