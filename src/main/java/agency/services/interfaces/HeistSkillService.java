package agency.services.interfaces;

import agency.dto.HeistDTO;
import agency.entity.HeistSkill;

import java.util.Set;

public interface HeistSkillService {

    void saveHeistSkill(HeistSkill heistSkill);

    void updateHeistSkill(HeistDTO heistDTO, String name);

    Set<HeistSkill> heistSkillByHeistId(String name);
}
