package agency.services.implementations;


import agency.dto.EligibleMembersDTO;
import agency.dto.HeistMemberDTO;
import agency.dto.HeistMemberSkillDTO;
import agency.entity.*;


import agency.enumeration.Status;
import agency.repository.HeistMemberRepository;
import agency.repository.HeistRepository;
import agency.repository.HeistSkillRepository;
import agency.repository.HeistMemberSkillRepository;
import agency.services.converters.EligibleMembersConverter;
import agency.services.converters.HeistMemberConverter;
import agency.services.interfaces.EmailService;
import agency.services.interfaces.HeistMemberService;
import agency.services.interfaces.SkillService;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;


@Service
public class HeistMemberServiceImpl implements HeistMemberService {

    private HeistMemberRepository heistMemberRepository;
    private HeistSkillRepository heistSkillRepository;
    private HeistRepository heistRepository;
    private HeistMemberSkillRepository heistMemberSkillRepository;
    private EligibleMembersConverter eligibleMembersConverter;
    private HeistMemberConverter heistMemberConverter;
    private SkillService skillService;
    private EmailService emailService;


    public HeistMemberServiceImpl(HeistMemberRepository heistMemberRepository, HeistSkillRepository heistSkillRepository, HeistRepository heistRepository, HeistMemberSkillRepository heistMemberSkillRepository,
                                  EligibleMembersConverter eligibleMembersConverter, HeistMemberConverter heistMemberConverter, SkillService skillService, EmailService emailService) {
        this.heistMemberRepository = heistMemberRepository;
        this.heistSkillRepository = heistSkillRepository;
        this.heistRepository = heistRepository;
        this.heistMemberSkillRepository = heistMemberSkillRepository;
        this.eligibleMembersConverter = eligibleMembersConverter;
        this.heistMemberConverter = heistMemberConverter;
        this.skillService = skillService;
        this.emailService = emailService;
    }

    @Override
    public HeistMember saveHeistMember(HeistMemberDTO heistMemberDTO) {


        HeistMember heistMember1 = heistMemberConverter.toEntity(heistMemberDTO);

        heistMemberRepository.save(heistMember1);

        for (HeistMemberSkillDTO skillDTO : heistMemberDTO.getSkills()) {
            Skill skill = new Skill();
            skill.setName(skillDTO.getName());
            skillService.saveSkill(skill);
        }

        HeistMember heistMember = new HeistMember();
        heistMember.setEmail(heistMemberDTO.getEmail());
        heistMember.setName(heistMemberDTO.getName());
        heistMember.setSex(heistMemberDTO.getSex());
        heistMember.setStatus(heistMemberDTO.getStatus());
        heistMember.setMainSkill(new Skill(heistMemberDTO.getMainSkill()));

        heistMemberDTO.getSkills().forEach(memberSkillDTO -> {
            Skill skill = new Skill();
            skill.setName(memberSkillDTO.getName());

            HeistMemberSkill heistMemberSkill = new HeistMemberSkill();
            heistMemberSkill.setLevel(memberSkillDTO.getLevel());
            heistMemberSkill.setSkill(skill);
            heistMemberSkill.setMember(heistMember);

        });

        emailService.sendEmailToMember(heistMemberDTO.getEmail());

        return heistMemberRepository.save(heistMember);

    }

    @Override
    public Optional<HeistMember> findHeistMemberById(String email) {

        return heistMemberRepository.findById(email);
    }

    @Override
    public Optional<HeistMember> findHeistMemberByStatusAndId(String email) {

        if (heistMemberRepository.findById(email).get().getStatus().equals(Status.AVAILABLE)
                || heistMemberRepository.findById(email).get().getStatus().equals(Status.EXPIRED)) {

            return heistMemberRepository.findById(email);

        }

        return null;
    }



    @Override
    public EligibleMembersDTO findEligibleHeistMember(String heistName) {

        Set<HeistMember> heistMembersSet = new HashSet<>();

        Optional<Heist> heistOptional = heistRepository.findById(heistName);

        Set<HeistSkill> heistSkillByHeist = heistSkillRepository.findHeistSkillByHeist(heistOptional.get());

        if (heistOptional.isPresent()) {

            Set<HeistMember> heistMembersByStatus = heistMemberRepository.findHeistMemberByStatusOrStatus(Status.AVAILABLE, Status.EXPIRED);

            for (HeistMember heistMember : heistMembersByStatus) {

                Set<HeistMemberSkill> memberSkillByHeistMember = heistMemberSkillRepository.findMemberSkillsByMember(heistMember);

                for (HeistSkill heistSkill : heistSkillByHeist) {

                    for (HeistMemberSkill heistMemberSkill : memberSkillByHeistMember) {

                        if (heistSkill.getSkill().equals(heistMemberSkill.getSkill()) && heistSkill.getLevel().length() <= heistMemberSkill.getLevel().length()) {

                            heistMembersSet.add(heistMember);

                            break;
                        }

                    }

                }
            }

        }
        return eligibleMembersConverter.toDto(heistMembersSet, heistSkillByHeist);
    }
}






