package tools.depict;

import java.awt.Dimension;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.OptionBuilder;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import tools.core.BaseArgumentHandler;
import tools.depict.layout.LayoutMethod;

public class ArgumentHandler extends BaseArgumentHandler {
	
	private Properties imageProperties;
	
	private String imagePropertiesFile;
	
	private boolean isImagePropertiesHelp;
	
	private List<Integer> matchedAtoms;
	
	private LayoutMethod layoutMethod;
	
	private int rows;
	
	private int cols;
	
	private int cellWidth;
	
	private int cellHeight;
	
	private int imageWidth;
	
	private int imageHeight;
	
	@SuppressWarnings("static-access")
	public ArgumentHandler(String[] args) throws ParseException {
		Options options = super.getOptions();
		options.addOption(
				OptionBuilder.hasOptionalArgs(2)
							 .withDescription("Image Properties")
							 .withValueSeparator()
							 .withArgName("option=value")
							 .create('P'));
		options.addOption(
				OptionBuilder.hasArg()
							 .withArgName("image properties file")
							 .create('p'));
		
		options.addOption(
				OptionBuilder.hasArg()
							 .withArgName("matched atoms")
							 .create('m'));
		
		options.addOption(
				OptionBuilder.hasArg()
							 .withArgName("layout method")
							 .withLongOpt("layout")
							 .create("l"));
		
		options.addOption(
				OptionBuilder.hasArg()
							 .withDescription("dimension of the cell WxH")
							 .withArgName("dimension")
							 .withLongOpt("dimension")
							 .create("d"));
		
		options.addOption(
				OptionBuilder.hasArg()
							 .withDescription("rows and columns of the grid RxC")
							 .withArgName("gridDimension")
							 .withLongOpt("gridDimension")
							 .create("g"));
		
		options.addOption(
				OptionBuilder.hasArg()
							 .withDescription("dimension of the image WxH")
							 .withArgName("imageDimension")
							 .withLongOpt("imageDimension")
							 .create("D"));
		
		CommandLine commandLine = super.parse(args);
		
		if (commandLine.hasOption('P')) {
			Properties optionProperties = commandLine.getOptionProperties("P"); 
			if (optionProperties.isEmpty()) {
				setImagePropertiesHelp(true);
			} else {
				setImageProperties(optionProperties);
			}
		} else {
			setImageProperties(new Properties());
		}
		
		if (commandLine.hasOption('p')) {
			String propertiesFilename = commandLine.getOptionValue('p');
			Properties propertiesFromFile = new Properties();
			try {
				FileInputStream input = new FileInputStream(propertiesFilename);
				propertiesFromFile.load(input);
				for (String property : propertiesFromFile.stringPropertyNames()) {
					imageProperties.put(property, propertiesFromFile.get(property));
				}
				input.close();
			} catch (FileNotFoundException fnf) {
				throw new ParseException("Properties file not found " + propertiesFilename);
			} catch (IOException e) {
				throw new ParseException("Properties file I/O error " + propertiesFilename);
			}
		}
		
		if (commandLine.hasOption('m')) {
			String matchList = commandLine.getOptionValue('m');
			String[] stringList = matchList.split(",");
			matchedAtoms = new ArrayList<Integer>();
			try {
				for (String stringPart : stringList) {
					int match = Integer.parseInt(stringPart);
					matchedAtoms.add(match);
					System.out.println("adding match " + match);
				}
			} catch (NumberFormatException nfe) {
				throw new ParseException("Match string invalid " + matchList);
			}
		} else {
			setMatchedAtoms(new ArrayList<Integer>());
		}
		
		if (commandLine.hasOption("l")) {
			String layoutOption = commandLine.getOptionValue('l', "SINGLE");
			LayoutMethod selectedMethod = LayoutMethod.valueOf(layoutOption.toUpperCase());
			setLayoutMethod(selectedMethod);
		}
		
		if (commandLine.hasOption("g")) {
			Dimension d = parseDimesionArg(commandLine, "g");
			cols = d.width;
			rows = d.height;
		}
		
		if (commandLine.hasOption("d")) {
			Dimension d = parseDimesionArg(commandLine, "d");
			cellWidth = d.width;
			cellHeight = d.height;
		}
		
		if (commandLine.hasOption("D")) {
			Dimension d = parseDimesionArg(commandLine, "D");
			imageWidth = d.width;
			imageHeight = d.height;
		}
	}
	
	private Dimension parseDimesionArg(CommandLine commandLine, String option) throws ParseException {
		String[] dimensionStringValues = commandLine.getOptionValue(option).split("x");
		Dimension dim = new Dimension();
		try {
			dim.height = Integer.parseInt(dimensionStringValues[0]);
			dim.width = Integer.parseInt(dimensionStringValues[1]);
		} catch (NumberFormatException nfe) {
			throw new ParseException("Dimension string invalid " + commandLine.getOptionValue(option));
		} catch (IndexOutOfBoundsException iiobe) {
			throw new ParseException("Dimension string invalid " + commandLine.getOptionValue(option));
		}
		return dim;
	}
	
	public void setImageProperties(Properties imageProperties) {
		this.imageProperties = imageProperties;
	}
	
	public Properties getImageProperties() {
		return this.imageProperties;
	}

	public boolean isImagePropertiesHelp() {
		return isImagePropertiesHelp;
	}

	public void setImagePropertiesHelp(boolean isImagePropertiesHelp) {
		this.isImagePropertiesHelp = isImagePropertiesHelp;
	}
	
	public String getImagePropertiesFile() {
		return imagePropertiesFile;
	}

	public void setImagePropertiesFile(String imagePropertiesFile) {
		this.imagePropertiesFile = imagePropertiesFile;
	}

	public List<Integer> getMatchedAtoms() {
		return matchedAtoms;
	}

	public void setMatchedAtoms(List<Integer> matchedAtoms) {
		this.matchedAtoms = matchedAtoms;
	}

	public LayoutMethod getLayoutMethod() {
		return layoutMethod;
	}

	public void setLayoutMethod(LayoutMethod layoutMethod) {
		this.layoutMethod = layoutMethod;
	}

	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

	public int getCols() {
		return cols;
	}

	public void setCols(int cols) {
		this.cols = cols;
	}

	public int getCellWidth() {
		return cellWidth;
	}

	public void setCellWidth(int cellWidth) {
		this.cellWidth = cellWidth;
	}

	public int getCellHeight() {
		return cellHeight;
	}

	public void setCellHeight(int cellHeight) {
		this.cellHeight = cellHeight;
	}

	public int getImageWidth() {
		return imageWidth;
	}

	public void setImageWidth(int imageWidth) {
		this.imageWidth = imageWidth;
	}

	public int getImageHeight() {
		return imageHeight;
	}

	public void setImageHeight(int imageHeight) {
		this.imageHeight = imageHeight;
	}

}
