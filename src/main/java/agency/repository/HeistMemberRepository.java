package agency.repository;

import agency.entity.HeistMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HeistMemberRepository extends JpaRepository<HeistMember, String> {


}
