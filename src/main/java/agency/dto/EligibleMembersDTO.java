package agency.dto;

import agency.entity.MemberSkill;

import java.util.Set;

public class EligibleMembersDTO {

    Set<HeistSkillDTO> skills;

    Set<HeistMemberDTO> members;

    public Set<HeistSkillDTO> getSkills() {
        return skills;
    }

    public void setSkills(Set<HeistSkillDTO> skills) {
        this.skills = skills;
    }

    public Set<HeistMemberDTO> getMembers() {
        return members;
    }

    public void setMembers(Set<HeistMemberDTO> members) {
        this.members = members;
    }
}
