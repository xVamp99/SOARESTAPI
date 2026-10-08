package utilities;

import com.fasterxml.jackson.databind.ObjectMapper;

import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.Provider;

/**
 * Configure Jackson (JSON) pour qu'il IGNORE les annotations JAXB (@XmlTransient...).
 *
 * Pourquoi ? Dans Etudiant, getOption() est annote @XmlTransient pour que l'option
 * n'apparaisse PAS dans le XML (GET /etudiants/option). Mais par defaut Jackson respecte
 * aussi cette annotation, et l'option disparaissait donc du JSON (GET /etudiants,
 * POST /etudiants...). Avec ce provider :
 *   - JSON  -> l'option est presente (comme demande dans l'atelier)
 *   - XML   -> l'option est absente (comme demande dans l'atelier)
 */
@Provider
public class JacksonConfig implements ContextResolver<ObjectMapper> {

    private final ObjectMapper mapper = new ObjectMapper(); // annotations Jackson uniquement

    @Override
    public ObjectMapper getContext(Class<?> type) {
        return mapper;
    }
}
