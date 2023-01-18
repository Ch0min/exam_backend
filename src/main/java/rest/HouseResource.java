package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dtofacades.HouseDTOFacade;
import dtos.HouseDTO;
import errorhandling.API_Exception;
import utils.EMF_Creator;

import javax.persistence.EntityManagerFactory;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.nio.charset.StandardCharsets;

@Path("houses")
public class HouseResource {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private HouseDTOFacade houseDTOFacade = HouseDTOFacade.getInstance(EMF);
    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();


    @GET
    @Path("/all")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getAllHouses() throws API_Exception {
        return Response.ok().entity(GSON.toJson(houseDTOFacade.getAllHouses()))
                .type(MediaType.APPLICATION_JSON_TYPE.withCharset(StandardCharsets.UTF_8.name())).build();
    }

    @GET
    @Path("/{houseID}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getHouseByID(@PathParam("houseID") int houseID) throws API_Exception {
        return Response.ok().entity(GSON.toJson(houseDTOFacade.getHouseByID(houseID)))
                .type(MediaType.APPLICATION_JSON_TYPE.withCharset(StandardCharsets.UTF_8.name())).build();
    }

    @POST
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    public Response createHouse(String content) throws API_Exception {
        HouseDTO houseDTO = GSON.fromJson(content, HouseDTO.class);
        HouseDTO newHouseDTO = houseDTOFacade.createHouse(houseDTO);
        return Response.ok().entity(GSON.toJson(newHouseDTO))
                .type(MediaType.APPLICATION_JSON_TYPE.withCharset(StandardCharsets.UTF_8.name())).build();
    }

}
