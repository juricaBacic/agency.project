package agency.services.interfaces;

import agency.dto.EligibleMembersDTO;
import agency.dto.HeistMemberDTO;
import agency.entity.HeistMember;

import java.util.Optional;


public interface HeistMemberService {

    HeistMember saveHeistMember(HeistMemberDTO heistMemberDTO);

    Optional<HeistMember> findHeistMemberById(String memberId);

    Optional<HeistMember> findHeistMemberByStatusAndId(String email);

    EligibleMembersDTO findEligibleHeistMember(String heistName);
}
