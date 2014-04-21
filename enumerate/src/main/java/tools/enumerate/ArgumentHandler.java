package tools.enumerate;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.OptionBuilder;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import tools.core.BaseArgumentHandler;

public class ArgumentHandler extends BaseArgumentHandler {
	
	private boolean makeSingleRandomStructure = false;
	
	@SuppressWarnings("static-access")
	public ArgumentHandler(String[] args) throws ParseException {
		Options options = super.getOptions();
		options.addOption(
				OptionBuilder.withDescription("Single random structure")
							 .create('r'));
		
		CommandLine commandLine = super.parse(args);
		
		if (commandLine.hasOption('r')) {
			System.out.println("generating single structure");
			setMakeSingleRandomStructure(true);
		}
	}

	public boolean getMakeSingleRandomStructure() {
		return makeSingleRandomStructure;
	}

	public void setMakeSingleRandomStructure(boolean makeSingleRandomStructure) {
		this.makeSingleRandomStructure = makeSingleRandomStructure;
	}
	
}
