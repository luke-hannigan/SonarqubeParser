package rhea.sonarqubeparser;
import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;

public class setSonarCLI {

	public boolean setCLI(String token, String proj_name) {
		
			String CurrentPath = System.getProperty("user.dir");
		
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(getClass().getClassLoader().getResourceAsStream("sonarScanner.sh")));
			
			String fileContents = "";
			String lineContents = "";
			
			while((lineContents=br.readLine()) != null) {
				fileContents = fileContents + lineContents + "\n";
			}
			
			String[] bashSplitter1 = fileContents.split("\n");
			String[] bashSplitter2 = bashSplitter1[3].split(" ");
			
		/***	String ip = "";
		//	String interfaceName = "docker0";
		 //   NetworkInterface networkInterface = NetworkInterface.getByName(interfaceName);
		 //   Enumeration<InetAddress> inetAddress = networkInterface.getInetAddresses();
		 //   InetAddress currentAddress;
		    currentAddress = inetAddress.nextElement();
		    while(inetAddress.hasMoreElements())
		    {
		        currentAddress = inetAddress.nextElement();
		        if(currentAddress instanceof Inet4Address && !currentAddress.isLoopbackAddress())
		        {
		            ip = currentAddress.toString();
		            break;
		        }
		    }**/
			
			for(int i = 0; i<bashSplitter2.length; i++) {
				if (bashSplitter2[i].contains("\"SONAR_HOST_URL")) {
					bashSplitter2[i] = "\"SONAR_HOST_URL=http://" + "sonarqube" + ":9000\"";
				}
				else if(bashSplitter2[i].contains("\"SONAR_LOGIN")) {
					bashSplitter2[i] = "\"SONAR_LOGIN=" + token + "\"";
				}
				else if(bashSplitter2[i].contains("\"-Dsonar.projectKey")) {
					bashSplitter2[i] = "\"-Dsonar.projectKey=" + proj_name + "\"" ;
				}
				else if(bashSplitter2[i].contains("\"/home/")){
					bashSplitter2[i] = "\"" + CurrentPath + "/output/" + proj_name + ":/usr/src\"";
				}
				
			}
			
			bashSplitter1[3] = "";
			fileContents = "";
			for(int i = 0; i<bashSplitter1.length-1; i++) {
				fileContents = fileContents + bashSplitter1[i] + "\n";
			}
			
			lineContents = "";
			for(int i = 0; i<bashSplitter2.length; i++) {
				lineContents = lineContents + " " + bashSplitter2[i];
			}
			
			lineContents = lineContents.trim();
			fileContents = fileContents + lineContents;
			
			FileWriter fr = new FileWriter(CurrentPath + "/sonarScanner.sh");
			fr.write(fileContents);
			br.close();
			fr.close();
			
		} catch (Exception e) {
			System.err.println("Error opening/writing file - " + e.getMessage());
		}
		
		return true;
	}
	
}
