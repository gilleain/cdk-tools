package tools.depict.blocktree.visitor;

import tools.depict.blocktree.model.GraphObject;
import tools.depict.blocktree.model.Vertex;

public interface DFSVisitor {
	
	public void visit(GraphObject g, Vertex v);

	public boolean seen(Vertex v);
	
	public void reset();

}
