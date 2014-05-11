package tools.depict.layout;

import org.openscience.cdk.interfaces.IAtom;
import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.layout.StructureDiagramGenerator;

public class ClassicLayoutAdapter implements ILayout<IAtomContainer> {
	
	private final StructureDiagramGenerator sdg;
	
	public ClassicLayoutAdapter() {
		this.sdg = new StructureDiagramGenerator();
	}

	@Override
	public void layout(IAtomContainer obj) {
		sdg.setMolecule(obj);
		try {
			sdg.generateCoordinates();
		} catch (Exception e) {
			// terrible to swallow all exceptions, but equally bad to throw 
			// the most general exception type
		}
		
		// copy onto the positions of the old atom container
		IAtomContainer laidOutAtomContainer = sdg.getMolecule();
		for (IAtom atom : laidOutAtomContainer.atoms()) {
			int index = laidOutAtomContainer.getAtomNumber(atom);
			obj.getAtom(index).setPoint2d(atom.getPoint2d());
		}
	}

}
