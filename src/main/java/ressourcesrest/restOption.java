package ressourcesrest;

import entities.Option;
import metiers.OptionBusiness;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * Ressource REST "Option"  ->  /rest/options
 */
@Path("options")
public class restOption {

    // Les donnees sont stockees dans des listes statiques (en memoire) :
    // peu importe que JAX-RS cree une nouvelle instance de cette classe a chaque requete.
    private final OptionBusiness optB = new OptionBusiness();

    // 1) POST /options : creation d'une option
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addOption(Option option) {
        if (option == null) {
            return Response.status(Response.Status.NOT_FOUND).build(); // 404 (selon l'enonce)
        }
        optB.addOption(option);
        return Response.status(Response.Status.OK).entity(option).build(); // 200
    }

    // 2) GET /options                      : toutes les options
    // 3) GET /options?domaine=Mathématiques : options d'un domaine
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getOptions(@QueryParam("domaine") String domaine) {
        List<Option> liste;
        if (domaine == null || domaine.trim().isEmpty()) {
            liste = optB.getListeOptions();
        } else {
            liste = optB.getOptionsByDomaine(domaine);
        }
        return Response.status(Response.Status.OK).entity(liste).build(); // 200
    }

    // 6) GET /options/{code} : une option par son code
    @GET
    @Path("{code}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getOptionByCode(@PathParam("code") int code) {
        Option option = optB.getOptionByCode(code);
        if (option == null) {
            return Response.status(Response.Status.NOT_FOUND).build(); // 404
        }
        return Response.status(Response.Status.OK).entity(option).build(); // 200
    }

    // 5) PUT /options/{code} : modification d'une option
    @PUT
    @Path("{code}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateOption(@PathParam("code") int code, Option option) {
        if (option == null || !optB.updateOption(code, option)) {
            return Response.status(Response.Status.NOT_FOUND).build(); // 404
        }
        return Response.status(Response.Status.OK).entity(optB.getOptionByCode(code)).build(); // 200
    }

    // 4) DELETE /options/{code} : suppression d'une option
    @DELETE
    @Path("{code}")
    public Response deleteOption(@PathParam("code") int code) {
        if (optB.deleteOption(code)) {
            return Response.status(Response.Status.NO_CONTENT).build(); // 204
        }
        return Response.status(Response.Status.NOT_FOUND).build(); // 404
    }
}
