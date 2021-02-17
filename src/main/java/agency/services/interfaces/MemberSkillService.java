package agency.services.interfaces;

import agency.dto.HeistMemberDTO;
import agency.entity.MemberSkill;

import java.util.Optional;
import java.util.Set;

public interface MemberSkillService {


     void saveMemberSkill(MemberSkill memberSkill);

     void updateMemberSkill(String email, HeistMemberDTO heistMemberDTO);

     void deleteMemberSkillByMemberAndSkill(String memberName, String skillName);

     Set<MemberSkill> getMemberSkillByMemberId(String email);

}
