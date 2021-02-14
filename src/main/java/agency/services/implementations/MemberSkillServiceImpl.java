package agency.services.implementations;

import agency.dto.HeistMemberDTO;
import agency.dto.MemberSkillDTO;
import agency.entity.HeistMember;
import agency.entity.MemberSkill;
import agency.entity.Skill;
import agency.repository.HeistMemberRepository;
import agency.repository.MemberSkillRepository;
import agency.services.interfaces.HeistMemberService;
import agency.services.interfaces.MemberSkillService;
import agency.services.interfaces.SkillService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class MemberSkillServiceImpl implements MemberSkillService {


    MemberSkillRepository memberSkillRepository;
    HeistMemberRepository heistMemberRepository;
    SkillService skillService;
    HeistMemberService heistMemberService;


    public MemberSkillServiceImpl(MemberSkillRepository memberSkillRepository, HeistMemberRepository heistMemberRepository, SkillService skillService, HeistMemberService heistMemberService) {
        this.memberSkillRepository = memberSkillRepository;
        this.heistMemberRepository = heistMemberRepository;
        this.skillService = skillService;
        this.heistMemberService = heistMemberService;
    }

    @Override
    public void saveMemberSkill(MemberSkill memberSkill) {

        memberSkillRepository.save(memberSkill);

    }

    @Override
    public void updateMemberSkill(String email, HeistMemberDTO heistMemberDTO) {

        Optional<HeistMember> optionalHeistMember = heistMemberRepository.findById(email);

        if (optionalHeistMember.isPresent()) {

            heistMemberDTO.getSkills().forEach(memberSkillDTO -> {
                Skill skill = new Skill();
                skill.setName(memberSkillDTO.getName());
                skillService.saveSkill(skill);
                MemberSkill memberSkill = new MemberSkill();
                memberSkill.setLevel(memberSkillDTO.getLevel());
                memberSkill.setSkill(skill);


                HeistMember heistMember = optionalHeistMember.get();
                memberSkill.setMember(heistMember);
                heistMember.setMainSkill(skillService.findSkillById(memberSkillDTO.getName()).get());

                saveMemberSkill(memberSkill);

            });
        }
    }

    @Override
    @Transactional
    public void deleteMemberSkillByMemberAndSkill(String memberName, String skillName) {

        Optional<Skill> skillOptional = skillService.findSkillById(skillName);
        Optional<HeistMember> heistMemberOptional = heistMemberService.findHeistMemberById(memberName);

        if (skillOptional.isPresent() && heistMemberOptional.isPresent()) {
            memberSkillRepository.deleteMemberSkillByMemberAndSkill(heistMemberOptional.get(), skillOptional.get());

        }
    }
}
