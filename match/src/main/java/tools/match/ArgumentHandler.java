package tools.match;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.OptionBuilder;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import tools.core.BaseArgumentHandler;
import tools.core.InputHandler;

public class ArgumentHandler extends BaseArgumentHandler {
	
	@SuppressWarnings("static-access")
	public ArgumentHandler(String[] args) throws ParseException {
		Options options = super.getOptions();
		
		options.addOption(
				OptionBuilder.hasArg()
							 .withArgName("query")
							 .create('q'));
		
		options.addOption(
				OptionBuilder.hasArg()
							 .withArgName("target")
							 .create('t'));
		
		options.addOption(
				OptionBuilder.hasArg()
							 .withArgName("query type")
							 .create('Q'));
		
		options.addOption(
				OptionBuilder.hasArg()
							 .withArgName("target type")
							 .create('T'));
		
		CommandLine commandLine = super.parse(args);
		
		if (commandLine.hasOption('q')) {
			String dataValue = commandLine.getOptionValue('q');
			if (commandLine.hasOption('Q')) {
				String typeValue = commandLine.getOptionValue('Q');
				super.addInputHandler("q", dataValue, typeValue);
			} else {
				// ? use default, or throw errors.
			}
		}
		
		if (commandLine.hasOption('t')) {
			String dataValue = commandLine.getOptionValue('t');
			if (commandLine.hasOption('T')) {
				String typeValue = commandLine.getOptionValue('T');
				super.addInputHandler("t", dataValue, typeValue);
			} else {
				// ? use default, or throw errors.
			}
		}
	}
	
	public InputHandler getQueryInputHandler() {
		return super.getInputHandler("q");
	}
	
	public InputHandler getTargetInputHandler() {
		return super.getInputHandler("t");
	}

}
