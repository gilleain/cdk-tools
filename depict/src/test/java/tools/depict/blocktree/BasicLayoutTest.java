package tools.depict.blocktree;

import org.junit.Test;
import org.openscience.cdk.DefaultChemObjectBuilder;
import org.openscience.cdk.interfaces.IAtom;
import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.interfaces.IBond;
import org.openscience.cdk.interfaces.IChemObjectBuilder;

public class BasicLayoutTest {

	private void printCoords(IAtomContainer atomContainer) {
		for (IAtom atom : atomContainer.atoms()) {
			System.out.println(atom.getPoint2d());
		}
	}
	
	private void testMol(IAtomContainer atomContainer) {
		GraphLayout layout = new GraphLayout(new ParameterSet());
		layout.layout(atomContainer);
		printCoords(atomContainer);
	}

	@Test
	public void simpleCycle() {
		IChemObjectBuilder builder = DefaultChemObjectBuilder.getInstance();
		IAtomContainer mol = builder.newInstance(IAtomContainer.class);
		IAtom a1 = builder.newInstance(IAtom.class, "C");
		IAtom a2 = builder.newInstance(IAtom.class, "C");
		IAtom a3 = builder.newInstance(IAtom.class, "C");
		mol.addAtom(a1);
		mol.addAtom(a2);
		mol.addAtom(a3);
		IBond b1 = builder.newInstance(IBond.class, a1, a2, IBond.Order.SINGLE);
		IBond b2 = builder.newInstance(IBond.class, a2, a3, IBond.Order.SINGLE);
		IBond b3 = builder.newInstance(IBond.class, a1, a3, IBond.Order.SINGLE);
		mol.addBond(b1);
		mol.addBond(b2);
		mol.addBond(b3);

		testMol(mol);
	}

	@Test
	public void cycleWithAttachedTree() {
		IChemObjectBuilder builder = DefaultChemObjectBuilder.getInstance();
		IAtomContainer mol = builder.newInstance(IAtomContainer.class);
		IAtom a1 = builder.newInstance(IAtom.class, "C");
		IAtom a2 = builder.newInstance(IAtom.class, "C");
		IAtom a3 = builder.newInstance(IAtom.class, "C");
		IAtom a4 = builder.newInstance(IAtom.class, "C");
		IAtom a5 = builder.newInstance(IAtom.class, "C");
		IAtom a6 = builder.newInstance(IAtom.class, "C");
		mol.addAtom(a1);
		mol.addAtom(a2);
		mol.addAtom(a3);
		mol.addAtom(a4);
		mol.addAtom(a5);
		IBond b1 = builder.newInstance(IBond.class, a1, a2, IBond.Order.SINGLE);
		IBond b2 = builder.newInstance(IBond.class, a2, a3, IBond.Order.SINGLE);
		IBond b3 = builder.newInstance(IBond.class, a3, a4, IBond.Order.SINGLE);
		IBond b4 = builder.newInstance(IBond.class, a3, a5, IBond.Order.SINGLE);
		IBond b5 = builder.newInstance(IBond.class, a2, a6, IBond.Order.SINGLE);
		IBond b6 = builder.newInstance(IBond.class, a1, a6, IBond.Order.SINGLE);
		mol.addBond(b1);
		mol.addBond(b2);
		mol.addBond(b3);
		mol.addBond(b4);
		mol.addBond(b5);
		mol.addBond(b6);
		
		testMol(mol);
	}

}
