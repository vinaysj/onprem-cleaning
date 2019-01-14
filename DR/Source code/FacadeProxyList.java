package apigee.delete.undeployedrevision;

import java.util.ArrayList;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class FacadeProxyList {

	public static ArrayList<String> getFacadeList(Client client, String domainName, String orgName, String env) {
		// String[] deployedRevisions = new String[5];
		ArrayList<String> facadeList = new ArrayList<String>();
		int loopVar = 1000;

		// retrieving the deployed revisions details
		// resource url
		WebTarget target1 = client
				.target("https://"+domainName+"/v1/organizations/" + orgName + "/e/" + env + "/deployments");

		String responseValue = target1.request(MediaType.APPLICATION_JSON).get(String.class);

		JSONObject json;
		try {
			json = new JSONObject(responseValue);
			JSONArray name1 = json.getJSONArray("aPIProxy");

			for (loopVar = 0; loopVar < 100; loopVar++) {
				try {
					String element = (String) name1.getJSONObject(loopVar).get("name");

					// String element = (String) name2.getJSONObject(0).get("name");
					facadeList.add(element);

				} catch (Exception e) {
					break;
				}
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}

		return facadeList;
	}

}
