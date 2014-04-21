package tools.core;

import static org.junit.Assert.assertEquals;

import org.apache.commons.cli.ParseException;
import org.junit.Test;

public class BaseArgumentHandlerTests {
	
	@Test
	public void parseIOTest() throws ParseException {
		String[] args = new String[] {"-i", "/in/path", "-I", "IN_TYPE", "-o", "/out/path", "-O", "OUT_TYPE"};
		BaseArgumentHandler handler = new BaseArgumentHandler();
		handler.parse(args);
		assertEquals("/in/path", handler.getInputHandler().getInputFilename());
		assertEquals("/out/path", handler.getOutputHandler().getOutputFilename());
	}

}
