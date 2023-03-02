package hosts;

import hamsters.Hamster;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

import java.util.List;
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

    public Host saveHamsterToHost(long hamsterId, long hostId) {
        EntityManager manager = factory.createEntityManager();
        try {
            manager.getTransaction().begin();
            Hamster hamster = manager.find(Hamster.class, hamsterId);
            Host host = manager.find(Host.class, hostId);
            host.addHamsterToHost(hamster);
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
    public Host findHostByIdWithHamsters(long hostId) {
        EntityManager manager = factory.createEntityManager();
        try {
            return manager.createQuery("select host from Host host left join fetch host.caredHamsters " +
                    "where host.id = :hostId", Host.class)
                    .setParameter("hostId", hostId)
                    .getSingleResult();
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

    public Set<Host> findHostsByLocationWithHamsters(String location) {
        EntityManager manager = factory.createEntityManager();
        try {
            return manager.createQuery("select host from Host host where host.location like :location", Host.class)
                    .setParameter("location", "%" + location + "%")
                    .getResultStream().collect(Collectors.toSet());
        } finally {
            manager.close();
        }
    }

}
