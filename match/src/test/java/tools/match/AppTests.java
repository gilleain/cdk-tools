package tools.match;

import org.junit.Test;

public class AppTests {
	
	private String find(String path) {
		return "src/test/resources/data/" + path;
	}
	
	@Test
	public void singleMatch() {
		App.main(new String[] {"-q", find("cc.mdl"), "-Q", "MDL", "-t", find("cccc.mdl"), "-T", "MDL"});
	}

}
