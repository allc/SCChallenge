import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

public class IdToName {

	public static void main(String[] args) throws IOException {
		BufferedReader br1 = new BufferedReader(new InputStreamReader(System.in));
		System.out.print("Email id: ");
		String emailId = br1.readLine();
		br1.close();
		String urlString = "http://www.ecs.soton.ac.uk/people/" + emailId;
		URL url = new URL(urlString);
		BufferedReader br2 = new BufferedReader(new InputStreamReader(url.openStream()));
		for (int i = 0; i < 7; i++) {
			br2.readLine();
		}
		String htmlLine = br2.readLine();
		br2.close();
		String s = htmlLine.substring(htmlLine.indexOf("<") + 7, htmlLine.indexOf("|") - 1);
		System.out.println(s);
	}

}
