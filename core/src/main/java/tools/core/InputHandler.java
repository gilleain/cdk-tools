package tools.core;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.openscience.cdk.AtomContainer;
import org.openscience.cdk.DefaultChemObjectBuilder;
import org.openscience.cdk.exception.CDKException;
import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.io.ISimpleChemObjectReader;
import org.openscience.cdk.io.MDLV2000Reader;
import org.openscience.cdk.io.SMILESReader;
import org.openscience.cdk.io.iterator.IIteratingChemObjectReader;
import org.openscience.cdk.io.iterator.IteratingSDFReader;
import org.openscience.cdk.io.iterator.IteratingSMILESReader;

/**
 * Common methods for all apps to access the input molecules.
 *  
 * @author maclean
 *
 */
public class InputHandler {
	
	public enum InputMode { UNKNOWN, SINGLE, MULTIPLE };
	
	private InputMode mode;
	
	private String inputFormat;
	
	private String inputFilename;
	
	/**
	 * The formats that are single molecule per file.
	 */
	private List<String> singleFormats = new ArrayList<String>();
	
	/**
	 * Formats that are multiple molecule per file.
	 */
	private List<String> multipleFormats = new ArrayList<String>();
	
	public InputHandler() {
		mode = null;
		singleFormats.add("MDL");
		multipleFormats.add("SMI");
		multipleFormats.add("SDF");
	}

	public InputMode getInputMode() {
		if (mode == null) {
			mode = getMode(inputFormat);
		}
		return mode;
	}
	
	public IAtomContainer getSingleInput() {
		if (inputFilename == null) {
			return null;
		} else {
			if (inputFormat == null) {
				// use o.o.cdk.io.FormatFactory?
			} else {
				FileReader fileReader;
				try {
					fileReader = new FileReader(new File(inputFilename));
				} catch (FileNotFoundException e) {
					e.printStackTrace();
					return null;
				} 
				ISimpleChemObjectReader reader = null;
				if (inputFormat.equals("MDL")) {
					reader = new MDLV2000Reader(fileReader);
				} else if (inputFormat.equals("SMI")) {
					reader = new SMILESReader(fileReader);
				}
				try {
					try {
	//					IChemFile chemFile = reader.read(new ChemFile());
	//					inputAtomContainers = ChemFileManipulator.getAllAtomContainers(chemFile);
						IAtomContainer atomContainer = reader.read(new AtomContainer());
						return atomContainer;
					} catch (CDKException e) {
						e.printStackTrace();
						return null;
					} finally {
						reader.close();
					}
				} catch (IOException ioe) {
					// UGH
					ioe.printStackTrace();
				}
			}
		}
		
		return null;
	}
	
	public List<IAtomContainer> getMultipleInputs() {
		if (inputFilename == null) {
			return null;
		} else {
			if (inputFormat == null) {
				// use o.o.cdk.io.FormatFactory?
			} else {
				FileReader fileReader;
				try {
					fileReader = new FileReader(new File(inputFilename));
				} catch (FileNotFoundException e) {
					e.printStackTrace();
					return null;
				} 
				IIteratingChemObjectReader<IAtomContainer> reader = null;
				if (inputFormat.equals("SDF")) {
					reader = new IteratingSDFReader(fileReader, DefaultChemObjectBuilder.getInstance());
				} else if (inputFormat.equals("SMI")) {
					reader = new IteratingSMILESReader(fileReader, DefaultChemObjectBuilder.getInstance());
				}
				try {
					try {
						List<IAtomContainer> inputs = new ArrayList<IAtomContainer>();
						while (reader.hasNext()) {
							inputs.add(reader.next());
						}
						return inputs;
					} finally {
						reader.close();
					}
				} catch (IOException ioe) {
					// UGH
					ioe.printStackTrace();
				}
			}
		}
		
		return null;
	}
	
	private InputMode getMode(String inputFormat) {
		for (String format : singleFormats) {
			if (format.equals(inputFormat)) {
				return InputMode.SINGLE;
			}
		}
		for (String format : multipleFormats) {
			if (format.equals(inputFormat)) {
				return InputMode.MULTIPLE;
			}
		}
		return InputMode.UNKNOWN;
	}
	
	public String getInputFormat() {
		return inputFormat;
	}

	public void setInputFormat(String inputFormat) {
		this.inputFormat = inputFormat;
	}

	public String getInputFilename() {
		return inputFilename;
	}

	public void setInputFilename(String inputFilename) {
		this.inputFilename = inputFilename;
	}
}
