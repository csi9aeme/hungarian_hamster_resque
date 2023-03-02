package hosts;

import hamsters.Hamster;
import hamsters.HamsterRepository;
import hamsters.Species;
import hamsters.Status;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jdk.jfr.SettingDescriptor;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

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
    void testSaveHost() {
        Host host = hostRepository.saveHost(new Host("Megyek Elemér", "Budapest", 2));

        assertThat(host.getId()).isNotNull();
    }

    @Test
    void testFindHostById() {
        Host host = hostRepository.saveHost(new Host("Megyek Elemér", "Budapest", 2));
        long id = host.getId();
        Host result = hostRepository.findHostById(id);

        assertThat(result.getName()).isEqualTo(host.getName());
    }

    @Test
    void testFindHostByName() {
        Host host = hostRepository.saveHost(new Host("Megyek Elemér", "Budapest", 2));

        Host result = hostRepository.findHostByName("Elemér");

        assertThat(result.getName()).isEqualTo(host.getName());
    }

    @Test
    void testFindHostByWithHamsters() {
        Host host = new Host("Megyek Elemér", "Budapest 18.");
        hostRepository.saveHost(host);
        Assertions.assertThat(host.getId()).isNotNull();

        Hamster hamster = new Hamster(
                "TestHamster", Species.DWARF, "vadas", LocalDate.parse("2022-12-10"),"Budapest 18.",
                Status.ADOPTABLE, "Keresi új lakhelyét....", host, LocalDate.parse("2023-02-28"));
        hamsterRepository.saveHamster(hamster);
        Assertions.assertThat(hamster.getId()).isNotNull();

        hostRepository.saveHamsterToHost(hamster.getId(), host.getId());

        Host actual = hostRepository.findHostByIdWithHamsters(host.getId());
        Assertions.assertThat(actual.getCaredHamsters())
                .hasSize(1);

    }

    @Test
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
    void testFindHostsByLocationWithHamster() {
        Host first = new Host("Megyek Elemér", "Budapest", 3);
        Host second = new Host("Cserepes Virág", "Szeged",2);
        hostRepository.saveHost(first);
        hostRepository.saveHost(second);
        Hamster hamster = new Hamster("TestHamsterOne", Species.GOLDEN, "dove", LocalDate.parse("2022-10-10"), Status.ADOPTABLE);
        hamsterRepository.saveHamster(hamster);
        Hamster hamster2 = new Hamster("TestHamsterTwo", Species.GOLDEN, "dove", LocalDate.parse("2022-10-10"), Status.ADOPTABLE);
        hamsterRepository.saveHamster(hamster2);
        Hamster hamster3 = new Hamster("TestHamsterThree", Species.GOLDEN, "dove", LocalDate.parse("2022-10-10"), Status.ADOPTABLE);
        hamsterRepository.saveHamster(hamster3);

        hostRepository.saveHamsterToHost(hamster.getId(), first.getId());
        hostRepository.saveHamsterToHost(hamster2.getId(), first.getId());
        hostRepository.saveHamsterToHost(hamster3.getId(), second.getId());


        Set<Host> result = hostRepository.findHostsByLocation("Budapest");
        assertThat(result)
                .hasSize(1);

    }

}