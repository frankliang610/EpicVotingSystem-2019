package EpicVotingSystem;

/**
 * File Name: VotingInterfaceUnitTest
 * Student ID: 21801886
 * author: Feng Liang
 * Date :
 * Description :
 */

import static org.junit.Assert.*;

import org.junit.Test;

public class VotingInterfaceUnitTest {

	@Test
	public void testVC() {
		
		VotingInterface vi = new VotingInterface();
		
		
		vi.validateAdmin("Alan Dixon", "Alan Dixoh");
		vi.validateAdmin("Arlen Howard", "Arlen Howard");
		
		VotingInterface.isNumeric("5");
		VotingInterface.isValidFullName("Karol Piper");
		
		vi.setNumberOfAdmin(200);
		assertEquals(200, vi.getNumberOfAdmin());
		
		vi.setNumberOfCandidate(20);
		assertEquals(20, vi.getNumberOfCandidate());
		
		vi.setNumberOfStaff(50);
		assertEquals(50, vi.getNumberOfStaff());
		
		
		
	}

}
