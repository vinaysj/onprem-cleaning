package apigee.delete.undeployedrevision;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
//import java.util.Iterator;
import java.util.Set;

import javax.ws.rs.client.Client;

public class RDController {
	public static void main(String[] args) throws Exception {

		String userName;
		String password;
		String protocol;
		String domainName;
		String orgName;
		String filePath;
		//String outputFilePath;
		String env;

		userName = args[0];
		password = args[1];
		protocol = args[2];
		domainName = args[3];
		orgName = args[4];
		filePath = args[5];
		//outputFilePath = args[6];

		HashSet<String> facadeProxySet = new HashSet<String>();

		// Authentication
		Client client = Connection.authenticate(userName, password);

		// get the list of all the proxy in an organization
		ArrayList<String> proxyList = ProxyList.getProxyList(protocol, client, domainName, orgName);
		// System.out.println("List of all the proxies in organization");
		// PrintList.printList(proxyList);

		// get the list of facade environments and the proxies deployed in these facade
		// environment
		ArrayList<String> facadeEnvList = ReadFromFile.getFacadeEnvironmentList(filePath);
		if (facadeEnvList.size() > 0) {
			Iterator<String> itr3 = facadeEnvList.iterator();
			while (itr3.hasNext()) {
				env = itr3.next();
				ArrayList<String> facadeProxyList = FacadeProxyList.getFacadeList(client, domainName, orgName, env);
				facadeProxySet.addAll(facadeProxyList);
			}

			 System.out.println("\nList of all the facade proxies in organization which will be excluded for the proxy deletion");
			 PrintList.printSet(facadeProxySet);

			// removing the facade proxies from the organizational proxy list
			proxyList.removeAll(facadeProxySet);
		}
		
		//removing the shared flows from the proxy list
		List<String> sharedFlowList = SharedFlows.getSharedFlowList(protocol, client, domainName, orgName);
		proxyList.removeAll(sharedFlowList);

		// System.out.println("\nList of proxies considered for revision deletion");
		// PrintList.printList(proxyList);

		// passing the proxy list for revision deletion
		if (proxyList.size() > 0) {
			for (String proxyName : proxyList) {
				// System.out.println("\nProxy Name: " + proxyName);

				// List all revisions for an API proxy
				String[] allRevisions = AllRevisions.listOfAllRevisions(protocol, client, domainName, orgName,
						proxyName);

				// check if there are any revisions in the proxy
				if (allRevisions.length > 0) {
					// list of details on all deployments of the API proxy for all environments
					ArrayList<String> deployedRevisions = DeployedRevisions.deployedRevision(protocol, client,
							domainName, orgName, proxyName);

					// System.out.println("\nList of all deployed revisions in proxy: " +proxyName);
					// PrintList.printList(deployedRevisions);

					// retaining few of the revisions
					Set<String> retainRevisionSet = RetainRevisions.retainRevisionList(deployedRevisions);

					// add a new method to print the retained proxy revisions for each proxy.

					RetainRevisions.printIntoFile(retainRevisionSet, proxyName, orgName);

					// list of all the revisions which are not deployed
					ArrayList<String> revDeletionList = RevisionDeletionList.intersection(allRevisions,
							retainRevisionSet);

					// System.out.println("\nList of revisions which will be deleted in proxy: "
					// +proxyName);
					// PrintList.printList(revDeletionList);

					// check whether there are undeployed revision and then delete
					if (revDeletionList.size() == 0) {
						System.out.println("\nProxy Name: " + proxyName);
						System.out.println("No revisions deleted");
					} else {
						// Deleting the revisions - passing the undeployed revisions for deletion
						DeleteRevisions.deleteRevisions(protocol, revDeletionList, client, domainName, orgName,
								proxyName);
						System.out.println("\nProxy Name: " + proxyName);
						System.out.printf("Deleted revisions:");
						for (String obj : revDeletionList) {
							System.out.printf("\t" + obj);
						}
						System.out.print("\n");
					}
				} else {
					System.out.println("There are no revisions in this proxy");
				}
			}

		} else {
			System.out.println("All proxies are facade proxies. No deletion can be done.");
		}
	}

}
