package ur.undeploy.revision;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

public class UndeployRevision {

	public static void undeployRevisions(String protocol, Client client, String domainName, String orgName, String env,
			String proxy, String deployedRevision) {

		try {
			WebTarget target = client.target(protocol + "://" + domainName + "/v1/organizations/" + orgName
					+ "/apis/" + proxy + "/revisions/" + deployedRevision + "/deployments?action=undeploy&env="+ env + "&force=force" );
			//(protocol +"://"+domainName+"/v1/organizations/"+orgName+"/apis/"+proxy+"revisions/"+deployedRevision+"/deployments?action=undeploy&env="+env+"&force=force");
			target.request(MediaType.APPLICATION_JSON).post(Entity.json("{}"),String.class);
			System.out.println("SUCCESSFUL: Revision undeployed");
		} catch (Exception e) {
			System.out.println("UNSUCCESSFULL: Could not undeploy the revision");
		}

	}

}
