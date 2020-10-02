package tank;

public class Tank1 extends Tank {
	int i = 0;
	public void action() {
		if(i == 0) {
			turnRight(90);
			Bullet();
			turnLeft(45);
			Bullet();
			turnRight(22);
			Bullet();
		}
		i++;
		
		//-----------------------------------------------------------------------
	}
	Tank1(int x, int y, int num) {
		super(x, y, num);
	}
}
