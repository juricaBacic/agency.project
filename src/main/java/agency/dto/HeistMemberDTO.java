package agency.dto;

import agency.enumeration.Sex;
import agency.enumeration.Status;
import java.util.Set;


public class HeistMemberDTO {


    private String mainSkill;
    private Set<MemberSkillDTO> skills;
    private String email;
    private String name;
    private Sex sex;
    private Status status;

    public String getMainSkill() {
        return mainSkill;
    }

    public void setMainSkill(String mainSkill) {
        this.mainSkill = mainSkill;
    }

    public Set<MemberSkillDTO> getSkills() {
        return skills;
    }

    public void setSkills(Set<MemberSkillDTO> skills) {
        this.skills = skills;
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