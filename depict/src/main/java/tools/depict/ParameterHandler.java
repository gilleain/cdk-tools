package tools.depict;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.cli.ParseException;
import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.renderer.generators.IGenerator;
import org.openscience.cdk.renderer.generators.IGeneratorParameter;

public class ParameterHandler {
	
	@SuppressWarnings("rawtypes")
	public static Map<String, IGeneratorParameter> getParamNameMap(
			List<IGenerator<IAtomContainer>> generators) {
		Map<String, IGeneratorParameter> fullNameMap = new HashMap<String, IGeneratorParameter>();
		Map<String, List<String>> shortToMediumNameMap = new HashMap<String, List<String>>();
		Map<String, List<String>> mediumToFullNameMap = new HashMap<String, List<String>>();
		
		for (IGenerator generator : generators) {
			for (Object parameterObj : generator.getParameters()) {
				IGeneratorParameter parameter = (IGeneratorParameter) parameterObj;
				// eg org.openscience.cdk.renderer.generators.BasicBondGenerator$BondDistance
				String fullClassName = parameter.getClass().getName();	
				// eg BasicBondGenerator$BondDistance
				String outerClassName = fullClassName.substring(fullClassName.lastIndexOf('.') + 1);
				// eg BondDistance
				String innerClassName = fullClassName.substring(fullClassName.lastIndexOf("$") + 1);
				
				// TODO : use the dotted name for inner-classes, for convenience?
//				String key = outerClassName.replace("$", ".");
				
				// map the short names
				if (shortToMediumNameMap.containsKey(innerClassName)) {
					List<String> existingValues = shortToMediumNameMap.get(innerClassName);
					existingValues.add(outerClassName);
				} else {
					List<String> existingValues = new ArrayList<String>();
					shortToMediumNameMap.put(innerClassName, existingValues);
					existingValues.add(outerClassName);
				}
				
				// map the medium names
				if (mediumToFullNameMap.containsKey(outerClassName)) {
					List<String> existingValues = mediumToFullNameMap.get(outerClassName);
					existingValues.add(fullClassName);
				} else {
					List<String> existingValues = new ArrayList<String>();
					mediumToFullNameMap.put(outerClassName, existingValues);
					existingValues.add(fullClassName);
				}
				
				fullNameMap.put(fullClassName, parameter);
			}
		}
		
		// now check for clashes
		Map<String, IGeneratorParameter> paramNameMap = new HashMap<String, IGeneratorParameter>();
		for (String innerClassName : shortToMediumNameMap.keySet()) {
			List<String> outerClassNames = shortToMediumNameMap.get(innerClassName); 
			if (outerClassNames.size() > 1) {
				for (String outerClassName : outerClassNames) {
					List<String> packageClassNames = mediumToFullNameMap.get(outerClassName);
					if (packageClassNames.size() > 1) {
						
					} else {
						String packageClassName = packageClassNames.get(0);
						IGeneratorParameter parameter = fullNameMap.get(packageClassName);
						paramNameMap.put(innerClassName, parameter);
					}
				}
			} else {
				String outerClassName = outerClassNames.get(0);
				String packageClassName = mediumToFullNameMap.get(outerClassName).get(0); 
				IGeneratorParameter parameter = fullNameMap.get(packageClassName);
				paramNameMap.put(innerClassName, parameter);
			}
		}
		return paramNameMap;
	}
	
	@SuppressWarnings("rawtypes")
	public static void applyParameters(
			Map<String, IGeneratorParameter> parameterNameMap, Properties properties) throws ParseException {
		
		for (String key : properties.stringPropertyNames()) {
			if (parameterNameMap.containsKey(key)) {
				IGeneratorParameter parameter = (IGeneratorParameter) parameterNameMap.get(key);
				String value = properties.getProperty(key);
				setParameter(value, parameter);
			}
		}
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private static void setParameter(String valueString, IGeneratorParameter parameter) {
		Class parameterClass = parameter.getDefault().getClass();
		if (parameterClass == String.class) {
			System.out.println("setting " + parameter.getClass().getSimpleName() + " to " + valueString);
			parameter.setValue(valueString);
		} else if (parameterClass == Color.class) {
			System.out.println("setting " + parameter.getClass().getSimpleName() + " to " + valueString);
			parameter.setValue(Color.getColor(valueString));
		} else if (parameterClass == Integer.class) {
			System.out.println("setting " + parameter.getClass().getSimpleName() + " to " + valueString);
			parameter.setValue(Integer.parseInt(valueString));
		} else if (parameterClass == Boolean.class) {
			System.out.println("setting " + parameter.getClass().getSimpleName() + " to " + valueString);
			parameter.setValue(Boolean.parseBoolean(valueString));
		} else if (parameterClass == Double.class) {
			System.out.println("setting " + parameter.getClass().getSimpleName() + " to " + valueString);
			parameter.setValue(Double.parseDouble(valueString));
		}
	}

}
