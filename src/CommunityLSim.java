import java.util.*;

/**
 * A class that represents the community and simulates
 * the running of the lottery.
 */
public final class CommunityLSim {

    ArrayList<CommunityMember> communityMembers;
    ArrayList<Integer> playerIndices;
    Random random = new Random();
    //you will need to add more instance variables

    /**
     * Creates a new Community with the specified number of people
     * @param numP The number of people in the community
     */
    public CommunityLSim( int numP) {
        //create the players
        this.communityMembers = new ArrayList<>();
        this.playerIndices = new ArrayList<>();
        for (int i = 0; i < numP; i++) {
            if (i < numP /2.0)
                this.communityMembers.add(new CommunityMember(CMemberKind.POORLY_PAID, (float)(99+Math.random())));
            else
                this.communityMembers.add(new CommunityMember(CMemberKind.WELL_PAID, (float)(100.1+Math.random())));
        }

    }

    public int getSize() {
        return this.communityMembers.size();
    }

    public CommunityMember getPlayer(int i) {
        return this.communityMembers.get(i);
    }

    /**
     * Give each community member some pocket change.
     * Give POORLY_PAID community members 0.03f, and give
     * WELL_PAID community members 0.1f.
     */
    // TODO: Implement this method.
    public void addPocketChange() {
        for(CommunityMember cm : communityMembers){
            if(cm.getKind() == CMemberKind.WELL_PAID){
                cm.addMoney(0.1f);
            }
            else{
                cm.addMoney(0.03f);
            }
        }
    }

    // TODO: Write a method that computes a new list of lottery players,
    //  choosing from the list of community members.
    //  You will likely want to change this method signature.
    private void reDoWhoPlays() {
        playerIndices.clear();
        Object[] poorPpl = randomUniqIndx((int)(communityMembers.size()*0.3),0,
                (int)(communityMembers.size()*0.5+0.5));
        Object[] richPpl = randomUniqIndx((int)(communityMembers.size()*0.2), (int)(communityMembers.size()*0.5+0.5),
                communityMembers.size());
        for(Object o: poorPpl){
            playerIndices.add((Integer) o);
        }
        for(Object o: richPpl){
            playerIndices.add((Integer) o);
        }
    }

    /* generate some random indices for who might play the lottery
        in a given range of indices */

    /**
     * Generate a number of random indices within an interval
     * @param numI The number of random unique indices to generate
     * @param startRange The lower bound of the interval, inclusive
     * @param endRange The upper bound of the interval, exclusive
     */
    // TODO: Implement this method. You will likely want to change this
    //  method signature.
    public Object[] randomUniqIndx(int numI, int startRange, int endRange) {
        HashSet<Integer> hash = new HashSet<>();
        int count = 0;
        while(count < numI){
            if(hash.add((int)(Math.random()*(endRange-startRange))+startRange)){
                count++;
            }
        }
        return hash.toArray();
    }

    public void scholarship(float amt){
        double num = Math.random();
        int index = 0;
        if(num < 0.3){
            index = (Integer)(randomUniqIndx(1,0, (int)(communityMembers.size()*0.5+0.5))[0]);
        }
        else{
            index = (Integer)(randomUniqIndx(1,(int)(communityMembers.size()*0.5+0.5),
                    communityMembers.size())[0]);
        }
        communityMembers.get(index).addMoney(amt);
    }

    public void simulateYears(int numYears) {
        // Simulate the lottery (see steps below)
        for (int year=0; year < numYears; year++) {
            // TODO Add pocket change for all community members, whether or not they're playing.
            addPocketChange();
            // TODO Re-compute the players who are playing the lottery in the current year.
            reDoWhoPlays();
            // TODO Simulate the lottery for those players.
            Game lotto = new Game();
            for(int index : playerIndices){
                communityMembers.get(index).playRandom();
                float winnings = lotto.getWinnings(communityMembers.get(index));
                communityMembers.get(index).addMoney(winnings);
                if(Math.abs(1.0f + winnings) < 0.0000001){
                    scholarship(1.0f);
                }
            }

            // 4. Update everyone's money for that year.
            for (CommunityMember cm : this.communityMembers) {
                cm.updateMoneyEachYear();
            }
        }
        moneyPrintout(numYears);
    }

    public float mostMoney(int year){
        float money = communityMembers.get(0).getPastMoney(year);;
        for(CommunityMember cm : communityMembers){
            if(cm.getPastMoney(year) > money){
                money = cm.getPastMoney(year);
            }
        }
        return money;
    }
    public float leastMoney(int year){
        float money = communityMembers.get(0).getPastMoney(year);
        for(CommunityMember cm : communityMembers){
            if(cm.getPastMoney(year) < money){
                money = cm.getPastMoney(year);
            }
        }
        return money;
    }


    public void moneyPrintout(int numYears){
        for(int i = 0; i < numYears; i++){
            System.out.println("After year "+i+":");
            System.out.println("The person with the most money has: "+mostMoney(i));
            System.out.println("The person with the least money has: "+leastMoney(i));
        }
    }
}
