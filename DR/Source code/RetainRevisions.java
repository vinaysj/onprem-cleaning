package apigee.delete.undeployedrevision;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class RetainRevisions {
	public static Set<String> retainRevisionList(ArrayList<String> deployedRevisions) {
		Set<String> set = new HashSet<String>();
		for (String obj : deployedRevisions) {

			int i = Integer.parseInt(obj);
			if (i >= 2) {
				String j = Integer.toString(i - 1);

				set.add(j);
			}
			set.add(obj);
		}

		return set;
	}
	public static void printIntoFile(Set<String> retainRevisionSet,String proxyname, String orgName) throws IOException {

        //try { 
  
        	String fileName = "deleterevisions_"+orgName+"_output.txt";
            // Open given file in append mode. 
            BufferedWriter out = new BufferedWriter( 
                   new FileWriter(fileName, true)); 
            out.newLine();
            out.newLine();
            out.write("proxy name: "+proxyname);
            out.newLine();
            out.write("Revisions retained: ");
            for(String str : retainRevisionSet) {
            	out.write(""+str+"\t");
            }
            out.close(); 
       // } 
//        catch (IOException e) { 
//            System.out.println("exception occoured while writing the output into the file" + e); 
//        } 
		
	}

}
