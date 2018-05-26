package Draw_Board;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

//For repaint at Frame fade out
class Shape_info {

	public static final int ShapeList = 0;
	// ---������ ������ ������ Ŭ����, ���������� �� ���� ��ü ������
	// --- �迭, ���� ���� ������� �ȿ� ��ü�� ���� ����

	// --- �� ������ ������ : id, �������� ����

	int start_X, start_Y;
	int id;

}

class Rectangle_info extends Shape_info {
	int width;
	int height;

	Rectangle_info(int argX, int argY, int argwidth, int argheight) {
		this.id = 1;
		this.start_X = argX;
		this.start_Y = argY;
		this.width = argwidth;
		this.height = argheight;
	}
}

class Circle_info extends Shape_info {
	int width;
	int height;

	Circle_info(int argX, int argY, int argwidth, int argheight) {
		this.id = 2;

		this.start_X = argX;
		this.start_Y = argY;
		this.width = argwidth;
		this.height = argheight;
	}
}

class Line_info extends Shape_info {
	int X2;
	int Y2;

	Line_info(int argX1, int argY1, int argX2, int argY2) {
		this.id = 3;
		this.start_X = argX1;
		this.start_Y = argY1;
		this.X2 = argX2;
		this.Y2 = argY2;
	}
}

class shapeDraw extends JFrame {

	// ��ư ���� ���� class
	class SetColor {
		SetColor() {
			Draw_Rectangle.setBackground(OriginalColor);
			Draw_Circle.setBackground(OriginalColor);
			Draw_Line.setBackground(OriginalColor);
			CurrentButton.setBackground(Color.ORANGE);
		}
	}

	// RightPanel Ŭ����
	class RightPanelC extends JPanel {

		Vector<Shape_info> ShapeList = new Vector<Shape_info>();

		// ---�׸��� �׸��� rightPanel

		public void paintComponent(Graphics g) {

			for (int i = 0; i < ShapeList.size(); i++) {
				Shape_info sp = ShapeList.get(i);
				// �簢
				if (sp.id == 1)
					g.drawRect(sp.start_X, sp.start_Y, 100, 100);

				// ����
				else if (sp.id == 2)
					g.drawOval(sp.start_X, sp.start_Y, 100, 100);

				// ����
				else if (sp.id == 3)
					g.drawLine(sp.start_X, sp.start_Y, ((Line_info) sp).X2, ((Line_info) sp).Y2);

			}
		}

		RightPanelC() {

			this.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent e) {

					if (CurrentButton == Draw_Rectangle) {
						Graphics g = rightPanel.getGraphics();
						Rectangle_info R = new Rectangle_info(e.getX() - 50, e.getY() - 50, 100, 100); // ��ü��������
						ShapeList.add(R);
						g.drawRect(e.getX() - 50, e.getY() - 50, 100, 100);
					}

					if (CurrentButton == Draw_Circle) {
						Graphics g = rightPanel.getGraphics();
						Circle_info C = new Circle_info(e.getX() - 50, e.getY() - 50, 100, 100);
						ShapeList.add(C);
						g.drawOval(e.getX() - 50, e.getY() - 50, 100, 100);
					}
				}
			});

			// ---Line : ������ ��� ���� �׷��� ��(������ǥ, ������ǥ �ʿ�)
			this.addMouseListener(new MouseAdapter() {
				public void mousePressed(MouseEvent e) {
					FirstX = e.getX();
					FirstY = e.getY();
				}

				public void mouseReleased(MouseEvent e) {
					LastX = e.getX();
					LastY = e.getY();

					if (CurrentButton == Draw_Line) {
						Graphics g = rightPanel.getGraphics();
						Line_info L = new Line_info(FirstX, FirstY, LastX, LastY);
						ShapeList.add(L);
						g.drawLine(FirstX, FirstY, LastX, LastY);
					}
				}
			}); // ---End of DrawLine function
		}
	}

	private JPanel leftPanel;
	private RightPanelC rightPanel;
	private JButton Draw_Rectangle, Draw_Circle, Draw_Line;
	private JButton CurrentButton;
	private Color OriginalColor;
	private int FirstX, FirstY;
	private int LastX, LastY;

	shapeDraw() {
		this.getContentPane().setLayout(new GridLayout(0, 2));

		rightPanel = new RightPanelC();
		leftPanel = new JPanel(); // ��ư�� �״� Panel

		this.add(leftPanel);
		this.add(rightPanel);

		leftPanel.setLayout(new GridLayout(3, 0)); // ��ư �״� GridLayout

		// make Buttons
		Draw_Rectangle = new JButton("�簢");
		Draw_Circle = new JButton("����");
		Draw_Line = new JButton("����");
		OriginalColor = Draw_Rectangle.getBackground();

		leftPanel.add(Draw_Rectangle);
		leftPanel.add(Draw_Circle);
		leftPanel.add(Draw_Line);

		Draw_Rectangle.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				CurrentButton = (JButton) e.getSource();
				new SetColor();

			}
		});

		Draw_Circle.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				CurrentButton = (JButton) e.getSource();
				new SetColor();
				new Circle_info((int) (e.getX() - 50), (int) (e.getY() - 50), 100, 100);
			}
		});

		Draw_Line.addMouseListener(new MouseAdapter() {

			public void mouseClicked(MouseEvent e) {
				CurrentButton = (JButton) e.getSource();
				new SetColor();
			}
		});

		// Frame ����
		this.setTitle("shape Board");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.setVisible(true);
		this.setBounds(450, 250, 1000, 700);
	}
}

public class Draw_Board {
	public static void main(String arsgp[]) {
		shapeDraw SD = new shapeDraw();
	}
}
