package agency.repository;

import agency.entity.Heist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HeistRepository extends JpaRepository<Heist, String>{


}
