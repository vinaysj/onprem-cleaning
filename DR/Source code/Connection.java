package apigee.delete.undeployedrevision;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import org.glassfish.jersey.client.authentication.HttpAuthenticationFeature;

public class Connection {

	public static Client authenticate(String userName, String password) {
		// Authentication
		Client client = ClientBuilder.newClient();

		client.register(HttpAuthenticationFeature.basic(userName, password));
		return client;
	}

}
