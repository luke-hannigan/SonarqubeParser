package rhea.sonarqubeparser;
import java.io.File;
import java.util.Arrays;

import org.eclipse.jgit.api.Git;

public class gitClone {

	    
	    public boolean cloneRepositoryHead(String gitURI, String directory){
	    	try {
	    	Git.cloneRepository()
	    	.setURI(gitURI)
	    	.setDirectory(new File(directory))
	         .call();
	    	}
	    	catch(Exception e) {
	    		System.err.println("Error Grabbing Git : " + e.getMessage());
	    		return false;
	    	}
	    	return true;
	    }
	    
	    //BranchesToClone and Branch are the same string.
	    public boolean cloneRepositoryBranch(String gitURI, String directory, String branchesToClone, String branch){
	    	try {
	    		Git.cloneRepository()
	    		  .setURI(gitURI)
	    		  .setDirectory(new File(directory))
	    		  .setBranchesToClone(Arrays.asList(branchesToClone))
	    		  .setBranch(branch)
	    		  .call();
	    	}
	    	catch(Exception e) {
	    		System.err.println("Error Grabbing Git : " + e.getMessage());
	    		return false;
	    	}
	    	return true;
	    }
	    
	    public boolean cloneAllBranches(String gitURI, String directory){
	    	try {
	    		Git.cloneRepository()
	    		.setURI(gitURI)
  			  	.setDirectory(new File(directory))
  			  	.setCloneAllBranches(true)
  			  	.call();
	    	}
	    	catch(Exception e) {
	    		System.err.println("Error Grabbing Git : " + e.getMessage());
	    		return false;
	    	}
	    	return true;
	    }
	    
	   
	    
	    
}


