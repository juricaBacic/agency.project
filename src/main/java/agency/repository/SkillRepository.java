package agency.repository;

import agency.entity.HeistMember;
import agency.entity.Skill;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SkillRepository extends JpaRepository<Skill, String> {
}
