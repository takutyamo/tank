package tank;

import java.awt.Point;

public class ChaseLos extends Thread {
	Thread th;
	int currentStep;
	Tank tank;
	Point[] pos = new Point[1001];

	public ChaseLos(int n, Tank tank, Point[] p) {
		this.currentStep = n;
		this.tank = tank;
		this.pos = p;
		th = new Thread(this);
		th.start();
	}

	public void run() {
		while(true) {
			if(pos[currentStep] == null) {
				tank.Tnotifly();
			return;
			}else if(tank.p.x < 100 || tank.p.x > 900 || tank.p.y < 100 || tank.p.y > 700){
				tank.p.x = pos[currentStep-2].x;
				tank.p.y = pos[currentStep-2].y;
				tank.turnRight(90);
				tank.going(1);
				tank.Tnotifly();
				return;
				}

			//System.out.println("a");
			tank.p.x = pos[currentStep].x;
			tank.p.y = pos[currentStep].y;
			//car.setRectangle();
			currentStep++;
			try {
				Thread.sleep(30);
			} catch (InterruptedException e) {
				// TODO 閾ｪ蜍慕函謌舌＆繧後◆ catch 繝悶Ο繝�繧ｯ
				e.printStackTrace();
			}
		}
	}

}
