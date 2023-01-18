package datafacades;

import entities.House;
import entities.Rental;
import errorhandling.API_Exception;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import java.util.List;

public class RentalFacade {
    private static EntityManagerFactory emf;
    private static RentalFacade instance;

    public RentalFacade() {
    }

    public static RentalFacade getRentalFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new RentalFacade();
        }
        return instance;
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public List<Rental> getAllRentals() throws API_Exception {
        EntityManager em = getEntityManager();
        try {
            TypedQuery<Rental> query = em.createQuery("SELECT r FROM Rental r", Rental.class);
            return query.getResultList();
        } catch (Exception e) {
            throw new API_Exception("Can't find any Rentals in the system", 404, e);
        }
    }

    public Rental createRental(Rental rental) throws API_Exception {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(rental);
            em.getTransaction().commit();
        } catch (Exception e) {
            throw new API_Exception("Can't create Rental: " + rental.getRentalID() + " in the system!");
        } finally {
            em.close();
        }
        return rental;
    }

    public Rental assignRentalToHouse(int rentalID, int houseID) throws API_Exception {
        EntityManager em = getEntityManager();
        Rental rental = em.find(Rental.class, rentalID);
        House house = em.find(House.class, houseID);

        try {
            em.getTransaction().begin();
            rental.assignHouse(house);
            em.getTransaction().commit();
        } catch (Exception e) {
            if (rental == null) {
                throw new API_Exception("Can't find Rental with the ID: " + rental.getRentalID(), 400, e);
            }
            if (house == null) {
                throw new API_Exception("Can't find House with the ID: " + house.getHouseID(), 400, e);
            }
        } finally {
            em.close();
        }
        return rental;
    }

    public Rental updateRental(Rental rental) throws API_Exception {
        EntityManager em = getEntityManager();

        try {
            em.find(Rental.class, rental.getRentalID());
            em.getTransaction().begin();
            Rental r = em.merge(rental);
            em.getTransaction().commit();
            return r;
        } catch (Exception e) {
            throw new API_Exception("Can't update Rental with the ID: " + rental.getRentalID(), 400, e);
        } finally {
            em.close();
        }
    }


    public Rental deleteRental(int rentalID) throws API_Exception {
        EntityManager em = getEntityManager();
        Rental rental = em.find(Rental.class, rentalID);

        try {
            em.getTransaction().begin();
            em.remove(rental);
            em.getTransaction().commit();
        } catch (Exception e) {
            if (rental == null) {
                throw new API_Exception("Can't delete Rental with the ID: " + rental.getRentalID(), 400, e);
            }
        } finally {
            em.close();
        }
        return rental;
    }


}
