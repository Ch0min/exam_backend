package dtos;

import entities.Rental;
import entities.Tenant;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class RentalDTO {
    private int rentalID;
    private String rentalStartDate;
    private String rentalEndDate;
    private int rentalPriceAnnual;
    private int rentalDeposit;
    private String rentalContactPerson;
    private HouseDTO house;
    private List<TenantDTO> tenants = new ArrayList<>();

    public RentalDTO(Rental rental){
        if(rental.getRentalID() != null){
            this.rentalID = rental.getRentalID();
        }
        this.rentalStartDate = rental.getRentalStartDate();
        this.rentalEndDate = rental.getRentalEndDate();
        this.rentalPriceAnnual = rental.getRentalPriceAnnual();
        this.rentalDeposit = rental.getRentalDeposit();
        this.rentalContactPerson = rental.getRentalContactPerson();
        this.house = new HouseDTO(rental.getHouse());
        if (rental.getTenants().size() > 0) {
            rental.getTenants().forEach((tenant -> {
                tenants.add(new TenantDTO(tenant));
            }));
        }
    }

    public Rental getEntity() {
        Rental rental = new Rental();
        if (this.rentalID > 0) {
            rental.setRentalID(this.rentalID);
        }
        rental.setRentalStartDate(this.rentalStartDate);
        rental.setRentalEndDate(this.rentalEndDate);
        rental.setRentalPriceAnnual(this.rentalPriceAnnual);
        rental.setRentalDeposit(this.rentalDeposit);
        rental.setRentalContactPerson(this.rentalContactPerson);
        rental.setHouse(this.house.getEntity());
        if(this.tenants != null){
            List<Tenant> tenantsList = new ArrayList<>();
            this.tenants.forEach(tenantDTO -> tenantsList.add(tenantDTO.getEntity()));
            rental.setTenants(tenantsList);
        }
        return rental;
    }

    public static List<RentalDTO> getRentalDTOs(List<Rental> rentals){
        List<RentalDTO> rentalDTOs = new ArrayList<>();
        rentals.forEach(rental -> rentalDTOs.add(new RentalDTO(rental)));

        return rentalDTOs;
    }

    public int getRentalID() {
        return rentalID;
    }

    public void setRentalID(int rentalID) {
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

    public int getRentalPriceAnnual() {
        return rentalPriceAnnual;
    }

    public void setRentalPriceAnnual(int rentalPriceAnnual) {
        this.rentalPriceAnnual = rentalPriceAnnual;
    }

    public int getRentalDeposit() {
        return rentalDeposit;
    }

    public void setRentalDeposit(int rentalDeposit) {
        this.rentalDeposit = rentalDeposit;
    }

    public String getRentalContactPerson() {
        return rentalContactPerson;
    }

    public void setRentalContactPerson(String rentalContactPerson) {
        this.rentalContactPerson = rentalContactPerson;
    }

    public HouseDTO getHouse() {
        return house;
    }

    public void setHouse(HouseDTO house) {
        this.house = house;
    }

    public List<TenantDTO> getTenants() {
        return tenants;
    }

    public void setTenants(List<TenantDTO> tenants) {
        this.tenants = tenants;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RentalDTO)) return false;
        RentalDTO rentalDTO = (RentalDTO) o;
        return getRentalID() == rentalDTO.getRentalID();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getRentalID());
    }

    @Override
    public String toString() {
        return "RentalDTO{" +
                "rentalID=" + rentalID +
                ", rentalStartDate='" + rentalStartDate + '\'' +
                ", rentalEndDate='" + rentalEndDate + '\'' +
                ", rentalPriceAnnual=" + rentalPriceAnnual +
                ", rentalDeposit=" + rentalDeposit +
                ", rentalContactPerson='" + rentalContactPerson + '\'' +
                ", house=" + house +
                ", tenants=" + tenants +
                '}';
    }
}
