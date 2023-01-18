package dtofacades;

import datafacades.RentalFacade;
import dtos.RentalDTO;
import dtos.TenantDTO;
import errorhandling.API_Exception;

import javax.persistence.EntityManagerFactory;
import java.util.List;

public class RentalDTOFacade {
    private static RentalFacade rentalFacade;
    private static RentalDTOFacade instance;

    public RentalDTOFacade() {
    }

    public static RentalDTOFacade getInstance(EntityManagerFactory _emf) {
        if (instance == null) {
            rentalFacade = RentalFacade.getRentalFacade(_emf);
            instance = new RentalDTOFacade();
        }
        return instance;
    }

    public List<RentalDTO> getAllRentals() throws API_Exception {
        return RentalDTO.getRentalDTOs(rentalFacade.getAllRentals());
    }

    public List<RentalDTO> getRentalsByTenant(String tenatUserName) throws API_Exception {
        return RentalDTO.getRentalDTOs(rentalFacade.getRentalsByTenant(tenatUserName));
    }

    public RentalDTO createRental(RentalDTO rentalDTO) throws API_Exception {
        return new RentalDTO(rentalFacade.createRental(rentalDTO.getEntity()));
    }

    public RentalDTO assignRentalToHouse(int rentalID, int houseID) throws API_Exception {
        return new RentalDTO(rentalFacade.assignRentalToHouse(rentalID, houseID));
    }

    public RentalDTO assignRentalToTenant(int rentalID, int tenantID) throws API_Exception {
        return new RentalDTO(rentalFacade.assignRentalToTenant(rentalID, tenantID));
    }

    public RentalDTO updateRental(RentalDTO rentalDTO) throws API_Exception {
        return new RentalDTO(rentalFacade.updateRental(rentalDTO.getEntity()));
    }

    public RentalDTO deleteRental(int rentalID) throws API_Exception {
        return new RentalDTO(rentalFacade.deleteRental(rentalID));
    }
}
