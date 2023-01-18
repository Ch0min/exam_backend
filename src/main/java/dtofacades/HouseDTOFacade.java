package dtofacades;

import datafacades.HouseFacade;
import dtos.HouseDTO;
import errorhandling.API_Exception;

import javax.persistence.EntityManagerFactory;
import java.util.List;

public class HouseDTOFacade {
    private static HouseFacade houseFacade;
    private static HouseDTOFacade instance;

    public HouseDTOFacade() {
    }

    public static HouseDTOFacade getInstance(EntityManagerFactory _emf) {
        if (instance == null) {
            houseFacade = HouseFacade.getHouseFacade(_emf);
            instance = new HouseDTOFacade();
        }
        return instance;
    }

    public List<HouseDTO> getAllHouses() throws API_Exception {
        return HouseDTO.getHouseDTOs(houseFacade.getAllHouses());
    }

    public HouseDTO getHouseByID(int houseID) throws API_Exception {
        return new HouseDTO(houseFacade.getHouseByID(houseID));
    }

    public HouseDTO createHouse(HouseDTO houseDTO) throws API_Exception {
        return new HouseDTO(houseFacade.createHouse(houseDTO.getEntity()));
    }
}
