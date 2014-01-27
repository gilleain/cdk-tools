package tools.io;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.OptionBuilder;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.cli.PosixParser;

public class ArgumentHandler {
	
	private final Options options;
	
	private String inputFormat;
	
	private String outputFormat;
	
	private String inputFilename;
	
	private String outputFilename;

	@SuppressWarnings("static-access")
	public ArgumentHandler(String[] args) throws ParseException {
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
		
		PosixParser parser = new PosixParser();
		boolean stopAtNonOption = true;
		CommandLine line = parser.parse(options, args, stopAtNonOption);
		
		if (line.hasOption('i')) {
			inputFormat = line.getOptionValue('i');
		}
		
		if (line.hasOption('o')) {
			outputFormat = line.getOptionValue('o');
		}
		
		if (line.hasOption('I')) {
			inputFilename = line.getOptionValue('I');
		}
		
		if (line.hasOption('O')) {
			outputFilename = line.getOptionValue('O');
		}
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
