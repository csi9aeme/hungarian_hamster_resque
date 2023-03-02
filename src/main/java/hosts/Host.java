package hosts;

import hamsters.Hamster;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "hosts")
public class Host {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String location;
    private int capacity = 1;

    @OneToMany(mappedBy = "host", cascade = CascadeType.PERSIST)
    private Set<Hamster> caredHamsters = new HashSet<>();

    public Host(){}

    public Host(String name, String location) {
        this.name = name;
        this.location = location;
    }

    public Host(String name, String location, int capacity) {
        this.name = name;
        this.location = location;
        this.capacity = capacity;
    }

    public void addHamsterToHost(Hamster hamster) {
        caredHamsters.add(hamster);
        hamster.setHost(this);
    }



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public Set<Hamster> getCaredHamsters() {
        return caredHamsters;
    }

    public void setCaredHamsters(Set<Hamster> caredHamsters) {
        this.caredHamsters = caredHamsters;
    }
}
