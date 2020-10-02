package tank;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

public class CurveBall implements Runnable {
	final int SIZE = 10;
	Point p;

	Thread th;

	CurveBall(int x ,int y) {
		p = new Point();
		p.x = x;
		p.y = y;

		th = new Thread(this);
		th.start();
	}

	public void draw(Graphics g) {
		g.setColor(Color.cyan);
		g.fillOval(this.p.x, this.p.y, SIZE, SIZE);
	}

	@Override
	public void run() {
		long time = System.currentTimeMillis();
		double x = p.x;
		double y;
		double dx = 0.1;
		while(true) {
			long currentTime = System.currentTimeMillis();
			if(currentTime - time >= 100L) {
				time = currentTime;
				x += dx;
				y = -100*Math.sin(0.01*x) + p.y;
				this.p.x = (int)Math.floor(x);
				this.p.y = (int)Math.floor(y);
				System.out.println(x);
				System.out.println(y);
			} else {
				continue;
			}
		}
	}
}
