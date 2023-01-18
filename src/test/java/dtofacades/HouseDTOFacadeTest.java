package dtofacades;

import dtos.HouseDTO;
import dtos.RentalDTO;
import dtos.TenantDTO;
import dtos.UserDTO;
import entities.*;
import errorhandling.API_Exception;
import org.junit.jupiter.api.*;
import utils.EMF_Creator;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class HouseDTOFacadeTest {
    private static EntityManagerFactory emf;
    private static HouseDTOFacade facade;

    Role userRole, adminRole;
    User admin, user, user2, user3, user4, user5;
    House house1, house2, house3;
    Rental rental1, rental2, rental3;
    Tenant tenant1, tenant2, tenant3, tenant4;

    UserDTO udtoAdmin, udto, udto2, udto3, udto4, udto5;
    HouseDTO hdto1, hdto2, hdto3;
    RentalDTO rdto1, rdto2, rdto3;
    TenantDTO tdto1, tdto2, tdto3, tdto4;

    public HouseDTOFacadeTest() {
    }

    @BeforeAll
    public static void setUpClass() {
        emf = EMF_Creator.createEntityManagerFactoryForTest();
        facade = HouseDTOFacade.getInstance(emf);
    }

    @AfterAll
    public static void tearDownClass() {
        System.out.println("EXECUTION OF ALL TESTS IN HOUSEDTOFACADETEST DONE");
    }

    @BeforeEach
    public void setUp() {
        EntityManager em = emf.createEntityManager();

        try {
            em.getTransaction().begin();
            em.createNamedQuery("Rental.deleteAllRows").executeUpdate();
            em.createNamedQuery("Tenant.deleteAllRows").executeUpdate();
            em.createNamedQuery("User.deleteAllRows").executeUpdate();
            em.createNamedQuery("Role.deleteAllRows").executeUpdate();
            em.createNamedQuery("House.deleteAllRows").executeUpdate();

            adminRole = new Role("admin");
            userRole = new Role("user");


            admin = new User("admin", "admin@gmail.com", "test123");
            user = new User("user", "user@gmail.com", "test123");
            user2 = new User("mark", "mark@gmail.com", "test123");
            user3 = new User("nick", "nick@gmail.com", "test123");
            user4 = new User("fido", "fido@gmail.com", "test123");
            user5 = new User("perle", "perle@gmail.com", "test123");

            house1 = new House("Lyngborgvej 3", "Kastrup", 5);
            house2 = new House("Kløvervej 7", "Kongens Lyngby", 3);
            house3 = new House("Tvingsager 10", "Hvidovre", 4);

            rental1 = new Rental("18-01-2023", "18-01-2030", 144000, 30000, "Susanne Lundgaard", house1);
            rental2 = new Rental("01-01-2020", "01-01-2025", 199000, 50000, "Briand Jensen", house2);
            rental3 = new Rental("31-12-2019", "31-12-2028", 122000, 25000, "Olfert Treflo", house3);

            tenant1 = new Tenant("Mark Lundgaard", 29842984, "Pakkemand", user2);
            tenant2 = new Tenant("Nick Jensen", 27332733, "Pædagog", user3);
            tenant3 = new Tenant("Fido Odif", 42070842, "McDonalds Medarbejder", user4);
            tenant4 = new Tenant("Perle Elrep", 98769876, "Lærer", user5);


            // Adding
            admin.addRole(adminRole);
            user.addRole(userRole);
            user2.addRole(adminRole);
            user2.addRole(userRole);
            user3.addRole(userRole);
            user4.addRole(userRole);
            user5.addRole(userRole);

            tenant1.addRental(rental1);
            tenant2.addRental(rental1);
            tenant3.addRental(rental2);
            tenant4.addRental(rental3);

            tenant1.addHouse(house1);
            tenant2.addHouse(house1);
            tenant3.addHouse(house2);
            tenant4.addHouse(house3);

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

        } finally {
            udtoAdmin = new UserDTO(admin);
            udto = new UserDTO(user);
            udto2 = new UserDTO(user2);
            udto3 = new UserDTO(user3);
            udto4 = new UserDTO(user4);
            udto5 = new UserDTO(user5);

            hdto1 = new HouseDTO(house1);
            hdto2 = new HouseDTO(house2);
            hdto3 = new HouseDTO(house3);

            rdto1 = new RentalDTO(rental1);
            rdto2 = new RentalDTO(rental2);
            rdto3 = new RentalDTO(rental3);

            tdto1 = new TenantDTO(tenant1);
            tdto2 = new TenantDTO(tenant2);
            tdto3 = new TenantDTO(tenant3);
            tdto4 = new TenantDTO(tenant4);

            em.close();
        }
    }

    @AfterEach
    public void tearDown() {
        System.out.println("EXECUTION OF TEST DONE");
    }

    @Test
    void getAllHousesDTO() throws API_Exception {
        System.out.println("Testing getAllHousesDTO...");
        List<HouseDTO> actual = facade.getAllHouses();
        int expected = 3;
        assertEquals(expected, actual.size());
    }

    @Test
    void getHouseByID() throws API_Exception {
        System.out.println("Testing getHouseByIDDTO...");
        HouseDTO testHouse = facade.getHouseByID(hdto1.getHouseID());
        assertEquals(hdto1, testHouse);
    }

    @Test
    void createHouseDTOTest() throws API_Exception {
        System.out.println("Testing createHouseDTOTest...");
        HouseDTO newHouseDTO = new HouseDTO(new House("Testvej 7", "Testbyen", 4));
        facade.createHouse(newHouseDTO);
        int actualSize = facade.getAllHouses().size();
        assertEquals(4, actualSize);
    }

}
