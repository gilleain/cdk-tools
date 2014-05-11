package tools.depict.blocktree.embedder;

import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

import tools.depict.blocktree.Representation;
import tools.depict.blocktree.model.Vertex;

public interface BlockEmbeddingLayout {
	
	public Representation layout(BlockEmbedding em, Rectangle2D canvas);
	
	public Representation layout(BlockEmbedding em, Vertex start, Point2D startPoint, Rectangle2D canvas);

}
