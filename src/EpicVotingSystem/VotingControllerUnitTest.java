package EpicVotingSystem;

/**
 * File Name: VotingControllerUnitTest
 * Student ID: 21801886
 * author: Feng Liang
 * Date :
 * Description :
 */

import static org.junit.Assert.*;

import org.junit.Test;

public class VotingControllerUnitTest {

	@Test
	public void testVC() {
		VotingController vc = new VotingController();
		
		vc.loadAdministratorData();
		vc.loadCandidateData();
		vc.loadStaffData();
		
		vc.getAdministrator(105);
		vc.getAdministrator("adminarlen");
		vc.getCandidate(2);
		vc.getStaff(201212);
		vc.getTotalVoters();
		vc.recordVote();
		
		vc.saveAdministratorData();
		vc.saveCandidateData();
		vc.saveStaffData();
		
		vc.timeStamp();
		
		
	}

}
