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
	
	/**
	 * Argument handlers for individual modules may use different options for input,
	 * for example see {@link tools.match.ArgumentHandler}.
	 */
	private Map<String, InputHandler> inputHandlers;

	@SuppressWarnings("static-access")
	public BaseArgumentHandler() {
		options = new Options();
		options.addOption("h", "help", false, "Command usage");
		
		// Note "I/O" and "i/o" for files and types respectively, following openbabel
		options.addOption(
			OptionBuilder.hasArg()
						 .withArgName("filepath")
						 .withDescription("Input filename")
						 .create('I'));
		
		options.addOption(
			OptionBuilder.hasArg()
						 .withArgName("type")
						 .withDescription("Input type")
						 .create('i'));
		
		
		options.addOption(
			OptionBuilder.hasArg()
						 .withArgName("filepath")
						 .withDescription("Output filename")
						 .create('O'));
		
		options.addOption(
			OptionBuilder.hasArg()
						 .withArgName("type")
  		   				 .withDescription("Output type")
						 .create('o'));
		
		this.inputHandlers = new HashMap<String, InputHandler>();
		this.outputHandler = new OutputHandler();
	}
	
	public CommandLine parse(String[] args) throws ParseException {
		PosixParser parser = new PosixParser();
		boolean stopAtNonOption = true;
		commandLine = parser.parse(options, args, stopAtNonOption);
		
		if (commandLine.hasOption('I')) {
			String dataValue = commandLine.getOptionValue('I');
			if (commandLine.hasOption('i')) {
				String typeValue = commandLine.getOptionValue('i');
				addInputHandler("i", dataValue, typeValue);
			} else {
				// ? use default, or throw errors.
			}
		}
		
		if (commandLine.hasOption('O')) {
			outputHandler.setOutputFilename(commandLine.getOptionValue('O'));
		}
		
		if (commandLine.hasOption('o')) {
			outputHandler.setOutputFormat(commandLine.getOptionValue('o'));
		}
		return commandLine;
	}
	
	public Options getOptions() {
		return this.options;
	}
	
	public void missingInputFilename() {
		System.err.println("Please supply an input filename with -I");
	}
	
	/**
	 * Get the default input handler for the option '-i'.
	 * 
	 * @return the input handler for -i
	 */
	public InputHandler getInputHandler() {
		return getInputHandler("i");
	}
	
	/**
	 * Get the input handler for the specific option value.
	 * 
	 * @param option the command-line option related to this input
	 * @return
	 */
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
