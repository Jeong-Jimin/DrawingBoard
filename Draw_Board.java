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
	// ---도형의 정보를 저장할 클래스, 도형이찍힐 때 마다 객체 생성됨
	// --- 배열, 벡터 등의 저장공간 안에 객체의 정보 저장

	// --- 각 도형의 공통점 : id, 시작점을 가짐

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

	// 버튼 색깔 조정 class
	class SetColor {
		SetColor() {
			Draw_Rectangle.setBackground(OriginalColor);
			Draw_Circle.setBackground(OriginalColor);
			Draw_Line.setBackground(OriginalColor);
			CurrentButton.setBackground(Color.ORANGE);
		}
	}

	// RightPanel 클래스
	class RightPanelC extends JPanel {

		Vector<Shape_info> ShapeList = new Vector<Shape_info>();

		// ---그림을 그리는 rightPanel

		public void paintComponent(Graphics g) {

			for (int i = 0; i < ShapeList.size(); i++) {
				Shape_info sp = ShapeList.get(i);
				// 사각
				if (sp.id == 1)
					g.drawRect(sp.start_X, sp.start_Y, 100, 100);

				// 동글
				else if (sp.id == 2)
					g.drawOval(sp.start_X, sp.start_Y, 100, 100);

				// 라인
				else if (sp.id == 3)
					g.drawLine(sp.start_X, sp.start_Y, ((Line_info) sp).X2, ((Line_info) sp).Y2);

			}
		}

		RightPanelC() {

			this.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent e) {

					if (CurrentButton == Draw_Rectangle) {
						Graphics g = rightPanel.getGraphics();
						Rectangle_info R = new Rectangle_info(e.getX() - 50, e.getY() - 50, 100, 100); // 객체정보저장
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

			// ---Line : 지정한 대로 선을 그려야 함(시작좌표, 종료좌표 필요)
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
		leftPanel = new JPanel(); // 버튼을 쌓는 Panel

		this.add(leftPanel);
		this.add(rightPanel);

		leftPanel.setLayout(new GridLayout(3, 0)); // 버튼 쌓는 GridLayout

		// make Buttons
		Draw_Rectangle = new JButton("사각");
		Draw_Circle = new JButton("원형");
		Draw_Line = new JButton("직선");
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

		// Frame 설정
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
