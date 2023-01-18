package entities;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@NamedQuery(name = "House.deleteAllRows", query = "DELETE from House")
@Table(name = "house")
public class House {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "house_id", nullable = false)
    private Integer houseID;

    @Column(name = "house_address", nullable = false)
    private String houseAddress;

    @Column(name = "house_city", nullable = false)
    private String houseCity;

    @Column(name = "number_of_rooms", nullable = false)
    private Integer numberOfRooms;

    @OneToMany(mappedBy = "house", cascade = CascadeType.ALL)
    private List<Rental> rentals = new ArrayList<>();

    @ManyToMany
    @JoinTable(name = "tenant_house",
            joinColumns = @JoinColumn(name = "house_id"),
            inverseJoinColumns = @JoinColumn(name = "tenant_id"))
    private List<Tenant> tenants = new ArrayList<>();

    public House() {
    }

    public House(String houseAddress, String houseCity, Integer numberOfRooms) {
        this.houseAddress = houseAddress;
        this.houseCity = houseCity;
        this.numberOfRooms = numberOfRooms;
    }

    public House(String houseAddress, String houseCity, Integer numberOfRooms, List<Rental> rentals) {
        this.houseAddress = houseAddress;
        this.houseCity = houseCity;
        this.numberOfRooms = numberOfRooms;
        this.rentals = rentals;
    }

    public House(Integer houseID, String houseAddress, String houseCity, Integer numberOfRooms, List<Rental> rentals) {
        this.houseID = houseID;
        this.houseAddress = houseAddress;
        this.houseCity = houseCity;
        this.numberOfRooms = numberOfRooms;
        this.rentals = rentals;
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

    public Integer getHouseID() {
        return houseID;
    }

    public void setHouseID(Integer houseID) {
        this.houseID = houseID;
    }

    public String getHouseAddress() {
        return houseAddress;
    }

    public void setHouseAddress(String houseAddress) {
        this.houseAddress = houseAddress;
    }

    public String getHouseCity() {
        return houseCity;
    }

    public void setHouseCity(String houseCity) {
        this.houseCity = houseCity;
    }

    public Integer getNumberOfRooms() {
        return numberOfRooms;
    }

    public void setNumberOfRooms(Integer numberOfRooms) {
        this.numberOfRooms = numberOfRooms;
    }

    public List<Rental> getRentals() {
        return rentals;
    }

    public void setRentals(List<Rental> rentals) {
        this.rentals = rentals;
    }

    public List<Tenant> getTenants() {
        return tenants;
    }

    public void setTenants(List<Tenant> tenants) {
        this.tenants = tenants;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof House)) return false;
        House house = (House) o;
        return getHouseID().equals(house.getHouseID());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getHouseID());
    }

    @Override
    public String toString() {
        return "House{" +
                "houseID=" + houseID +
                ", houseAddress='" + houseAddress + '\'' +
                ", houseCity='" + houseCity + '\'' +
                ", numberOfRooms=" + numberOfRooms +
                ", rentals=" + rentals +
                ", tenants=" + tenants +
                '}';
    }
}
