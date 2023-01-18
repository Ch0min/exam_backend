package datafacades;

import entities.House;
import errorhandling.API_Exception;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import java.util.List;

public class HouseFacade {
    private static EntityManagerFactory emf;
    private static HouseFacade instance;

    public HouseFacade() {
    }

    public static HouseFacade getHouseFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new HouseFacade();
        }
        return instance;
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public List<House> getAllHouses() throws API_Exception {
        EntityManager em = getEntityManager();

        try {
            TypedQuery<House> query = em.createQuery("SELECT h FROM House h", House.class);
            return query.getResultList();
        } catch (Exception e) {
            throw new API_Exception("Can't find any Houses in the system", 404, e);
        }
    }

    public House getHouseByID(int houseID) throws API_Exception {
        EntityManager em = getEntityManager();

        try {
            House h = em.find(House.class, houseID);
            return h;
        } catch (Exception e){
            throw new API_Exception("Can't find a House with the House ID: " + houseID, 404, e);
        }
    }

    public House createHouse(House house) throws API_Exception {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(house);
            em.getTransaction().commit();
        } catch (Exception e) {
            throw new API_Exception("Can't create House: " + house.getHouseID() + " in the system!");
        } finally {
            em.close();
        }
        return house;
    }


}
