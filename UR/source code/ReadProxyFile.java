package ur.undeploy.revision;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class ReadProxyFile {
	public static ArrayList<String> getProxyList(String filePath) throws IOException {

		BufferedReader in = null;
		ArrayList<String> proxyList = new ArrayList<String>();
		try {
			in = new BufferedReader(new FileReader(filePath));
			String str;
			while ((str = in.readLine()) != null) {
				proxyList.add(str);
			}
		} catch (FileNotFoundException e) {
			System.out.println("please specific the correct path location");
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (in != null) {
				in.close();
			}
		}

		return proxyList;
	}
}
