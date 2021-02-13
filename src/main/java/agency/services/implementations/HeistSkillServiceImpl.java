package agency.services.implementations;

import agency.entity.HeistSkill;
import agency.repository.HeistSkillRepository;
import agency.services.interfaces.HeistSkillService;
import org.springframework.stereotype.Service;

@Service
public class HeistSkillServiceImpl implements HeistSkillService {

    HeistSkillRepository heistSkillRepository;

    public HeistSkillServiceImpl(HeistSkillRepository heistSkillRepository) {
        this.heistSkillRepository = heistSkillRepository;
    }

    @Override
    public void saveHeistSkill(HeistSkill heistSkill) {

        heistSkillRepository.save(heistSkill);

    }
}
