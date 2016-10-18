import javax.swing.JFrame;
import javax.swing.JPanel;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.HeadlessException;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;


public class TreeDrawer extends JFrame  {

	public TreeDrawer() {
		setSize(640, 400);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setContentPane(new DrawArea());
		setVisible(true);
	}

}

class DrawArea extends JPanel {
	
	PositionCalculator TD;
	Tree tree;
	ArrayList<Point> nodeCoords;
	float nodeSize, siblingSeparation;
		
	public DrawArea() {
		TD = new PositionCalculator(2);
		tree = TD.tree;
	}
	
	@Override
	/**
	 * There is an issue where float values are narrowed to integer values as the 
	 * drawRect class only will accept integer values. Therefore, the drawing is a
	 * little off when PositionCalculator defines a node's x-position to be something 
	 * with a fractional part.
	 */
	protected void paintComponent(Graphics g) {
		for(int i = 0; i < tree.nodeLabels.size(); i++) {
			Node n = tree.nodeTable.get(tree.nodeLabels.get(i));
	
			// I'm not good at graphics, so I hacked out numbers to get the tree mostly centered in the window
			// and to scale it up.
			int x_scaleFactor = 15;		// To scale up the x-coords for this size window
			int y_scaleFactor = 70;		// To scale up the y-coords for this size window
			int x_offset = 125;
			int y_offset = 30;
			
			
			Graphics2D g2 = (Graphics2D) g;
			
			// For rectangular nodes instead of circular
			//Rectangle2D r = new Rectangle2D.Float((n.x * x_scaleFactor) + x_offset, (n.depth * y_scaleFactor) + y_offset, 20, 20);
			
			Ellipse2D e = new Ellipse2D.Float((n.x * x_scaleFactor) + x_offset, (n.depth * y_scaleFactor) + y_offset, 22, 22);
			 
			g2.draw(e);
			 
			g2.drawString(n.toString(), (float) e.getCenterX() - 3, (float) e.getCenterY() + 5);
			
			// Draw lines between parent and child
			if (!n.isLeaf()) {
				for (Node child : n.children) {					
					float child_x = (child.x * x_scaleFactor) + x_offset;
					float child_y = (child.depth * y_scaleFactor) + y_offset;
					Line2D l = new Line2D.Float((float) e.getCenterX(), (float) e.getCenterY() + (float) e.getWidth()/2, child_x + (float) e.getWidth()/2, child_y);
					g2.draw(l);
				}
			}
			
		}
	}
	
	
	
}
