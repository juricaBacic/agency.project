package agency.repository;

import agency.entity.HeistSkill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HeistSkillRepository extends JpaRepository<HeistSkill, Long > {



}
