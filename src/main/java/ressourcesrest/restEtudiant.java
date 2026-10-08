package ressourcesrest;

import entities.Etudiant;
import entities.EtudiantList;
import entities.Option;
import metiers.EtudiantBusiness;
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

/**
 * Ressource REST "Etudiant"  ->  /rest/etudiants
 */
@Path("etudiants")
public class restEtudiant {

    private final EtudiantBusiness etuB = new EtudiantBusiness();
    private final OptionBusiness optB = new OptionBusiness();

    // 1) POST /etudiants : creation d'un etudiant (404 si l'option indiquee n'existe pas)
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addEtudiant(Etudiant etudiant) {
        if (etudiant == null || etudiant.getOption() == null) {
            return Response.status(Response.Status.BAD_REQUEST).build(); // 400 : corps invalide
        }
        if (!etuB.addEtudiant(etudiant)) {
            return Response.status(Response.Status.NOT_FOUND).build(); // 404 : option inexistante
        }
        return Response.status(Response.Status.OK).entity(etudiant).build(); // 200
    }

    // 2) GET /etudiants : tous les etudiants
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllEtudiants() {
        return Response.status(Response.Status.OK).entity(etuB.getAllEtudiants()).build(); // 200
    }

    // 6) GET /etudiants/option?codeOption=1 : etudiants d'une option, en XML
    //    (chemin litteral "option" : JAX-RS le prefere a "{identifiant}")
    @GET
    @Path("option")
    @Produces(MediaType.APPLICATION_XML)
    public Response getEtudiantsByOption(@QueryParam("codeOption") int codeOption) {
        Option option = optB.getOptionByCode(codeOption);
        if (option == null) {
            return Response.status(Response.Status.NOT_FOUND).build(); // 404 : option inexistante
        }
        EtudiantList liste = new EtudiantList(etuB.getEtudiantsByOption(option));
        return Response.status(Response.Status.OK).entity(liste).build(); // 200 (XML)
    }

    // 3) GET /etudiants/{identifiant} : un etudiant
    @GET
    @Path("{identifiant}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getEtudiantByIdentifiant(@PathParam("identifiant") String identifiant) {
        Etudiant etudiant = etuB.getEtudiantByIdentifiant(identifiant);
        if (etudiant == null) {
            return Response.status(Response.Status.NOT_FOUND).build(); // 404
        }
        return Response.status(Response.Status.OK).entity(etudiant).build(); // 200
    }

    // 5) PUT /etudiants/{identifiant} : modification (404 si etudiant ou option inexistant)
    @PUT
    @Path("{identifiant}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateEtudiant(@PathParam("identifiant") String identifiant, Etudiant etudiant) {
        if (etudiant == null || etudiant.getOption() == null) {
            return Response.status(Response.Status.BAD_REQUEST).build(); // 400 : corps invalide
        }
        Option option = optB.getOptionByCode(etudiant.getOption().getCodeOption());
        if (option == null) {
            return Response.status(Response.Status.NOT_FOUND).build(); // 404 : option inexistante
        }
        etudiant.setIdentifiant(identifiant); // l'identifiant de l'URL fait foi
        etudiant.setOption(option);           // on rattache l'option reelle (celle du serveur)
        if (!etuB.updateEtudiant(identifiant, etudiant)) {
            return Response.status(Response.Status.NOT_FOUND).build(); // 404 : etudiant inexistant
        }
        return Response.status(Response.Status.OK).entity(etudiant).build(); // 200
    }

    // 4) DELETE /etudiants/{identifiant} : suppression
    @DELETE
    @Path("{identifiant}")
    public Response deleteEtudiant(@PathParam("identifiant") String identifiant) {
        if (etuB.deleteEtudiant(identifiant)) {
            return Response.status(Response.Status.NO_CONTENT).build(); // 204
        }
        return Response.status(Response.Status.NOT_FOUND).build(); // 404
    }
}
