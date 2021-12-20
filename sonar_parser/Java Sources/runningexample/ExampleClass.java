package rhea.sonarqubeparser.runningexample;
import java.io.File;
import java.util.concurrent.TimeUnit;
import rhea.sonarqubeparser.*;

public class ExampleClass {

	public static void main(String[] args) {
						
		//Getting a GIT URI (Done Dynamically in final Build)
		String gitURI = args[0]; 
		
		//Extracting the Project Name
		String firstSet[] = gitURI.split("/");
		String gitProject_Name = firstSet[firstSet.length - 1].replace(".git", "");
		
		//Generating New Key Based on Project Name
		seleniumSonar selenium = new seleniumSonar();
		selenium.generateProjectKey(gitProject_Name);
		
		//Creating a new Git Clone instance.
		gitClone g = new gitClone();  
		//Setting directory as current dir + project name + project key as directory must not exist and has to be empty 
		String localdirectory = System.getProperty("user.dir") + "/output/" + selenium.getName() + selenium.getKey();
		////////Optional cloning branch
		/////String branch = "refs/heads/bugfix/download-url-status";  Must be referred as refs/heads/(branch-name)
		
		//Check to see if clone was successful
		boolean b = g.cloneRepositoryHead(gitURI, localdirectory);
		
		if(b == true) {
		
		//Necessary wait period to automate SonarQube
		try {
			TimeUnit.SECONDS.sleep(15);
		} catch (Exception e) {
			System.err.println("Error Pausing Application");
		}
		
		
		 //Automate Token Generation for CLI Setup and check if working
		 b = selenium.automateSonarTokenGeneration();
		 
		 
		 if(b == true) {
			
			 	//Create new setSonarCLI instance
			 	setSonarCLI s = new setSonarCLI();
			 	
			 	//Check if scanner file was successfully generated
				b = s.setCLI(selenium.getToken(), selenium.getName()+selenium.getKey());
				
				if(b == true) {
					
					//Check if scanner file was successfully removed by cleanup script (meaning that it has stopped executing)
					File scannerFile = new File(System.getProperty("user.dir") + "/sonarScanner.sh");
					
					while(scannerFile.exists() == true) {
						try {
							TimeUnit.SECONDS.sleep(5);
						} catch (Exception e) {
							System.err.println("Error Pausing Application");
						}
					}
					
					//Create New Sonar DB Instance
					sonarDB postgresSonar = new sonarDB();
					
					if(postgresSonar.connectAndFetchToXML(selenium.getName()+selenium.getKey()) == true) {
						
						System.err.println("Exection Successful");
						
					}
					else {
						
						System.err.println("Error Getting values from DB and writing to XML");
						
					}
					
					
				}
				else {
					
					System.err.println("Error Setting CLI");
					
				}
			 
		 }
		 else {
			 
			 
			 System.err.println("Error Automating SonarQube");
			 return;
			 
		 }
		 
		
		
		}
		else {
			
			System.err.println("Error Cloing Repository");
			return;
			
		}
		
		
	}
	
}
