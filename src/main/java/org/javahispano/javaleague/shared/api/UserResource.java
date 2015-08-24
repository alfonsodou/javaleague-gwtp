/**
 * 
 */
package org.javahispano.javaleague.shared.api;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import static org.javahispano.javaleague.shared.api.ApiParameters.TOKEN;
import static org.javahispano.javaleague.shared.api.ApiParameters.EMAIL;
import static org.javahispano.javaleague.shared.api.ApiPaths.USER;

/**
 * @author adou
 *
 */
@Path(USER)
@Produces(MediaType.APPLICATION_JSON)
public interface UserResource {
	@GET
	void authenticate(@QueryParam(TOKEN) String token,
			@QueryParam(EMAIL) String email);
}
