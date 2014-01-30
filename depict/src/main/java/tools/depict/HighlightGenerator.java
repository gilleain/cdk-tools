package tools.depict;

import java.awt.Color;
import java.util.Arrays;
import java.util.List;

import javax.vecmath.Point2d;

import org.openscience.cdk.interfaces.IAtom;
import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.renderer.RendererModel;
import org.openscience.cdk.renderer.elements.ElementGroup;
import org.openscience.cdk.renderer.elements.IRenderingElement;
import org.openscience.cdk.renderer.elements.OvalElement;
import org.openscience.cdk.renderer.generators.BasicSceneGenerator.Scale;
import org.openscience.cdk.renderer.generators.IGenerator;
import org.openscience.cdk.renderer.generators.IGeneratorParameter;
import org.openscience.cdk.renderer.generators.parameter.AbstractGeneratorParameter;

public class HighlightGenerator implements IGenerator<IAtomContainer> {
	
	public static class AtomHighlightColor extends AbstractGeneratorParameter<Color> {
    	/** Returns the default value.
    	 * @return {@link Color}.BLACK */
        public Color getDefault() {
            return Color.BLACK;
        }
    }
	private AtomHighlightColor atomHighlightColor = new AtomHighlightColor();
	
	public static class AtomHighlightRadius extends AbstractGeneratorParameter<Integer> {
    	/** Returns the default value. **/
        public Integer getDefault() {
            return 8;
        }
    }
	private AtomHighlightRadius atomHighlightRadius = new AtomHighlightRadius();
	
	public static class AtomHighlightFill extends AbstractGeneratorParameter<Boolean> {
    	/** Returns the default value. **/
        public Boolean getDefault() {
            return true;
        }
    }
	private AtomHighlightFill atomHighlightFill = new AtomHighlightFill();

	public List<IGeneratorParameter<?>> getParameters() {
		 return Arrays.asList(
	                new IGeneratorParameter<?>[] {
	                	atomHighlightColor,
	                	atomHighlightRadius,
	                	atomHighlightFill
	                }
		);
	}

	public IRenderingElement generate(IAtomContainer atomContainer, RendererModel model) {
		ElementGroup elements = new ElementGroup();
		for (IAtom atom : atomContainer.atoms()) {
			if (atom.getProperty("HIGHLIGHT") != null) {
				Point2d p = atom.getPoint2d();
				double radius = 
						model.get(AtomHighlightRadius.class) /
				        model.getParameter(Scale.class).getValue();
				Color color = model.get(AtomHighlightColor.class);
				boolean fill = model.get(AtomHighlightFill.class);
				elements.add(new OvalElement(p.x, p.y, radius, fill, color));
			}
		}
		return elements;
	}

}
