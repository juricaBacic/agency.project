package agency.services.interfaces;

import agency.entity.HeistMember;

import java.util.List;

public interface ConfirmMembersForHeistService {

    void updateHeistMemberForHeistIfMeetsRequiredConditions(List<HeistMember> heistMemberList, String name);
}
