package dtofacades;

import datafacades.TenantFacade;
import dtos.HouseDTO;
import dtos.TenantDTO;
import errorhandling.API_Exception;

import javax.persistence.EntityManagerFactory;
import java.util.List;

public class TenantDTOFacade {
    private static TenantFacade tenantFacade;
    private static TenantDTOFacade instance;

    public TenantDTOFacade() {
    }

    public static TenantDTOFacade getInstance(EntityManagerFactory _emf) {
        if (instance == null) {
            tenantFacade = TenantFacade.getTenantFacade(_emf);
            instance = new TenantDTOFacade();
        }
        return instance;
    }

    public List<TenantDTO> getAllTenants() throws API_Exception {
        return TenantDTO.getTenantDTOs(tenantFacade.getAllTenants());
    }

    public TenantDTO getTenantByID(int tenantID) throws API_Exception {
        return new TenantDTO(tenantFacade.getTenantByID(tenantID));
    }

    public List<TenantDTO> getTenantsByHouse(int houseID) throws API_Exception {
        return TenantDTO.getTenantDTOs(tenantFacade.getTenantsByHouse(houseID));
    }

    public TenantDTO createTenant(TenantDTO tenantDTO) throws API_Exception {
        return new TenantDTO(tenantFacade.createTenant(tenantDTO.getEntity()));
    }
}
