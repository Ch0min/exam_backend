package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dtos.HouseDTO;
import dtos.RentalDTO;
import dtos.TenantDTO;
import dtos.UserDTO;
import entities.*;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.parsing.Parser;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.EMF_Creator;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.core.UriBuilder;
import java.net.URI;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

//Disabled
public class LoginEndpointTest {

    private static final int SERVER_PORT = 7777;
    private static final String SERVER_URL = "http://localhost/api";

    Role userRole, adminRole;
    User admin, user, user_admin, user3, user4, user5;
    House house1, house2, house3;
    Rental rental1, rental2, rental3;
    Tenant tenant1, tenant2, tenant3, tenant4;

    UserDTO udtoAdmin, udto, udtoBoth, udto3, udto4, udto5;
    HouseDTO hdto1, hdto2, hdto3;
    RentalDTO rdto1, rdto2, rdto3;
    TenantDTO tdto1, tdto2, tdto3, tdto4;


    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    static final URI BASE_URI = UriBuilder.fromUri(SERVER_URL).port(SERVER_PORT).build();
    private static HttpServer httpServer;
    private static EntityManagerFactory emf;
    
    static HttpServer startServer() {
        ResourceConfig rc = ResourceConfig.forApplication(new ApplicationConfig());
        return GrizzlyHttpServerFactory.createHttpServer(BASE_URI, rc);
    }

    @BeforeAll
    public static void setUpClass() {
        //This method must be called before you request the EntityManagerFactory
        EMF_Creator.startREST_TestWithDB();
        emf = EMF_Creator.createEntityManagerFactoryForTest();

        httpServer = startServer();
        //Setup RestAssured
        RestAssured.baseURI = SERVER_URL;
        RestAssured.port = SERVER_PORT;
        RestAssured.defaultParser = Parser.JSON;
    }

    @AfterAll
    public static void closeTestServer() {
        //Don't forget this, if you called its counterpart in @BeforeAll
        EMF_Creator.endREST_TestWithDB();
        
        httpServer.shutdownNow();
    }

    // Setup the DataBase (used by the test-server and this test) in a known state BEFORE EACH TEST
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
            user_admin = new User("user_admin", "mark@gmail.com", "test123");
            user3 = new User("nick", "nick@gmail.com", "test123");
            user4 = new User("fido", "fido@gmail.com", "test123");
            user5 = new User("perle", "perle@gmail.com", "test123");

            house1 = new House("Lyngborgvej 3", "Kastrup", 5);
            house2 = new House("Kløvervej 7", "Kongens Lyngby", 3);
            house3 = new House("Tvingsager 10", "Hvidovre", 4);

            rental1 = new Rental("18-01-2023", "18-01-2030", 144000, 30000, "Susanne Lundgaard", house1);
            rental2 = new Rental("01-01-2020", "01-01-2025", 199000, 50000, "Briand Jensen", house2);
            rental3 = new Rental("31-12-2019", "31-12-2028", 122000, 25000, "Olfert Treflo", house3);

            tenant1 = new Tenant("Mark Lundgaard", 29842984, "Pakkemand", user_admin);
            tenant2 = new Tenant("Nick Jensen", 27332733, "Pædagog", user3);
            tenant3 = new Tenant("Fido Odif", 42070842, "McDonalds Medarbejder", user4);
            tenant4 = new Tenant("Perle Elrep", 98769876, "Lærer", user5);


            // Adding
            admin.addRole(adminRole);
            user.addRole(userRole);
            user_admin.addRole(adminRole);
            user_admin.addRole(userRole);
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
            em.persist(user_admin);
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
            udtoBoth = new UserDTO(user_admin);
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

    // This is how we hold on to the token after login, similar to that a client must store the token somewhere
    private static String securityToken;

    //Utility method to login and set the returned securityToken
    private static void login(String role, String password) {
        String json = String.format("{username: \"%s\", password: \"%s\"}", role, password);
        securityToken = given()
                .contentType("application/json")
                .body(json)
                //.when().post("/api/login")
                .when().post("/login")
                .then()
                .extract().path("token");
        //System.out.println("TOKEN ---> " + securityToken);
    }

    private void logOut() {
        securityToken = null;
    }

    @Test
    public void serverIsRunning() {
        given().when().get("/info").then().statusCode(200);
    }

    @Test
    public void testRestNoAuthenticationRequired() {
        given()
                .contentType("application/json")
                .when()
                .get("/info/").then()
                .statusCode(200)
                .body("msg", equalTo("Hello anonymous"));
    }

    @Test
    public void testRestForAdmin() {
        login("admin", "test123");
        given()
                .contentType("application/json")
                .accept(ContentType.JSON)
                .header("x-access-token", securityToken)
                .when()
                .get("/info/admin").then()
                .statusCode(200)
                .body("msg", equalTo("Hello to (admin) User: admin"));
    }

    @Test
    public void testRestForUser() {
        login("user", "test123");
        given()
                .contentType("application/json")
                .header("x-access-token", securityToken)
                .when()
                .get("/info/user").then()
                .statusCode(200)
                .body("msg", equalTo("Hello to User: user"));
    }

    @Test
    public void testAutorizedUserCannotAccessAdminPage() {
        login("user", "test123");
        given()
                .contentType("application/json")
                .header("x-access-token", securityToken)
                .when()
                .get("/info/admin").then() //Call Admin endpoint as user
                .statusCode(401);
    }

    @Test
    public void testAutorizedAdminCannotAccessUserPage() {
        login("admin", "test123");
        given()
                .contentType("application/json")
                .header("x-access-token", securityToken)
                .when()
                .get("/info/user").then() //Call User endpoint as Admin
                .statusCode(401);
    }

    @Test
    public void testRestForMultiRole1() {
        login("user_admin", "test123");
        given()
                .contentType("application/json")
                .accept(ContentType.JSON)
                .header("x-access-token", securityToken)
                .when()
                .get("/info/admin").then()
                .statusCode(200)
                .body("msg", equalTo("Hello to (admin) User: user_admin"));
    }

    @Test
    public void testRestForMultiRole2() {
        login("user_admin", "test123");
        given()
                .contentType("application/json")
                .header("x-access-token", securityToken)
                .when()
                .get("/info/user").then()
                .statusCode(200)
                .body("msg", equalTo("Hello to User: user_admin"));
    }

    @Test
    public void userNotAuthenticated() {
        logOut();
        given()
                .contentType("application/json")
                .when()
                .get("/info/user").then()
                .statusCode(403)
                .body("code", equalTo(403))
                .body("message", equalTo("Not authenticated - do login"));
    }

    @Test
    public void adminNotAuthenticated() {
        logOut();
        given()
                .contentType("application/json")
                .when()
                .get("/info/user").then()
                .statusCode(403)
                .body("code", equalTo(403))
                .body("message", equalTo("Not authenticated - do login"));
    }

}
