package tools.depict.blocktree;

import tools.depict.blocktree.embedder.BlockEmbedding;

public interface Refiner {
	
	public Representation refine(Representation representation, BlockEmbedding embedding);

}
