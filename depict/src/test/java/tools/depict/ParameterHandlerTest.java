package tools.depict;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.renderer.generators.BasicAtomGenerator;
import org.openscience.cdk.renderer.generators.BasicBondGenerator;
import org.openscience.cdk.renderer.generators.BasicSceneGenerator;
import org.openscience.cdk.renderer.generators.IGenerator;
import org.openscience.cdk.renderer.generators.IGeneratorParameter;

@SuppressWarnings("rawtypes")
public class ParameterHandlerTest {
	
	private List<IGenerator<IAtomContainer>> getGeneratorList() {
		List<IGenerator<IAtomContainer>> generators = 
				new ArrayList<IGenerator<IAtomContainer>>();
		generators.add(new BasicSceneGenerator());
		generators.add(new BasicBondGenerator());
		generators.add(new BasicAtomGenerator());
		return generators;
	}
	
	@Test
	public void nameResolutionTest() {
		List<IGenerator<IAtomContainer>> generators = getGeneratorList();
		Map<String, IGeneratorParameter> paramNameMap = 
				ParameterHandler.getParamNameMap(generators);
		for (String key : paramNameMap.keySet()) {
			System.out.println(key + "\t" + paramNameMap.get(key));
		}
	}

}
