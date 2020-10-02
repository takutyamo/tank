package tank;

import java.awt.Point;

public class StraightInFacing extends Thread {
	Tank tank;
	Thread th;
	int addis;

	public StraightInFacing(Tank t, int n) {
		tank = t;
		addis = n;
		th = new Thread(this);
		th.start();
	}

	public void run() {
		Point op = new Point();
		op.x = tank.p.x;
		op.y = tank.p.y;
		double dx = tank.p.x;
		double dy = tank.p.y;
		while(Math.pow(Math.pow(dx - op.x, 2) + Math.pow(dy - op.y, 2), 0.5) <= addis) {
			dx += tank.v * Math.cos(Math.toRadians(tank.angle));
			dy += tank.v * Math.sin(Math.toRadians(tank.angle));
			//MainPanel.tankList[tank.n].p.x = (int) Math.floor(dx);
			//MainPanel.tankList[tank.n].p.y = (int) Math.floor(dy);
			tank.p.x = (int) Math.floor(dx);
			tank.p.y = (int) Math.floor(dy);
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		tank.Tnotifly();
		//MainPanel.tankList[tank.n].Tnotifly();
	}
}
