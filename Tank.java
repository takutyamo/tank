package tank;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.geom.AffineTransform;
import java.util.LinkedList;

import javax.swing.ImageIcon;

public class Tank extends Thread {
	Point p;
	Point pos2;
	int MaxPath = 1001;
	Point[] path = new Point[MaxPath];
	final int size = 40;
	double angle = 0.0;
	Thread th;
	int count = 0;
	int n;
	int flag = 0;
	int currentStep;
	int Hp = 2;
	int num;
	int[] tankList;

	final int LINE_RADER_SIZE = 30;
	public int v = 1;
	public int w = 1;

	public boolean dl = true;

	LinkedList<Bullet> blist = new LinkedList<Bullet>();
	LinkedList<CurveBall> clist = new LinkedList<CurveBall>();

	Image image;

	Tank(int x, int y, int num) {
		this.p = new Point();
		this.p.x = x;
		this.p.y = y;
		this.n = num;

		ImageIcon icon = new ImageIcon(getClass().getResource("tank2.png"));
		image = icon.getImage();

		th = new Thread(this);
		th.start();
	}

	public void draw(Graphics g) {
		Graphics2D g2 = (Graphics2D)g;
		AffineTransform at = g2.getTransform();
		at.setToRotation(Math.toRadians(angle), this.p.x + (this.size-10) / 2, this.p.y + (this.size-10) / 2);
		g2.setTransform(at);

		g2.drawImage(image, this.p.x-14, this.p.y-14, this.size+14, this.size+14, null);
		//g2.setColor(Color.RED);
		//g2.fillRect(this.p.x, this.p.y, this.size+10, this.size+10);
	}

	public void Bullet() {
		Bullet b = new Bullet(p.x + size / 2, p.y + size / 2, angle);
		blist.add(b);
	}

	public void Bullet(int angle) {
		Bullet b = new Bullet(p.x, p.y, this.angle + angle);
		blist.add(b);
	}

	public void curveBall() {
		CurveBall cb = new CurveBall(this.p.x, this.p.y);
		clist.add(cb);
	}

	public void turnRight(int angle) {
		ChangeAngle ca = new ChangeAngle(this, angle, 1);
		Twait();
		ca = null;
	}

	public void turnLeft(int angle) {
		ChangeAngle ca = new ChangeAngle(this, angle, -1);
		Twait();
		ca = null;
	}

	public void rightCurve(int angle) {
		Curve c = new Curve(this, angle, 1);
		Twait();
		c = null;
	}

	public void leftCurve(int angle) {
		Curve c = new Curve(this, angle, -1);
		Twait();
		c = null;
	}

	public void goStraight(int n) {
		StraightInFacing sif = new StraightInFacing(this, n);
		Twait();
		sif = null;
	}

	public int cordinate() {
		if(p.x < MainPanel.WIDTH/2 && p.y < MainPanel.HEIGHT/2) {
			return 1;
		} else if(p.x > MainPanel.WIDTH/2 && p.y < MainPanel.HEIGHT/2) {
			return 2;
		} else if(p.x < MainPanel.WIDTH/2 && p.y > MainPanel.HEIGHT/2) {
			return 4;
		} else if(p.x > MainPanel.WIDTH/2 && p.y > MainPanel.HEIGHT/2) {
			return 3;
		}
		return 0;
	}

	public double disCenter() {
        return Math.pow(Math.pow(this.p.x + this.size / 2 - MainPanel.WIDTH / 2, 2) + Math.pow(-this.p.y + -this.size / 2 + MainPanel.HEIGHT / 2, 2), 0.5);
    }

    public void faceCenter() {
        double centerAngle = Math.toDegrees(Math.PI / 2 - Math.asin((-this.p.y - this.size / 2 + MainPanel.HEIGHT / 2) / disCenter()));
        ChangeAngle ca = new ChangeAngle(this, -this.angle + (-this.p.y -this.size + MainPanel.HEIGHT / 2) / Math.abs(-this.p.y -this.size + MainPanel.HEIGHT / 2) * 90 + (this.p.x + this.size / 2 - MainPanel.WIDTH / 2) * (-this.p.y -this.size / 2 + MainPanel.HEIGHT / 2) / Math.abs((this.p.x + this.p.x - MainPanel.WIDTH / 2) * (-this.p.y - this.p.y + MainPanel.HEIGHT / 2)) * centerAngle, (int)(-this.p.y -this.size + MainPanel.HEIGHT / 2) / Math.abs(-this.p.y -this.size + MainPanel.HEIGHT / 2));
        System.out.println(centerAngle);
        Twait();
        System.out.println("a"+this.angle);
        ca = null;
    }

    public void Hello() {
    	System.out.println("Hello");
    }

	public void going(int n) {
		pos2 = new Point();

		double radian = angle * Math.PI/180;
		double y = Math.sin(radian) * n;
		double x = Math.cos(radian) * n;
		System.out.println(p.x + "/" + p.y);
		pos2 = new Point();
		pos2.x = (int) (p.x + x);
		pos2.y = (int) (p.y + y);

		currentStep = 1;

		if(pos2.x == p.x && pos2.y == p.y) {return;}

		int nextX = (int) p.x;
		int nextY = (int) p.y;
		int deltaX = pos2.x - p.x;
		int deltaY = pos2.y - p.y;
		int stepX,stepY;
		int step;
		int fraction;

		for(step = 0; step < MaxPath;step++) {
			path[step] = null;
		}
		step = 0;
		if(deltaX < 0) {stepX = -1;}else {stepX = 1;}
		if(deltaY < 0) {stepY = -1;}else {stepY = 1;}
		deltaX = Math.abs(deltaX * 2);
		deltaY = Math.abs(deltaY * 2);

		path[step] = new Point(nextX,nextY);
		step++;

		if(deltaX > deltaY) {
			fraction = deltaY - deltaX/2;
			while(nextX != pos2.x) {
				if(fraction >= 0) {
					nextY += stepY;
					fraction -= deltaX;
				}
				nextX += stepX;
				fraction += deltaY;
				path[step] = new Point(nextX,nextY);
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
				path[step] = new Point(nextX,nextY);
				step++;
			}
		}
		ChaseLos c = new ChaseLos(currentStep,this,path);
		Twait();
		c = null;
	}

	public void action() {

	}

	synchronized public void Twait() {
		try {
			wait();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	synchronized public void Tnotifly() {
		notify();
	}

	public void damage() {
        this.Hp -= 20;
        if(this.Hp <= 0) {
            dl = false;
        }
	}

	 public int coordinate() {
         if(p.x < MainPanel.WIDTH/2 && p.y < MainPanel.HEIGHT/2) {return 1;}
         else if(p.x > MainPanel.WIDTH/2 && p.y < MainPanel.HEIGHT/2) {return 2;}
         else if(p.x < MainPanel.WIDTH/2 && p.y > MainPanel.HEIGHT/2) {return 3;}
         else if(p.x > MainPanel.WIDTH/2 && p.y > MainPanel.HEIGHT/2) {return 4;}
         return 0;
}

public int Radian() {
         int x2 = p.x;
         int y2 = p.y;
         double radian = Math.atan2(y2-MainPanel.WIDTH/2, x2-MainPanel.HEIGHT/2);
         double degree = radian * 180/Math.PI;
         System.out.println(degree);
         return (int)degree;
}

public boolean reader() {
         for(int i = 0;i < 2;i++) {
                      if(i != num && MainPanel.tankList[i] != null && check(this,MainPanel.tankList[i])) {
                                    return true;
                      }
         }
         return false;
}

public boolean lineRader() {
	for(int i = 0; i < MainPanel.tankList.length; i++) {
		if(MainPanel.tankList[i] == this || MainPanel.tankList[i] == null || n == i) {
			continue;
		}
		double x = (MainPanel.tankList[i].p.x + MainPanel.tankList[i].size / 2 - (this.p.x + this.size / 2)) * Math.cos(Math.toRadians(-this.angle)) - (MainPanel.tankList[i].p.y + MainPanel.tankList[i].size / 2 - (this.p.y + this.size / 2)) * Math.sin(Math.toRadians(-this.angle)) + this.p.x + this.size / 2;
		double y = (MainPanel.tankList[i].p.x + MainPanel.tankList[i].size / 2 - (this.p.x + this.size / 2)) * Math.sin(Math.toRadians(-this.angle)) + (MainPanel.tankList[i].p.y + MainPanel.tankList[i].size / 2 - (this.p.y + this.size / 2)) * Math.cos(Math.toRadians(-this.angle)) + this.p.y + this.size / 2;
		if(this.p.y + this.size / 2 - LINE_RADER_SIZE <= y  && y <= this.p.y + this.size / 2 + LINE_RADER_SIZE && this.p.x <= x) {
			System.out.println("iru");
			return true;
		}
	}
	return false;
}

public boolean check(Tank tank1, Tank tank2) {
         int tankx = tank1.p.x;
         int tanky = tank1.p.y;
         int tankr = tank1.size/2 + 200;
         int bx = tank2.p.x;
         int by = tank2.p.y;
         int br = tank2.size/2;
         int vx = bx-tankx;
         int vy = by-tanky;
         int v = br+tankr;
         return vx*vx+vy*vy<=v*v;
}

public boolean hit() {
    Point[] dp = new Point[16];
    int a = 1;
    for(int i = 0; i < 4; i++) {
        double x = 0;
        double y = 0;
        if(this.angle % 45 == 0) {
            x = this.p.x + this.size / 2 + this.size / 2 * Math.sqrt(2) * (int)Math.cos(Math.PI / 2 * i);
            if(i % 2 == 0) {
                y = this.p.y + this.size / 2;
            } else {
                y = this.p.y + this.size / 2 + a * this.size / 2 * Math.sqrt(2);
                //System.out.println(a);
                a = -1;
            }
        } else {
            x = this.p.x + this.size / 2 + this.size / 2 * Math.sqrt(2) * Math.cos(((this.angle + 45) % 90 + 90 * i) * Math.PI / 180);
            y = this.p.y + this.size / 2 + Math.signum(Math.sin(((this.angle + 45) % 90 + 90 * i) * Math.PI / 180)) * Math.sqrt(Math.pow(Math.sqrt(2) * this.size / 2, 2) - Math.pow(x - (this.p.x + this.size / 2), 2));
        }
        dp[i] = new Point((int)x, (int)y);
        int masux = dp[i].x/50;
        int masuy = dp[i].y/50;
        if(MainPanel.map[masuy][masux] >= 1) {
            MainPanel.map[masuy][masux] = 2;
            return true;
        }
    }
a = 1;
    for(int i = 4; i < 8; i++) {
        double x = 0;
        double y = 0;
        if((this.angle) % 90 == 0) {
            x = this.p.x + this.size / 2 + this.size / 2 * (int)Math.cos(Math.PI / 2 * i);
            if(i % 2 == 0) {
                y = this.p.y + this.size / 2;
            } else {
                y = this.p.y + this.size / 2 + a * this.size / 2;
                //System.out.println(a);
                a = -1;
            }
        } else {
            x = this.p.x + this.size / 2 + this.size / 2 * Math.cos((this.angle % 90 + 90 * i) * Math.PI / 180);
            y = this.p.y + this.size / 2 + Math.signum(Math.sin((this.angle % 90 + 90 * i) * Math.PI / 180)) * Math.sqrt(Math.pow(this.size / 2, 2) - Math.pow(x - (this.p.x + this.size / 2), 2));
        }
        dp[i] = new Point((int)x, (int)y);
        int masux = dp[i].x/50;
        int masuy = dp[i].y/50;
        if(MainPanel.map[masuy][masux] == 1) {
            MainPanel.map[masuy][masux] = 2;
            return true;
        }
    }
a = 1;
    for(int i = 8; i < 12; i++) {
        double x = 0;
        double y = 0;
        if((this.angle + 22.5) % 90 == 0) {
            x = this.p.x + this.size / 2 + Math.sqrt(Math.pow(this.size / 2, 2) + Math.pow(this.size / 4, 2)) * (int)Math.cos(Math.PI / 2 * i);
            if(i % 2 == 0) {
                y = this.p.y + this.size / 2;
            } else {
                y = this.p.y + this.size / 2 + a * Math.sqrt(Math.pow(this.size / 2, 2) + Math.pow(this.size / 4, 2));
                //System.out.println(a);
                a = -1;
            }
        } else {
            x = this.p.x + this.size / 2 + Math.sqrt(Math.pow(this.size / 2, 2) + Math.pow(this.size / 4, 2)) * Math.cos(((this.angle + 22.5) % 90 + 90 * i) * Math.PI / 180);
            y = this.p.y + this.size / 2 + Math.signum(Math.sin(((this.angle + 22.5) % 90 + 90 * i) * Math.PI / 180)) * Math.sqrt(Math.pow(this.size / 2, 2) + Math.pow(this.size / 4, 2) - Math.pow(x - (this.p.x + this.size / 2), 2));
        }
        dp[i] = new Point((int)x, (int)y);
        int masux = dp[i].x/50;
        int masuy = dp[i].y/50;
        if(MainPanel.map[masuy][masux] == 1) {
            MainPanel.map[masuy][masux] = 2;
            return true;
        }
    }
a = 1;
    for(int i = 8; i < 12; i++) {
        double x = 0;
        double y = 0;
        if((this.angle + 67.5) % 90 == 0) {
            x = this.p.x + this.size / 2 + Math.sqrt(Math.pow(this.size / 2, 2) + Math.pow(this.size / 4, 2)) * (int)Math.cos(Math.PI / 2 * i);
            if(i % 2 == 0) {
                y = this.p.y + this.size / 2;
            } else {
                y = this.p.y + this.size / 2 + a * Math.sqrt(Math.pow(this.size / 2, 2) + Math.pow(this.size / 4, 2));
                //System.out.println(a);
                a = -1;
            }
        } else {
            x = this.p.x + this.size / 2 + Math.sqrt(Math.pow(this.size / 2, 2) + Math.pow(this.size / 4, 2)) * Math.cos(((this.angle + 67.5) % 90 + 90 * i) * Math.PI / 180);
            y = this.p.y + this.size / 2 + Math.signum(Math.sin(((this.angle + 67.5) % 90 + 90 * i) * Math.PI / 180)) * Math.sqrt(Math.pow(this.size / 2, 2) + Math.pow(this.size / 4, 2) - Math.pow(x - (this.p.x + this.size / 2), 2));
        }
        dp[i] = new Point((int)x, (int)y);
        int masux = dp[i].x/50;
        int masuy = dp[i].y/50;
        if(MainPanel.map[masuy][masux] == 1) {
            MainPanel.map[masuy][masux] = 2;
            return true;
        }
    }
    return false;}

	public void run() {
		while(dl) {
			if(flag == 0) {action();}
			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	public LinkedList<Bullet> getter() {
		return blist;
	}
	public LinkedList<CurveBall> getBall(){
		return clist;
	}
}