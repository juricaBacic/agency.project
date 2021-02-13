package agency.dto;

import javax.persistence.Column;
import javax.persistence.Id;
import java.time.LocalDateTime;
import java.util.Set;

public class HeistDTO {


    private String name;
    private String location;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Set<HeistSkillDTO> skills;


    public Set<HeistSkillDTO> getSkills() {
        return skills;
    }

    public void setSkills(Set<HeistSkillDTO> skills) {
        this.skills = skills;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }
}
