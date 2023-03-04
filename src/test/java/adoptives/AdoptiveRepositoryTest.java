package adoptives;

import hamsters.Hamster;
import hamsters.HamsterRepository;
import hamsters.HamsterStatus;
import hamsters.Species;
import hosts.HostRepository;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

class AdoptiveRepositoryTest {

    EntityManagerFactory emf;

    AdoptiveRepository adoptiveRepository;
    HamsterRepository hamsterRepository;
    HostRepository hostRepository;

    Adoptive adoptive;

    @BeforeEach
    void init() {
        emf = Persistence.createEntityManagerFactory("pu");
        adoptiveRepository = new AdoptiveRepository(emf);
        hamsterRepository = new HamsterRepository(emf);
        hostRepository = new HostRepository(emf);

        adoptive = adoptiveRepository.saveAdoptive(new Adoptive("Zsíros B. Ödön", "912122RA", LocalDate.parse("1979-12-03"), "Budapest",
                "Nagy Rozália", "1191 Budapest..."));
    }

    @AfterEach
    void close() {
        emf.close();
    }

    @Test
    @DisplayName("Save the adoptive person")
    void testSaveAdoptiveWithAllInformation() {
        Adoptive adoptive = new Adoptive("Zsíros B. Ödön", "912122RA", LocalDate.parse("1979-12-03"), "Budapest",
                "Nagy Rozália", "1191 Budapest...");
        adoptiveRepository.saveAdoptive(adoptive);

        assertThat(adoptive.getId()).isNotNull();
    }

    @Test
    @DisplayName("Find adoptive person by ID")
    void testFindAdoptiveById() {
        long id = adoptive.getId();
        Adoptive expected = adoptiveRepository.findAdoptiveById(id);

        assertThat(expected.getId()).isEqualTo(adoptive.getId());
    }

    @Test
    @DisplayName("Adopt a hamster")
    void testAdoptHamster() {
        Hamster hamster = hamsterRepository.saveHamster(new Hamster("TestHamsterFirst", Species.GOLDEN, "dove", LocalDate.parse("2022-10-10"), HamsterStatus.ADOPTABLE));
        adoptive = adoptiveRepository.adoptHamster(hamster.getId(), adoptive.getId(), LocalDate.now());
        List<Hamster> hamsters = adoptive.getAdoptedHamsters().stream().toList();

        assertThat(hamsters.get(0))
                .isNotNull()
                .extracting(Hamster::getStatus)
                .isEqualTo(HamsterStatus.ADOPTED);
    }

    @Test
    @DisplayName("List of adopted hamsters")
    void testFindHamstersOfAdoptiveById() {
        Hamster hamster = hamsterRepository.saveHamster(new Hamster("TestHamsterFirst", Species.GOLDEN, "dove", LocalDate.parse("2022-10-10"), HamsterStatus.ADOPTABLE));
        Hamster hamster2 = hamsterRepository.saveHamster(new Hamster("TestHamsterSecond", Species.GOLDEN, "dove", LocalDate.parse("2022-10-10"), HamsterStatus.ADOPTABLE));

        long adoptiveId = adoptive.getId();

        adoptiveRepository.adoptHamster(hamster.getId(), adoptiveId, LocalDate.now());
        adoptiveRepository.adoptHamster(hamster2.getId(), adoptiveId, LocalDate.now());
        List<Hamster> hamsters = adoptiveRepository.findHamstersOfAdoptiveById(adoptiveId).stream().toList();

        assertThat(hamsters)
                .hasSize(2)
                .extracting(Hamster::getName)
                .contains("TestHamsterFirst");
    }


}