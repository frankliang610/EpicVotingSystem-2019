/**
 * 
 */
package EpicVotingSystem;
/**
 * File Name : VotingController
 * author : Frank Liang
 * Date :
 * Description :
 */
import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class VotingController
{
    //Create an Arraylist read & store staff & candidate data from file
    private ArrayList<Staff> staffs = new ArrayList<Staff>();
    private ArrayList<Candidate> candidates = new ArrayList<Candidate>();
    
    // Create an ArrayList read, store & modify the admin data from file
    private ArrayList<Administrator> administrators = new ArrayList<Administrator>();
     

   //Type to access individual staff & candidate from array list
    private Staff theStaff;
    private Candidate theCandidate;
    
    // Access to individual administrator from array list
    private Administrator theAdministrator;
    
    //VotingController constructor
    public VotingController()
    {
        loadStaffData();
        loadCandidateData();
        loadAdministratorData();
        
    }

    //=================================================================
    // add loadStaffDate() method to load staff names from file. New additon.
    public void loadStaffData()
    {
        // Assignment 2 Note : use this method to read data from staff text file
    	// and store in arraylist .
    	// Write the code for this method by using loadCandidateData() as sample syntax
    	try {
    		String fileName = "staff.txt";
    		File thisFile = new File(fileName);
    		BufferedReader read = new BufferedReader(new FileReader(thisFile));
    	
    		String staffData;
    		
    		while((staffData = read.readLine()) != null) {
    			

    			
    			String[] staffDetails = staffData.split(",");
    			int id = Integer.parseInt(staffDetails[0]);
    			int hasVotedOrNot = Integer.parseInt(staffDetails[2]);
    			theStaff = new Staff(id, staffDetails[1], hasVotedOrNot, staffDetails[3], staffDetails[4]);
    			staffs.add(theStaff);
    		}
    		read.close();
    		
    	}catch(IOException e) {
    		
    		System.out.println("Error! There was a problem with loading staffs' names from the staff file" + e);
    	}
    	
    }
    //=================================================================

    //loads candidates from file. This method is complete and working ok.
    public void loadCandidateData()
    {
        try
        {
             String fileName = "candidates.txt";
             File theFile = new File(fileName);

             
             BufferedReader read = new BufferedReader(new FileReader(theFile));

             String candidateData;

             while((candidateData = read.readLine())!= null)
             {
                 
            	 String[] candidateDetails = candidateData.split(",");
                 int code = Integer.parseInt(candidateDetails[0]);
                 int votes = Integer.parseInt(candidateDetails[2]);
                 theCandidate = new Candidate(code, candidateDetails[1], votes);
                 candidates.add(theCandidate);
             }
             read.close();
         }
         catch(IOException e)
         {
             System.out.println("Error! There was a problem with loading candidate names from file");
         }
    }
    
    // add loadAdministratorData() method to load admin data from the Admin.txt file. New addition.
    public void loadAdministratorData() {
    	
    	
    	try {
    		
    		String fileName = "Admin.txt";
    		File adminFile = new File(fileName);
    		
    		BufferedReader reader = new BufferedReader(new FileReader(adminFile));
    		String administratorData;
    		
    		while((administratorData =reader.readLine()) !=null){
    			
    			String[] administratorDetails = administratorData.split(",");
    			int id = Integer.parseInt(administratorDetails[0]);
    			theAdministrator = new Administrator(id, administratorDetails[1], administratorDetails[2], administratorDetails[3]);
    			administrators.add(theAdministrator);
    			
    
    		}
    		reader.close();
    		
    		
    	} catch(IOException e) {
    		System.out.println("Error! There was a problem with loading administrators' data from file" + e);
    	}
    }
    
    //returns the collection of candidates
    public ArrayList<Candidate> getCandidates()
    {
        return candidates;
    }
    
    //return the collection of staffs
    public ArrayList<Staff> getStaffs()
    {
    	return staffs;
    }
    
    //return the collection of Administrators. New addition
    public ArrayList<Administrator> getAdministrators()
    {
    	return administrators;
    }
    
    //returns total number of staffs in the collection
    public int getTotalVoters()
    {
        return staffs.size();
    }


    //every staff vote must be saved to file
    public void recordVote()
    {
        theStaff.setVoted();
        theStaff.setTimeStamp(timeStamp());
        theCandidate.addVote();
        saveStaffData();//save to file
        saveCandidateData();//save to file
    }


    //writes staffs back to file
    public void saveStaffData()
    {
        try
        {
            BufferedWriter writer = new  BufferedWriter(new FileWriter("staff.txt"));
            Iterator<Staff> it = staffs.iterator();
            String staffDetails;
            while(it.hasNext())
            {
                theStaff = (Staff) it.next();
                staffDetails = theStaff.getId() + "," +theStaff.getName() + "," 
                				+ theStaff.hasVoted() + "," + theStaff.getPassword() +  ","+ theStaff.getTimeStamp() +"\n";
                writer.write(staffDetails);
            }
            writer.close();
        }
        catch(IOException e)
        {
            System.out.println("The staff file can NOT be written correctly, please contact your administrator!");
        }
    }

    //=================================================================
    // add saveCandidateDate() method to write candidates date back to file. New addition.
    public void saveCandidateData()
    {
       
    	try {
    		BufferedWriter writer = new BufferedWriter(new FileWriter("candidates.txt"));
    		Iterator<Candidate> it = candidates.iterator();
    		String candidateDetails;
    		while(it.hasNext()) {
    			theCandidate = (Candidate) it.next();
    			candidateDetails = theCandidate.getCandidateCode() + "," + theCandidate.getName() + "," 
						+ theCandidate.getVotes() + "\n";
    			writer.write(candidateDetails);
    		}
    		writer.close();
    		
    	}catch(IOException e) {
    		
    		System.out.println("The candidates file can NOT be written correctly, please contact your administrator!" + e);
    	}
    }
    //=================================================================
	
    // add save saveAdministratorData() method to write admin members data back to the file. New addition.
    public void saveAdministratorData() {
    	
    	try {
    		
    		BufferedWriter writer = new BufferedWriter(new FileWriter("Admin.txt"));
    		Iterator<Administrator> it = administrators.iterator();
    		String administratorDetails;
            
    		while(it.hasNext()) {
    			theAdministrator = (Administrator) it.next();
    			administratorDetails = theAdministrator.getID() + "," + theAdministrator.getName()
    									+ "," + theAdministrator.getUserName() + "," + theAdministrator.getPassword() + "\n";
    			writer.write(administratorDetails);
    						
    		}
    		writer.close();
    		
    	}catch(IOException e) {
    		System.out.println("The admin file can NOT be written correctly, please contact your administrator!" + e);
    	}
    	
    }
    
    //returns a staff if found in the staffs ArrayList
    public Staff getStaff(int id)
    {
        Iterator<Staff> it = staffs.iterator();
        while(it.hasNext())
        {
            theStaff = (Staff) it.next();
            if(theStaff.getId()== id)
            {
                return theStaff;
            }
        }
        return null;
    }

    //=================================================================
    // add getCandidate() method to return the candidate 
    // if one is found in the candidates ArrayList. New addition.
    public Candidate getCandidate(int candidateCode)
    {
        // Assignment 2 Note : use this method to return the candidate if found in the candidates ArrayList
        //  Write the code for this method by using getStaff() as sample syntax
        Iterator<Candidate> it = candidates.iterator();
        while(it.hasNext()) {
        	theCandidate = (Candidate) it.next();
        	if( theCandidate.getCandidateCode() == candidateCode ) {
        		
        		return theCandidate;
        				
        	}
   
        }
    	
    	return null;
    }
    
    // add loadAdministratorData method to load the administrator data from file
    
    // add getAdmin method to return the admin 
    // if an administrator is found in the admin file. New addition 
    public Administrator getAdministrator(String adminUserName) {
    	
    	Iterator<Administrator> it = administrators.iterator();
    	while(it.hasNext()) {
    		theAdministrator = (Administrator) it.next();
    		if( theAdministrator.getUserName().equals(adminUserName)) {
    			
    			return theAdministrator;
    			
    		}
    	}
    	
    	return null;
    }
    
    //add getAdminID method to return an admin member exists in the admin file. New addition.
	public Administrator getAdministrator(int adminID) {
	    	
	    	Iterator<Administrator> it = administrators.iterator();
	    	while(it.hasNext()) {
	    		theAdministrator = (Administrator) it.next();
	    		if( theAdministrator.getID() == adminID) {
	    			
	    			return theAdministrator;
	    			
	    		}
	    	}
	    	
	    	return null;
	    }
    
    // add saveAdministratorData method to write data back to the admin file and save the changes.
    // add timeStamp() method to return the time when staff performs voting. New addtion.
    // add timeStamp method to return the time stamp when voting is performed. New addition.
    public String timeStamp() {
    	
    	LocalDateTime votedDateTime = LocalDateTime.now();
    	
    	String formatString = "dd/MM/yyyy HH:mm:ss a";
    	DateTimeFormatter dtf = DateTimeFormatter.ofPattern(formatString);
    	   	
    	String votedDateTimeToString = votedDateTime.format(dtf);
    
    	return votedDateTimeToString;
    	
    }
    
}