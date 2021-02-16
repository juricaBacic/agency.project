package agency.entity.composite_key;

import java.io.Serializable;

public class HeistSkillId implements Serializable {


    private String skill;
    private String heist;


    public String getSkill() {
        return skill;
    }

    public void setSkill(String skill) {
        this.skill = skill;
    }

    public String getHeist() {
        return heist;
    }

    public void setHeist(String heist) {
        this.heist = heist;
    }
}
