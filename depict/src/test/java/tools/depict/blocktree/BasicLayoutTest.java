package tools.depict.blocktree;

import org.junit.Test;
import org.openscience.cdk.DefaultChemObjectBuilder;
import org.openscience.cdk.interfaces.IAtom;
import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.interfaces.IBond;
import org.openscience.cdk.interfaces.IChemObjectBuilder;

public class BasicLayoutTest {

	@Test
	public void testA() {
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
		
		GraphLayout layout = new GraphLayout(new ParameterSet());
		layout.layout(mol);
	}

}
