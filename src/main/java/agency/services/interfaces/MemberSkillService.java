package agency.services.interfaces;

import agency.dto.HeistMemberDTO;
import agency.entity.MemberSkill;

public interface MemberSkillService {


     void saveMemberSkill(MemberSkill memberSkill);

     void updateMemberSkill(String email, HeistMemberDTO heistMemberDTO);

     void deleteMemberSkillByMemberAndSkill(String memberName, String skillName);

}
