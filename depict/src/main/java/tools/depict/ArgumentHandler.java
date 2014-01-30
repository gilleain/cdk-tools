package tools.depict;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.OptionBuilder;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import tools.core.BaseArgumentHandler;

public class ArgumentHandler extends BaseArgumentHandler {
	
	private Properties imageProperties;
	
	private String imagePropertiesFile;
	
	private boolean isImagePropertiesHelp;
	
	@SuppressWarnings("static-access")
	public ArgumentHandler(String[] args) throws ParseException {
		super(args);
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

}
