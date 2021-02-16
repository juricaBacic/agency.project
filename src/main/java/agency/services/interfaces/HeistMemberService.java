package agency.services.interfaces;

import agency.dto.EligibleMembersDTO;
import agency.dto.HeistDTO;
import agency.dto.HeistMemberDTO;
import agency.entity.HeistMember;
import agency.services.implementations.EligibleMembersConverter;

import java.util.Optional;
import java.util.Set;

public interface HeistMemberService {

    HeistMember saveHeistMember(HeistMember heistMember);

    Optional<HeistMember> findHeistMemberById(String memberId);

    Optional<HeistMember> findHeistMemberByStatusAndId(String email);

    EligibleMembersDTO findEligibleHeistMember(String heistName);
}
