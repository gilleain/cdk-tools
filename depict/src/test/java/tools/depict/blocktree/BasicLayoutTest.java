package tools.depict.blocktree;

import org.junit.Test;
import org.openscience.cdk.interfaces.IAtom;
import org.openscience.cdk.interfaces.IAtomContainer;

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
        testMol(TestAtomContainerFactory.simpleCycle());
    }

    @Test
    public void cycleWithAttachedTree() {
        testMol(TestAtomContainerFactory.cycleWithAttachedTree());
    }
    
    @Test
    public void blockWithSingleVertexTreeAttached() {
        testMol(TestAtomContainerFactory.blockWithSingleVertexTreeAttached());
    }

}
