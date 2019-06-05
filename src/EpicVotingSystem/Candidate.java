/**
 * 
 * 
 */

package EpicVotingSystem;
/**
 * File Name : Candidate
 * author : Frank Liang
 * Date :
 * Description :
 */
public class Candidate
{
    int candidateCode = 999;//999 is not an eligible candidate
    String name = null;
    int votes = 0; //total votes

    //constructor
    public Candidate(int candidateCode, String name, int votes)
    {
        this.candidateCode = candidateCode;
        this.name = name;
        this.votes = votes;
    }

    public int getCandidateCode ()
    {
        return candidateCode;
    }

    public String getName()
    {
        return  name;
    }

    public int getVotes()
    {
        return  votes;
    }

    public void addVote()
    {
        votes++;
    }

}
