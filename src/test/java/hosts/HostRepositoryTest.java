package hosts;

import hamsters.Hamster;
import hamsters.HamsterRepository;
import hamsters.Species;
import hamsters.HamsterStatus;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;


import java.time.LocalDate;
import java.util.Set;

import static org.assertj.core.api.Assertions.*;

class HostRepositoryTest {

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
    @DisplayName("Save host")
    void testSaveHost() {
        Host host = hostRepository.saveHost(new Host("Megyek Elemér", "Budapest", 2));

        assertThat(host.getId()).isNotNull();
    }

    @Test
    @DisplayName("Update host status")
    void testUpdateHostStatus() {
        Host host = hostRepository.saveHost(new Host("Megyek Elemér", "Budapest", 2));
        assertThat(host.getStatus()).isEqualTo(HostStatus.ACTIVE);
        assertThat(host.getCapacity()).isEqualTo(2);

        host = hostRepository.updateHostStatus(host.getId(), HostStatus.NON_ACTIVE);
        assertThat(host.getStatus())
                .isNotEqualByComparingTo(HostStatus.ACTIVE)
                .isEqualTo(HostStatus.NON_ACTIVE);
        assertThat(host.getCapacity()).isEqualTo(0);

    }

    @Test
    @DisplayName("Find host by ID")
    void testFindHostById() {
        Host host = hostRepository.saveHost(new Host("Megyek Elemér", "Budapest", 2));
        long id = host.getId();
        Host result = hostRepository.findHostById(id);

        assertThat(result.getName()).isEqualTo(host.getName());
    }

    @Test
    @DisplayName("Find host by name")
    void testFindHostByName() {
        Host host = hostRepository.saveHost(new Host("Megyek Elemér", "Budapest", 2));

        Host result = hostRepository.findHostByName("Elemér");

        assertThat(result.getName()).isEqualTo(host.getName());
    }

    @Test
    @DisplayName("Find host by ID with hamsters' list")
    void testFindHostByWithHamsters() {
        Host host = new Host("Megyek Elemér", "Budapest 18.");
        hostRepository.saveHost(host);
        assertThat(host.getId()).isNotNull();

        Hamster hamster = new Hamster(
                "TestHamster", Species.DWARF, "vadas", LocalDate.parse("2022-12-10"),
                HamsterStatus.ADOPTABLE, "Keresi új lakhelyét....", host, LocalDate.parse("2023-02-28"));
        hamsterRepository.saveHamster(hamster);
        assertThat(hamster.getId()).isNotNull();

        hamsterRepository.saveHamsterToHost(hamster.getId(), host.getId());

        Set<Hamster> hamsters = hostRepository.findHostWithAllHamsters(host.getId());
        assertThat(hamsters)
                .hasSize(1);


    }

    @Test
    @DisplayName("List of hosts by location")
    void testFindHostsByLocation() {
        Host first = new Host("Megyek Elemér", "Budapest", 3);
        Host second = new Host("Cserepes Virág", "Szeged",2);
        Host third = new Host("Godz Illa", "Budapest", 1);
        Host fourth = new Host("Major Anna", "Budapest", 2);
        hostRepository.saveHost(first);
        hostRepository.saveHost(second);
        hostRepository.saveHost(third);
        hostRepository.saveHost(fourth);

        Set<Host> result = hostRepository.findHostsByLocation("Budapest");
        assertThat(result)
                .hasSize(3)
                .extracting(Host::getName)
                .contains("Godz Illa");
    }

    @Test
    @DisplayName("List of hosts with hamsters in care")
    void testFindHostsByLocationWithHamster() {
        Host first = new Host("Megyek Elemér", "Budapest", 3);
        Host second = new Host("Cserepes Virág", "Szeged",2);
        hostRepository.saveHost(first);
        hostRepository.saveHost(second);
        Hamster hamster = new Hamster("TestHamsterOne", Species.GOLDEN, "dove", LocalDate.parse("2022-10-10"), HamsterStatus.ADOPTABLE);
        hamsterRepository.saveHamster(hamster);
        Hamster hamster2 = new Hamster("TestHamsterTwo", Species.GOLDEN, "dove", LocalDate.parse("2022-10-10"), HamsterStatus.ADOPTABLE);
        hamsterRepository.saveHamster(hamster2);
        Hamster hamster3 = new Hamster("TestHamsterThree", Species.GOLDEN, "dove", LocalDate.parse("2022-10-10"), HamsterStatus.ADOPTABLE);
        hamsterRepository.saveHamster(hamster3);

        hamsterRepository.saveHamsterToHost(hamster.getId(), first.getId());
        hamsterRepository.saveHamsterToHost(hamster2.getId(), first.getId());
        hamsterRepository.saveHamsterToHost(hamster3.getId(), second.getId());


        Set<Host> result = hostRepository.findHostsByLocation("Budapest");
        assertThat(result)
                .hasSize(1);

    }

    @Test
    @DisplayName("List of actual hamsters in care")
    void testFindHostWithActualHamsters() {
        Host host = new Host("Megyek Elemér", "Budapest", 3);
        hostRepository.saveHost(host);
        Hamster hamster = hamsterRepository.saveHamster(
                new Hamster("TestHamsterOne", Species.GOLDEN, "dove", LocalDate.parse("2022-10-10"), HamsterStatus.ADOPTABLE));
        Hamster hamster2 = hamsterRepository.saveHamster(
                new Hamster("TestHamsterTwo", Species.GOLDEN, "dove", LocalDate.parse("2022-10-10"), HamsterStatus.PERMANENTLY_CARED_FOR));
        Hamster hamster3 = hamsterRepository.saveHamster(
                new Hamster("TestHamsterThree", Species.GOLDEN, "dove", LocalDate.parse("2022-10-10"), HamsterStatus.UNDER_MEDICAL_TREATMENT));
        Hamster hamster4 = hamsterRepository.saveHamster(
                new Hamster("TestHamsterThree", Species.GOLDEN, "dove", LocalDate.parse("2019-10-10"), HamsterStatus.DECEASED));
        Hamster hamster5 = hamsterRepository.saveHamster(
                new Hamster("TestHamsterThree", Species.GOLDEN, "dove", LocalDate.parse("2019-10-10"), HamsterStatus.ADOPTED));

        hamsterRepository.saveHamsterToHost(hamster.getId(), host.getId());
        hamsterRepository.saveHamsterToHost(hamster2.getId(), host.getId());
        hamsterRepository.saveHamsterToHost(hamster3.getId(), host.getId());
        hamsterRepository.saveHamsterToHost(hamster4.getId(), host.getId());
        hamsterRepository.saveHamsterToHost(hamster5.getId(), host.getId());

        Set<Hamster> all = hostRepository.findHostWithAllHamsters(host.getId());
        assertThat(all)
                .hasSize(5);
        Set<Hamster> result = hostRepository.findHostWithActualHamsters(host.getId());
        assertThat(result)
                .hasSize(3)
                .extracting(Hamster::getStatus)
                .doesNotContain(HamsterStatus.DECEASED);
    }


}