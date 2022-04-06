package agency.dto;

import agency.entity.HeistMember;
import agency.enumeration.Status;

import java.time.LocalDateTime;
import java.util.Set;

public class HeistDTO {

    private String name;
    private String location;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Set<HeistSkillDTO> skills;
    private Set<HeistMember> heistMembers;
    private Status status = Status.PLANNING;

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
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

    public Set<HeistSkillDTO> getSkills() {
        return skills;
    }

    public void setSkills(Set<HeistSkillDTO> skills) {
        this.skills = skills;
    }

    public Set<HeistMember> getHeistMembers() {
        return heistMembers;
    }

    public void setHeistMembers(Set<HeistMember> heistMembers) {
        this.heistMembers = heistMembers;
    }

}
