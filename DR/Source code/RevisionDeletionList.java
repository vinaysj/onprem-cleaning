package apigee.delete.undeployedrevision;

import java.util.ArrayList;
import java.util.Set;

public class RevisionDeletionList {

	public static ArrayList<String> intersection(String allRevisions[], Set<String> deployedRevisions) {

		ArrayList<String> revDeletionList = new ArrayList<String>();

		// getting the Revisions which are in allRevisions and not in deployedRevisions
		for (int i = 0; i < allRevisions.length; i++) {
			boolean flag = false;
			for (String obj : deployedRevisions) {
				if (allRevisions[i].equals(obj)) {
					flag = true;
				}
			}
			if (flag == false) {
				revDeletionList.add(allRevisions[i]);
			}
		}

		return (revDeletionList);

	}
}
