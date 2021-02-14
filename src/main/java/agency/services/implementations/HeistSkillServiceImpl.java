package agency.services.implementations;

import agency.dto.HeistDTO;
import agency.dto.HeistSkillDTO;
import agency.entity.Heist;
import agency.entity.HeistMember;
import agency.entity.HeistSkill;
import agency.entity.Skill;
import agency.repository.HeistRepository;
import agency.repository.HeistSkillRepository;
import agency.services.interfaces.HeistSkillService;
import agency.services.interfaces.SkillService;
import org.springframework.expression.spel.ast.OpAnd;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class HeistSkillServiceImpl implements HeistSkillService {


    HeistSkillRepository heistSkillRepository;
    HeistRepository heistRepository;
    SkillService skillService;



    public HeistSkillServiceImpl(HeistSkillRepository heistSkillRepository, HeistRepository heistRepository, SkillService skillService) {


        this.heistSkillRepository = heistSkillRepository;
        this.heistRepository = heistRepository;
        this.skillService = skillService;

    }

    @Override
    public void saveHeistSkill(HeistSkill heistSkill) {

        heistSkillRepository.save(heistSkill);

    }

    @Override
    public void updateHeistSkill(HeistDTO heistDTO, String name) {

        Optional<Heist> optionalHeist = heistRepository.findById(name);

        if (optionalHeist.isPresent()) {

            heistDTO.getSkills().forEach(heistSkillDTO -> {
                Skill skill = new Skill();
                skill.setName(heistSkillDTO.getName());
                skillService.saveSkill(skill);
                HeistSkill heistSkill = new HeistSkill();
                heistSkill.setLevel(heistSkillDTO.getLevel());
                heistSkill.setMember(heistSkillDTO.getMembers());
                heistSkill.setSkill(skill);

                Heist heist1 = optionalHeist.get();
                heistSkill.setHeist(heist1);
                saveHeistSkill(heistSkill);

            });
        }
    }
}
