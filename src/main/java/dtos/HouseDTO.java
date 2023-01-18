package dtos;

import entities.House;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class HouseDTO {
    private int houseID;
    private String houseAddress;
    private String houseCity;
    private int numberOfRooms;
    private List<String> rentals;

    public HouseDTO(House house) {
        if (house.getHouseID() != null) {
            this.houseID = house.getHouseID();
        }
        this.houseAddress = house.getHouseAddress();
        this.houseCity = house.getHouseCity();
        this.numberOfRooms = house.getNumberOfRooms();
        this.rentals = house.getRentalsAsStrings();
    }

    public House getEntity() {
        House house = new House();
        if (this.houseID != 0) {
            house.setHouseID(this.houseID);
        }
        house.setHouseAddress(this.houseAddress);
        house.setHouseCity(this.houseCity);
        house.setNumberOfRooms(this.numberOfRooms);
        house.getRentalsAsStrings();

        return house;
    }

    public static List<HouseDTO> getHouseDTOs(List<House> houses){
        List<HouseDTO> houseDTOs = new ArrayList<>();
        houses.forEach(house -> houseDTOs.add(new HouseDTO(house)));

        return houseDTOs;
    }

    public int getHouseID() {
        return houseID;
    }

    public void setHouseID(int houseID) {
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

    public int getNumberOfRooms() {
        return numberOfRooms;
    }

    public void setNumberOfRooms(int numberOfRooms) {
        this.numberOfRooms = numberOfRooms;
    }

    public List<String> getRentals() {
        return rentals;
    }

    public void setRentals(List<String> rentals) {
        this.rentals = rentals;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof HouseDTO)) return false;
        HouseDTO houseDTO = (HouseDTO) o;
        return getHouseID() == houseDTO.getHouseID();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getHouseID());
    }

    @Override
    public String toString() {
        return "HouseDTO{" +
                "houseID=" + houseID +
                ", houseAddress='" + houseAddress + '\'' +
                ", houseCity='" + houseCity + '\'' +
                ", numberOfRooms=" + numberOfRooms +
                ", rentals=" + rentals +
                '}';
    }
}
