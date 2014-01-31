package tools.match;

import java.util.List;

import org.apache.commons.cli.ParseException;
import org.openscience.cdk.exception.CDKException;
import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.isomorphism.UniversalIsomorphismTester;
import org.openscience.cdk.isomorphism.matchers.IQueryAtomContainer;
import org.openscience.cdk.isomorphism.mcss.RMap;

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
				IQueryAtomContainer queryAtomContainer = (IQueryAtomContainer)queryInput.getSingleInput();
				if (targetInput.getInputMode() == InputHandler.InputMode.SINGLE) {
					IAtomContainer targetAtomContainer = queryInput.getSingleInput();
					action(queryAtomContainer, targetAtomContainer, arguments);
				} else {
					List<IAtomContainer> targetAtomContainers = targetInput.getMultipleInputs();
					for (IAtomContainer targetAtomContainer : targetAtomContainers) {
						action(queryAtomContainer, targetAtomContainer, arguments);
					}
				}
			} else {
				// XXX - are multiple queries allowed?
			}
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (CDKException e) {
			e.printStackTrace();
		}
	}
	
	private static void action(IQueryAtomContainer queryAtomContainer,
							   IAtomContainer targetAtomContainer,
							   ArgumentHandler arguments) throws CDKException {
		UniversalIsomorphismTester tester = new UniversalIsomorphismTester();
		List<RMap> rmap = tester.getIsomorphAtomsMap(
				queryAtomContainer, targetAtomContainer);
		for (RMap mapping : rmap) {
				System.out.println(mapping.getId1() + "->" + mapping.getId2());
		}
	}

}
