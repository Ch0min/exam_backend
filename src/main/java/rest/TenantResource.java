package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dtofacades.TenantDTOFacade;
import dtos.HouseDTO;
import dtos.TenantDTO;
import errorhandling.API_Exception;
import utils.EMF_Creator;

import javax.persistence.EntityManagerFactory;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.nio.charset.StandardCharsets;

@Path("tenants")
public class TenantResource {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private TenantDTOFacade tenantDTOFacade = TenantDTOFacade.getInstance(EMF);
    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();


    @GET
    @Path("/all")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getAllTenants() throws API_Exception {
        return Response.ok().entity(GSON.toJson(tenantDTOFacade.getAllTenants()))
                .type(MediaType.APPLICATION_JSON_TYPE.withCharset(StandardCharsets.UTF_8.name())).build();
    }

    @GET
    @Path("/{houseID}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getTenantsByHouse(@PathParam("houseID") int houseID) throws API_Exception {
        return Response.ok().entity(GSON.toJson(tenantDTOFacade.getTenantsByHouse(houseID)))
                .type(MediaType.APPLICATION_JSON_TYPE.withCharset(StandardCharsets.UTF_8.name())).build();
    }

    @POST
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    public Response createTenant(String content) throws API_Exception {
        TenantDTO tenantDTO = GSON.fromJson(content, TenantDTO.class);
        TenantDTO newTenantDTO = tenantDTOFacade.createTenant(tenantDTO);
        return Response.ok().entity(GSON.toJson(newTenantDTO))
                .type(MediaType.APPLICATION_JSON_TYPE.withCharset(StandardCharsets.UTF_8.name())).build();
    }
}
