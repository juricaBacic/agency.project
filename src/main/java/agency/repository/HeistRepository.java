package agency.repository;

import agency.entity.Heist;
import agency.enumeration.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.Optional;




@Repository
public interface HeistRepository extends JpaRepository<Heist, String>{


    @Query("SELECT h.status FROM  Heist h WHERE h.name = ?1")
    Optional<Status>getStatusByHeistId(String name);


}
