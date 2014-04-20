package tools.core;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.openscience.cdk.AtomContainer;
import org.openscience.cdk.exception.CDKException;
import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.io.ISimpleChemObjectReader;
import org.openscience.cdk.io.MDLV2000Reader;
import org.openscience.cdk.io.SMILESReader;

/**
 * Common methods for all apps to access the input molecules.
 *  
 * @author maclean
 *
 */
public class InputHandler {
	
	public enum InputMode { SINGLE, MULTIPLE };
	
	private InputMode mode;
	
	private String inputFormat;
	
	private String inputFilename;
	
	private List<String> singleFormats = new ArrayList<String>();
	
	public InputHandler() {
		mode = null;
		singleFormats.add("MDL");
		singleFormats.add("SMI");
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
		return null;	//TODO
	}
	
	private InputMode getMode(String inputFormat) {
		// XXX -why doesn't the contains method work here?
//		if (singleFormats.contains(inputFormat)) {
//			return InputMode.SINGLE;
//		} else {
//			return InputMode.MULTIPLE;	// TODO
//		}
		for (String format : singleFormats) {
			if (format.equals(inputFormat)) {
				return InputMode.SINGLE;
			}
		}
		return InputMode.MULTIPLE;	// TODO
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
