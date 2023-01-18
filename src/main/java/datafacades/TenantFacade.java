package datafacades;

import entities.Tenant;
import errorhandling.API_Exception;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import java.util.List;

public class TenantFacade {
    private static EntityManagerFactory emf;
    private static TenantFacade instance;

    public TenantFacade() {
    }

    public static TenantFacade getTenantFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new TenantFacade();
        }
        return instance;
    }


    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public List<Tenant> getAllTenants() throws API_Exception {
        EntityManager em = getEntityManager();

        try {
            TypedQuery<Tenant> query = em.createQuery("SELECT t FROM Tenant t", Tenant.class);
            return query.getResultList();
        } catch (Exception e) {
            throw new API_Exception("Can't find any Tenants in the system", 404, e);
        }
    }

    public List<Tenant> getTenantsByHouse(int houseID) throws API_Exception {
        EntityManager em = getEntityManager();

        try {
            TypedQuery<Tenant> query = em.createQuery("SELECT t FROM Tenant t JOIN t.rentals r JOIN r.house h " +
                    "WHERE h.houseID = :houseID", Tenant.class);
            query.setParameter("houseID", houseID);
            List<Tenant> tenants = query.getResultList();
            return tenants;
        } catch (Exception e) {
            throw new API_Exception("Can't find Tenants living in a particular house", 404, e);
        } finally {
            em.close();
        }
    }

    public Tenant createTenant(Tenant tenant) throws API_Exception {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(tenant);
            em.getTransaction().commit();
        } catch (Exception e) {
            throw new API_Exception("Can't create Tenant: " + tenant.getTenantID() + " in the system!");
        } finally {
            em.close();
        }
        return tenant;
    }
}
