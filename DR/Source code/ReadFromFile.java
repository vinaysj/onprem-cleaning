package apigee.delete.undeployedrevision;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

public class ReadFromFile {
	public static ArrayList<String> getFacadeEnvironmentList(String filePath)
			throws IOException, FileNotFoundException {

		BufferedReader in = null;
		ArrayList<String> exceptionalEnvironmentList = new ArrayList<String>();

		in = new BufferedReader(new FileReader(filePath));
		String str;
		while ((str = in.readLine()) != null) {
			exceptionalEnvironmentList.add(str);
		}

		if (in != null) {
			in.close();
		}

		System.out.println(
				"\nProxies deployed in these environments will not be considered for revision deletion");
		Iterator<String> itr1 = exceptionalEnvironmentList.iterator();
		while (itr1.hasNext()) {
			System.out.println(itr1.next());
		}
		

		return exceptionalEnvironmentList;

	}
}
