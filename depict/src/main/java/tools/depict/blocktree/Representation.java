package tools.depict.blocktree;

import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import tools.depict.blocktree.model.Edge;
import tools.depict.blocktree.model.Vertex;



/**
 * A graphical representation of a graph.
 * 
 * @author maclean
 *
 */
public class Representation {
	
	private Map<Vertex, Point2D> points;
	
	private Map<Edge, Line2D> lines;
	
	public Representation() {
		this.points = new HashMap<Vertex, Point2D>();
		this.lines = new HashMap<Edge, Line2D>();
	}
	
	public void addPoint(Vertex vertex, Point2D point) {
		this.points.put(vertex, point);
	}
	
	public Point2D getPoint(Vertex vertex) {
		return points.get(vertex);
	}
	
	public void addLine(Edge edge, Line2D line) {
		this.lines.put(edge, line);
	}

	public List<Point2D> getPoints() {
		return new ArrayList<Point2D>(points.values());
	}
	
	public void centerOn(int x, int y) {
	    centerOn(new Point2D.Double(x, y));
	}
	
	public void centerOn(Point2D c) {
	    Rectangle2D bounds = null;
	    for (Point2D p : points.values()) {
	        if (bounds == null) {
	            bounds = new Rectangle2D.Double(p.getX(), p.getY(), 0, 0);
	        } else {
	            bounds.add(p);
	        }
	    }
	    double dx = c.getX() - bounds.getCenterX();
	    double dy = c.getY() - bounds.getCenterY();
	    for (Point2D p : points.values()) {
	        p.setLocation(p.getX() + dx, p.getY() + dy);
	    }
	    for (Line2D line : lines.values()) {
	        Point2D p1 = line.getP1();
	        Point2D p2 = line.getP2();
	        p1.setLocation(p1.getX() + dx, p1.getY() + dy);
	        p2.setLocation(p2.getX() + dx, p2.getY() + dy);
	        line.setLine(p1, p2);
	    }
	}

	public List<Vertex> getVertices() {
		return new ArrayList<Vertex>(points.keySet());
	}
	
	public String toString() {
	    StringBuffer sb = new StringBuffer();
	    for (Vertex v : points.keySet()) {
	        sb.append(v).append("\t").append(points.get(v)).append("\n");
	    }
	    for (Edge edge : lines.keySet()) {
            Line2D line = lines.get(edge);
	        sb.append(String.format("%s %s %s", edge, line.getP1(), line.getP2()));
	    }
	    return sb.toString();
	}

    public Vertex getVertex(int index) {
        for (Vertex v : points.keySet()) {
            if (v.getIndex() == index) {
                return v;
            }
        }
        return null;
    }

    public void add(Representation other) {
        points.putAll(other.points);
        lines.putAll(other.lines);
    }

}
