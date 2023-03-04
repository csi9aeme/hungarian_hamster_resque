package hamsters;

import hosts.Host;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.Set;
import java.util.stream.Collectors;

public class HamsterRepository {

    private EntityManagerFactory factory;

    public HamsterRepository(EntityManagerFactory factory) {
        this.factory = factory;
    }

    public Hamster saveHamster(Hamster hamster) {
        EntityManager manager = factory.createEntityManager();
        try {
            manager.getTransaction().begin();
            manager.persist(hamster);
            manager.getTransaction().commit();
            return hamster;
        } finally {
            manager.close();
        }
    }
    public Hamster saveHamsterToHost(long hamsterId, long hostId) {
        EntityManager manager = factory.createEntityManager();
        try {
            manager.getTransaction().begin();
            Hamster hamster = manager.find(Hamster.class, hamsterId);
            Host host = manager.find(Host.class, hostId);
            host.addHamsterToHost(hamster);
            manager.getTransaction().commit();
            return hamster;
        } finally {
            manager.close();
        }
    }

    public Set<Hamster> createListOfAllHamsters() {
        EntityManager manager = factory.createEntityManager();
        try {
            return manager.createQuery("select ham from Hamster ham", Hamster.class)
                    .getResultStream().collect(Collectors.toSet());
            }
        finally {
            manager.close();;
        }
    }


    public Hamster findHamsterById(long hamsterId) {
        EntityManager manager = factory.createEntityManager();
        try {
            return manager.find(Hamster.class, hamsterId);
        } finally {
            manager.close();
        }
    }

    public Set<Hamster> findHamstersByName(String name) {
        EntityManager manager = factory.createEntityManager();
        try {
            return manager.createQuery(
                    "select ham from Hamster ham where ham.name = :name", Hamster.class)
                    .setParameter("name", name)
                    .getResultStream()
                    .collect(Collectors.toSet());
        } finally {
            manager.close();
        }
    }

    public Set<Hamster> findHamstersByStatus(HamsterStatus hamsterStatus) {
        EntityManager manager = factory.createEntityManager();
        try {
            return manager.createQuery(
                            "select ham from Hamster ham where ham.hamsterStatus = :status", Hamster.class)
                    .setParameter("status", hamsterStatus)
                    .getResultStream()
                    .collect(Collectors.toSet());
        } finally {
            manager.close();
        }
    }

    public Set<Hamster> findHamstersByAge(LocalDate birthDate) {
        EntityManager manager = factory.createEntityManager();
        try {
            return manager.createQuery(
                            "select ham from Hamster ham where ham.dateOfBirth between :birthDate and :now", Hamster.class)
                    .setParameter("birthDate", birthDate)
                    .setParameter("now", LocalDate.now())
                    .getResultStream()
                    .collect(Collectors.toSet());
        } finally {
            manager.close();
        }
    }

    public Set<Hamster> findHamstersBySpecies(Species species) {
        EntityManager manager = factory.createEntityManager();
        try {
            return manager.createQuery(
                            "select ham from Hamster ham where ham.species = :species", Hamster.class)
                    .setParameter("species", species)
                    .getResultStream()
                    .collect(Collectors.toSet());
        } finally {
            manager.close();
        }
    }

    public Hamster updateHamsterStatusById(long id, HamsterStatus hamsterStatus, LocalDate adoptionTime) {
        EntityManager manager = factory.createEntityManager();
        try {
            manager.getTransaction().begin();
            Hamster hamster = manager.find(Hamster.class, id);
            hamster.setStatus(hamsterStatus);
            hamster.setDateOfAdoption(adoptionTime);
            manager.getTransaction().commit();
            return hamster;
        } finally {
            manager.close();
        }
    }

    public Set<Hamster> findHamstersByLocation(String location) {
        EntityManager manager = factory.createEntityManager();
        try {
            return manager.createQuery("select ham from Hamster ham left join fetch ham.host " +
                            "where ham.host.location = :location", Hamster.class)
                    .setParameter("location", location)
                    .getResultStream().collect(Collectors.toSet());
        } finally {
            manager.close();
        }
    }



}
