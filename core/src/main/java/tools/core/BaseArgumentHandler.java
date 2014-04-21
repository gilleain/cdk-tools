package tools.core;

import java.util.HashMap;
import java.util.Map;

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
	
	private OutputHandler outputHandler;
	
	private Map<String, InputHandler> inputHandlers;

	@SuppressWarnings("static-access")
	public BaseArgumentHandler(String[] args) {
		options = new Options();
		options.addOption("h", "help", false, "Command usage");
		options.addOption(
			OptionBuilder.hasArg()
						 .withArgName("filepath")
						 .withDescription("Input filename")
						 .create('i'));
		
		options.addOption(
			OptionBuilder.hasArg()
						 .withArgName("type")
						 .withDescription("Input type")
						 .create('I'));
		
		
		options.addOption(
			OptionBuilder.hasArg()
						 .withArgName("filepath")
						 .withDescription("Output filename")
						 .create('o'));
		
		options.addOption(
			OptionBuilder.hasArg()
						 .withArgName("type")
  		   				 .withDescription("Output type")
						 .create('O'));
		
		this.inputHandlers = new HashMap<String, InputHandler>();
	}
	
	public CommandLine parse(String[] args) throws ParseException {
		PosixParser parser = new PosixParser();
		boolean stopAtNonOption = true;
		commandLine = parser.parse(options, args, stopAtNonOption);
		
		if (commandLine.hasOption('i')) {
			String dataValue = commandLine.getOptionValue('i');
			if (commandLine.hasOption('I')) {
				String typeValue = commandLine.getOptionValue('I');
				addInputHandler("i", dataValue, typeValue);
			} else {
				// ? use default, or throw errors.
			}
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
		return getInputHandler("i");
	}
	public InputHandler getInputHandler(String option) {
		if (inputHandlers.containsKey(option)) {
			return inputHandlers.get(option);
		}
		return null;	// TODO : error/message
	}
	
	public void addInputHandler(
			String option, String dataValue, String typeValue) {
		this.inputHandlers.put(option, 
				makeInputHandler(dataValue, typeValue));
	}
	
	private InputHandler makeInputHandler(String dataValue, String typeValue) {
		InputHandler inputHandler = new InputHandler();
		inputHandler.setInputFilename(dataValue);
		inputHandler.setInputFormat(typeValue);
		return inputHandler;
	}
	
	public OutputHandler getOutputHandler() {
		return outputHandler;
	}
}
