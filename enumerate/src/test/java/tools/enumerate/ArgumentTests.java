package tools.enumerate;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.apache.commons.cli.ParseException;
import org.junit.Test;

public class ArgumentTests {
	
	@Test
	public void limitTest() throws ParseException {
		String[] args = new String[] {"-l", "5", "-r"};
		ArgumentHandler handler = new ArgumentHandler(args);
		assertTrue(handler.getMakeSingleRandomStructure());
		assertEquals(5, handler.getLimitToGenerate());
	}

}
