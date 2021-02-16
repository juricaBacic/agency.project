package agency.services.implementations;

import agency.dto.MemberSkillDTO;
import agency.entity.MemberSkill;
import org.springframework.stereotype.Component;

@Component
public class MemberSkillConverter {


    public MemberSkillDTO toDto(MemberSkill memberSkill){

        MemberSkillDTO memberSkillDTO = new MemberSkillDTO();

        memberSkillDTO.setLevel(memberSkill.getLevel());
        memberSkillDTO.setName(memberSkill.getSkill().getName());

        return memberSkillDTO;
    }

}
