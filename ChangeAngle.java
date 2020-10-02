package tank;

public class ChangeAngle extends Thread {
	Thread th;
	Tank tank;
	int angle;
	int rotatDir;

	public ChangeAngle(Tank tank, double d, int rd) {
		this.tank = tank;
		this.angle = (int)d;
		this.rotatDir = rd;
		th = new Thread(this);
		th.start();
	}

	public void run() {
		int num = 0;
		while(num < angle && MainPanel.tankList[tank.n].dl) {
			tank.angle = tank.angle + this.rotatDir;
			num++;
			if(tank.angle >= 360 || tank.angle <= -360) {tank.angle = 0; }
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		MainPanel.tankList[tank.n].Tnotifly();
	}
}