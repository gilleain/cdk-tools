package tools.convert;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.List;

import org.apache.commons.cli.ParseException;
import org.openscience.cdk.ChemFile;
import org.openscience.cdk.DefaultChemObjectBuilder;
import org.openscience.cdk.exception.CDKException;
import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.interfaces.IChemFile;
import org.openscience.cdk.interfaces.IChemObjectBuilder;
import org.openscience.cdk.io.ISimpleChemObjectReader;
import org.openscience.cdk.io.MDLV2000Reader;
import org.openscience.cdk.io.MDLV2000Writer;
import org.openscience.cdk.smiles.SmilesGenerator;
import org.openscience.cdk.tools.CDKHydrogenAdder;
import org.openscience.cdk.tools.manipulator.AtomContainerManipulator;
import org.openscience.cdk.tools.manipulator.ChemFileManipulator;

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
			String inputFormat = arguments.getInputFormat();
			String outputFormat = arguments.getOutputFormat();
			
			String inputFilename = arguments.getInputFilename();
			String outputFilename = arguments.getOutputFilename();
			
			List<IAtomContainer> inputAtomContainers = null;
			if (inputFilename == null) {
				arguments.missingInputFilename();
				return;
			} else {
				if (inputFormat == null) {
					// use o.o.cdk.io.FormatFactory?
				} else {
					ISimpleChemObjectReader reader = null;
					if (inputFormat.equals("MDL")) {
						try {
							reader = new MDLV2000Reader(new FileReader(new File(inputFilename)));
						} catch (FileNotFoundException e) {
							e.printStackTrace();
							return;
						}
					}
					try {
						IChemFile chemFile = reader.read(new ChemFile());
						inputAtomContainers = ChemFileManipulator.getAllAtomContainers(chemFile);
					} catch (CDKException e) {
						e.printStackTrace();
						return;
					}
				}
			}
			if (inputAtomContainers != null) {
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
						for (IAtomContainer atomContainer : inputAtomContainers) {
							transform(atomContainer, arguments);
							String smiles = smilesGenerator.create(atomContainer);
							writer.write(smiles);
							writer.write(NEW_LINE);
							writer.flush();
						}
						writer.close();
					} else if (outputFormat.equals("MDL")) {
						MDLV2000Writer mdlWriter = new MDLV2000Writer(writer);
						for (IAtomContainer atomContainer : inputAtomContainers) {
							transform(atomContainer, arguments);
							try {
								mdlWriter.writeMolecule(atomContainer);
							} catch (Exception e) {
								// seriously.. ?
							}
						}
						mdlWriter.close();
					}
				} catch (IOException e) {
					e.printStackTrace();
				} catch (CDKException e) {
					e.printStackTrace();
				}
			}
		} catch (ParseException e) {
			System.err.println(e);
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
