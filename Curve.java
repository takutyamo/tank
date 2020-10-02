package tank;

import java.awt.Point;

public class Curve implements Runnable {
	Tank tank;
	Thread th;
	int rotatDir;
	int angle;

	Curve(Tank tank, int angle, int rotatDir) {
		this.tank = tank;
		this.angle = angle;
		this.rotatDir = rotatDir;
		th = new Thread(this);
		th.start();
	}

	@Override
	public void run() {
		int num = 0;
		Point op = new Point();
		op.x = tank.p.x;
		op.y = tank.p.y;
		double dx = tank.p.x;
		double dy = tank.p.y;
		while(num < angle) {
			tank.angle = tank.angle + this.rotatDir;
			dx += tank.v * Math.cos(tank.w * Math.toRadians(tank.angle));
			dy += tank.v * Math.sin(tank.w * Math.toRadians(tank.angle));
			tank.p.x = (int) Math.floor(dx);
			tank.p.y = (int) Math.floor(dy);
			num++;
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		tank.Tnotifly();
	}
}
