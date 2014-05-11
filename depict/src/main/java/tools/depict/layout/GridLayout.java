package tools.depict.layout;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.util.List;

import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.renderer.IRenderer;
import org.openscience.cdk.renderer.visitor.AWTDrawVisitor;

public class GridLayout {

	private int rows;

	private int cols;
	
	public GridLayout(int rows, int cols) {
		this.rows = rows;
		this.cols = cols;
	}

	public void layout(List<IAtomContainer> mols, 
					   IRenderer<IAtomContainer> renderer,
					   Dimension cellCanvas, Graphics2D g) {
		double w = cellCanvas.width;
		double h = cellCanvas.height;

		double w2 = w / 2;
		double h2 = h / 2;
		double centerX = w2;
		double centerY = h2;
		int colCounter = 1;
		for (IAtomContainer mol : mols) {
			Rectangle2D bounds = new Rectangle2D.Double(centerX - w2, centerY - h2, w, h);
			renderer.paint(mol, new AWTDrawVisitor(g), bounds, false);
			if (colCounter < cols) {
				centerX += w;
				colCounter++;
			} else { 
				centerY += h;
				centerX = w / 2;
				colCounter = 1;
			}
		}
	}
}
