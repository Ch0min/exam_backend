package datafacades;

import entities.*;
import utils.EMF_Creator;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class Populator {
    public static void populate() throws ParseException {
        EntityManagerFactory emf = EMF_Creator.createEntityManagerFactory();
        EntityManager em = emf.createEntityManager();

        Role adminRole = new Role("admin");
        Role userRole = new Role("user");

        User admin = new User("admin", "admin@gmail.com","test123");
        User user = new User("user", "user@gmail.com","test123");
        User user2 = new User("mark", "mark@gmail.com","test123");
        User user3 = new User("nick", "nick@gmail.com","test123");
        User user4 = new User("fido", "fido@gmail.com","test123");
        User user5 = new User("perle", "perle@gmail.com","test123");

        House house1 = new House("Lyngborgvej 3", "Kastrup", 5);
        House house2 = new House("Kløvervej 7", "Kongens Lyngby", 3);
        House house3 = new House("Tvingsager 10", "Hvidovre", 4);

        Rental rental1 = new Rental("18-01-2023", "18-01-2030", 144000, 30000, "Susanne Lundgaard", house1);
        Rental rental2 = new Rental("01-01-2020", "01-01-2025", 199000, 50000, "Briand Jensen", house2);
        Rental rental3 = new Rental("31-12-2019", "31-12-2028", 122000, 25000, "Olfert Treflo", house3);

        Tenant tenant1 = new Tenant("Mark Lundgaard", 29842984, "Pakkemand", user2);
        Tenant tenant2 = new Tenant("Nick Jensen", 27332733, "Pædagog", user3);
        Tenant tenant3 = new Tenant("Fido Odif", 42070842, "McDonalds Medarbejder", user4);
        Tenant tenant4 = new Tenant("Perle Elrep", 98769876, "Lærer", user5);


        if(admin.getUserPass().equals("test")||user.getUserPass().equals("test"))
            throw new UnsupportedOperationException("You have not changed the passwords");

        em.getTransaction().begin();

        // Adding
        admin.addRole(adminRole);
        user.addRole(userRole);
        user2.addRole(adminRole);
        user3.addRole(userRole);
        user4.addRole(userRole);
        user5.addRole(userRole);

        tenant1.addRental(rental1);
        tenant2.addRental(rental1);
        tenant3.addRental(rental2);
        tenant4.addRental(rental3);

        rental2.addTenant(tenant4);

        // Persisting
        em.persist(userRole);
        em.persist(adminRole);

        em.persist(user);
        em.persist(admin);
        em.persist(user2);
        em.persist(user3);
        em.persist(user4);
        em.persist(user5);

        em.persist(house1);
        em.persist(house2);
        em.persist(house3);

        em.persist(rental1);
        em.persist(rental2);
        em.persist(rental3);

        em.persist(tenant1);
        em.persist(tenant2);
        em.persist(tenant3);
        em.persist(tenant4);

        em.getTransaction().commit();
        System.out.println("PW: " + user.getUserPass());
        System.out.println("Testing user with OK password: " + user.verifyPassword("test"));
        System.out.println("Testing user with wrong password: " + user.verifyPassword("test1"));
        System.out.println("Created TEST Users");

    }

    public static void main(String[] args) throws ParseException {
        populate();
    }
}
