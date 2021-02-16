package agency.services.implementations;

import agency.dto.HeistSkillDTO;
import agency.entity.HeistSkill;
import org.springframework.stereotype.Component;


@Component
public class HeistSkillConverter {


   public HeistSkillDTO toDto (HeistSkill heistSkill){

       HeistSkillDTO heistSkillDTO = new HeistSkillDTO();

       heistSkillDTO.setLevel(heistSkill.getLevel());
       heistSkillDTO.setMembers(heistSkill.getMember());
       heistSkillDTO.setName(heistSkill.getSkill().getName());

       return heistSkillDTO;
   }




}
