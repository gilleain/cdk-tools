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
	
	private String inputFormat;
	
	private String outputFormat;
	
	private String inputFilename;
	
	private String outputFilename;

	@SuppressWarnings("static-access")
	public BaseArgumentHandler(String[] args) {
		options = new Options();
		options.addOption("h", "help", false, "Command usage");
		options.addOption(
			OptionBuilder.hasArg().withArgName("type").withDescription("Input type").create('i'));
		options.addOption(
			OptionBuilder.hasArg().withArgName("type").withDescription("Output type").create('o'));
		options.addOption(
			OptionBuilder.hasArg().withArgName("filepath").withDescription("Input filename").create('I'));
		options.addOption(
			OptionBuilder.hasArg().withArgName("filepath").withDescription("Output filename").create('O'));
	}
	
	public CommandLine parse(String[] args) throws ParseException {
		PosixParser parser = new PosixParser();
		boolean stopAtNonOption = true;
		commandLine = parser.parse(options, args, stopAtNonOption);
		
		if (commandLine.hasOption('i')) {
			inputFormat = commandLine.getOptionValue('i');
		}
		
		if (commandLine.hasOption('o')) {
			outputFormat = commandLine.getOptionValue('o');
		}
		
		if (commandLine.hasOption('I')) {
			inputFilename = commandLine.getOptionValue('I');
		}
		
		if (commandLine.hasOption('O')) {
			outputFilename = commandLine.getOptionValue('O');
		}
		return commandLine;
	}
	
	public Options getOptions() {
		return this.options;
	}

	public String getInputFormat() {
		return inputFormat;
	}

	public void setInputFormat(String inputFormat) {
		this.inputFormat = inputFormat;
	}

	public String getOutputFormat() {
		return outputFormat;
	}

	public void setOutputFormat(String outputFormat) {
		this.outputFormat = outputFormat;
	}
	
	public void missingInputFilename() {
		System.err.println("Please supply an input filename with -I");
	}

	public String getInputFilename() {
		return inputFilename;
	}

	public void setInputFilename(String inputFilename) {
		this.inputFilename = inputFilename;
	}

	public String getOutputFilename() {
		return outputFilename;
	}

	public void setOutputFilename(String outputFilename) {
		this.outputFilename = outputFilename;
	}

}
