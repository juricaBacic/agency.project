package agency.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;


@Entity
public class Skill {


    @Id
    @Column(name = "name")
    private String name;

    public Skill(String name) {
        this.name = name;
    }
    public Skill() {}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}
