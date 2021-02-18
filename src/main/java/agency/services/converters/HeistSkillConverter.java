package agency.services.converters;

import agency.dto.HeistSkillDTO;
import agency.entity.HeistSkill;
import agency.repository.HeistSkillRepository;
import org.springframework.stereotype.Component;


@Component
public class HeistSkillConverter {

    HeistSkillRepository heistSkillRepository;

    public HeistSkillConverter(HeistSkillRepository heistSkillRepository) {

        this.heistSkillRepository = heistSkillRepository;

    }

    public HeistSkillDTO toDto (HeistSkill heistSkill){

       HeistSkillDTO heistSkillDTO = new HeistSkillDTO();

       heistSkillDTO.setLevel(heistSkill.getLevel());
       heistSkillDTO.setMembers(heistSkill.getMember());
       heistSkillDTO.setName(heistSkill.getSkill().getName());

       return heistSkillDTO;
   }

    public HeistSkill toEntity (HeistSkillDTO heistSkillDTO){

        HeistSkill heistSkill = new HeistSkill();

        heistSkill.setLevel(heistSkillDTO.getLevel());
        heistSkill.setMember(heistSkillDTO.getMembers());

        return heistSkill;
    }


}
