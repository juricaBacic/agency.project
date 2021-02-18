package agency.services.implementations;

import agency.dto.HeistDTO;
import agency.dto.HeistSkillDTO;
import agency.entity.Heist;
import agency.entity.HeistSkill;
import agency.entity.Skill;
import agency.enumeration.Status;
import agency.repository.HeistRepository;
import agency.services.converters.HeistConverter;
import agency.services.converters.HeistSkillConverter;
import agency.services.interfaces.HeistService;
import agency.services.interfaces.HeistSkillService;
import agency.services.interfaces.SkillService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class HeistServiceImpl  implements HeistService {

    private HeistRepository heistRepository;
    private SkillService skillService;
    private HeistSkillService heistSkillService;
    private HeistConverter heistConverter;
    private HeistSkillConverter heistSkillConverter;

    public HeistServiceImpl(HeistRepository heistRepository, SkillService skillService,
                            HeistSkillService heistSkillService, HeistConverter heistConverter, HeistSkillConverter heistSkillConverter) {
        this.heistRepository = heistRepository;
        this.skillService = skillService;
        this.heistSkillService = heistSkillService;
        this.heistConverter = heistConverter;
        this.heistSkillConverter = heistSkillConverter;
    }

    @Override
    public Heist saveHeist(HeistDTO heistDTO) {

        Heist heist = heistConverter.toEntity(heistDTO);

        heistRepository.save(heist);

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
        return  heist;
    }

    @Override
    public Optional<Heist> getHeistById(String name) {

        Optional<Heist> heistById = heistRepository.findById(name);

        return heistById;

    }

    @Override
    public Optional<Heist>getHeistWithNameStatusANdSkillsByName(String name) {

        Optional<Heist> heistById = heistRepository.findById(name);

        return heistById;
    }

    @Override
    public Optional<Status> getHeistStatusByHeistId(String name) {

        Optional <Status> statusByHeistId = heistRepository.getStatusByHeistId(name);

        return statusByHeistId;
    }


}