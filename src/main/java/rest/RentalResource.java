package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dtofacades.RentalDTOFacade;
import dtos.RentalDTO;
import errorhandling.API_Exception;
import utils.EMF_Creator;

import javax.persistence.EntityManagerFactory;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.nio.charset.StandardCharsets;

@Path("rentals")
public class RentalResource {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private RentalDTOFacade rentalDTOFacade = RentalDTOFacade.getInstance(EMF);
    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();


    @GET
    @Path("/all")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getAllRentals() throws API_Exception {
        return Response.ok().entity(GSON.toJson(rentalDTOFacade.getAllRentals()))
                .type(MediaType.APPLICATION_JSON_TYPE.withCharset(StandardCharsets.UTF_8.name())).build();
    }

    @POST
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    public Response createRental(String content) throws API_Exception {
        RentalDTO rentalDTO = GSON.fromJson(content, RentalDTO.class);
        RentalDTO newRentalDTO = rentalDTOFacade.createRental(rentalDTO);
        return Response.ok().entity(GSON.toJson(newRentalDTO))
                .type(MediaType.APPLICATION_JSON_TYPE.withCharset(StandardCharsets.UTF_8.name())).build();
    }

    @POST
    @Path("/assign/{rentalID}/{houseID}")
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    public Response assignRentalToHouse(@PathParam("rentalID") int rentalID,
                                        @PathParam("houseID") int houseID) throws API_Exception {
        return Response.ok().entity(GSON.toJson(rentalDTOFacade.assignRentalToHouse(rentalID, houseID)))
                .type(MediaType.APPLICATION_JSON_TYPE.withCharset(StandardCharsets.UTF_8.name())).build();
    }

    @PUT
    @Path("/update/{rentalID}")
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    public Response updateBoat(@PathParam("rentalID") int rentalID, String content) throws API_Exception {
        RentalDTO rentalDTO = GSON.fromJson(content, RentalDTO.class);
        rentalDTO.setRentalID(rentalID);
        RentalDTO updatedRentalDTO = rentalDTOFacade.updateRental(rentalDTO);
        return Response.ok().entity(GSON.toJson(updatedRentalDTO))
                .type(MediaType.APPLICATION_JSON_TYPE.withCharset(StandardCharsets.UTF_8.name())).build();
    }

    @DELETE
    @Path("{rentalID}")
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    public Response deleteBoat(@PathParam("rentalID") int rentalID) throws API_Exception {
        RentalDTO deletedRental = rentalDTOFacade.deleteRental(rentalID);
        return Response.ok().entity(GSON.toJson(deletedRental))
                .type(MediaType.APPLICATION_JSON_TYPE.withCharset(StandardCharsets.UTF_8.name())).build();
    }

}
