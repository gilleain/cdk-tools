package tools.convert;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.OptionBuilder;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import tools.core.BaseArgumentHandler;

public class ArgumentHandler extends BaseArgumentHandler {
	
	private boolean shouldAddHydrogens = false;
	
	@SuppressWarnings("static-access")
	public ArgumentHandler(String[] args) throws ParseException {
		super(args);
		Options options = super.getOptions();
		options.addOption(
				OptionBuilder.withDescription("Add hydrogens")
							 .create('a'));
		
		CommandLine commandLine = super.parse(args);
		
		if (commandLine.hasOption('a')) {
			System.out.println("adding hydrogens");
			setShouldAddHydrogens(true);
		}
	}

	public boolean shouldAddHydrogens() {
		return shouldAddHydrogens;
	}

	public void setShouldAddHydrogens(boolean shouldAddHydrogens) {
		this.shouldAddHydrogens = shouldAddHydrogens;
	}
	
}
