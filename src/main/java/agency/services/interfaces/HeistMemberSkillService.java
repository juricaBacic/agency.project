package agency.services.interfaces;

import agency.dto.HeistMemberDTO;
import agency.entity.HeistMemberSkill;

import java.util.Set;

public interface HeistMemberSkillService {


    void saveMemberSkill(HeistMemberSkill heistMemberSkill);

    void updateMemberSkill(String email, HeistMemberDTO heistMemberDTO);

    void deleteMemberSkillByMemberAndSkill(String memberName, String skillName);

    Set<HeistMemberSkill> getMemberSkillByMemberId(String email);

}
