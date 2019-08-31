package EpicVotingSystem;
/**
 * File Name: Administrator
 * Student ID: 21801886
 * author: Feng Liang
 * Date :
 * Description :
 */
public class Administrator {
	
	private int id;
	private String name;
	private String username;
	private String password;
	
	public Administrator (int id, String name, String username, String password) 
	{
		this.id = id;
        this.name = name;
        this.username = username;
        this.password = password;
        
	}
	
	public void setID(int id) 
	{
		this.id = id;
	}
	
	public void setName(String name)
	{
		this.name = name;
	}
	
	public void setUserName(String username)
	{
		this.username = username;
	}
	
	public void setPassword(String password)
	{
		this.password = password;
	}
	
	public int getID()
	{
		return id;
	}
	
	public String getName() {
		return name;
	}
	
	public String getUserName()
	{
		return username;
	}
	
	public String getPassword()
	{
		return password;
	}
	
}
