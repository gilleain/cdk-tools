package tools.depict.blocktree;

import java.awt.geom.Rectangle2D;

import tools.depict.blocktree.model.graph.Graph;


public interface CanvasLayout {
    
    public Representation layout(Graph graph, Rectangle2D canvas);
    
}
