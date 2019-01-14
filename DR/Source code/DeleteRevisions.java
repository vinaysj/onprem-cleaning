
package apigee.delete.undeployedrevision;

import java.util.ArrayList;

import javax.ws.rs.client.Client;
//import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
//import javax.ws.rs.core.MediaType;

//import org.glassfish.jersey.client.authentication.HttpAuthenticationFeature;

public class DeleteRevisions {
	public static void deleteRevisions(String protocol, ArrayList<String> undeployedRevisions, Client client,
			String domainName, String orgName, String proxyName) {

		// looping each revision numbers for deletion
		for (String obj1 : undeployedRevisions) {

			// calling the delete method
			// resource url

			WebTarget target = client.target(protocol + "://" + domainName + "/v1/organizations/" + orgName + "/apis/"
					+ proxyName + "/revisions/" + obj1);
			target.request().delete();

		}
	}

}
