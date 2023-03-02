package hamsters;

import hosts.Host;
import hosts.HostRepository;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalDate;
import java.util.List;
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
    void testSaveHamsterToDatabase() {
        Hamster hamster = new Hamster("TestHamster", Species.GOLDEN, "dove", LocalDate.parse("2022-10-10"), Status.ADOPTABLE);
        hamsterRepository.saveHamster(hamster);

        assertThat(hamster).isNotNull();
    }



    @Test
    void testFindHamsterById() {
        Hamster hamster = new Hamster("TestHamster", Species.GOLDEN, "dove", LocalDate.parse("2022-10-10"), Status.ADOPTABLE);
        hamsterRepository.saveHamster(hamster);

        assertThat(hamster).isNotNull();

        Hamster result = hamsterRepository.findHamsterById(hamster.getId());

        assertThat(result.getId()).isEqualTo(hamster.getId());
        assertThat(result.getName()).isEqualTo("TestHamster");

    }

    @Test
    void testFindHamstersByName() {
        Hamster hamster = new Hamster("TestHamster", Species.GOLDEN, "dove", LocalDate.parse("2022-10-10"), Status.ADOPTABLE);
        hamsterRepository.saveHamster(hamster);
        Hamster hamster2 = new Hamster("TestHamster", Species.DWARF, "dove", LocalDate.parse("2022-10-10"), Status.ADOPTABLE);
        hamsterRepository.saveHamster(hamster2);
        Hamster hamster3 = new Hamster("TestHamster", Species.CAMPBELL, "dove", LocalDate.parse("2022-10-10"), Status.ADOPTABLE);
        hamsterRepository.saveHamster(hamster3);
        Hamster hamster4 = new Hamster("TestHamsterNotTheSame", Species.DWARF, "dove", LocalDate.parse("2022-10-10"), Status.ADOPTABLE);
        hamsterRepository.saveHamster(hamster4);

        Set<Hamster> result = hamsterRepository.findHamstersByName("TestHamster");

        assertThat(result)
                .hasSize(3)
                .extracting(Hamster::getName)
                .containsOnly("TestHamster");
    }
    @Test
    void testFindHamstersByStatus() {
        Hamster hamster = new Hamster("TestHamsterFirst", Species.GOLDEN, "dove", LocalDate.parse("2022-10-10"), Status.ADOPTABLE);
        hamsterRepository.saveHamster(hamster);
        Hamster hamster2 = new Hamster("TestHamsterSecond", Species.DWARF, "dove", LocalDate.parse("2022-10-10"), Status.ADOPTABLE);
        hamsterRepository.saveHamster(hamster2);
        Hamster hamster3 = new Hamster("TestHamsterThird", Species.CAMPBELL, "dove", LocalDate.parse("2022-10-10"), Status.DECEASED);
        hamsterRepository.saveHamster(hamster3);
        Hamster hamster4 = new Hamster("TestHamsterFourth", Species.DWARF, "dove", LocalDate.parse("2022-10-10"), Status.PERMANENTLY_CARED_FOR);
        hamsterRepository.saveHamster(hamster4);

        Set<Hamster> result = hamsterRepository.findHamstersByStatus(Status.ADOPTABLE);

        assertThat(result)
                .hasSize(2)
                .extracting(Hamster::getName)
                .contains("TestHamsterFirst");
    }

    @Test
    void TestFindHamstersByAge() {
        Hamster hamster = new Hamster("TestHamsterFirst", Species.GOLDEN, "dove", LocalDate.parse("2022-10-10"), Status.ADOPTABLE);
        hamsterRepository.saveHamster(hamster);
        Hamster hamster2 = new Hamster("TestHamsterSecond", Species.DWARF, "dove", LocalDate.parse("2022-11-10"), Status.ADOPTABLE);
        hamsterRepository.saveHamster(hamster2);
        Hamster hamster3 = new Hamster("TestHamsterThird", Species.CAMPBELL, "dove", LocalDate.parse("2022-12-10"), Status.DECEASED);
        hamsterRepository.saveHamster(hamster3);
        Hamster hamster4 = new Hamster("TestHamsterFourth", Species.DWARF, "dove", LocalDate.parse("2023-01-10"), Status.PERMANENTLY_CARED_FOR);
        hamsterRepository.saveHamster(hamster4);

        Set<Hamster> result = hamsterRepository.findHamstersByAge(LocalDate.parse("2022-12-01"));
        assertThat(result)
                .hasSize(2)
                .extracting(Hamster::getName)
                .contains("TestHamsterFourth");
    }

    @Test
    void testUpdateHamsterStatus() {
        Hamster hamster = new Hamster("TestHamster", Species.GOLDEN, "dove", LocalDate.parse("2022-10-10"), Status.ADOPTABLE);
        hamsterRepository.saveHamster(hamster);

        assertThat(hamster.getStatus()).isEqualTo(Status.ADOPTABLE);

        Hamster updated = hamsterRepository.updateHamsterStatusById(hamster.getId(), Status.ADOPTED, LocalDate.now());
        assertThat(updated.getStatus()).isEqualTo(Status.ADOPTED);
    }


}