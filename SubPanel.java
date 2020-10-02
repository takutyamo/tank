package tank;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;
import javax.swing.border.BevelBorder;

public class SubPanel extends JPanel{
	private static final int WIDTH = 240;
    private static final int HEIGHT = 240;

    public SubPanel() {
        // パネルの推奨サイズを設定、pack()するときに必要
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        // 変数などの初期化
        setBackground(Color.lightGray);
        setBorder(new BevelBorder(BevelBorder.LOWERED));
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.black);
    }
}
