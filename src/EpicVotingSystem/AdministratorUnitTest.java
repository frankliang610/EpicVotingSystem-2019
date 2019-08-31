package EpicVotingSystem;

/**
 * File Name: AdministratorUnitTest
 * Student ID: 21801886
 * author: Feng Liang
 * Date :
 * Description :
 */

import static org.junit.Assert.*;

import org.junit.Test;

public class AdministratorUnitTest {

	@Test
	public void testContructor() {
		
		Administrator admin = new Administrator(110, "Debbie Hill", "admindebbie", "hill011");
		
		assertEquals(110, admin.getID());
		assertEquals("Debbie Hill", admin.getName());
		assertEquals("admindebbie", admin.getUserName());
		assertEquals("hill011", admin.getPassword());
		
	}
	
	@Test
	public void testSetMethod() {
		Administrator admin = new Administrator(0, null, null, null);
		admin.setID(109);
		admin.setName("Roxy Lopez");
		admin.setUserName("adminroxy");
		admin.setPassword("lopez901");
		
		assertEquals(109, admin.getID());
		assertEquals("Roxy Lopez", admin.getName());
		assertEquals("adminroxy", admin.getUserName());
		assertEquals("lopez901", admin.getPassword());
	}
	

}
