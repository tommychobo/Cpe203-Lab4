import java.util.HashSet;

public class Game {
    private HashSet<Integer> winningNums;
    public Game(){
        winningNums = new HashSet<Integer>(5);
    }

    public void winningLotNumber(){
        winningNums.clear();
        int numCount = 0;
        while(numCount < 5){
            if(winningNums.add((int)(Math.random()*42)+1)){
                numCount++;
            }
        }
    }

    public int numMatches(HashSet<Integer> playerVals){
        int count = 0;
        Object[] pVals = playerVals.toArray();
        for(Object o : pVals){
            if(winningNums.contains((Integer)o)){
                count++;
            }
        }
        return count;
    }

    public float getWinnings(CommunityMember cm){
        int matches = numMatches(cm.getLottoNums());
        switch(matches){
            case 1:
                return 0.0f;
            case 2:
                return 1.0f;
            case 3:
                return 10.86f;
            case 4:
                return 197.53f;
            case 5:
                return 212534.83f;
        }
        //matches is zero:
        return -1.0f;
    }


    public void winningNumCheck(){
        System.out.println("Num items: "+winningNums.size());
        for(int i = 1; i <= 42; i++){
            if(winningNums.contains(i)){
                System.out.println(i);
            }
        }

        //testing a few players
        for(int i = 0; i < 20; i++) {
            CommunityMember cm = new CommunityMember(CMemberKind.WELL_PAID, 100.5f);
            cm.playRandom();
            System.out.println("Number of matches: "+numMatches(cm.getLottoNums()));
        }
    }

    public HashSet<Integer> getWinningNums() {
        return winningNums;
    }
}
