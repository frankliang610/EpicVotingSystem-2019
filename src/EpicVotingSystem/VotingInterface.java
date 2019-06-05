/**
* 
*/
package EpicVotingSystem;
import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;


/**
* File Name : VotingInterface
* author : Frank Liang
* Date :
* Description :
*/

public class VotingInterface
{
	private VotingController vc;
	private Staff theStaff;
	private Candidate theCandidate;
	private Administrator theAdministrator;
	//  private final String USERNAME = "admin"; // this variable is no longer used since the admin file added.
	//  private final String PASSWORD ="123";	// this variable is no longer used since the admin file added.
	private int numberOfCandidates = 0;
	private int numberOfAdmins = 0;
	private int numberOfStaffs = 0;
	
	// time range variables. New addition
	String startTimeString;
	String endTimeString;
	String formatString = "dd/MM/yyyy";
	LocalDate startTime;
	LocalDate endTime;

	// add this to customize the displayed date format. New addition
	private DateTimeFormatter dtf = DateTimeFormatter.ofPattern(formatString);
	
	private BufferedReader in = new BufferedReader( new InputStreamReader( System.in ));
	
	
	public static void main(String[] args)
	{
		 VotingInterface vi = new VotingInterface();
		 vi.start();
	}
	
	// Commence session  *************************************************
	
	//This methods is complete and does not require change.
	public void start()
	{
		 vc = new VotingController();
		 commenceVoting();
	}
	
	public void commenceVoting()
	{
		boolean isValidInput = false;
	
		//  administratorsInfo();
		System.out.println();	
		breakingLine();
		System.out.println("************ Welcome to the self-voting system ************");
		breakingLine();
		System.out.println("* -> Are you a voter? If you are, please enter 1.");
		System.out.println("* -> Are you an administrator? If you are, please enter 2.");
		System.out.println("* -> If you need any help, please enter 9.");
		System.out.println("* -> If you want to end the program, please enter 0.");
		breakingLine();    
		System.out.println("Please enter the number accordingly:");
		String inputNumString = getInput(); 
	
		while(!isValidInput) {
			
			if (!isNumeric(inputNumString)) {
				
				isValidInput = true;
				System.out.println("Then input can ONLY be a number, please try again: ");
				commenceVoting();
				
			}
			else {
				
				isValidInput = true;
				int inputNum = Integer.parseInt(inputNumString);
				
				if (inputNum == 1) {
		    		
		    		timeRangeCheck(); 
		    		
		    	} else if (inputNum == 2) {
		    		
		    		adminLogin();
	
		    	} else if(inputNum == 9) {
		    		
		    		manageHelp();
	
		    	} else if(inputNum == 0) {
		    		
		    		isValidInput = true;
		    		System.out.println("The system has been ended!");
		    		
		    	} else {
		    		
		    		isValidInput = true;
		    		System.out.println("Please follow the instruction to enter a valid number."
		    				+ "\nPlease try again: ");
		    		commenceVoting();
		    	}
			}
			
		}
	
	}
	
	// *******************************************************************
	
	
	// Admin session *****************************************************	
	
	//This method helps to manage admin session after a successful login
	private boolean manageAdmin()
	{
		boolean adminQuit = false;
		boolean systemQuit = false;
	
		while (!adminQuit){
		    System.out.println(
		    		  "\nTo continue voting, please enter \"Q\"."
		    		+ "\nTo modify the Admin file, please enter \"A\"."
		    		+ "\nTo modify the Staff file, please enter \"S\"."
		    		+ "\nTo modify the Candidate file, please enter \"C\"."
		    		+ "\nTo modify the time rang of voting, please enter \"T\"."
		    		+ "\nTo view the set voting time, please enter \"VT\"."
		    		+ "\nTo view the voting result, please enter \"VR\"."
		    		+ "\nTo view the staff voting result, please enter \"VS\"."
		    		+ "\nTo end voting enter \"Stop\":");
		    
		    String input = getInput();

			    if (input.equalsIgnoreCase("Q")){
			        //back to voting
			        adminQuit = true;
			        commenceVoting();
			    }
			    else if(input.equalsIgnoreCase("A")) {
			    	adminQuit = true;
			    	adminFileSetting();
			    }
			    else if(input.equalsIgnoreCase("S")) {
			    	adminQuit = true;
			    	staffFileSetting();
			    }
			    else if(input.equalsIgnoreCase("C")) {
			    	adminQuit = true;
			    	candidateFileSetting();
			    }
			    else if(input.equalsIgnoreCase("T")) {
			    	adminQuit = true;
			    	timeRangeSetting();
			    	breakingLineFour();
			    	System.out.println("The time range has been set."
			    			+ "\nTo go back to the previous menu, please enter \"Q\": "
			    			+ "\nTo end the voting system, please enter \"Stop\". ");
			    	input = getInput();
			    		
			    		if(input.equalsIgnoreCase("Q")) {
			    			manageAdmin();
			    		}
			    		else if(input.equalsIgnoreCase("Stop")) {
			    			 adminQuit = true;
			                 systemQuit = true;
			                 System.out.println("Voting System Closed");
			    		}
			    		else {
			    			System.out.println("Invalid input, the system will go back to the previous menu.\n");
			    			manageAdmin();
			    		}
			    	
			    	
			    }
			    else if(input.equalsIgnoreCase("VT")) {
			    	adminQuit = true;
			    	timeRangeDisplay();
			    	manageAdmin();
			    }
			    else if(input.equalsIgnoreCase("VR")) {
			    	adminQuit = true;
			    	printVoteResults();
			    	manageAdmin();
			    }
			    else if(input.equalsIgnoreCase("VS")) {
			    	adminQuit = true;
			    	printStaffVoteResults();
			    	manageAdmin();
			    }
			    
			    else if(input.equalsIgnoreCase("Stop")){
			        //stop system
			        adminQuit = true;
			        systemQuit = true;
			        System.out.println("Voting System Closed");
			    }
			    else{
			        System.out.println("Invalid input, please try again: \n");
			        manageAdmin();
			    }
		}
		return systemQuit;
	}
	
	// Administrator login. This method checks whether the input user name and password
	// match the ones stored in the admin file. New modification.
	public void adminLogin() {
	 
		 String inputUserName = null;
		 String userName = null;
		 String userPassword;
		 String inputPassword; 
		 
		 boolean loginQuit = false;
		 int countLogin = 0;
		 
		 while (!loginQuit) {
			 
			 countLogin++;
			 
				 try {
					 
					 System.out.println("Please enter your username: "); 
					 inputUserName = getInput(); 
					 
					 theAdministrator = vc.getAdministrator(inputUserName);
					 userName = theAdministrator.getUserName(); 
					 
				 } catch(NullPointerException e) {
					 
					 System.out.println("The user name does NOT exist, please try again: ");
					 adminLogin();
					 return;
				 }
				 
				
				 userPassword = theAdministrator.getPassword();
				 System.out.println("Please enter your password: "); 
				 inputPassword = getInput();
				 
				 boolean userNameCheck = validateAdmin(userName, inputUserName);
				 boolean userPasswordCheack = validateAdmin(userPassword, inputPassword);
				 
				 if (userNameCheck == true && userPasswordCheack == true ) {
					 
					 loginQuit = true;
					 manageAdmin();
					 
				 } else if ( countLogin > 2) {
					 
					 loginQuit = true;
					 commenceVoting();
					 
				 } else {
					 System.out.println("Invalid username or password.");
				 }
				 
			 
			 
		 }	 
	}
	
		// validates administrator username & password. This method is complete and does not require additional code. 
		// This method has been modified to adapt to extension requirement. New modification.
		public boolean validateAdmin(String existValue, String inputValue)
		{
		 
			if( existValue.equals(inputValue) ){
			    return true;
			}
			else{
			    return false;
			}
		}
	
		//add administratorsInfo to display all the admins data. (New addition)
		public void administratorsInfo() {
	
			 setNumberOfAdmin(0);
			 ArrayList<Administrator> admins = vc.getAdministrators();
			 Iterator<Administrator> it = admins.iterator();
			 
			 breakingLine();
			 System.out.println("** ID *** Name ********** Username ****** Password ********");
			 breakingLine();
			 
			 while(it.hasNext()) {
				 theAdministrator = (Administrator)it.next();
				 
				 System.out.println("   "+theAdministrator.getID() + "\t  " + theAdministrator.getName() 
						 			+ "\t  " + theAdministrator.getUserName() + "\t  " + theAdministrator.getPassword());
				setNumberOfAdmin(getNumberOfAdmin() + 1);
				 
			 }
			 System.out.println();
			 breakingLineFour();
			 System.out.println("The total numbers of Admins are: " +numberOfAdmins);
			 breakingLine();
	}
	
	
	// add adminFileSetting method, to modify Admin.txt content. New addition
	public void adminFileSetting() {
	 
		 boolean isValidInput = false;
		 
		 administratorsInfo();
		 
		 System.out.println("\nTo add a new admin member, please enter \"A\"."
				 			+ "\nTo delete an existing admin member, please enter \"D\"."
				 			+ "\nTo go back to previous menu, please enter \"Q\"."	
				 			+ "\nTo end the system, please enter \"Stop\".");
		 
		 String input = getInput(); 
		 
		 while(!isValidInput) {
			 
			 if (isNumeric(input)) {
				 
				 isValidInput = true;
				 System.out.println("Only letter is an accepted input. "
				 		+ "\nPlease try again: ");
				 
				 adminFileSetting();
				 
			 }
			 else {
				 
				 if(input.equalsIgnoreCase("a")) {
					 addAdminMember();
				 }
				 else if(input.equalsIgnoreCase("d")) {
					 deleteAdminMember();
				 }
				 else if(input.equalsIgnoreCase("q")) {
					 manageAdmin();
				 }
				 else if(input.equalsIgnoreCase("stop")) {
					 return;
				 }
				 else {
					 isValidInput = true;
					 System.out.println("Please follow the instruction to enter a valid letter(s)." 
					 		+ "\nPlease try again: ");
					 adminFileSetting();
				 }
				 
			 }
			 
		 }
	 
	}
	
	// add addAdminMember method to add a new member into Admin.txt file. New addition
	public void addAdminMember() {
	 
		 ArrayList<Administrator> administrators = vc.getAdministrators();
		 
		 int id = 0;
		 String input;
		 String name;
		 String username; 
		 String password;
		 
		 boolean isValidID = false;
		 boolean isValidName = false;
		 
		 System.out.println("Enter a new admin member's ID: ");
		 input = getInput();
		 
		 while(!isValidID) {
			 
			 if(isNumeric(input) == true) {
			 
				 theAdministrator = vc.getAdministrator(Integer.parseInt(input));
					 if(vc.getAdministrator(Integer.parseInt(input)) != null) {
						 isValidID = false;
						 System.out.println("\nThe entered ID has already existed, please try a new one: ");
						 addAdminMember();
						 
					 } else {
						 isValidID = true;
						 System.out.println("The entered ID is valid, please continue.");
					 }
				 
			} else {
					 isValidID = true;
					 System.out.println("Admin member ID must be a number, please try again!");
					 addAdminMember();
				 }
		 }
		 
		 id = Integer.parseInt(input);
		 
		 while(!isValidName) { 
			 System.out.println("Enter the new admin member's full name(i.e. John Smith): ");
			 input = getInput();
			 if(isValidFullName(input) == true) { // when entered name is valid
				 isValidName = true;
			 } else {
				 System.out.println("The entered name is INVALID, please try again: ");
				 input = getInput();
			 }
			 
		 }
		 
		 name = input;
		 
		 System.out.println("Enter the new admin member's username(i.e. adminjohn): ");
		 input = getInput();
		 username = input;
		 
		 System.out.println("Enter the new admin member's password(i.e. smith601): ");
		 input = getInput();
		 password = input;
		 
		 theAdministrator = new Administrator(id, name, username, password);
		 administrators.add(theAdministrator);
		 
		 vc.saveAdministratorData();
		 
		 System.out.println("The added admin member has been saved.");
		 breakingLineFour();
		 System.out.println("To continue add anther member, please enter \"A\"."
				 		  + "\nTo go back to previous menu, please enter \"Q\"."
				 		  + "\nTo quit the system, please enter \"Stop\".");
		 
		 input = getInput();
		 
		 if (input.equalsIgnoreCase("A")) {
			 addAdminMember();
		 }
		 else if(input.equalsIgnoreCase("Q")) {
			 adminFileSetting();
		 }
		 else if(input.equalsIgnoreCase("Stop")) {
			 System.out.println("System has been stopped.");
			 
		 }
		 else {
			 System.out.println("Invalid input, please try again: ");
			 addAdminMember();
		 }
	}
	
	// add deleteAdminMember method to delete an existing 
	// admin member from Admin.txt file. New addition
	public void deleteAdminMember() {
	
		 ArrayList<Administrator> administrators = vc.getAdministrators();
		 
		 int id = 0;
		 boolean isValidID = false;
		 
		 System.out.println("To delete any admin member, please enter the member ID: ");
		 String input = getInput();
		 
		 while(!isValidID) {
			 if (isNumeric(input) == true) {
				 try {
						 id = Integer.parseInt(input);
						 theAdministrator = vc.getAdministrator(id);
						 int deleteMemberID = theAdministrator.getID();
						 String deleteMemberName = theAdministrator.getName();
						 String deleteMemberUserName = theAdministrator.getUserName();
						 int deleteIndex = administrators.indexOf(vc.getAdministrator(deleteMemberID)); // locate the index of the selected element in the admin file.
						 
						 breakingLine();
						 System.out.println("** ID *** Name ******** Username ******** Password ********");
				    	 breakingLine();
						 System.out.println("   " + deleteMemberID + "\t" + deleteMemberName + "\t" + deleteMemberUserName +"\t  " + "********");
						 breakingLineFour();
						 
						 System.out.println("Is this member you really want to deletel from the Admin.txt file?"
						 		+ "			\nIf it is yes, please enter 'Y', if it is no, please enter 'N': ");
						 input = getInput();
						 
						 if(isNumeric(input) == true) {
							 
							 System.out.println("Only letter are allow to enter here, please try again:");
							 input = getInput();
							 
						 } else {
							 
							 if(input.equalsIgnoreCase("Y")) {
								 
								 administrators.remove(deleteIndex);
								 vc.saveAdministratorData();
								 System.out.println("\nThe selected admin member " + "\"" + deleteMemberName + "\"" + "has been deleted from the file.");
								 System.out.println();
								 administratorsInfo();
								 breakingLineFour();
								 System.out.println("To continue delete another member, please enter \"D\"."
								 		  + "\nTo go back to previous menu, please enter \"Q\"."
								 		  + "\nTo quit the system, please enter \"Stop\".");
						 
								 input = getInput(); 
								 
									 if (input.equalsIgnoreCase("D")) {
										 deleteAdminMember();
									 }
									 else if(input.equalsIgnoreCase("Q")) {
										 adminFileSetting();
									 }
									 else if(input.equalsIgnoreCase("Stop")) {
										 System.out.println("System has been stopped.");
										 return;
									 }
									 else {
										 System.out.println("Invalid input, please try again: ");
										 deleteAdminMember();
									 }
										 
							}
							else if (input.equalsIgnoreCase("N")) {
								 System.out.println("You have cancelled this operation, and the system will go back to previous menu.");
								 deleteAdminMember();
							}
							 else {
								 System.out.println("The entered letter is invalid, please try again: ");
								 deleteAdminMember();
							 }
									 
						 }
						 
				 	}catch(NullPointerException e) {
						System.out.println("The entered ID is not found, please try again: ");
						input = getInput();
				 }
			 }
		 }
	}
	
	// ******************************************************************	 
	
	
	// Staff session ****************************************************
	
	// add mangeVote method to enable staff to 
	// login and perform voting.
	public void manageVote()
	{
		boolean voteQuit = false;
		int count = 0;
	
		while (!voteQuit) {
			count++;
			System.out.println("Please enter your voter ID or enter \"Q\" to go back.\nTo end this voting please enter \"Stop\": ");
			String input = getInput();
			
			if( count > 2) {
				voteQuit = true;
				System.out.println("You have entered invalid ID too many times");
				commenceVoting();
				
			} else if (isNumeric(input) == true) {
				
				try {
					theStaff = vc.getStaff(Integer.parseInt(input));
					System.out.println(theStaff.getName());
					
						if(theStaff.hasVoted() == 1) {
							
							System.out.println("You have voted already.Good bye!");
							breakingLineFour();
							voteQuit = true;
							commenceVoting();
							
							
						} else if (theStaff.hasVoted() == 0) {
							
							passwordCheck();
							
						} else {
							
							System.out.println("It seems to be a problem. Please contact your administrator.");
						
						}
					
				} catch (NullPointerException e) {
					
					System.out.println("The entered ID is not found, please try again: ");
					input = getInput();
				}
				
				
			} else {
				
				if(input.equalsIgnoreCase("Q")) {
					
					voteQuit = true;
					commenceVoting();
					
				} else if (input.equalsIgnoreCase("Stop")) {
					
					System.out.println("You have ended the system.");
					System.out.println("Voting System Closed!");
					breakingLineFour();
					voteQuit = true;
					
				} else {
					
					System.out.println("Invalid input, please re-enter : \n\n");
				
				}
			
			}
	
		}
	
		breakingLine();
	
	}
	//==============================================================
	
	// Password check method. This method check if the password(staff) 
	// input matches the password given in the Staff file. New addition
	public void passwordCheck() {
	 
		 int countPasswordEnter = 0;
		 
		 String thePassword = theStaff.getPassword();
		 System.out.println("Please enter your staff password. \nOr enter \"Q\" to go back main screen: ");
		 
		 String input = getInput();
		 
		 while(!input.equals(thePassword)) {
			 
			 countPasswordEnter++;
			 
			 if(input.equalsIgnoreCase("Q")) {
				 
				 commenceVoting();
				 
			 } else if(countPasswordEnter > 2) {
				 
				 System.out.println("You have entered invalid password too many times. \nNow the system is going back to the main screen.");
				 breakingLineFour();
				 commenceVoting();
				 
			 } else {
				 
				 System.out.println("Invalid password, please try again!!");
				 
			 }
			 
			 input = getInput();
		 }
		 
		 breakingLineFour();
		 getStaffVote();
	 
	}
	
	// add displayVotingScreen method to show the
	// welcome message when the staff logged in successfully. New addition
	public void displayVotingScreen()
	{
		 // Assignment 2 Note :
		 // Use this methods to  welcome & display staff name after a successful login 
		 // then display candidate names and instructions on how to place a vote.
		 
		 System.out.printf("Welcome to the voting system, Mr.\\Ms. %s.\n", theStaff.getName());
		 System.out.println("The candidates are listed as follows along with a candidate code.");
		 System.out.println("You can vote for any candidates only enter the candidate code, please.");
		 
		 breakingLineTwo();
		 candidatesInfo();
		 breakingLineTwo();
		 
	}
	  
	//manages a staff vote. New modification
	private void getStaffVote() 
	{
		 int candidateCode;
		 boolean quitStaffVote = false;
		 
		 displayVotingScreen();
		 
		 while(!quitStaffVote) {
			 
			 System.out.println("Please enter a candidate code or enter \"Q\" to go back to the main screen: ");
			 String input = getInput();
			 
			 if (isNumeric(input) == true) {
				 
				 try {
					candidateCode = Integer.parseInt(input);
					theCandidate = vc.getCandidate(candidateCode);
					System.out.println("\nYou have selected " + theCandidate.getName()); // not complete yet
					System.out.println("\nEnter \"Y\" to confirm your selection or \"C\" to Cancel this voting, then click the Enter key: ");
					input = getInput();
					
	      				if(isNumeric(input) == false) {
	      					
	      					if(input.equalsIgnoreCase("Y")) {
	      						vc.recordVote();
	      						System.out.println("\nThank you for you voting " + theStaff.getName());
	      						System.out.println("Your voting time is: " + theStaff.getTimeStamp()  + ". Bye!");
	      						breakingLineFour();
	      						quitStaffVote = true;
	      						commenceVoting();
	      						
	      					} else if (input.equalsIgnoreCase("C")) {
	      						
	      						System.out.println("You have cancelled your selection.");
	      						breakingLineFour();
	      						displayVotingScreen();
	      						
	      					} else {
	      						
	      						System.out.println("Invalid input, please try again!"); // need to add more code below.
	      						
	      					}
	      					
	      					
	      				} else {
	      					
	      					System.out.println("Invalid input, please re-enter : \n\n");
	      					
	      				}
					
					
					}catch (NullPointerException e) {
						
						System.out.println("The candidate code you entered does not exist."
								+ "\nPlease try again!");
						breakingLineFour();
						getStaffVote();
				
					}
				 
			 } else {
				
				if(input.equalsIgnoreCase("C")) {
	   			
					quitStaffVote = true;
	   				commenceVoting();
	   			
	   			} else {
	   			
	   				System.out.println("Can NOT understand your input, please re-enter : \n\n");
	   		
	   			}
			}  
		 }   	 
	}
	
	//add staffsInfo method to display all the staff data. (New addition)
	public void staffsInfo() {
		 setNumberOfStaff(0);
		 ArrayList<Staff> staff = vc.getStaffs();
		 Iterator<Staff> it = staff.iterator();
		 
		 breakingLine();
		 System.out.println("* ID ** Name ******** Voted **** Password *** Voted Time **");
		 breakingLine();
		 
		 while(it.hasNext()) {
			 theStaff = (Staff)it.next();
			 System.out.println(theStaff.getId() + "\t" 
					 			+ theStaff.getName() + "\t" 
					 			+ theStaff.hasVoted() + "\t" 
					 			+ theStaff.getPassword() + "\t" 
					 			+ theStaff.getTimeStamp() );
			 
			 setNumberOfStaff(getNumberOfStaff() + 1);
		 }
		 System.out.println();
		 breakingLineFour();
		 System.out.println("The total numbers of staffs are: " + numberOfStaffs);
		 breakingLine();
	}
	
	
	// add printStaffVoteResults() method to display the 
	// result of staff voting situation. (New addition)
	public void printStaffVoteResults() {
			int votedCount = 0;
			int notVotedCount = 0;
			double staffVotedRate = 0.0;
	
			setNumberOfStaff(0);
			ArrayList<Staff> staff = vc.getStaffs();
			Iterator<Staff> it = staff.iterator();
	
			breakingLine();
		    System.out.println("* ID **** Name **** Voted/Not Voted *** Voted Time ********");
		    breakingLine();
	
		    while(it.hasNext()){
		    	theStaff = (Staff)it.next();
		    	if(theStaff.hasVoted() == 1){
		    		System.out.println(theStaff.getId() + "\t" 
		    				 			+ theStaff.getName() + "\t" 
		    				 			+ "Voted" + "\t\t" 
		    				 			+ theStaff.getTimeStamp() );
		    		votedCount++;
	
		    	} 
		    	
		    	if(theStaff.hasVoted() == 0){
		    		System.out.println(theStaff.getId() + "\t" 
		    				 			+ theStaff.getName() + "\t" 
		    				 			+ "Not Voted" + "\t" 
		    				 			+ "No Time Record!");
		    		notVotedCount++;
		    	}
		    	
		    	setNumberOfStaff(getNumberOfStaff() + 1);
		    	
		    }
		    
		    staffVotedRate = ( (double)votedCount / numberOfStaffs ) * 100;
		    breakingLineFour();
		    System.out.println("The total numbers of staffs are: " + numberOfStaffs + " people.");
		    System.out.println("The total numbers of vated staffs are: " + votedCount + " people.");
		    System.out.println("The total numbers of staffs are: " + notVotedCount + " people.");
		    System.out.printf("The staff vote rate is: %.1f%%\n", staffVotedRate);
		    breakingLine();
			 
	}
	
	
	// add staffFileSetting method, to modify 
	// staff.txt content. New addition
	public void staffFileSetting() {
	 
		 staffsInfo();
		 boolean isValidInput = false;
		 
		 System.out.println("\nTo add a new staff member, please enter \"A\"."
		 			+ "\nTo delete an existing staff member, please enter \"D\"."
		 			+ "\nTo go back to previous menu, please enter \"Q\"." 
		 			+ "\nTo end the system, please enter \"Stop\".");
		String input = getInput(); // add exception 
	
		while(!isValidInput) {
			 
			 if (isNumeric(input)) {
				 
				 isValidInput = true;
				 System.out.println("Only letter is an accepted input. "
				 		+ "\nPlease try again: ");
				 
				 staffFileSetting();
				 
			 }
			 else {
	
				if(input.equalsIgnoreCase("A")) {
					 addStaffMember();
				}
				else if(input.equalsIgnoreCase("D")) {
					 deleteStaffMember();
				}
				else if(input.equalsIgnoreCase("Q")) {
					 manageAdmin();
				}
				else if(input.equalsIgnoreCase("Stop")) {
					 return;
				}
				else {
					isValidInput = true;
					 System.out.println("Please follow the instruction to enter a valid letter(s)." 
					 		+ "\nPlease try again: ");
					 staffFileSetting();
					 
				}
			 }
		}
	}
	
	// add addStaffMember method to add a new 
	// member into the staff.txt file. New addition
	public void addStaffMember() {
		 ArrayList<Staff> staffs = vc.getStaffs();
		 
		 int id = 0;
		 int voted = 0;
		 String input;
		 String name; 
		 String password;
		 String timeStamp = null;
		 
		 boolean isValidID = false;
		 boolean isValidName = false;
		 
		 System.out.println("Enter a new staff member's ID: ");
		 input = getInput();
		 
		 while(!isValidID) {
			 
			 if(isNumeric(input) == true) {
			 
			 theStaff = vc.getStaff(Integer.parseInt(input));
				 if(vc.getStaff(Integer.parseInt(input)) != null) {
					 isValidID = false;
					 System.out.println("\nThe entered ID has already existed, please try a new one: ");
					 input = getInput();
					 
				 } else {
					 isValidID = true;
					 System.out.println("The entered ID is valid");
				 }
			 
			 } else {
				 System.out.println("Staff member ID must be a number, please try again!");
			 }
		 }
		 
		 id = Integer.parseInt(input);
		 
		 while(!isValidName) { 
			 System.out.println("Enter the new staff member's full name(i.e. John Smith): ");
			 input = getInput();
	
				 if(isValidFullName(input) == true) { // when entered name is valid
					 isValidName = true;
				 } else {
					 System.out.println("The entered name is INVALID, please try again: ");
					 input = getInput();
				 }
			 
		 }
		 name = input;
		 
		 
		 System.out.println("Enter the new staff member's password(i.e. smith601): ");
		 input = getInput();
		 password = input;
		 
		 theStaff = new Staff(id, name, voted, password, timeStamp);
		 staffs.add(theStaff);
		 
		 vc.saveStaffData();
		 
		 System.out.println("The added staff member has been saved.");
		 breakingLineFour();
		 System.out.println("To continue add anther member, please enter \"A\"."
				 		  + "\nTo go back to previous menu, please enter \"Q\"."
				 		  + "\nTo quit the system, please enter \"Stop\".");
		 
		 input = getInput();
		 
			 if (input.equalsIgnoreCase("A")) {
				 addStaffMember();
			 }
			 else if(input.equalsIgnoreCase("Q")) {
				 staffFileSetting();
			 }
			 else if(input.equalsIgnoreCase("Stop")) {
				 System.out.println("System has been stopped.");
				 return;
			 }
			 else {
				 System.out.println("Invalid input, please try again: ");
				 addStaffMember();
			 }
	}
	
	
	// add deleteStaffMember method to delete an existing 
	// staff member from staff.txt file. New addition
	public void deleteStaffMember() {
		 ArrayList<Staff> staffs = vc.getStaffs();
		 
		 int id = 0;
		 boolean isValidID = false;
		 
		 System.out.println("To delete any staff member, please enter the staff member ID: ");
		 String input = getInput();
		 
		 while(!isValidID) {
			 if (isNumeric(input) == true) {
				 try {
						 id = Integer.parseInt(input);
						 theStaff = vc.getStaff(id);
						 int deleteMemberID = theStaff.getId();
						 String deleteMemberName = theStaff.getName();
						 int deleteMemberVoted = theStaff.hasVoted();
						 String deleteMemberTimeStamp = theStaff.getTimeStamp();
						 int deleteIndex = staffs.indexOf(vc.getStaff(deleteMemberID)); // locate the index of the selected element in the admin file.
						 
						 breakingLine();
				    	 System.out.println("* ID ** Name ******** Voted **** Password *** Voted Time **");
				    	 breakingLine();
						 System.out.println(deleteMemberID + "\t" + deleteMemberName + "\t" + deleteMemberVoted +"\t " + "********" + "\t" + deleteMemberTimeStamp);
						 breakingLineFour();
						 
						 System.out.println("Is this member you really want to delete from the staff.txt file?"
						 					+ "\nIf it is a yes, please enter 'Y', if it is a no, please enter 'N': ");
						 
						 input = getInput();
						 
						 if(isNumeric(input) == true) {
							 
							 System.out.println("Only letter are allow to enter here, please try again:");
							 input = getInput();
							 
						 } else {
							 
							 if(input.equalsIgnoreCase("Y")) {
								 
								 staffs.remove(deleteIndex);
								 vc.saveStaffData();
								 System.out.println("\nThe selected staff member has been deleted from the file.");
								 staffsInfo();
								 breakingLineFour();
								 System.out.println("To continue delete another member, please enter \"D\"."
								 		  + "\nTo go back to previous menu, please enter \"Q\"."
								 		  + "\nTo quit the system, please enter \"Stop\".");
						 
								 input = getInput(); 
								 
									 if (input.equalsIgnoreCase("D")) {
										 deleteStaffMember();
									 }
									 else if(input.equalsIgnoreCase("Q")) {
										 staffFileSetting();
									 }
									 else if(input.equalsIgnoreCase("Stop")) {
										 System.out.println("System has been stopped.");
										 return;
									 }
									 else {
										 System.out.println("Invalid input, please try again: ");
										 deleteStaffMember();
									 }
										 
							}
							else if (input.equalsIgnoreCase("N")) {
								 System.out.println("You have cancelled this operation, and the system will go back to the previous menu.");
								 staffFileSetting();
							}
							 else {
								 System.out.println("The entered letter is invalid, please try again: ");
								 deleteStaffMember();
							 }
									 
						 }
						 
			 	}catch(NullPointerException e) {

					System.out.println("The entered ID is not found, please try again: ");
					deleteStaffMember();
				 }
			 }
		 }
	}
	// ******************************************************************	 
	
	
	// Candidate session ************************************************
	
	// prints out the voting results after a successful 
	// admin login. New modification
	public void printVoteResults()
	{
		 int totalVoters = vc.getTotalVoters(); // 
		 int candidateVotes = 0 ; // 
		 double totalVoted = 0;
		 
		 DecimalFormat df = new DecimalFormat("#.##");
	
		 ArrayList<Candidate> candidates = vc.getCandidates();
		 
		 Iterator<Candidate> it = candidates.iterator();
		 while(it.hasNext()) {

			 theCandidate = (Candidate) it.next();
			 candidateVotes = theCandidate.getVotes();
			 totalVoted += candidateVotes;

		 }
	 
		 System.out.println("\n\t   STAFF REPRESENTATIVE VOTING STATISTICS");
		 System.out.println("\t ==========================================\n");
		 System.out.println("\t Numbers on voting list:" + totalVoters);
		 System.out.println("\t Numbers voted: " + (int)totalVoted + " (" + (df.format(((totalVoted/totalVoters))*100)) + ")%\n");
		 System.out.println("\t Voting Results");
		 breakingLineThree();
		 System.out.println("\t Candidate\t\tVotes\t(Votes%)");
		 System.out.println("\t ____________\t\t_____\t ________\n");
		 
		 it = candidates.iterator();
		 while(it.hasNext()) {

			 theCandidate = (Candidate)it.next();
			 candidateVotes = theCandidate.getVotes();
			 System.out.println("\t Candidate" + " " + theCandidate.getCandidateCode() + ": "
			 					+ "\t\t  " + theCandidate.getVotes() 
					 			+ "\t (" + (df.format((candidateVotes/totalVoted)*100)) + "%)");
		 
		 }
	 
	 	breakingLineThree();
	
	}
	
	//add candidatesInfo method to display all the candidate data. (New addition)
	public void candidatesInfo() {
	 
		 setNumberOfCandidate(0);
		 ArrayList<Candidate> candidates = vc.getCandidates();
		 Iterator<Candidate> it = candidates.iterator();
		 
		 breakingLineTwo();
		 System.out.println("** Candidate Code *** Candiate Name **");
		 breakingLineTwo();
		 
		 while(it.hasNext()) {
			 
			 theCandidate = (Candidate)it.next();
			 
			 System.out.println("\t" +  theCandidate.getCandidateCode() 
			 					+ "\t\t" + theCandidate.getName());
			 setNumberOfCandidate(getNumberOfCandidate() + 1);

	 	}
	 
		 System.out.println();
		 System.out.println("--------------------------------------");
		 System.out.println("The total numbers of candidates are: " + numberOfCandidates);
		 breakingLineTwo();
	 
	}
	
	// add candidateFileSetting method, to modify 
	// candidate.txt content. New addition
	public void candidateFileSetting() {
	 	candidatesInfo();
	 	boolean isValidInput = false;
	 
	 	System.out.println("\nTo add a new candidate member, please enter \"A\"."
	 			+ "\nTo delete an existing candidate member, please enter \"D\"."
	 			+ "\nTo go back to previous menu, please enter \"Q\"."	// go back to manageAdmin()
	 			+ "\nTo end the system, please enter \"Stop\".");
	 	String input = getInput(); // throws exception
	 
			while(!isValidInput) {
						 
				 if (isNumeric(input)) {
					 
					 isValidInput = true;
					 System.out.println("Only letter is an accepted input. "
					 		+ "\nPlease try again: ");
					 
					 candidateFileSetting();
					 
				 }
				 else {
	
					if(input.equalsIgnoreCase("A")) {
						 addCandidateMember();
					}
					else if(input.equalsIgnoreCase("D")) {
						 deleteCandidateMember();
					}
					else if(input.equalsIgnoreCase("Q")) {
						 manageAdmin();
					}
					else if(input.equalsIgnoreCase("Stop")) {
						isValidInput = true;
						System.out.println("The system has been stopped.");
						
					}
					else {
						isValidInput = true;
						System.out.println("Please follow the instruction to enter a valid letter(s)." 
						 		+ "\nPlease try again: ");
						candidateFileSetting();
						
					}
				 }
			}
	}
	
	// add addCandidateMember method to add a new member 
	// into the candidates.txt file. New addition
	public void addCandidateMember() {
		 ArrayList<Candidate> candidates = vc.getCandidates();
		 
		 int id = 0;
		 int votes = 0;
		 String name; 
		 String input;
		 
		 boolean isValidID = false;
		 boolean isValidName = false;
		 
		 System.out.println("Enter a new candiate member's ID: ");
		 input = getInput();
		 
		 while(!isValidID) {
			 
			 if(isNumeric(input) == true) {
			 
			 theCandidate = vc.getCandidate(Integer.parseInt(input));
				 if(vc.getCandidate(Integer.parseInt(input)) != null) {

					 isValidID = false;
					 System.out.println("\nThe entered ID has already existed, please try a new one: ");
					 addCandidateMember();
					 
				 } else {

					 isValidID = true;
					 System.out.println("The entered ID is valid, please continue.");
				 
				 }
			 
			 } else {

				 isValidID = true;
				 System.out.println("Candidate member ID must be a number, please try again!");
				 addCandidateMember();

			 }
		 }
		 
		 id = Integer.parseInt(input);
		 
		 while(!isValidName) { 
			
			 System.out.println("Enter the new candidate member's full name(i.e. John Smith): ");
			 input = getInput();
			 
			 if(isValidFullName(input) == true) { // when entered name is valid
				 isValidName = true;
			 } else {
				 System.out.println("The entered name is INVALID, please try again: ");
				 input = getInput();
			 }
			 
		 }
		 name = input;
	 
		 theCandidate = new Candidate(id, name, votes);
		 candidates.add(theCandidate);
		 
		 vc.saveCandidateData();;
		 
		 System.out.println("The new added candidate member" + "\"" + name + "\"" + "has been saved.");
		 candidatesInfo();
		 breakingLineFour();
		 System.out.println("To continue add anther member, please enter \"A\"."
				 		  + "\nTo go back to previous menu, please enter \"Q\"."
				 		  + "\nTo quit the system, please enter \"Stop\".");
		 
		 input = getInput(); 
		 
		 if (input.equalsIgnoreCase("A")) {
			 addCandidateMember();
		 }
		 else if(input.equalsIgnoreCase("Q")) {
			 candidateFileSetting();
		 }
		 else if(input.equalsIgnoreCase("Stop")) {
			 System.out.println("System has been stopped.");
		 }
		 else {
			 System.out.println("Invalid input, please try again: ");
			 addCandidateMember();
		 }
	 }
	 
	// add deleteCandidateMember method to delete an existing 
	// member from the candidates.txt file. New addition
	public void deleteCandidateMember() {
	ArrayList<Candidate> candidates = vc.getCandidates();
	 
	 int code = 0;
	 boolean isValidID = false;
	 
	 System.out.println("To delete any candidate member, please enter the candidate member code: ");
	 String input = getInput();
	 
		while(!isValidID) {
			 if (isNumeric(input) == true) {
				 try {
						 code = Integer.parseInt(input);
						 theCandidate = vc.getCandidate(code);
						 int deleteMemberCode = theCandidate.getCandidateCode();
						 String deleteMemberName = theCandidate.getName();
						 int deleteMemberVotes = theCandidate.getVotes();
						 
						 int deleteIndex = candidates.indexOf(vc.getCandidate(deleteMemberCode)); // locate the index of the selected element in the admin file.
						 
						 breakingLine();
						 System.out.println("*** Candidate Code *** Candiate Name ********* Votes ******");
				    	 
						 breakingLine();
						 System.out.println("\t" +  deleteMemberCode + "\t\t" + deleteMemberName + "\t\t" + deleteMemberVotes );
						 breakingLineFour();
						 
						 System.out.println("Is this member " + "\"" + deleteMemberName + "\""+ " you really want to deletel from the candidates.txt file?"
						 					+ "\nIf it is yes, please enter 'Y', if it is no, please enter 'N': ");
						 
						 input = getInput();
						 
						 if(isNumeric(input) == true) {
							 
							 System.out.println("Only letter are allow to enter here, please try again:");
							 deleteCandidateMember();
							 
						 } else {
							 
							 if(input.equalsIgnoreCase("Y")) {
								 
								 candidates.remove(deleteIndex);
								 vc.saveCandidateData();
								 System.out.println("\nThe selected candidate member has been deleted from the file.");
								 candidatesInfo();
								 breakingLineFour();
								 System.out.println("To continue delete another member, please enter \"D\"."
								 		  + "\nTo go back to previous menu, please enter \"Q\"."
								 		  + "\nTo quit the system, please enter \"Stop\".");
						 
								 input = getInput(); // 
								 
									 if (input.equalsIgnoreCase("D")) {
										 deleteCandidateMember();
									 }
									 else if(input.equalsIgnoreCase("Q")) {
										 candidateFileSetting();
									 }
									 else if(input.equalsIgnoreCase("Stop")) {
										 System.out.println("The system has been stopped.");
									 }
									 else {
										 System.out.println("Invalid input, please try again: ");
										 deleteCandidateMember();
									 }
										 
							}
							else if (input.equalsIgnoreCase("N")) {
								 System.out.println("You have cancelled this operation, and the system will go back to previous menu.");
								 candidateFileSetting();
								 return;
							}
							 else {
								 System.out.println("The entered letter is invalid, please try again: ");
								 deleteCandidateMember();
							 }
									 
						 }
						 
				 	}catch(NullPointerException e) {
						System.out.println("The entered ID is not found, please try again: ");
						deleteCandidateMember();
				 }
			 }
			 else {
				 System.out.println("Invalid input, please try again: ");
				 deleteCandidateMember();
			 }
		}
	}
	
	// ******************************************************************
	
	
	
	// Help session **************************************************** 
	
	// Manage Help. This method manage help menu to jump into 
	// help screen or jump back to the main menu. (New addition)
	private boolean manageHelp()
	{
		boolean helpQuit = false;
		boolean systemQuit = false;
	
		while (!helpQuit){
			
			helpMessageDisplay();
			
		    System.out.println("\nTo go back to the main screen, please enter\"Q\"."
		    				 + "\nTo end the sytem enter \"Stop\": ");
		    
		    String input = getInput(); // throws exception
		    if (input.equalsIgnoreCase("Q")){
		        // go back to the main screen.
		    	helpQuit = true;
		        commenceVoting();
		    }
		    else if(input.equalsIgnoreCase("Stop")){
		        //stop running the system
		    	helpQuit = true;
		        systemQuit = true;
		        System.out.println("Voting System Closed");
		    }
		    else{
		        System.out.println("Invalid input, please re-enter : \n\n");
		    }
		}
		return systemQuit;
	}
	
	// Help information display. (New addition)
	public void helpMessageDisplay() { 
	
		System.out.println("This is the HELP MENU. "
		 					+ "\nHere you can get more information about how to use this voting system.");
		breakingLine();
		System.out.println("-> As a voter,you can vote during the vote period, which is SEVEN days. "
		 					+ "\n   Before the start voting date or after the end voting date, no one can vote for the candidates. "
		 					+ "\n   And during the voting period, a voter needs to input the voter ID and the password,"
		 					+ "\n   then he/she can make a voting.");
		breakingLineFive();
		System.out.println("-> As an administrator, you can edit and maintain this voting with a valid username and password as assigned. "
		 					+ "\n   Such as voting result displaying, candidate file modifying, staff file modifying and even the administrator file modifying."
		 					+ "\n   The time range for voting also needs to be set by an administrator in advance.");
		breakingLineFive();
		System.out.println("-> If there are any other questions needs to be inquried, please contact the administrator around you.");
	}
	
	// ****************************************************************** 
	
	
	// Time setting, checking and displaying session ********************
	
	// time range setting and the range check (New addition)
	// add timeRangeSetting method, to change the voting time and limit it within seven days.
	public void timeRangeSetting() { 
		
	 try {
		 
			System.out.println("Please enter a start date of voting (i.e. 20/10/2019): "
	 					+ "\nNote: The interval days between start date and end date is SEVEN days.");
			startTimeString = getInput(); // NPE error, check it out 2019/05/31 1725
			System.out.println("The start day of voting has been set: " + startTimeString);
			startTime = LocalDate.parse(startTimeString, dtf);
			 
			System.out.println("Please enter a end date of voting (i.e. 27/10/2019): "
			 					+ "\nNote: The interval time between start date and end date is SEVEN days.");
			endTimeString = getInput();
			System.out.println("The end day of voting has been set: " + endTimeString);
			endTime = LocalDate.parse(endTimeString, dtf);
			 
		 } catch(DateTimeParseException e) {
			 
			System.out.println("The entered date is invalid, please try again.");
			timeRangeSetting();
			 
		 }
	}
	
	// add timeRangeCheck to validate the current time is in-between the time range.
	public void timeRangeCheck() {
		
		try { // if the voting date is not set properly, a null pointer exception will be caught.
			
			LocalDate currentDate = LocalDate.now(); // setup today's date
			String currentDateString = dtf.format(currentDate); // convert it to a string.
			System.out.println("Today's date is: " + currentDateString);
			
			boolean isStartDate = currentDate.isAfter(startTime);
			boolean isEndDate = currentDate.isBefore(endTime);
			
			if(isStartDate && isEndDate) {
				System.out.println("Today is available to vote.");
				manageVote();
			}
			else if (!isStartDate && isEndDate) {
				System.out.println("The voting date is coming soon...");
			}
			else if(isStartDate && !isEndDate) {
				System.out.println("The voting season has ended, thank you for your concern. See you next time.");
			}
			else {
				System.out.println("There seems to be a problem with the system date, please contact the system administrator.");
			}
			
		} catch(NullPointerException e) {
			System.out.println("The voting date has not been set properly, please contact the administrator.");
		}
		 
	}
	
	public void timeRangeDisplay() {
	
		 if(startTime == null && endTimeString == null) {
			System.out.println("The voting time has not been set yet. Please set the voting time range.");
			manageAdmin();
		 }
		 else {
		 	System.out.println("The start day of voting is: " + startTimeString);
			System.out.println("The end day of voting is: " + endTimeString);
		 }
	}
	
	// ******************************************************************
	
	
	// General method session *******************************************
	
	//screen input reader. This method is complete and working ok. 
	public String getInput()
	{
		String theInput = "";
	
		try
		{
		    theInput = in.readLine().trim();
		}
		catch(IOException e)
		{
		    System.out.println(e);
		}
		return theInput;
	
	}
	
	// add isNumeric method to check the input is 
	// a number or a string. (New addition)
	public static boolean isNumeric(String str) {
	
		 try {
			 Integer.parseInt(str);
			 return true;
		 } catch(NumberFormatException e) {
			 return false;
		 }
	
	}
	
	//add isValidFullName method to validate the input name is valid or invalid. (New addition)
	public static boolean isValidFullName(String fullName) {
	
		 boolean isValid = false;
		 String fullNameRegex = "^[\\p{L}\\s.'\\-,]+$";
		 Pattern pattern = Pattern.compile(fullNameRegex, Pattern.CASE_INSENSITIVE);
		 Matcher matcher = pattern.matcher(fullName);
		 if (matcher.matches()) {
			 isValid = true;
		 }
		 return isValid;
	 
	}
	
	// set and get the numbers of each group(admin candidate and staff)
	public void setNumberOfAdmin(int numberOfAdmins) {
		this.numberOfAdmins = numberOfAdmins;
	}
	
	public int getNumberOfAdmin() {
	
		return numberOfAdmins;
	}
	
	public void setNumberOfStaff(int numberOfStaffs) {
	
		this.numberOfStaffs = numberOfStaffs;
	}
	
	public int getNumberOfStaff() {
	
		return numberOfStaffs;
	}
	
	public void setNumberOfCandidate(int numberOfCandidates) {
	
		this.numberOfCandidates = numberOfCandidates;
	}
	
	public int getNumberOfCandidate() {
	
		return numberOfCandidates;
	}
	
	public void breakingLine() {
	
		System.out.println("***********************************************************");
	}
	
	public void breakingLineTwo() {
	
		System.out.println("**************************************");
	}
	
	public void breakingLineThree() {
	
		System.out.println("\t ------------------------------------------");
	}
	
	public void breakingLineFour() {
	
		System.out.println("---------------------------------------------------");
	}
	
	public void breakingLineFive() {
	
		System.out.println("------------------------------------------------------------------------------------------------------");
	}

// ******************************************************************

}

