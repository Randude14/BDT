package bdt.tower;

public class BombTower extends Tower {

	public BombTower() {

	}

	public int getID() {
		return 6;
	}

	public String getTowerName() {
		return "BombTower";
	}
	
	public boolean canHaveNearbyTowers() {
		return false;
	}

}
