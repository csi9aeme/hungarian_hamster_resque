package hamsters;

import adoptives.Adoptive;
import hosts.Host;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "hamsters")
public class Hamster {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Enumerated(EnumType.STRING)
    private Species species;

    private String color;

    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

    private String location;
    @Enumerated(EnumType.STRING)
    private Status status;
    private String description;

    @Column(name = "with_us_since")
    private LocalDate shelterTime;

    @ManyToOne
    private Host host;

    @ManyToOne
    private Adoptive adoptive;

    @Column(name = "date_of_adoption")
    private LocalDate dateOfAdoption;

    public Hamster(){}
    public Hamster(String name, Species species, String color, LocalDate dateOfBirth, Status status) {
        this.name = name;
        this.species = species;
        this.color = color;
        this.dateOfBirth = dateOfBirth;
        this.status = status;
    }

    public Hamster(String name, Species species, String color, LocalDate dateOfBirth, String location, Status status, String description, Host host, LocalDate shelterTime) {
        this.name = name;
        this.species = species;
        this.color = color;
        this.dateOfBirth = dateOfBirth;
        this.location = location;
        this.status = status;
        this.description = description;
        this.host = host;
        this.shelterTime = shelterTime;

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

    public Species getSpecies() {
        return species;
    }

    public void setSpecies(Species species) {
        this.species = species;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation() {
        this.location = host.getLocation();
    }

    public Host getHost() {
        return host;
    }

    public void setHost(Host host) {
        this.host = host;
    }

    public LocalDate getDateOfAdoption() {
        return dateOfAdoption;
    }

    public void setDateOfAdoption(LocalDate dateOfAdoption) {
        this.dateOfAdoption = dateOfAdoption;
    }

    public Adoptive getAdoptive() {
        return adoptive;
    }

    public void setAdoptive(Adoptive adoptive) {
        this.adoptive = adoptive;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getShelterTime() {
        return shelterTime;
    }

    public void setShelterTime(LocalDate shelterTime) {
        this.shelterTime = shelterTime;
    }
}
