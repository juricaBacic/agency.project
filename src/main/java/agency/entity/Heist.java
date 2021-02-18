package agency.entity;

import agency.enumeration.Status;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;


@Entity
public class Heist {

    @Id
    private String name;

    private String location;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "confirmed_members",
            joinColumns = {@JoinColumn (name = "name" )}, inverseJoinColumns = {@JoinColumn (name = "email")})
    private Set<HeistMember> heistMembers;

    @Column(name = "start_time", columnDefinition = "TIMESTAMP")
    private LocalDateTime startTime;

    @Column(name = "end_time", columnDefinition = "TIMESTAMP")
    private LocalDateTime endTime;

    @Enumerated(EnumType.STRING)
    private Status status = Status.PLANNING;

    public Set<HeistMember> getHeistMembers() {
        return heistMembers;
    }

    public void setHeistMembers(Set<HeistMember> heistMembers) {
        this.heistMembers = heistMembers;
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

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
