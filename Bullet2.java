package tank;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;

public class Bullet2 extends Thread {
	final int SIZE = 10;
	int MaxPath = 1501;
	Point p;
	Point pos2;
	int currentStep;
	Point[] pos = new Point[MaxPath];
	Thread th;
	double angle;
	Image Bullet;

	Bullet2(int bx, int by, double angle) {
		p = new Point();
		p.x = bx;
		p.y = by;
		Bullet = MainPanel.getBulletImage();
		th = new Thread(this);
		currentStep = 1;
		this.angle = angle;
		int n = 1000;
		pos2 = new Point();

		double radian = angle * Math.PI/180;
		double y = Math.sin(radian) * n;
		double x = Math.cos(radian) * n;
		//System.out.println(p.x + "/" + p.y);
		pos2 = new Point();
		pos2.x = (int) (p.x + x);//遘ｻ蜍募�医�ｮ蠎ｧ讓�
		pos2.y = (int) (p.y + y);
		//System.out.println(pos2);

		currentStep = 1;

		if(pos2.x == p.x && pos2.y == p.y) {return;}

		int nextX = (int) p.x;	//閾ｪ蛻�縺ｮ菴咲ｽｮ
		int nextY = (int) p.y;
		int deltaX = pos2.x - p.x;	//逶ｸ謇九�ｮ菴咲ｽｮ縺ｨ閾ｪ蛻�縺ｮ菴咲ｽｮ縺ｮ蟾ｮ
		int deltaY = pos2.y - p.y;
		int stepX,stepY;
		int step;					//譬ｼ邏阪☆繧矩�榊�励�ｮ豺ｻ縺亥ｭ�
		int fraction;

		for(step = 0; step < MaxPath;step++) {//蛻晄悄蛹�
			pos[step] = null;
		}
		step = 0;
		if(deltaX < 0) {stepX = -1;}else {stepX = 1;}
		if(deltaY < 0) {stepY = -1;}else {stepY = 1;}
		deltaX = Math.abs(deltaX * 2);//邨ｶ蟇ｾ蛟､繧呈ｱゅａ繧�
		deltaY = Math.abs(deltaY * 2);

		pos[step] = new Point(nextX,nextY);
		step++;

		if(deltaX > deltaY) {
			fraction = deltaY - deltaX/2;	//fraction縺ｮ蛻晄悄蛟､
			while(nextX != pos2.x) {
				if(fraction >= 0) {
					nextY += stepY;
					fraction -= deltaX;
				}
				nextX += stepX;
				fraction += deltaY;
				pos[step] = new Point(nextX,nextY);
				step++;
			}
		}else {
			fraction = deltaX -deltaY/2;

			while(nextY != pos2.y) {
				if(fraction >= 0) {
					nextX += stepX;
					fraction -= deltaY;
				}
				nextY += stepY;
				fraction += deltaX;
				pos[step] = new Point(nextX,nextY);
				step++;
			}
		}
		th.start();
	}
	public void draw(Graphics g) {
		//g.setColor(Color.yellow);
		g.drawImage(Bullet,p.x-SIZE/2, p.y-SIZE/2, SIZE, SIZE,null);
	}

	public void going() {
		int n = 1000;
		pos2 = new Point();

		double radian = angle * Math.PI/180;
		double y = Math.sin(radian) * n;
		double x = Math.cos(radian) * n;
		//System.out.println(p.x + "/" + p.y);
		pos2 = new Point();
		pos2.x = (int) (p.x + x);//遘ｻ蜍募�医�ｮ蠎ｧ讓�
		pos2.y = (int) (p.y + y);
		//System.out.println(pos2);

		currentStep = 1;

		if(pos2.x == p.x && pos2.y == p.y) {return;}

		int nextX = (int) p.x;	//閾ｪ蛻�縺ｮ菴咲ｽｮ
		int nextY = (int) p.y;
		int deltaX = pos2.x - p.x;	//逶ｸ謇九�ｮ菴咲ｽｮ縺ｨ閾ｪ蛻�縺ｮ菴咲ｽｮ縺ｮ蟾ｮ
		int deltaY = pos2.y - p.y;
		int stepX,stepY;
		int step;					//譬ｼ邏阪☆繧矩�榊�励�ｮ豺ｻ縺亥ｭ�
		int fraction;

		for(step = 0; step < MaxPath;step++) {//蛻晄悄蛹�
			pos[step] = null;
		}
		step = 0;
		if(deltaX < 0) {stepX = -1;}else {stepX = 1;}
		if(deltaY < 0) {stepY = -1;}else {stepY = 1;}
		deltaX = Math.abs(deltaX * 2);//邨ｶ蟇ｾ蛟､繧呈ｱゅａ繧�
		deltaY = Math.abs(deltaY * 2);

		pos[step] = new Point(nextX,nextY);
		step++;

		if(deltaX > deltaY) {
			fraction = deltaY - deltaX/2;	//fraction縺ｮ蛻晄悄蛟､
			while(nextX != pos2.x) {
				if(fraction >= 0) {
					nextY += stepY;
					fraction -= deltaX;
				}
				nextX += stepX;
				fraction += deltaY;
				pos[step] = new Point(nextX,nextY);
				step++;
			}
		}else {
			fraction = deltaX -deltaY/2;

			while(nextY != pos2.y) {
				if(fraction >= 0) {
					nextX += stepX;
					fraction -= deltaY;
				}
				nextY += stepY;
				fraction += deltaX;
				pos[step] = new Point(nextX,nextY);
				step++;
			}
		}
	}
	public boolean hit() {
        Point[] cp = new Point[4];
        cp[0] = new Point(this.p.x + this.SIZE / 2, this.p.y);
        cp[1] = new Point(this.p.x + this.SIZE / 2, this.p.y + this.SIZE/2);
        cp[2] = new Point(this.p.x - this.SIZE / 2, this.p.y);
        cp[3] = new Point(this.p.x, this.p.y - this.SIZE / 2);
        for(int i = 0; i < cp.length; i++) {
            if(MainPanel.map[(int)(cp[i].y / 50)][(int)(cp[i].x / 50)] == 1) {
                System.out.println(cp[1]);
                return true;
            }
        }
        return false;
    }
/*public void run() {
        double dx = this.p.x;
        double dy = this.p.y;
        while(true) {
            dx += this.v * Math.cos(Math.toRadians(this.angle));
            dy += this.v * Math.sin(Math.toRadians(this.angle));
            //MainPanel.tankList[tank.n].p.x = (int) Math.floor(dx);
            //MainPanel.tankList[tank.n].p.y = (int) Math.floor(dy);
            this.p.x = (int) Math.floor(dx);
            this.p.y = (int) Math.floor(dy);
            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }*/

	public void run() {
		while(true) {
			if(pos[currentStep] == null) {
				return;}

			//System.out.println("a");
			this.p.x = pos[currentStep].x;
			this.p.y = pos[currentStep].y;
			//car.setRectangle();
			currentStep++;

			try {
				Thread.sleep(3);
			} catch (InterruptedException e) {
				// TODO 閾ｪ蜍慕函謌舌＆繧後◆ catch 繝悶Ο繝�繧ｯ
				e.printStackTrace();
			}
		}
	}
}
