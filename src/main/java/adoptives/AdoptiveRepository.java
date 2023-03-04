package adoptives;

import hamsters.Hamster;
import hamsters.HamsterStatus;
import jakarta.persistence.*;
import net.bytebuddy.asm.Advice;

import java.time.LocalDate;
import java.util.Set;
import java.util.stream.Collectors;

public class AdoptiveRepository {

    EntityManagerFactory factory;

    public AdoptiveRepository(EntityManagerFactory factory) {
        this.factory = factory;
    }

    public Adoptive saveAdoptive(Adoptive adoptive) {
        EntityManager manager = factory.createEntityManager();
        try {
            manager.getTransaction().begin();
            manager.persist(adoptive);
            manager.getTransaction().commit();
            return adoptive;
        }
        finally {
            manager.close();
        }
    }

    public Adoptive findAdoptiveById(long adoptiveId) {
        EntityManager manager = factory.createEntityManager();
        try {
            return manager.find(Adoptive.class, adoptiveId);
        } finally {
            manager.close();
        }
    }

    public Adoptive adoptHamster(long hamsterId, long adoptiveId, LocalDate adoptionDate) {
        EntityManager manager = factory.createEntityManager();
        try {
            manager.getTransaction().begin();
            Hamster hamster = manager.find(Hamster.class, hamsterId);
            Adoptive adoptive = manager.find(Adoptive.class, adoptiveId);
            adoptive.addHamsterToAdopted(hamster);
            hamster.setDateOfAdoption(adoptionDate);
            hamster.setStatus(HamsterStatus.ADOPTED);
            manager.getTransaction().commit();
            return adoptive;
        } finally {
            manager.close();
        }
    }
    public Set<Hamster> findHamstersOfAdoptiveById(long adoptiveId) {
        EntityManager manager = factory.createEntityManager();
        try {
            return manager.createQuery("select ham from Hamster ham left join fetch ham.adoptive " +
                            "where ham.adoptive.id = :adoptiveId", Hamster.class)
                    .setParameter("adoptiveId", adoptiveId)
                    .getResultStream().collect(Collectors.toSet());
        } finally {
            manager.close();
        }
    }

}
