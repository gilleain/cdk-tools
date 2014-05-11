package tools.depict.blocktree;

import java.awt.geom.Point2D;

import tools.depict.blocktree.model.graph.Graph;

public interface PointLayout {

	public Representation layout(Graph graph, Point2D center);
}
