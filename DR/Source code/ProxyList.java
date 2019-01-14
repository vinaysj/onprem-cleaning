package apigee.delete.undeployedrevision;

import java.util.ArrayList;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ProxyList {

	public static ArrayList<String> getProxyList(String protocol, Client client, String domainName, String orgName) {
		// String[] deployedRevisions = new String[5];
		ArrayList<String> proxyList = new ArrayList<String>();
		int loopVar = 1000;

		// retrieving the deployed revisions details
		// resource url
		WebTarget target1 = client.target(protocol + "://" + domainName + "/v1/organizations/" + orgName + "/deployments"); 
		String responseValue = target1.request(MediaType.APPLICATION_JSON).get(String.class);

//		String responseValue = target1.request(MediaType.APPLICATION_JSON).get(String.class);

		JSONObject json;
		try {
			json = new JSONObject(responseValue);
			JSONArray name1 = json.getJSONArray("environment");

			for (int loopVar2 = 0; loopVar2 < 1000; loopVar2++) {
				JSONArray name2 = (JSONArray) name1.getJSONObject(loopVar2).get("aPIProxy");

				// JSONArray name1 = json.getJSONArray("aPIProxy");

				for (loopVar = 0; loopVar < 1000; loopVar++) {
					try {

						String element = (String) name2.getJSONObject(loopVar).get("name");

						// String element = (String) name2.getJSONObject(0).get("name");
						proxyList.add(element);

					} catch (Exception e) {
						break;
					}
				}
			}
		} catch (JSONException e) {
			// e.printStackTrace();
		}

		return proxyList;
	}

}
