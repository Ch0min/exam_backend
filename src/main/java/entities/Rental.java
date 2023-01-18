package entities;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@NamedQuery(name = "Rental.deleteAllRows", query = "DELETE from Rental")
@Table(name = "rental")
public class Rental {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rental_id", nullable = false)
    private Integer rentalID;

    @Column(name = "rental_start_date", nullable = false)
    private String rentalStartDate;

    @Column(name = "rental_end_date", nullable = false)
    private String rentalEndDate;

    @Column(name = "rental_price_annual", nullable = false)
    private Integer rentalPriceAnnual;

    @Column(name = "rental_deposit", nullable = false)
    private Integer rentalDeposit;

    @Column(name = "rental_contact_person", nullable = false)
    private String rentalContactPerson;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "house_id", nullable = false)
    private House house;

    @ManyToMany
    @JoinTable(name = "tenant_rental",
            joinColumns = @JoinColumn(name = "rental_id"),
            inverseJoinColumns = @JoinColumn(name = "tenant_id"))
    private List<Tenant> tenants = new ArrayList<>();

    public Rental() {
    }

    public Rental(String rentalStartDate, String rentalEndDate, Integer rentalPriceAnnual, Integer rentalDeposit, String rentalContactPerson, House house) {
        this.rentalStartDate = rentalStartDate;
        this.rentalEndDate = rentalEndDate;
        this.rentalPriceAnnual = rentalPriceAnnual;
        this.rentalDeposit = rentalDeposit;
        this.rentalContactPerson = rentalContactPerson;
        this.house = house;
    }

    public Rental(Integer rentalID, String rentalStartDate, String rentalEndDate, Integer rentalPriceAnnual, Integer rentalDeposit, String rentalContactPerson, House house, List<Tenant> tenants) {
        this.rentalID = rentalID;
        this.rentalStartDate = rentalStartDate;
        this.rentalEndDate = rentalEndDate;
        this.rentalPriceAnnual = rentalPriceAnnual;
        this.rentalDeposit = rentalDeposit;
        this.rentalContactPerson = rentalContactPerson;
        this.house = house;
        this.tenants = tenants;
    }

    public Integer getRentalID() {
        return rentalID;
    }

    public void setRentalID(Integer rentalID) {
        this.rentalID = rentalID;
    }

    public String getRentalStartDate() {
        return rentalStartDate;
    }

    public void setRentalStartDate(String rentalStartDate) {
        this.rentalStartDate = rentalStartDate;
    }

    public String getRentalEndDate() {
        return rentalEndDate;
    }

    public void setRentalEndDate(String rentalEndDate) {
        this.rentalEndDate = rentalEndDate;
    }

    public Integer getRentalPriceAnnual() {
        return rentalPriceAnnual;
    }

    public void setRentalPriceAnnual(Integer rentalPriceAnnual) {
        this.rentalPriceAnnual = rentalPriceAnnual;
    }

    public Integer getRentalDeposit() {
        return rentalDeposit;
    }

    public void setRentalDeposit(Integer rentalDeposit) {
        this.rentalDeposit = rentalDeposit;
    }

    public String getRentalContactPerson() {
        return rentalContactPerson;
    }

    public void setRentalContactPerson(String rentalcontactPerson) {
        this.rentalContactPerson = rentalcontactPerson;
    }

    public House getHouse() {
        return house;
    }

    public void setHouse(House house) {
        this.house = house;
    }

    public List<Tenant> getTenants() {
        return tenants;
    }

    public void setTenants(List<Tenant> tenants) {
        this.tenants = tenants;
    }

    public void assignHouse(House house) {
        if (house != null) {
            this.house = house;
            house.getRentals().add(this);
        }
    }

    public void addTenant(Tenant rentalTenant) {
        tenants.add(rentalTenant);
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Rental)) return false;
        Rental rental = (Rental) o;
        return getRentalID().equals(rental.getRentalID());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getRentalID());
    }

    @Override
    public String toString() {
        return "Rental{" +
                "rentalID=" + rentalID +
                ", rentalStartDate='" + rentalStartDate + '\'' +
                ", rentalEndDate='" + rentalEndDate + '\'' +
                ", rentalPriceAnnual='" + rentalPriceAnnual + '\'' +
                ", rentalDeposit='" + rentalDeposit + '\'' +
                ", rentalcontactPerson='" + rentalContactPerson + '\'' +
                ", house=" + house +
                ", tenants=" + tenants +
                '}';
    }
}
