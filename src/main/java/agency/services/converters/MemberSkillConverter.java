package agency.services.converters;

import agency.dto.HeistMemberSkillDTO;
import agency.entity.HeistMemberSkill;
import org.springframework.stereotype.Component;

@Component
public class MemberSkillConverter {


    public HeistMemberSkillDTO toDto(HeistMemberSkill heistMemberSkill){

        HeistMemberSkillDTO heistMemberSkillDTO = new HeistMemberSkillDTO();

        heistMemberSkillDTO.setLevel(heistMemberSkill.getLevel());
        heistMemberSkillDTO.setName(heistMemberSkill.getSkill().getName());

        return heistMemberSkillDTO;
    }

}
