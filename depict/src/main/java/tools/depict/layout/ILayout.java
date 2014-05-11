package tools.depict.layout;

import org.openscience.cdk.interfaces.IChemObject;

public interface ILayout<T extends IChemObject> {

	public void layout(T obj);
}
