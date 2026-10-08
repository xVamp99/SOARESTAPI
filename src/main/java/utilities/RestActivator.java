package utilities;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

/**
 * Active JAX-RS : toutes les ressources REST sont accessibles sous
 * http://localhost:8080/<nom-du-projet>/rest/...
 */
@ApplicationPath("rest")
public class RestActivator extends Application {
}
