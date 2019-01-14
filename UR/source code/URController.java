package ur.undeploy.revision;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import javax.ws.rs.client.Client;

public class URController {
	public static void main(String[] args) throws IOException {

		// get inputs org, env, file location-proxylist
		String userName;
		String password;
		String protocol;
		String domainName;
		String orgName;
		String env;
		String proxyFilePath;
		String facadeEnvFilePath;

		userName = args[0];
		password = args[1];
		protocol = args[2];
		domainName = args[3];
		orgName = args[4];
		env = args[5];
		proxyFilePath = args[6];
		facadeEnvFilePath = args[7];

		String proxy;
		String deployedRevision=null;

		// Client client = Connection.authenticate(userName, password);
		Client client = Connection.authenticate(userName, password);

		// call the method to read the file and store it in the list
		ArrayList<String> proxyList = ReadProxyFile.getProxyList(proxyFilePath);
		
		/*System.out.println("list of proxies read from file");
		for(String a: proxyList) {
			System.out.println(a);
		}*/
		
		//get the list of sharedflows names and remove these shared flows names from the proxy list
		List<String> sharedFlowList = SharedFlows.getSharedFlowList(protocol, client, domainName, orgName);
		proxyList.removeAll(sharedFlowList);
		
		/*System.out.println("list of proxies after removing shared flows");
		for(String a: proxyList) {
			System.out.println(a);
		}*/
		
		//removing the facade proxies
		HashSet<String> facadeProxySet = new HashSet<String>();
		String facadeEnv; 
		ArrayList<String> facadeEnvList = ReadFromFile.getFacadeEnvironmentList(facadeEnvFilePath);
		if (facadeEnvList.size() > 0) {
			Iterator<String> itr3 = facadeEnvList.iterator();
			while (itr3.hasNext()) {
				facadeEnv = itr3.next();
				ArrayList<String> facadeProxyList = FacadeProxyList.getFacadeList(client, domainName, orgName, facadeEnv);
				facadeProxySet.addAll(facadeProxyList);
			}

			proxyList.removeAll(facadeProxySet);
		}
		
		/*System.out.println("list of proxies after removing facade proxies");
		for(String a: proxyList) {
			System.out.println(a);
		}*/
			
		// call the method to get revision number: use a loop: for all the proxies
		Iterator<String> itr3 = proxyList.iterator();
		while (itr3.hasNext()) {
			proxy = itr3.next();
			System.out.println("======");
			System.out.println("\nproxy name: " + proxy);
			deployedRevision = DeployedRevisions.deployedRevision(protocol, client, domainName, orgName, env, proxy);
			
			System.out.println("deployed revision: " + deployedRevision);

			// pass these details to undeploy revision
			if(deployedRevision != null) {
			UndeployRevision.undeployRevisions(protocol, client, domainName, orgName, env, proxy, deployedRevision);
			}
			
		}

		
	}

}
