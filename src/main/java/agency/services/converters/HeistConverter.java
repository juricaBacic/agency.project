package agency.services.converters;

import agency.dto.HeistDTO;
import agency.entity.Heist;
import agency.repository.MemberSkillRepository;
import org.springframework.stereotype.Component;



@Component
public class HeistConverter {

    MemberSkillConverter memberSkillConverter;
    MemberSkillRepository memberSkillRepository;

    public HeistConverter(MemberSkillConverter memberSkillConverter,MemberSkillRepository memberSkillRepository) {
        this.memberSkillConverter = memberSkillConverter;
        this.memberSkillRepository = memberSkillRepository;
    }

    public HeistDTO toDto (Heist heist){

        HeistDTO heistDTO = new HeistDTO();

        heistDTO.setName(heist.getName());
        heistDTO.setLocation(heist.getLocation());
        heistDTO.setStartTime(heist.getStartTime());
        heistDTO.setEndTime(heist.getEndTime());
        heistDTO.setHeistMembers(heist.getHeistMembers());
        //heistDTO.setSkills(memberSkillDTO.getName());



        return heistDTO;
    }

}
