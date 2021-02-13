package agency.entity;

import agency.dto.MemberSkillDTO;
import agency.enumeration.Sex;
import agency.enumeration.Status;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "heist_member")
public class HeistMember {

    @ManyToOne
    private Skill mainSkill;

    @Id
    private String email;
    private String name;
    private Sex sex;
    private Status status;


    public Skill getMainSkill() {
        return mainSkill;
    }

    public void setMainSkill(Skill mainSkill) {
        this.mainSkill = mainSkill;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Sex getSex() {
        return sex;
    }

    public void setSex(Sex sex) {
        this.sex = sex;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}