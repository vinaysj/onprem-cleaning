package ur.undeploy.revision;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

import org.json.JSONArray;
import org.json.JSONObject;

public class DeployedRevisions {
	public static String deployedRevision(String protocol, Client client, String domainName, String orgName, String env,
			String proxyName) {
		
		String deployedRevision = null;
		try {
		// retrieving the deployed revisions details
		// resource url
		WebTarget target1 = client.target(protocol + "://" + domainName + "/v1/organizations/" + orgName
				+ "/environments/" + env + "/apis/" + proxyName + "/deployments");

		String responseValue = target1.request(MediaType.APPLICATION_JSON).get(String.class);

		JSONObject json;
		
			json = new JSONObject(responseValue);
			JSONArray name1 = json.getJSONArray("revision");

			deployedRevision = (String) name1.getJSONObject(0).get("name");

		} catch (Exception e) {
			System.out.println("This proxy does not have deployed revisions in " +env);
		}

		return deployedRevision;
	}
}
