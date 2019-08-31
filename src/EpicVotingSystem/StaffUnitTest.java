package EpicVotingSystem;

/**
 * File Name: StaffUnitTest
 * Student ID: 21801886
 * author: Feng Liang
 * Date :
 * Description :
 */

import static org.junit.Assert.*;

import org.junit.Test;

public class StaffUnitTest {

	@Test
	public void testConstructor() {
		
		Staff staff = new Staff(201211,"Steve Lane",1,"lane11","06/06/2019 16:13:20");
		
		assertEquals(201211, staff.getId());
		assertEquals("Steve Lane", staff.getName());
		assertEquals(1, staff.hasVoted());
		assertEquals("lane11", staff.getPassword());
		assertEquals("06/06/2019 16:13:20", staff.getTimeStamp());

	}
	
	@Test
	public void testSetMethod()	{
		
		Staff staff = new Staff(0, null, 0, null, null);
		
		staff.setId(201220);
		staff.setName("Jack Ryan");
		staff.setVoted();
		staff.setPassword("ryan20");
		staff.setTimeStamp("06/09/2019 10:15:59");
		
		assertEquals(201220, staff.getId());
		assertEquals("Jack Ryan", staff.getName());
		assertEquals(1, staff.hasVoted());
		assertEquals("ryan20", staff.getPassword());
		assertEquals("06/09/2019 10:15:59", staff.getTimeStamp());
		
		
	}

}
