package EpicVotingSystem;

/**
 * File Name: CandidateUnitTest
 * Student ID: 21801886
 * author: Feng Liang
 * Date :
 * Description :
 */

import static org.junit.Assert.*;

import org.junit.Test;

public class CandidateUnitTest {

	@Test
	public void testConstructor() {
		Candidate can = new Candidate(7,"Karol Piper", 55);
		
		assertEquals(7, can.getCandidateCode());
		assertEquals("Karol Piper", can.getName());
		assertEquals(55, can.getVotes());
		
	}
	
	@Test
	public void testAddVoteMethod() {
		Candidate can = new Candidate(0, null, 0);
		can.addVote();
		
		assertEquals(1, can.getVotes());
	}

}
