package agency.repository;

import agency.entity.Heist;
import agency.entity.HeistSkill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface HeistSkillRepository extends JpaRepository<HeistSkill, Long > {

    Set<HeistSkill> findHeistSkillByHeist(Heist heist);


}
