package tools.core;

import org.openscience.cdk.interfaces.IAtom;
import org.openscience.cdk.interfaces.IAtomContainer;

public class MatchIO {

	public void writeMatch(IAtomContainer target, int[] match) {
		for (IAtom atom : target.atoms()) {
			System.out.print(atom.getSymbol());
		}
	}

}
