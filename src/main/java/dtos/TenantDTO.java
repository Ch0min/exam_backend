package dtos;

import entities.Tenant;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class TenantDTO {
    private int tenantID;
    private String tenantName;
    private int tenantPhone;
    private String tenantJob;
    private UserDTO user;
    private List<String> rentals;


    public TenantDTO(Tenant tenant){
        if(tenant.getTenantID() != null){
            this.tenantID = tenant.getTenantID();
        }
        this.tenantName = tenant.getTenantName();
        this.tenantPhone = tenant.getTenantPhone();
        this.tenantJob = tenant.getTenantJob();
        this.user = new UserDTO(tenant.getUser());
        this.rentals = tenant.getRentalsAsStrings();
    }

    public Tenant getEntity() {
        Tenant tenant = new Tenant();
        if (this.tenantID > 0) {
            tenant.setTenantID(this.tenantID);
        }
        tenant.setTenantName(this.tenantName);
        tenant.setTenantPhone(this.tenantPhone);
        tenant.setTenantJob(this.tenantJob);
        tenant.setUser(this.user.getEntity());
        tenant.getRentalsAsStrings();

        return tenant;
    }

    public static List<TenantDTO> getTenantDTOs(List<Tenant> tenants){
        List<TenantDTO> tenantDTOs = new ArrayList<>();
        tenants.forEach(tenant -> tenantDTOs.add(new TenantDTO(tenant)));
        return tenantDTOs;
    }

    public int getTenantID() {
        return tenantID;
    }

    public void setTenantID(int tenantID) {
        this.tenantID = tenantID;
    }

    public String getTenantName() {
        return tenantName;
    }

    public void setTenantName(String tenantName) {
        this.tenantName = tenantName;
    }

    public int getTenantPhone() {
        return tenantPhone;
    }

    public void setTenantPhone(int tenantPhone) {
        this.tenantPhone = tenantPhone;
    }

    public String getTenantJob() {
        return tenantJob;
    }

    public void setTenantJob(String tenantJob) {
        this.tenantJob = tenantJob;
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
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
        if (!(o instanceof TenantDTO)) return false;
        TenantDTO tenantDTO = (TenantDTO) o;
        return getTenantID() == tenantDTO.getTenantID();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTenantID());
    }

    @Override
    public String toString() {
        return "TenantDTO{" +
                "tenantID=" + tenantID +
                ", tenantName='" + tenantName + '\'' +
                ", tenantPhone=" + tenantPhone +
                ", tenantJob='" + tenantJob + '\'' +
                ", user=" + user +
                ", rentals=" + rentals +
                '}';
    }
}
