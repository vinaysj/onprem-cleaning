package ur.undeploy.revision;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

public class SharedFlows {
	public static List<String> getSharedFlowList(String protocol, Client client, String domainName, String orgName) {
		WebTarget target1 = client
				.target(protocol + "://" + domainName + "/v1/organizations/" + orgName + "/sharedflows");

		String responseValue = target1.request(MediaType.APPLICATION_JSON).get(String.class);
		responseValue = responseValue.substring(1, responseValue.length() - 1);
		responseValue = responseValue.replaceAll(" ", "");
		String[] sharedFlowArray = responseValue.split(",");
		List<String> sharedFlowList = new ArrayList<>();
		for (String a : sharedFlowArray) {
			a = a.substring(1, a.length() - 1);
			sharedFlowList.add(a);
		}
		return sharedFlowList;
	}

}
