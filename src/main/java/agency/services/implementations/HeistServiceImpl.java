package agency.services.implementations;

import agency.dto.HeistDTO;
import agency.dto.HeistSkillDTO;
import agency.entity.Heist;
import agency.entity.HeistSkill;
import agency.entity.Skill;
import agency.repository.HeistRepository;
import agency.services.interfaces.HeistService;
import agency.services.interfaces.HeistSkillService;
import agency.services.interfaces.SkillService;
import org.springframework.stereotype.Service;

@Service
public class HeistServiceImpl  implements HeistService {

    private HeistRepository heistRepository;
    private SkillService skillService;
    private HeistSkillService heistSkillService;

    public HeistServiceImpl(HeistRepository heistRepository, SkillService skillService, HeistSkillService heistSkillService) {
        this.heistRepository = heistRepository;
        this.skillService = skillService;
        this.heistSkillService = heistSkillService;
    }

    @Override
    public Heist saveHeist(HeistDTO heistDTO) {
        Heist heist = new Heist();

        heist.setName(heistDTO.getName());
        heist.setLocation(heistDTO.getLocation());
        heist.setEndTime(heistDTO.getEndTime());
        heist.setStartTime(heistDTO.getStartTime());

        for (HeistSkillDTO heistSkillDTO : heistDTO.getSkills()) {
            Skill skill = new Skill();
            skill.setName(heistSkillDTO.getName());
            skillService.saveSkill(skill);

            HeistSkill heistSkill = new HeistSkill();
            heistSkill.setHeist(heist);
            heistSkill.setLevel(heistSkillDTO.getLevel());
            heistSkill.setMember(heistSkillDTO.getMembers());
            heistSkill.setSkill(skill);

            heistSkillService.saveHeistSkill(heistSkill);

        }

        return heistRepository.save(heist);
    }
}
