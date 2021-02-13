package agency.services.implementations;

import agency.entity.Skill;
import agency.repository.SkillRepository;
import agency.services.interfaces.SkillService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SkillServiceImpl implements SkillService {

    private SkillRepository skillRepository;


    public SkillServiceImpl(SkillRepository skillRepository) {
        this.skillRepository = skillRepository;
    }

    @Override
    public void saveSkill(Skill skill) {
        skillRepository.save(skill);
    }

    @Override
    public Optional<Skill> findSkillById(String name) {

        return skillRepository.findById(name);
    }


}
