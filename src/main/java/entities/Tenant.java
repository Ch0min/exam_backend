package entities;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@NamedQuery(name = "Tenant.deleteAllRows", query = "DELETE from Tenant")
@Table(name = "tenant")
public class Tenant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tenant_id", nullable = false)
    private Integer tenantID;

    @Column(name = "tenant_name", nullable = false)
    private String tenantName;

    @Column(name = "tenant_phone", nullable = false)
    private Integer tenantPhone;

    @Column(name = "tenant_job", nullable = false)
    private String tenantJob;

    @OneToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name="user_name", referencedColumnName = "user_name")
    private User user;

    @ManyToMany
    @JoinTable(name = "tenant_rental",
            joinColumns = @JoinColumn(name = "tenant_id"),
            inverseJoinColumns = @JoinColumn(name = "rental_id"))
    private List<Rental> rentals = new ArrayList<>();

    @ManyToMany
    @JoinTable(name = "tenant_house",
            joinColumns = @JoinColumn(name = "tenant_id"),
            inverseJoinColumns = @JoinColumn(name = "house_id"))
    private List<House> houses = new ArrayList<>();

    public Tenant() {
    }

    public Tenant(String tenantName, Integer tenantPhone, String tenantJob) {
        this.tenantName = tenantName;
        this.tenantPhone = tenantPhone;
        this.tenantJob = tenantJob;
    }

    public Tenant(String tenantName, Integer tenantPhone, String tenantJob, User user) {
        this.tenantName = tenantName;
        this.tenantPhone = tenantPhone;
        this.tenantJob = tenantJob;
        this.user = user;
    }

    public Tenant(Integer tenantID, String tenantName, Integer tenantPhone, String tenantJob, User user, List<Rental> rentals, List<House> houses) {
        this.tenantID = tenantID;
        this.tenantName = tenantName;
        this.tenantPhone = tenantPhone;
        this.tenantJob = tenantJob;
        this.user = user;
        this.rentals = rentals;
        this.houses = houses;
    }

    public List<String> getRentalsAsStrings(){
        if(rentals.isEmpty()) {
            return null;
        }
        List<String> rentalsAsStrings = new ArrayList<>();
        rentals.forEach((r ->{
            rentalsAsStrings.add(r.getRentalStartDate());
            rentalsAsStrings.add(r.getRentalEndDate());
            rentalsAsStrings.add(String.valueOf(r.getRentalPriceAnnual()));
            rentalsAsStrings.add(String.valueOf(r.getRentalDeposit()));
            rentalsAsStrings.add(r.getRentalContactPerson());
            rentalsAsStrings.add(String.valueOf(r.getHouse().getHouseID()));
        }));
        return rentalsAsStrings;
    }

    public List<String> getHousesAsStrings(){
        if(houses.isEmpty()) {
            return null;
        }
        List<String> housesAsStrings = new ArrayList<>();
        houses.forEach((h ->{
            housesAsStrings.add(h.getHouseAddress());
            housesAsStrings.add(h.getHouseCity());
            housesAsStrings.add(String.valueOf(h.getNumberOfRooms()));
        }));
        return housesAsStrings;
    }

    public Integer getTenantID() {
        return tenantID;
    }

    public void setTenantID(Integer tenantID) {
        this.tenantID = tenantID;
    }

    public String getTenantName() {
        return tenantName;
    }

    public void setTenantName(String tenantName) {
        this.tenantName = tenantName;
    }

    public Integer getTenantPhone() {
        return tenantPhone;
    }

    public void setTenantPhone(Integer tenantPhone) {
        this.tenantPhone = tenantPhone;
    }

    public String getTenantJob() {
        return tenantJob;
    }

    public void setTenantJob(String tenantJob) {
        this.tenantJob = tenantJob;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Rental> getRentals() {
        return rentals;
    }

    public void setRentals(List<Rental> rentals) {
        this.rentals = rentals;
    }

    public List<House> getHouses() {
        return houses;
    }

    public void setHouses(List<House> houses) {
        this.houses = houses;
    }

    public void addRental(Rental tenantRental) {
        rentals.add(tenantRental);
    }

    public void addHouse(House tenantHouse) {
        houses.add(tenantHouse);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Tenant)) return false;
        Tenant tenant = (Tenant) o;
        return getTenantID().equals(tenant.getTenantID());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTenantID());
    }

    @Override
    public String toString() {
        return "Tenant{" +
                "tenantID=" + tenantID +
                ", tenantName='" + tenantName + '\'' +
                ", tenantPhone=" + tenantPhone +
                ", tenantJob='" + tenantJob + '\'' +
                ", user=" + user +
                ", rentals=" + rentals +
                ", houses=" + houses +
                '}';
    }
}
