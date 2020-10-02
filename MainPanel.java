package tank;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.AffineTransform;
import java.util.LinkedList;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;

public class MainPanel extends JPanel implements Runnable ,KeyListener{
	static Tank tankList[] = new Tank[4];
	static final int WIDTH = 750;
	static final int HEIGHT = 750;
	Thread th;
	Tank tank1, tank2, tank3, tank4;
	int flag = 0;
	LinkedList<Tank> list = new LinkedList<Tank>();
	LinkedList<CurveBall> clist = new LinkedList<CurveBall>();
	static int[][] map =
		{{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
		{1,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
		{1,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
		{1,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
		{1,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
		{1,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
		{1,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
		{1,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
		{1,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
		{1,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
		{1,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
		{1,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
		{1,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
		{1,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
		{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1}};
	int tileSize = 50;
	Image airon,center,wood;
	static Image bullet;

	MainPanel() {
		ImageIcon icon = new ImageIcon(getClass().getResource("airon.gif"));
		airon = icon.getImage();
		icon = new ImageIcon(getClass().getResource("bullet2.png"));
		bullet = icon.getImage();
		icon = new ImageIcon(getClass().getResource("wood.png"));
		wood = icon.getImage();
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
		setBorder(new BevelBorder(BevelBorder.RAISED));
		tank1 = new Tank1(75, 75, 0);
		tankList[0] = tank1;
		tank2 = new Tank2(75, 625, 1);
		tankList[1] = tank2;
		tank3 = new Tank3(425, 425, 2);
		tankList[2] = tank3;
		tank4 = new Tank4(220, 350, 3);
		tankList[3] = tank4;

		setFocusable(true);
		addKeyListener(this);

		th = new Thread(this);
		th.start();

	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, WIDTH, HEIGHT);
		for(int i = 0;i < 15;i++) {
			for(int j = 0;j < 15;j++){
				switch(map[i][j]) {
				case 1:
					g.drawImage(airon,j * tileSize,i * tileSize,tileSize,tileSize,null);
					continue;
				case 2:
					g.drawImage(wood,j * tileSize,i * tileSize,tileSize,tileSize,null);
					continue;
				}
			}
			}
		//g.setColor(Color.blue);
		/*for(int i = 0;i < 150;i++) {
			if(i%5 == 0) {
				g.setColor(Color.green);
				g.drawLine(0, i*5, 750, i*5);
				g.drawLine(i*5, 0, i*5, 750);
			}else {
				g.setColor(Color.blue);
			g.drawLine(0, i*5, 150, i*5);
			g.drawLine(i*5, 0, i*5, 150);
			}
		}*/
		g.setColor(Color.blue);
		g.drawImage(center,WIDTH/2, HEIGHT/2, 50, 50,null);
		for(int i = 0;i < tankList.length;i++) {
            if(tankList[i] != null) {
                tankList[i].draw(g);
                Graphics2D g2 = (Graphics2D)g;
                AffineTransform at = g2.getTransform();
                at.setToRotation(Math.toRadians(0));
                g2.setTransform(at);
            }
        }

        for(int i = 0;i < tankList.length;i++) {
            if(tankList[i] != null) {
            	if(tankList[i].hit()) {
            		System.out.println("かべ");
            		tankList[i].dl = false;
            		tankList[i] = null;
            	}}
            if(tankList[i] != null) {
                LinkedList<Bullet> list = tankList[i].getter();
               for(int a = 0; a < list.size(); a++) {
                    Bullet b = (Bullet) list.get(a);
                    b.draw(g);
                    if(b.p.x < 0 ||  b.p.x > WIDTH  ||b.p.y < 0 || b.p.y > HEIGHT||b.hit()) {
                    	tankList[i].blist.remove(b);
                        //tank1.blist.remove(b);
                        //b = null;
                        break;
                    }
                    for(int j = 0;j < tankList.length;j++) {
                        if(j != i) {
                         if(tankList[j] != null) {
                             if(check(tankList[j],b)) {
                            	 tankList[i].blist.remove(b);
                                 tankList[j].damage();
                                 if(tankList[j].dl == false) {
                                     tankList[j] = null;
                                     break;
                                 }
                             }
                         }
                       }
                    }
                }
            }
           }
        }

	public boolean check(Tank t, Bullet b) {
		int carx = t.p.x + t.size/2,cary = t.p.y + t.size/2,carr = t.size/2;
		int bx = b.p.x + b.SIZE/2,by = b.p.y + b.SIZE/2,br = b.SIZE/2;
		int vx = bx-carx;
		int vy = by-cary;
		int v = br+carr;
		//System.out.println(car.p + "/" + b.p);
		return vx*vx+vy*vy<=v*v;
	}
	public boolean check(Tank t, CurveBall b) {
		int carx = t.p.x,cary = t.p.y,carr = t.size/2;
		int bx = b.p.x,by = b.p.y,br = b.SIZE/2;
		int vx = bx-carx;
		int vy = by-cary;
		int v = br+carr;
		//System.out.println(car.p + "/" + b.p);
		return vx*vx+vy*vy<=v*v;
	}
	public boolean check(Tank t,int x,int y) {
		int carx = t.p.x,cary = t.p.y,carr = t.size/2;
		int bx = x,by = y,br = tileSize/2;
		int vx = bx-carx;
		int vy = by-cary;
		int v = br+carr;
		//System.out.println(car.p + "/" + b.p);
		return vx*vx+vy*vy<=v*v;
	}

	public void run() {
		while(true) {
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			repaint();
		}
	}

		public static Image getBulletImage() {
			return bullet;
		}

		@Override
		public void keyTyped(KeyEvent e) {
			// TODO 自動生成されたメソッド・スタブ

		}

		@Override
		public void keyPressed(KeyEvent e) {
			// TODO 自動生成されたメソッド・スタブ
			int key = e.getKeyCode();
			if(key == KeyEvent.VK_D) {
				tankList[0].p.x += 1;
			}
			if(key == KeyEvent.VK_A) {
				tankList[0].p.x -= 1;
			}
			if(key == KeyEvent.VK_W) {
				tankList[0].p.y -= 1;
				}
			if(key == KeyEvent.VK_S) {
				tankList[0].p.y += 1;
			}
			if(key == KeyEvent.VK_Q) {
				tankList[0].angle += 1;
			}
			if(key == KeyEvent.VK_E) {
				tankList[0].angle -= 1;
			}
			if(key == KeyEvent.VK_ENTER) {
				tankList[0].Bullet();
			}
			if(key == KeyEvent.VK_G) {
				tankList[0].goStraight(1);
			}
		}

		public static int bigDecimal(double x) {
			int y = (int)x;
			double w = x%y;
			if(w >= 0.5) {
				y++;
			}
			//System.out.println(y);
			return y;
		}

		@Override
		public void keyReleased(KeyEvent e) {
			// TODO 自動生成されたメソッド・スタブ

		}
}
