import java.util.*;

public class CommunityMember {
	private CMemberKind kind;
	private float money;
	private ArrayList<Float> moneyOverTime;
    Random random = new Random();
	private int red, green, blue;
	private HashSet<Integer> lottoNums;

	//constructor
	public CommunityMember(CMemberKind pK, float startFunds) {
		this.kind = pK;
		this.money = startFunds;
		this.moneyOverTime = new ArrayList<Float>();
		this.moneyOverTime.add(startFunds);
		this.red = this.random.nextInt(100);
		this.green = this.random.nextInt(100);
		this.blue = this.random.nextInt(100);
		lottoNums = new HashSet<Integer>(5);
		//overall blue tint to POORLY_PAID	
		if (this.kind == CMemberKind.WELL_PAID) {
			this.red += 100;
		} else {
			this.blue += 100;
		}
	}

	public void playRandom(){
		int numCount = 0;
		while(numCount < 5){
			if(lottoNums.add((int)(Math.random()*42)+1)){
				numCount++;
			}
		}
	}

	public int getR() { return this.red; }
	public int getG() { return this.green; }
	public int getB() { return this.blue; }
	public float getMoney() { return this.money; }
	public void addMoney(float m){
		this.money += m;
	}
	public CMemberKind getKind() { return this.kind; }
	public ArrayList<Float> getFunds() { return this.moneyOverTime; }
	public HashSet<Integer> getLottoNums(){
		return lottoNums;
	}
	public void updateMoneyEachYear() {
		this.moneyOverTime.add(this.money);
	}

}
