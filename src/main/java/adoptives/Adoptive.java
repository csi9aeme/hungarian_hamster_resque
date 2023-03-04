package adoptives;

import hamsters.Hamster;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "adoptives")
public class Adoptive {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String idNumber;
    private LocalDate dateOfBirth;
    private String birthplace;
    private String nameOfMother;
    private String address;
    private String email;
    private String phoneNumber;

    @OneToMany(mappedBy ="adoptive")
    private Set<Hamster> adoptedHamsters = new HashSet<>();

    public Adoptive(){}

    public Adoptive(String name, String idNumber, LocalDate dateOfBirth, String birthplace, String nameOfMother, String address) {
        this.name = name;
        this.idNumber = idNumber;
        this.dateOfBirth = dateOfBirth;
        this.birthplace = birthplace;
        this.nameOfMother = nameOfMother;
        this.address = address;
    }

    public Adoptive(String name, String idNumber, LocalDate dateOfBirth, String birthplace, String nameOfMother, String address, String email, String phoneNumber) {
        this.name = name;
        this.idNumber = idNumber;
        this.dateOfBirth = dateOfBirth;
        this.birthplace = birthplace;
        this.nameOfMother = nameOfMother;
        this.address = address;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    public void addHamsterToAdopted(Hamster hamster) {
        adoptedHamsters.add(hamster);
        hamster.setAdoptive(this);
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

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getBirthplace() {
        return birthplace;
    }

    public void setBirthplace(String birthplace) {
        this.birthplace = birthplace;
    }

    public String getNameOfMother() {
        return nameOfMother;
    }

    public void setNameOfMother(String nameOfMother) {
        this.nameOfMother = nameOfMother;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Set<Hamster> getAdoptedHamsters() {
        return adoptedHamsters;
    }

    public void setAdoptedHamsters(Set<Hamster> adoptedHamsters) {
        this.adoptedHamsters = adoptedHamsters;
    }
}
