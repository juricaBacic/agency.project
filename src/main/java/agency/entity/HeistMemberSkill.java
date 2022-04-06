package agency.entity;

import agency.entity.composite_key.MemberSkillId;

import javax.persistence.*;

@Entity
@IdClass(MemberSkillId.class)
public class HeistMemberSkill {

    @Id
    @ManyToOne
    private HeistMember member;

    @Id
    @ManyToOne
    private Skill skill;

    private String level;

    private long time = 0;
    
    public HeistMember getMember() {
        return member;
    }

    public void setMember(HeistMember member) {
        this.member = member;
    }

    public Skill getSkill() {
        return skill;
    }

    public void setSkill(Skill skill) {
        this.skill = skill;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }
}
