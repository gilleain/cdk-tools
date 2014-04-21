package tools.enumerate;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.OptionBuilder;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import tools.core.BaseArgumentHandler;

public class ArgumentHandler extends BaseArgumentHandler {
	
	private boolean makeSingleRandomStructure = false;
	
	private int limitToGenerate = 50;
	
	@SuppressWarnings("static-access")
	public ArgumentHandler(String[] args) throws ParseException {
		Options options = super.getOptions();
		options.addOption(
				OptionBuilder.withDescription("Random structure(s)")
							 .create('r'));
		options.addOption(
				OptionBuilder.hasArg()
							 .withDescription("Limiting number")
							 .create('l'));
		
		CommandLine commandLine = super.parse(args);
		
		if (commandLine.hasOption('r')) {
			System.out.println("generating random structures");
			setMakeSingleRandomStructure(true);
		}
		
		if (commandLine.hasOption('l')) {
			int limit = Integer.parseInt(commandLine.getOptionValue('l'));
			System.out.println("limiting to " + limit);
			setLimitToGenerate(limit);
		}
	}

	public boolean getMakeSingleRandomStructure() {
		return makeSingleRandomStructure;
	}

	public void setMakeSingleRandomStructure(boolean makeSingleRandomStructure) {
		this.makeSingleRandomStructure = makeSingleRandomStructure;
	}

	public int getLimitToGenerate() {
		return limitToGenerate;
	}

	public void setLimitToGenerate(int limitToGenerate) {
		this.limitToGenerate = limitToGenerate;
	}
	
}
