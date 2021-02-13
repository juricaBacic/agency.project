package agency.services.interfaces;

import agency.entity.HeistMember;

import java.util.Optional;

public interface HeistMemberService {

    HeistMember saveHeistMember(HeistMember heistMember);

    Optional<HeistMember> findHeistMemberById(String memberId);
}
