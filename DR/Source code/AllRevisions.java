package apigee.delete.undeployedrevision;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

public class AllRevisions {

	public static String[] listOfAllRevisions(String protocol, Client client, String domainName, String orgName,
			String proxyName) throws Exception {

		// retrieving the revision details from apigee
		// Resource url
		try {
			WebTarget target = client.target(protocol + "://" + domainName + "/v1/organizations/" + orgName + "/apis/"
					+ proxyName + "/revisions");

			// Formating the result into an array
			String unformated1 = target.request(MediaType.APPLICATION_JSON).get(String.class);

			String unformated2 = (unformated1.replaceAll("\"|,", ""));
			String unformated3 = unformated2.substring(2, (unformated2.length()) - 1);
			String[] allRevisions = unformated3.split(" ");

			return allRevisions;
		} catch (Exception E) {
			throw new Exception("**please  give correct input**");
		}

	}

}
