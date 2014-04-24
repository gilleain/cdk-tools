package tools.depict.layout;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.util.List;

import javax.vecmath.Point2d;

import org.openscience.cdk.geometry.GeometryTools;
import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.renderer.IRenderer;
import org.openscience.cdk.renderer.visitor.AWTDrawVisitor;

public class GridLayout {

	private int rows;

	private int cols;
	
	private int bondLength;
	
	private int borderX;
	
	private int borderY;

	public GridLayout(int rows, int cols, int bondLength, int borderX, int borderY) {
		this.rows = rows;
		this.cols = cols;
		this.bondLength = bondLength;
		this.borderX = borderX;
		this.borderY = borderY;
	}

	public void layout(List<IAtomContainer> mols, 
					   IRenderer<IAtomContainer> renderer,
					   Dimension cellCanvas, Graphics2D g) {
		AffineTransform originalTransform = g.getTransform();
		double w = cellCanvas.width;
		double h = cellCanvas.height;

		double w2 = w / 2;
		double h2 = h / 2;
		double centerX = w2;
		double centerY = h2;
		int colCounter = 1;
		for (IAtomContainer mol : mols) {
			double zoom = calculateZoom(mol, cellCanvas);
//			g.translate(centerX, centerY);
//			g.scale(zoom, zoom);
			Rectangle2D bounds = new Rectangle2D.Double(centerX - w2, centerY - h2, w, h);
			renderer.paint(mol, new AWTDrawVisitor(g), bounds, false);
//			g.setTransform(originalTransform);
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

	private double calculateZoom(IAtomContainer ac, Dimension canvas) {
		double scaleFactor = GeometryTools.getScaleFactor(ac, bondLength);
		GeometryTools.translate2DCenterTo(ac, new Point2d(0,0));
		GeometryTools.scaleMolecule(ac, scaleFactor);
		Rectangle2D r2D = GeometryTools.getRectangle2D(ac);
		double canvasWidth = canvas.width;
		double canvasHeight = canvas.height;
		double objectWidth = r2D.getWidth() + (borderX * 2);
		double objectHeight = r2D.getHeight() + (borderY * 2);
		return Math.min(canvasWidth / objectWidth, canvasHeight / objectHeight);
	}

}
