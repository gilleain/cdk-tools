package tools.io;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import org.apache.commons.cli.ParseException;
import org.openscience.cdk.ChemFile;
import org.openscience.cdk.exception.CDKException;
import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.interfaces.IChemFile;
import org.openscience.cdk.io.ISimpleChemObjectReader;
import org.openscience.cdk.io.MDLV2000Reader;
import org.openscience.cdk.smiles.SmilesGenerator;
import org.openscience.cdk.tools.manipulator.ChemFileManipulator;

/**
 * CDK-Tools IO main application class, used for converting between file formats.
 * 
 * @author maclean
 *
 */
public class App {

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
				if (outputFormat.equals("SMI")) {
					try {
						BufferedWriter writer = new BufferedWriter(
								new FileWriter(new File(outputFilename)));
						SmilesGenerator smilesGenerator = SmilesGenerator.unique();
						for (IAtomContainer atomContainer : inputAtomContainers) {
							String smiles = smilesGenerator.create(atomContainer);
							writer.write(smiles);
							writer.newLine();
							writer.flush();
						}
						writer.close();
					} catch (IOException e) {
						e.printStackTrace();
					} catch (CDKException e) {
						e.printStackTrace();
					}
				}
			}
		} catch (ParseException e) {
			System.err.println(e);
		}
		
	}

}
