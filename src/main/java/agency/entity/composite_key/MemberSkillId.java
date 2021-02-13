package agency.entity.composite_key;

import java.io.Serializable;

public class MemberSkillId implements Serializable {
    private String member;
    private String skill;

    public String getMember() {
        return member;
    }

    public void setMember(String member) {
        this.member = member;
    }

    public String getSkill() {
        return skill;
    }

    public void setSkill(String skill) {
        this.skill = skill;
    }
}
