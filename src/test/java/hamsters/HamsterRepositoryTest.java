package hamsters;

import hosts.Host;
import hosts.HostRepository;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalDate;
import java.util.Set;


class HamsterRepositoryTest {

    EntityManagerFactory factory;
    HamsterRepository hamsterRepository;

    HostRepository hostRepository;

    @BeforeEach
    void init() {
        factory = Persistence.createEntityManagerFactory("pu");
        hamsterRepository = new HamsterRepository(factory);
        hostRepository = new HostRepository(factory);

    }
    @AfterEach
    void close() {
        factory.close();
    }

    @Test
    @DisplayName("Save hamster ")
    void testSaveHamster() {
        Hamster hamster = new Hamster("TestHamster", Species.GOLDEN, "dove", LocalDate.parse("2022-10-10"), HamsterStatus.ADOPTABLE);
        hamsterRepository.saveHamster(hamster);

        assertThat(hamster.getId()).isNotNull();
    }

    @Test
    @DisplayName("List of all hamsters")
    void testCreateListOfAllHamster() {
        Hamster hamster = new Hamster("TestHamsterFirst", Species.GOLDEN, "dove", LocalDate.parse("2022-10-10"), HamsterStatus.ADOPTABLE);
        hamsterRepository.saveHamster(hamster);
        Hamster hamster2 = new Hamster("TestHamsterSecond", Species.DWARF, "dove", LocalDate.parse("2022-11-10"), HamsterStatus.ADOPTABLE);
        hamsterRepository.saveHamster(hamster2);
        Hamster hamster3 = new Hamster("TestHamsterThird", Species.CAMPBELL, "dove", LocalDate.parse("2022-12-10"), HamsterStatus.DECEASED);
        hamsterRepository.saveHamster(hamster3);
        Hamster hamster4 = new Hamster("TestHamsterFourth", Species.DWARF, "dove", LocalDate.parse("2023-01-10"), HamsterStatus.PERMANENTLY_CARED_FOR);
        hamsterRepository.saveHamster(hamster4);

        Set<Hamster> all = hamsterRepository.createListOfAllHamsters();
        assertThat(all)
                .hasSize(4);
    }



    @Test
    @DisplayName("Find hamster by ID")
    void testFindHamsterById() {
        Hamster hamster = new Hamster("TestHamster", Species.GOLDEN, "dove", LocalDate.parse("2022-10-10"), HamsterStatus.ADOPTABLE);
        hamsterRepository.saveHamster(hamster);

        assertThat(hamster.getId()).isNotNull();

        Hamster result = hamsterRepository.findHamsterById(hamster.getId());

        assertThat(result.getId()).isEqualTo(hamster.getId());
        assertThat(result.getName()).isEqualTo("TestHamster");

    }


    @Test
    @DisplayName("Find hamster by name")
    void testFindHamstersByName() {
        Hamster hamster = new Hamster("TestHamster", Species.GOLDEN, "dove", LocalDate.parse("2022-10-10"), HamsterStatus.ADOPTABLE);
        hamsterRepository.saveHamster(hamster);
        Hamster hamster2 = new Hamster("TestHamster", Species.DWARF, "dove", LocalDate.parse("2022-10-10"), HamsterStatus.ADOPTABLE);
        hamsterRepository.saveHamster(hamster2);
        Hamster hamster3 = new Hamster("TestHamster", Species.CAMPBELL, "dove", LocalDate.parse("2022-10-10"), HamsterStatus.ADOPTABLE);
        hamsterRepository.saveHamster(hamster3);
        Hamster hamster4 = new Hamster("TestHamsterNotTheSame", Species.DWARF, "dove", LocalDate.parse("2022-10-10"), HamsterStatus.ADOPTABLE);
        hamsterRepository.saveHamster(hamster4);

        Set<Hamster> result = hamsterRepository.findHamstersByName("TestHamster");

        assertThat(result)
                .hasSize(3)
                .extracting(Hamster::getName)
                .containsOnly("TestHamster");
    }
    @Test
    @DisplayName("List of hamster by status")
    void testFindHamstersByStatus() {
        Hamster hamster = new Hamster("TestHamsterFirst", Species.GOLDEN, "dove", LocalDate.parse("2022-10-10"), HamsterStatus.ADOPTABLE);
        hamsterRepository.saveHamster(hamster);
        Hamster hamster2 = new Hamster("TestHamsterSecond", Species.DWARF, "dove", LocalDate.parse("2022-10-10"), HamsterStatus.ADOPTABLE);
        hamsterRepository.saveHamster(hamster2);
        Hamster hamster3 = new Hamster("TestHamsterThird", Species.CAMPBELL, "dove", LocalDate.parse("2022-10-10"), HamsterStatus.DECEASED);
        hamsterRepository.saveHamster(hamster3);
        Hamster hamster4 = new Hamster("TestHamsterFourth", Species.DWARF, "dove", LocalDate.parse("2022-10-10"), HamsterStatus.PERMANENTLY_CARED_FOR);
        hamsterRepository.saveHamster(hamster4);

        Set<Hamster> result = hamsterRepository.findHamstersByStatus(HamsterStatus.ADOPTABLE);

        assertThat(result)
                .hasSize(2)
                .extracting(Hamster::getName)
                .contains("TestHamsterFirst");
    }

    @Test
    @DisplayName("List of hamsters by age in interval")
    void TestFindHamstersByAge() {
        Hamster hamster = new Hamster("TestHamsterFirst", Species.GOLDEN, "dove", LocalDate.parse("2022-10-10"), HamsterStatus.ADOPTABLE);
        hamsterRepository.saveHamster(hamster);
        Hamster hamster2 = new Hamster("TestHamsterSecond", Species.DWARF, "dove", LocalDate.parse("2022-11-10"), HamsterStatus.ADOPTABLE);
        hamsterRepository.saveHamster(hamster2);
        Hamster hamster3 = new Hamster("TestHamsterThird", Species.CAMPBELL, "dove", LocalDate.parse("2022-12-10"), HamsterStatus.DECEASED);
        hamsterRepository.saveHamster(hamster3);
        Hamster hamster4 = new Hamster("TestHamsterFourth", Species.DWARF, "dove", LocalDate.parse("2023-01-10"), HamsterStatus.PERMANENTLY_CARED_FOR);
        hamsterRepository.saveHamster(hamster4);

        Set<Hamster> result = hamsterRepository.findHamstersByAge(LocalDate.parse("2022-12-01"));
        assertThat(result)
                .hasSize(2)
                .extracting(Hamster::getName)
                .contains("TestHamsterFourth");
    }

    @Test
    @DisplayName("List of hamsters by species")
    void testFindHamstersBySpecies() {
        Hamster hamster = new Hamster("TestHamsterFirst", Species.GOLDEN, "dove", LocalDate.parse("2022-10-10"), HamsterStatus.ADOPTABLE);
        hamsterRepository.saveHamster(hamster);
        Hamster hamster2 = new Hamster("TestHamsterSecond", Species.DWARF, "dove", LocalDate.parse("2022-11-10"), HamsterStatus.ADOPTABLE);
        hamsterRepository.saveHamster(hamster2);
        Hamster hamster3 = new Hamster("TestHamsterThird", Species.CAMPBELL, "dove", LocalDate.parse("2022-12-10"), HamsterStatus.DECEASED);
        hamsterRepository.saveHamster(hamster3);
        Hamster hamster4 = new Hamster("TestHamsterFourth", Species.DWARF, "dove", LocalDate.parse("2023-01-10"), HamsterStatus.PERMANENTLY_CARED_FOR);
        hamsterRepository.saveHamster(hamster4);

        Set<Hamster> result = hamsterRepository.findHamstersBySpecies(Species.DWARF);
        assertThat(result)
                .hasSize(2);

    }

    @Test
    @DisplayName("Update the status of a hamster")
    void testUpdateHamsterStatus() {
        Hamster hamster = new Hamster("TestHamster", Species.GOLDEN, "dove", LocalDate.parse("2022-10-10"), HamsterStatus.ADOPTABLE);
        hamsterRepository.saveHamster(hamster);

        assertThat(hamster.getStatus()).isEqualTo(HamsterStatus.ADOPTABLE);

        Hamster updated = hamsterRepository.updateHamsterStatusById(hamster.getId(), HamsterStatus.ADOPTED, LocalDate.now());
        assertThat(updated.getStatus()).isEqualTo(HamsterStatus.ADOPTED);
    }

    @Test
    @DisplayName("List of hamsters by the host's location")
    void testFindHamstersByLocation() {
        Hamster hamster = new Hamster("TestHamsterFirst", Species.GOLDEN, "dove", LocalDate.parse("2022-10-10"), HamsterStatus.ADOPTABLE);
        hamsterRepository.saveHamster(hamster);
        Hamster hamster2 = new Hamster("TestHamsterSecond", Species.DWARF, "dove", LocalDate.parse("2022-11-10"), HamsterStatus.ADOPTABLE);
        hamsterRepository.saveHamster(hamster2);
        Hamster hamster3 = new Hamster("TestHamsterThird", Species.CAMPBELL, "dove", LocalDate.parse("2022-12-10"), HamsterStatus.DECEASED);
        hamsterRepository.saveHamster(hamster3);
        Hamster hamster4 = new Hamster("TestHamsterFourth", Species.DWARF, "dove", LocalDate.parse("2023-01-10"), HamsterStatus.PERMANENTLY_CARED_FOR);
        hamsterRepository.saveHamster(hamster4);

        Host host = hostRepository.saveHost(new Host("Megyek Elemér", "Budapest", 2));
        Host host2 = hostRepository.saveHost(new Host("Cserepes Virág", "Szeged", 2));
        Host host3 = hostRepository.saveHost(new Host("Zsíros B. Ödön", "Budapest", 2));

        hamsterRepository.saveHamsterToHost(hamster.getId(), host.getId());
        hamsterRepository.saveHamsterToHost(hamster2.getId(), host2.getId());
        hamsterRepository.saveHamsterToHost(hamster3.getId(), host3.getId());
        hamsterRepository.saveHamsterToHost(hamster4.getId(), host3.getId());


        Set<Hamster> result = hamsterRepository.findHamstersByLocation("Budapest");
        assertThat(result)
                .hasSize(3);
        result = hamsterRepository.findHamstersByLocation("Szeged");
        assertThat(result)
                .hasSize(1);
    }


}