package agency.services.converters;

import java.util.HashSet;
import java.util.Set;
import agency.dto.HeistDTO;
import agency.dto.HeistSkillDTO;
import agency.entity.Heist;
import agency.entity.HeistSkill;
import agency.repository.HeistSkillRepository;
import org.springframework.stereotype.Component;



@Component
public class HeistConverter {

    HeistSkillConverter heistSkillConverter;
    HeistSkillRepository heistSkillRepository;

    public HeistConverter(HeistSkillConverter heistSkillConverter, HeistSkillRepository heistSkillRepository)
    {
        this.heistSkillConverter = heistSkillConverter;
        this.heistSkillRepository = heistSkillRepository;
    }

    public HeistDTO toDto (Heist heist){

        HeistDTO heistDTO = new HeistDTO();

        heistDTO.setName(heist.getName());
        heistDTO.setLocation(heist.getLocation());
        heistDTO.setStartTime(heist.getStartTime());
        heistDTO.setEndTime(heist.getEndTime());
        heistDTO.setHeistMembers(heist.getHeistMembers());
        heistDTO.setStatus(heist.getStatus());

        final Set<HeistSkill> heistSkills = heistSkillRepository.findHeistSkillByHeist(heist);
        final Set<HeistSkillDTO> heistSkillDTOS = new HashSet<>();
        for (HeistSkill heistSkill : heistSkills)
        {
            heistSkillDTOS.add(heistSkillConverter.toDto(heistSkill));
            heistDTO.setSkills(heistSkillDTOS);
        }
        return heistDTO;
    }

    public HeistDTO toDtoForNameSkillAndStatus (Heist heist){

        HeistDTO heistDTO = new HeistDTO();

        heistDTO.setName(heist.getName());
        heistDTO.setStatus(heist.getStatus());

        final Set<HeistSkill> heistSkills = heistSkillRepository.findHeistSkillByHeist(heist);

        final Set<HeistSkillDTO> heistSkillDTOS = new HashSet<>();

        for (HeistSkill heistSkill : heistSkills)
        {
            heistSkillDTOS.add(heistSkillConverter.toDto(heistSkill));
            heistDTO.setSkills(heistSkillDTOS);
        }
        return heistDTO;
    }

   public Heist toEntity(HeistDTO heistDTO){

        Heist heist = new Heist();

        heist.setStatus(heistDTO.getStatus());
        heist.setName(heistDTO.getName());
        heist.setStartTime(heistDTO.getStartTime());
        heist.setEndTime(heistDTO.getEndTime());
        heist.setLocation(heistDTO.getLocation());
        heist.setHeistMembers(heistDTO.getHeistMembers());

        return heist;
   }

}