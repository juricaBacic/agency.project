package agency.services.implementations;


import java.time.LocalDateTime;
import agency.entity.Heist;
import agency.entity.HeistMember;
import agency.entity.HeistSkill;
import agency.entity.HeistMemberSkill;
import agency.enumeration.Status;
import agency.repository.HeistRepository;
import agency.repository.HeistSkillRepository;
import agency.repository.HeistMemberSkillRepository;
import agency.services.interfaces.AutomaticHeistStartService;
import agency.services.interfaces.EmailService;
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
    private EmailService emailService;
    private HeistMemberSkillRepository heistMemberSkillRepository;
    private HeistSkillRepository heistSkillRepository;
    private Environment env;

    private Long levelUPTime;

    public AutomaticHeistStartServiceImpl(HeistRepository heistRepository, EmailService emailService, HeistMemberSkillRepository heistMemberSkillRepository, HeistSkillRepository heistSkillRepository, Environment env) {
        this.heistRepository = heistRepository;
        this.emailService = emailService;
        this.heistMemberSkillRepository = heistMemberSkillRepository;
        this.heistSkillRepository = heistSkillRepository;
        this.env = env;
        levelUPTime=Long.parseLong(env.getProperty("app.levelUpTime", "86400"));
    }

    @Override
    @Scheduled(cron = "0 * * * * *")
    public void startHeistAutomaticallyAndChangeStatus() {

        LocalDateTime localDateTime = LocalDateTime.now();

        List<Heist> findAllHeist = heistRepository.findAll();

        if (!findAllHeist.isEmpty()) {

            for (Heist heist : findAllHeist) {

                if (localDateTime.isAfter(heist.getStartTime()) && localDateTime.isBefore(heist.getEndTime()) && heist.getStatus().equals(Status.PLANNING)) {

                    heist.setStatus(Status.IN_PROGRESS);
                    Set<HeistMember> heistMembers = heist.getHeistMembers();
                    for (HeistMember heistMembersForHeist : heistMembers) {

                        emailService.sendEmailToMember(heistMembersForHeist.getEmail());

                    }
                    heistRepository.save(heist);

                } else if (localDateTime.isAfter(heist.getEndTime()) && heist.getStatus().equals(Status.IN_PROGRESS)) {
                    levelUpMemberSkill(heist);
                    heist.setStatus(Status.FINISHED);
                    Set<HeistMember> heistMembers = heist.getHeistMembers();
                    for (HeistMember heistMembersForHeist : heistMembers) {
                        emailService.sendEmailToMember(heistMembersForHeist.getEmail());
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

            Set<HeistMemberSkill> memberSkillByHeistMember = heistMemberSkillRepository.findMemberSkillsByMember(heistMember);

            for (HeistSkill heistSkill : heistSkillByHeist) {

                for (HeistMemberSkill heistMemberSkill : memberSkillByHeistMember) {

                    if (heistSkill.getSkill().equals(heistMemberSkill.getSkill()) && heistSkill.getLevel().length() <= heistMemberSkill.getLevel().length()) {

                        heistMemberSkill.setTime(restOfTimeSpent);

                        String levels = "";

                        for (int i = 0; i < numberOfLevelUps; i++) {

                            levels += "*";
                        }

                        String newLevel = (heistMemberSkill.getLevel() + levels).length() > 10 ? "**********" : heistMemberSkill.getLevel() + levels;

                        heistMemberSkill.setLevel(newLevel);

                        heistMemberSkillRepository.save(heistMemberSkill);

                        break;
                    }

                }

            }
        }

    }
}
