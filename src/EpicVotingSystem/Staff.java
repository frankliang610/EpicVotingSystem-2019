/**
 * Note: This class is complete. Don't change code in this class,
 * unless you want to add more functionality  for Staff object
 */
package EpicVotingSystem;
/**
 * File Name : Staff
 * Student ID: 21801886
 * author: Feng Liang
 * Date :
 * Description :
 */
public class Staff
{
    private int id;
    private String name;
    private int voted; //has the staff voted
    private String password; // add password for each staff
    private String timestamp = null; // add timestamp when staff voting

    public Staff(int id, String name, int voted, String password, String timestamp)
    {
            this.id = id;
            this.name = name;
            this.voted = voted;
            this.password = password;
            this.timestamp = timestamp;
    }

    public void setId(int id)
    {
       this.id = id;
    }

    public void setName(String name)
    {
            this.name = name;
    }

    public void setVoted()
    {
            this.voted = 1;
    }
    
    public void setPassword(String password) // add password setting method
    {
    		this.password = password;
    }
    public int getId()
    {
       return id;
    }

    public String getName()
    {
            return name;
    }

    public int hasVoted() // add
    {
            return voted;
    }
    
    public String getPassword() // add password getting method
    {
    	return password;
    }
    
    public void setTimeStamp(String timestamp) // add
    {
    	this.timestamp = timestamp;
    }
    
    public String getTimeStamp() // add
    {
    	return timestamp;
    }

}
