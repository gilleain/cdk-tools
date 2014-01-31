package tools.core;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.OptionBuilder;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.cli.PosixParser;

/**
 * The basic argument handler to deal with arguments common to all the tools.
 * 
 * @author maclean
 *
 */
public class BaseArgumentHandler {
	
	private final Options options;
	
	private CommandLine commandLine;
	
	private InputHandler inputHandler;
	
	private OutputHandler outputHandler;

	@SuppressWarnings("static-access")
	public BaseArgumentHandler(String[] args) {
		options = new Options();
		options.addOption("h", "help", false, "Command usage");
		options.addOption(
			OptionBuilder.hasArg()
						 .withArgName("type")
						 .withDescription("Input type")
						 .create('i'));
		
		options.addOption(
			OptionBuilder.hasArg()
						 .withArgName("filepath")
						 .withDescription("Input filename")
						 .create('I'));
		
		
		options.addOption(
			OptionBuilder.hasArg()
						 .withArgName("type")
						 .withDescription("Output type")
						 .create('o'));
		
		options.addOption(
			OptionBuilder.hasArg()
						 .withArgName("filepath")
						 .withDescription("Output filename")
						 .create('O'));
	}
	
	public CommandLine parse(String[] args) throws ParseException {
		PosixParser parser = new PosixParser();
		boolean stopAtNonOption = true;
		commandLine = parser.parse(options, args, stopAtNonOption);
		
		if (commandLine.hasOption('i')) {
			inputHandler.setInputFilename(commandLine.getOptionValue('i'));
		}
		
		if (commandLine.hasOption('I')) {
			inputHandler.setInputFormat(commandLine.getOptionValue('I'));
		}
		
		if (commandLine.hasOption('o')) {
			outputHandler.setOutputFilename(commandLine.getOptionValue('o'));
		}
		
		if (commandLine.hasOption('O')) {
			outputHandler.setOutputFormat(commandLine.getOptionValue('O'));
		}
		return commandLine;
	}
	
	public Options getOptions() {
		return this.options;
	}
	
	public void missingInputFilename() {
		System.err.println("Please supply an input filename with -I");
	}
	
	public InputHandler getInputHandler() {
		return inputHandler;
	}
	
	public OutputHandler getOutputHandler() {
		return outputHandler;
	}
}
