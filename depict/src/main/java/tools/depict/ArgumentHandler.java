package tools.depict;

import java.util.Properties;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.OptionBuilder;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import tools.core.BaseArgumentHandler;

public class ArgumentHandler extends BaseArgumentHandler {
	
	private Properties imageProperties;
	
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

}
