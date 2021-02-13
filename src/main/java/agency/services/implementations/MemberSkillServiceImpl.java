package agency.services.implementations;

import agency.dto.HeistMemberDTO;
import agency.dto.MemberSkillDTO;
import agency.entity.HeistMember;
import agency.entity.MemberSkill;
import agency.entity.Skill;
import agency.repository.HeistMemberRepository;
import agency.repository.MemberSkillRepository;
import agency.services.interfaces.MemberSkillService;
import agency.services.interfaces.SkillService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MemberSkillServiceImpl implements MemberSkillService {


    MemberSkillRepository memberSkillRepository;
    HeistMemberRepository heistMemberRepository;
    SkillService skillService;


    public MemberSkillServiceImpl(MemberSkillRepository memberSkillRepository, HeistMemberRepository heistMemberRepository, SkillService skillService) {
        this.memberSkillRepository = memberSkillRepository;
        this.heistMemberRepository = heistMemberRepository;
        this.skillService = skillService;
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

}
