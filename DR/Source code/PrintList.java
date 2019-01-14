package apigee.delete.undeployedrevision;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

public class PrintList {
	public static void printList(ArrayList<String> printList) {
		if (printList.size() == 0) {
			System.out.println("empty list");
		} else {
			System.out.println("no. of elements in list: " + printList.size());
			Iterator<String> itr = printList.iterator();
			while (itr.hasNext()) {
				System.out.println(itr.next());
			}
		}
	}

	public static void printSet(HashSet<String> set) {
		if (set.size() == 0) {
			System.out.println("empty set");
		} else {
			System.out.println("no. of elements in the list: " +set.size() );
			for (String s : set) {
				System.out.println(s);
			}
		}
	}

}
