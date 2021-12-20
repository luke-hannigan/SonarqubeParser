package rhea.sonarqubeparser;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class sonarDB{
	
	public class sonarData{
		
		private String severity;
		private String message;
		private String line;
		private String tags;
		private String component_uuid;
		
		public sonarData(String string, String string2, String string3, String string4, String string5) {
			if(string == null) {
				this.severity = "null";
			}else {
				this.severity = string;
			}
			
			if(string2 == null) {
				this.message = "null";
			}else {
				this.message = string2;
			}
			
			if(string3 == null) {
				this.line = "null";
			}else {
				this.line = string3;
			}
			
			if(string4 == null) {
				this.tags = "null";
			}else {
				this.tags = string4;
			}
			
			if(string5 == null) {
				this.component_uuid = "null";
			}else {
				this.component_uuid = string5;
			}	
		}
		
		public String getcomponent_uuid() {
			return this.component_uuid;
		}
		
	}
	
	private Connection conn = null;
	private String project_name = "";
	private ArrayList<sonarData> dbData = new ArrayList<sonarData>();
	
	   public boolean connectAndFetchToXML(String proj_name) {
	     
		   this.project_name = proj_name;
	      try {
	         Class.forName("org.postgresql.Driver");
	         conn = DriverManager.getConnection("jdbc:postgresql://postgres:5432/postgres", "sonar", "sonar");
	         
	         boolean b = queryDB();
		     conn.close();
		     
		     if(b == true) {
		    	 
		    	 b = writeXML();
		    	 
		    	 if(b == true) {
		    		 return true;
		    	 }
		    	 else {
		    		 System.err.println("Error Writing XML File");
		    		 return false;
		    	 }
		    	 
		     }
		     else {
		    	 System.err.println("Error Getting Data from Database");
		    	 return false;
		     }
		     
	      } catch (Exception e) {
	        System.err.println("Error Connecting to Database");
	        return false;
	      }
	   }

	private boolean writeXML() {
		Collections.sort(dbData, Comparator.comparing(sonarData::getcomponent_uuid));
		Path filePath = Paths.get(System.getProperty("user.dir") + "/output/" + this.project_name + ".xml");
		 
	        try
	        {	
	        	Files.createFile(filePath);
	        	if(dbData.size() > 0) {
	            Files.writeString(filePath, "<" + this.project_name + ">\n\n", StandardOpenOption.APPEND);
	            for(int i=0; i<dbData.size(); i++) {
	            	if(i > 0 && dbData.get(i).component_uuid.equals(dbData.get(i-1).getcomponent_uuid())){
	            		Files.writeString(filePath, "\n\t\t<issue>\n", StandardOpenOption.APPEND);
	            		Files.writeString(filePath, "\t\t\t\t\t\t<severity>" + URLEncoder.encode(dbData.get(i).severity, StandardCharsets.UTF_8) + "</severity>\n", StandardOpenOption.APPEND);
	            		Files.writeString(filePath, "\t\t\t\t\t\t<line>" + URLEncoder.encode(dbData.get(i).line, StandardCharsets.UTF_8) + "</line>\n", StandardOpenOption.APPEND);
	            		Files.writeString(filePath, "\t\t\t\t\t\t<tags>" + URLEncoder.encode(dbData.get(i).tags, StandardCharsets.UTF_8) + "</tags>\n", StandardOpenOption.APPEND);
	            		Files.writeString(filePath, "\t\t\t\t\t\t<message>" + URLEncoder.encode(dbData.get(i).message, StandardCharsets.UTF_8) + "</message>\n", StandardOpenOption.APPEND);
	            		Files.writeString(filePath, "\t\t</issue>\n\n" , StandardOpenOption.APPEND);
	            	}
	            	else {
	            		if(i > 0 && !dbData.get(i).component_uuid.equals(dbData.get(i-1).getcomponent_uuid())){
	            			Files.writeString(filePath, "\n\t\t</file>\n", StandardOpenOption.APPEND);
	            		}
	            		Files.writeString(filePath, "\n\t\t<file>\n", StandardOpenOption.APPEND);
	            		Files.writeString(filePath, "\n\n\t\t<name>" + URLEncoder.encode(dbData.get(i).component_uuid, StandardCharsets.UTF_8) + "</name>\n\n", StandardOpenOption.APPEND);
	            		Files.writeString(filePath, "\n\t\t<issue>\n", StandardOpenOption.APPEND);
	            		Files.writeString(filePath, "\t\t\t\t\t\t<severity>" + URLEncoder.encode(dbData.get(i).severity, StandardCharsets.UTF_8) + "</severity>\n", StandardOpenOption.APPEND);
	            		Files.writeString(filePath, "\t\t\t\t\t\t<line>" + URLEncoder.encode(dbData.get(i).line, StandardCharsets.UTF_8) + "</line>\n", StandardOpenOption.APPEND);
	            		Files.writeString(filePath, "\t\t\t\t\t\t<tags>" + URLEncoder.encode(dbData.get(i).tags, StandardCharsets.UTF_8) + "</tags>\n", StandardOpenOption.APPEND);
	            		Files.writeString(filePath, "\t\t\t\t\t\t<message>" + URLEncoder.encode(dbData.get(i).message, StandardCharsets.UTF_8) + "</message>\n", StandardOpenOption.APPEND);
	            		Files.writeString(filePath, "\t\t</issue>\n\n" , StandardOpenOption.APPEND);
	            	}
	            }
	            
	            Files.writeString(filePath, "\n\t\t</file>\n\n" , StandardOpenOption.APPEND);
	            Files.writeString(filePath, "</" + this.project_name + ">", StandardOpenOption.APPEND);
	        	}
	            
	        } 
	        catch(Exception e){
	        	System.err.println("Error Writing to XML File ");
	        	e.printStackTrace();
	        	return false;
	        }
		
		return true;
	}

	private boolean queryDB() {
		
		try {
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery("SELECT uuid FROM projects WHERE kee = '" + this.project_name +"'");
			
			rs.next();
			String proj_uuid = rs.getString(1);			
			rs = st.executeQuery("SELECT severity, message, line, tags, component_uuid FROM issues WHERE project_uuid = '" + proj_uuid + "'");
			while(rs.next()) {
				dbData.add(new sonarData(rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5)));
			}
			
			for(int i = 0; i<dbData.size(); i++) {
			rs = st.executeQuery("SELECT long_name FROM components WHERE uuid = '" + dbData.get(i).component_uuid + "'");
			rs.next();
			dbData.get(i).component_uuid = this.project_name + "/" +rs.getString(1);
			
			}			
			rs.close();
			st.close();
			
		} catch (Exception e) {
			System.err.println("Error Querying Database - " + e.getMessage());
			return false;
		}
		
		
		return true;
	}
}