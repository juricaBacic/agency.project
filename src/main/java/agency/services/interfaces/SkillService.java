package agency.services.interfaces;

import agency.entity.Skill;

import java.util.Optional;

public interface SkillService {

    void saveSkill(Skill skill);

    Optional<Skill> findSkillById(String name);
}
