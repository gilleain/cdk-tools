package tools.depict.blocktree;

import org.junit.Test;
import org.openscience.cdk.DefaultChemObjectBuilder;
import org.openscience.cdk.interfaces.IAtom;
import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.interfaces.IBond;
import org.openscience.cdk.interfaces.IChemObjectBuilder;

/**
 * Utility class to create molecules for depiction test cases.
 * 
 * @author maclean
 * 
 */
public class TestAtomContainerFactory {

	@Test
	public static IAtomContainer simpleCycle() {
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
		return mol;
	}

	/**
	 * Smiles : C1C(C(C)C)C1
	 * Isopropyl-cyclopropane.
	 */
	@Test
	public static IAtomContainer cycleWithAttachedTree() {
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
		return mol;
	}

	/**
	 * Smiles : C12C3C1N23C
	 * An unlikely N-methyl hetrotetrahedrane molecule.s
	 */
	@Test
	public static IAtomContainer blockWithSingleVertexTreeAttached() {
		IChemObjectBuilder builder = DefaultChemObjectBuilder.getInstance();
		IAtomContainer mol = builder.newInstance(IAtomContainer.class);
		
		IAtom a1 = builder.newInstance(IAtom.class, "C");
		IAtom a2 = builder.newInstance(IAtom.class, "C");
		IAtom a3 = builder.newInstance(IAtom.class, "C");
		IAtom a4 = builder.newInstance(IAtom.class, "N");
		IAtom a5 = builder.newInstance(IAtom.class, "C");
		
		mol.addAtom(a1);
		mol.addAtom(a2);
		mol.addAtom(a3);
		mol.addAtom(a4);
		mol.addAtom(a5);
		
		IBond b1 = builder.newInstance(IBond.class, a1, a2, IBond.Order.SINGLE);
		IBond b2 = builder.newInstance(IBond.class, a2, a3, IBond.Order.SINGLE);
		IBond b3 = builder.newInstance(IBond.class, a1, a3, IBond.Order.SINGLE);
		IBond b4 = builder.newInstance(IBond.class, a3, a4, IBond.Order.SINGLE);
		IBond b5 = builder.newInstance(IBond.class, a1, a4, IBond.Order.SINGLE);
		IBond b6 = builder.newInstance(IBond.class, a2, a4, IBond.Order.SINGLE);
		IBond b7 = builder.newInstance(IBond.class, a4, a5, IBond.Order.SINGLE);
		
		mol.addBond(b1);
		mol.addBond(b2);
		mol.addBond(b3);
		mol.addBond(b4);
		mol.addBond(b5);
		mol.addBond(b6);
		mol.addBond(b7);
		return mol;
	}

}
