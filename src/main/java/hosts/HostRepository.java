package hosts;

import hamsters.Hamster;
import hamsters.HamsterStatus;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

import java.util.Set;
import java.util.stream.Collectors;

public class HostRepository {

    private EntityManagerFactory factory;

    public HostRepository(EntityManagerFactory factory) {
        this.factory = factory;
    }

    public Host saveHost(Host host) {
        EntityManager manager = factory.createEntityManager();
        try {
            manager.getTransaction().begin();
            manager.persist(host);
            manager.getTransaction().commit();
            return host;
        } finally {
            manager.close();
        }
    }



    public Host findHostById(long hostId) {
        EntityManager manager = factory.createEntityManager();
        try {
            return manager.find(Host.class, hostId);
        } finally {
            manager.close();
        }
    }

    public Host updateHostStatus(long hostId, HostStatus status) {
        EntityManager manager = factory.createEntityManager();
        try {
            manager.getTransaction().begin();
            Host hostForUpdate = manager.find(Host.class, hostId);
            hostForUpdate.setStatus(status);
            hostForUpdate.setCapacity(0);
            manager.getTransaction().commit();
            return hostForUpdate;

        } finally {
            manager.close();
        }
    }

    public Host findHostByName(String name) {
        EntityManager manager = factory.createEntityManager();
        try {
            return manager.createQuery(
                    "select host from Host host where host.name like :name", Host.class)
                    .setParameter("name", "%" + name + "%")
                    .getSingleResult();
        } finally {
            manager.close();
        }
    }

    public Set<Host> findHostsByLocation(String location) {
        EntityManager manager = factory.createEntityManager();
        try {
            return manager.createQuery("select host from Host host where host.location like :location", Host.class)
                    .setParameter("location", "%" + location + "%")
                    .getResultStream().collect(Collectors.toSet());
        } finally {
            manager.close();
        }
    }

    public Set<Hamster> findHostWithAllHamsters(long hostId) {
        EntityManager manager = factory.createEntityManager();
        try {
            return manager.createQuery("select ham from Hamster ham left join fetch ham.host " +
                            "where ham.host.id = :hostId", Hamster.class)
                    .setParameter("hostId", hostId)
                    .getResultStream().collect(Collectors.toSet());
        } finally {
            manager.close();
        }
    }
    public Set<Hamster> findHostWithActualHamsters(long hostId) {
        EntityManager manager = factory.createEntityManager();
        try {
            return manager.createQuery("select ham from Hamster ham left join fetch ham.host " +
                            "where ham.host.id = :hostId " +
                            "AND ham.hamsterStatus = :adoptable " +
                            "OR ham.hamsterStatus = :treatment " +
                            "OR ham.hamsterStatus = :permCared", Hamster.class)
                    .setParameter("hostId", hostId)
                    .setParameter("adoptable", HamsterStatus.ADOPTABLE)
                    .setParameter("treatment", HamsterStatus.UNDER_MEDICAL_TREATMENT)
                    .setParameter("permCared", HamsterStatus.PERMANENTLY_CARED_FOR)
                    .getResultStream().collect(Collectors.toSet());
        } finally {
            manager.close();
        }
    }

}
