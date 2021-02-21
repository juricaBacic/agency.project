package agency.services.implementations;

import agency.dto.HeistMemberDTO;
import agency.entity.HeistMember;
import agency.entity.HeistMemberSkill;
import agency.entity.Skill;
import agency.repository.HeistMemberRepository;
import agency.repository.HeistMemberSkillRepository;
import agency.services.interfaces.HeistMemberService;
import agency.services.interfaces.HeistMemberSkillService;
import agency.services.interfaces.SkillService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Set;

@Service
public class HeistHeistMemberSkillServiceImpl implements HeistMemberSkillService {


    HeistMemberSkillRepository heistMemberSkillRepository;
    HeistMemberRepository heistMemberRepository;
    SkillService skillService;
    HeistMemberService heistMemberService;


    public HeistHeistMemberSkillServiceImpl(HeistMemberSkillRepository heistMemberSkillRepository, HeistMemberRepository heistMemberRepository, SkillService skillService, HeistMemberService heistMemberService) {
        this.heistMemberSkillRepository = heistMemberSkillRepository;
        this.heistMemberRepository = heistMemberRepository;
        this.skillService = skillService;
        this.heistMemberService = heistMemberService;
    }

    @Override
    public void saveMemberSkill(HeistMemberSkill heistMemberSkill) {

        heistMemberSkillRepository.save(heistMemberSkill);

    }



    @Override
    public void updateMemberSkill(String email, HeistMemberDTO heistMemberDTO) {

        Optional<HeistMember> optionalHeistMember = heistMemberRepository.findById(email);

        if (optionalHeistMember.isPresent()) {

            heistMemberDTO.getSkills().forEach(memberSkillDTO -> {
                Skill skill = new Skill();
                skill.setName(memberSkillDTO.getName());
                skillService.saveSkill(skill);
                HeistMemberSkill heistMemberSkill = new HeistMemberSkill();
                heistMemberSkill.setLevel(memberSkillDTO.getLevel());
                heistMemberSkill.setSkill(skill);


                HeistMember heistMember = optionalHeistMember.get();
                heistMemberSkill.setMember(heistMember);
                heistMember.setMainSkill(skillService.findSkillById(memberSkillDTO.getName()).get());

                saveMemberSkill(heistMemberSkill);

            });
        }
    }

    @Override
    @Transactional
    public void deleteMemberSkillByMemberAndSkill(String memberName, String skillName) {

        Optional<Skill> skillOptional = skillService.findSkillById(skillName);
        Optional<HeistMember> heistMemberOptional = heistMemberService.findHeistMemberById(memberName);

        if (skillOptional.isPresent() && heistMemberOptional.isPresent()) {
            heistMemberSkillRepository.deleteMemberSkillByMemberAndSkill(heistMemberOptional.get(), skillOptional.get());

        }
    }

    @Override
    public Set<HeistMemberSkill> getMemberSkillByMemberId(String email) {

        Set<HeistMemberSkill> memberSkillByHeistMemberEmail = heistMemberSkillRepository.findMemberSkillsByMemberEmail(email);

        return memberSkillByHeistMemberEmail;
    }
}
