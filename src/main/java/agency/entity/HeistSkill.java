package agency.entity;

import agency.entity.composite_key.HeistSkillId;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.ManyToOne;

@Entity
@IdClass(HeistSkillId.class)
public class HeistSkill {

    @Id
    @ManyToOne
    private Heist heist;

    @Id
    @ManyToOne
    private Skill skill;

    private int member;
    private String level;


    public Heist getHeist() {
        return heist;
    }

    public void setHeist(Heist heist) {
        this.heist = heist;
    }

    public Skill getSkill() {
        return skill;
    }

    public void setSkill(Skill skill) {
        this.skill = skill;
    }

    public int getMember() {
        return member;
    }

    public void setMember(int member) {
        this.member = member;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }
}
