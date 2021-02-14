package agency.services.interfaces;

import agency.dto.HeistDTO;
import agency.entity.Heist;
import agency.entity.HeistSkill;

public interface HeistSkillService {

    void saveHeistSkill(HeistSkill heistSkill);

    void updateHeistSkill(HeistDTO heistDTO, String name);
}
