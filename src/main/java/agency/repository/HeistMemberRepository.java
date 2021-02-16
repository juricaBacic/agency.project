package agency.repository;

import agency.entity.HeistMember;
import agency.enumeration.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface HeistMemberRepository extends JpaRepository<HeistMember, String> {

    Set<HeistMember> findHeistMemberByStatusOrStatus(Status statusOne, Status statusTwo);

    @Query("SELECT hm FROM  HeistMember hm WHERE hm.email = ?1 AND hm.status = 'AVAILABLE' OR hm.status = 'RETIRED'")
    HeistMember findHeistMemberWhoWillParticipateInHeist(String email);

}
