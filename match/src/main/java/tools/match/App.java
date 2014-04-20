package tools.match;

import java.util.List;
import java.util.Map;

import org.apache.commons.cli.ParseException;
import org.openscience.cdk.exception.CDKException;
import org.openscience.cdk.interfaces.IAtom;
import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.isomorphism.Pattern;
import org.openscience.cdk.smsd.labelling.AtomContainerPrinter;

import tools.core.InputHandler;

/**
 * CDK-Tools match main class, used for finding matches of query molecules in targets.
 *  
 * @author maclean
 *
 */
public class App {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ArgumentHandler arguments;
		try {
			arguments = new ArgumentHandler(args);
		
			InputHandler queryInput = arguments.getQueryInputHandler();
			InputHandler targetInput = arguments.getTargetInputHandler();

			if (queryInput.getInputMode() == InputHandler.InputMode.SINGLE) {
				IAtomContainer queryAtomContainer = queryInput.getSingleInput();
				if (targetInput.getInputMode() == InputHandler.InputMode.SINGLE) {
					IAtomContainer targetAtomContainer = targetInput.getSingleInput();
					action(queryAtomContainer, targetAtomContainer, arguments);
				} else {
					List<IAtomContainer> targetAtomContainers = targetInput.getMultipleInputs();
					for (IAtomContainer targetAtomContainer : targetAtomContainers) {
						action(queryAtomContainer, targetAtomContainer, arguments);
					}
				}
			} else {
				// XXX - are multiple queries allowed?
				System.err.println("Multiple queries not allowed");
			}
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (CDKException e) {
			e.printStackTrace();
		}
	}
	
	private static void action(IAtomContainer queryAtomContainer,
							   IAtomContainer targetAtomContainer,
							   ArgumentHandler arguments) throws CDKException {
		
		AtomContainerPrinter acp = new AtomContainerPrinter();
		System.out.println(acp.toString(queryAtomContainer));
		System.out.println(acp.toString(targetAtomContainer));
		
		Pattern pattern = Pattern.findSubstructure(queryAtomContainer);
		int matchCount = 0;
		for (Map<IAtom, IAtom> atomMap : pattern.matchAll(targetAtomContainer).toAtomMap()) {
			System.out.print(matchCount + " : ");
			for (IAtom pAtom : atomMap.keySet()) {
				System.out.print(
						queryAtomContainer.getAtomNumber(pAtom)
						+ " -> " + targetAtomContainer.getAtomNumber(atomMap.get(pAtom))
						+ ", ");
			}
			System.out.println();
			matchCount++;
		}
	}

}
