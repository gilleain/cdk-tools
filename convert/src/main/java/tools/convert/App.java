package tools.convert;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.List;

import org.apache.commons.cli.ParseException;
import org.openscience.cdk.DefaultChemObjectBuilder;
import org.openscience.cdk.exception.CDKException;
import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.interfaces.IChemObjectBuilder;
import org.openscience.cdk.io.CDKSourceCodeWriter;
import org.openscience.cdk.io.MDLV2000Writer;
import org.openscience.cdk.smiles.SmilesGenerator;
import org.openscience.cdk.tools.CDKHydrogenAdder;
import org.openscience.cdk.tools.manipulator.AtomContainerManipulator;

import tools.core.InputHandler;
import tools.core.OutputHandler;

/**
 * CDK-Tools IO main application class, used for converting between file formats.
 * 
 * @author maclean
 *
 */
public class App {
	
	private final static String NEW_LINE = System.getProperty("line.separator");

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			ArgumentHandler arguments = new ArgumentHandler(args);
			InputHandler input = arguments.getInputHandler();
			if (input.getInputMode() == InputHandler.InputMode.SINGLE) {
				IAtomContainer atomContainer = input.getSingleInput();
				action(atomContainer, arguments);
			} else {
				List<IAtomContainer> inputAtomContainers = input.getMultipleInputs();
				for (IAtomContainer atomContainer : inputAtomContainers) {
					action(atomContainer, arguments);
				}
			}
		} catch (ParseException e) {
			System.err.println(e);
		}
	}
	
	private static void action(IAtomContainer atomContainer, ArgumentHandler arguments) {
		OutputHandler output = arguments.getOutputHandler();
		String outputFilename = output.getOutputFilename();
		String outputFormat = output.getOutputFormat();
		try {
			Writer writer;
			if (outputFilename.equals("-")) {
				writer = new PrintWriter(System.out);
			} else {
				writer = new BufferedWriter(
										new FileWriter(
											new File(outputFilename)));
			}
			if (outputFormat.equals("SMI")) {
				SmilesGenerator smilesGenerator = SmilesGenerator.unique();
				transform(atomContainer, arguments);
				String smiles = smilesGenerator.create(atomContainer);
				writer.write(smiles);
				writer.write(NEW_LINE);
				writer.flush();
				writer.close();
			} else if (outputFormat.equals("MDL")) {
				MDLV2000Writer mdlWriter = new MDLV2000Writer(writer);
				transform(atomContainer, arguments);
				try {
					mdlWriter.writeMolecule(atomContainer);
				} catch (Exception e) {
					// seriously.. ?
				}
				mdlWriter.close();
			} else if (outputFormat.equals("CODE")) {
				CDKSourceCodeWriter sourceWriter = new CDKSourceCodeWriter(writer);
				transform(atomContainer, arguments);
				sourceWriter.write(atomContainer);
				writer.write(NEW_LINE);
				writer.flush();
				sourceWriter.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (CDKException e) {
			e.printStackTrace();
		}
	}
	
	private static void transform(IAtomContainer atomContainer, ArgumentHandler args) throws CDKException {
		if (args.shouldAddHydrogens()) {
			IChemObjectBuilder builder = DefaultChemObjectBuilder.getInstance();
			AtomContainerManipulator.percieveAtomTypesAndConfigureAtoms(atomContainer);
			CDKHydrogenAdder.getInstance(builder)
					.addImplicitHydrogens(atomContainer);
			AtomContainerManipulator.convertImplicitToExplicitHydrogens(atomContainer);
		}
	}

}
