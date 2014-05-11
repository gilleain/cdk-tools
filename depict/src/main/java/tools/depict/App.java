package tools.depict;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;

import org.apache.commons.cli.ParseException;
import org.openscience.cdk.exception.CDKException;
import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.layout.StructureDiagramGenerator;
import org.openscience.cdk.renderer.AtomContainerRenderer;
import org.openscience.cdk.renderer.IRenderer;
import org.openscience.cdk.renderer.RendererModel;
import org.openscience.cdk.renderer.font.AWTFontManager;
import org.openscience.cdk.renderer.generators.BasicAtomGenerator;
import org.openscience.cdk.renderer.generators.BasicBondGenerator;
import org.openscience.cdk.renderer.generators.BasicSceneGenerator;
import org.openscience.cdk.renderer.generators.IGenerator;
import org.openscience.cdk.renderer.generators.IGeneratorParameter;
import org.openscience.cdk.renderer.visitor.AWTDrawVisitor;

import tools.core.InputHandler;
import tools.core.OutputHandler;
import tools.depict.layout.GridLayout;
import tools.depict.layout.LayoutFactory;
import tools.depict.layout.LayoutMethod;

/**
 * CDK-Tools 'depict' main application class, used for drawing molecules.
 * 
 * @author maclean
 *
 */
public class App {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			ArgumentHandler arguments = new ArgumentHandler(args);
			
			// verify any generator parameters as early as possible
			List<IGenerator<IAtomContainer>> generators = makeGenerators();
			@SuppressWarnings("rawtypes")
			Map<String, IGeneratorParameter> parameterNameMap = 
					ParameterHandler.getParamNameMap(generators);
			ParameterHandler.applyParameters(parameterNameMap, arguments.getImageProperties());
			
			InputHandler input = arguments.getInputHandler();
			if (input.getInputMode() == InputHandler.InputMode.SINGLE) {
				IAtomContainer atomContainer = input.getSingleInput();
				drawAtomContainer(atomContainer, arguments);
			} else {
				List<IAtomContainer> inputAtomContainers = input.getMultipleInputs();
				LayoutMethod layoutMethod = arguments.getLayoutMethod();
				if (layoutMethod == LayoutMethod.SINGLE) {
					for (IAtomContainer atomContainer : inputAtomContainers) {
						drawAtomContainer(atomContainer, arguments);
					}
				} else {
					drawMultipleAtomContainers(inputAtomContainers, arguments);
				}
			}
			
		} catch (ParseException e) {
			System.err.println(e);
		}
	}
	
	private static void drawMultipleAtomContainers(List<IAtomContainer> atomContainers, ArgumentHandler arguments) {
		LayoutMethod layoutMethod = arguments.getLayoutMethod();
		List<IGenerator<IAtomContainer>> generators = makeGenerators();
		List<IAtomContainer> laidOutAtomContainers = new ArrayList<IAtomContainer>();
		for (IAtomContainer atomContainer : atomContainers) {
			try {
				laidOutAtomContainers.add(makeDiagram(atomContainer));
			} catch (CDKException e) {
				e.printStackTrace();
			}
		}
		IRenderer<IAtomContainer> renderer = new AtomContainerRenderer(generators, new AWTFontManager());
		int w = 500;
		int h = 500;
		RendererModel model = renderer.getRenderer2DModel();
		model.set(BasicSceneGenerator.BackgroundColor.class, Color.WHITE);
		Image image = new BufferedImage(w, h, BufferedImage.TYPE_4BYTE_ABGR);
		Graphics2D graphics = (Graphics2D) image.getGraphics();
		graphics.setColor(Color.WHITE);
		graphics.fillRect(0, 0, w, h);
		GridLayout layout = null;
		if (layoutMethod == LayoutMethod.GRID) {
			layout = LayoutFactory.makeGridLayout(atomContainers.size());
		} else if (layoutMethod == LayoutMethod.HORIZONTAL) {
			layout = LayoutFactory.makeHorizontalLayout(atomContainers.size());
		} else if (layoutMethod == LayoutMethod.VERTICAL) {
			layout = LayoutFactory.makeVerticalLayout(atomContainers.size());
		} else {
			throw new IllegalArgumentException("Unkown layout method " + layoutMethod);
		}
		layout.layout(laidOutAtomContainers, renderer, new Dimension(50, 50), graphics);
		
		OutputHandler output = arguments.getOutputHandler();
		String outputFormat = output.getOutputFormat();
		String outputFilename = output.getOutputFilename();
		try {
			if (outputFormat.equals("PNG")) {
				ImageIO.write((RenderedImage) image, "PNG", new File(outputFilename));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private static void drawAtomContainer(IAtomContainer atomContainer, ArgumentHandler arguments) {
		OutputHandler output = arguments.getOutputHandler();
		String outputFormat = output.getOutputFormat();
		String outputFilename = output.getOutputFilename();
		List<IGenerator<IAtomContainer>> generators = makeGenerators();
		try {
			// XXX - this highlights all the containers with the same matches!
			highlightAtoms(atomContainer, arguments.getMatchedAtoms());

			atomContainer = makeDiagram(atomContainer);
			IRenderer<IAtomContainer> renderer = new AtomContainerRenderer(generators, new AWTFontManager());
			Image image = draw(atomContainer, renderer, arguments);
			if (outputFormat.equals("PNG")) {
				ImageIO.write((RenderedImage) image, "PNG", new File(outputFilename));
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (CDKException e) {
			e.printStackTrace();
		}		
	}
	
	private static void highlightAtoms(IAtomContainer atomContainer, List<Integer> matches) {
		int maxIndex = atomContainer.getAtomCount() - 1;
		for (int match : matches) {
			if (maxIndex > match) {	// TODO ... else?
				atomContainer.getAtom(match).setProperty("HIGHLIGHT", true);
			}
		}
	}
	
	private static List<IGenerator<IAtomContainer>> makeGenerators() {
		List<IGenerator<IAtomContainer>> generators = new ArrayList<IGenerator<IAtomContainer>>();
		generators.add(new BasicSceneGenerator());
		generators.add(new BasicBondGenerator());
		generators.add(new BasicAtomGenerator());
		generators.add(new HighlightGenerator());
		return generators;
	}
	
	private static IAtomContainer makeDiagram(IAtomContainer atomContainer) throws CDKException {
		StructureDiagramGenerator sdg = new StructureDiagramGenerator();
		sdg.setMolecule(atomContainer);
		sdg.generateCoordinates();
		IAtomContainer resultMolecule = sdg.getMolecule(); 
		return resultMolecule;
	}
	
	private static Image draw(IAtomContainer atomContainer, 
							  IRenderer<IAtomContainer> renderer,
							  ArgumentHandler arguments) {
		int w = 500;
		int h = 500;
		
		RendererModel model = renderer.getRenderer2DModel();
		model.set(BasicSceneGenerator.BackgroundColor.class, Color.WHITE);
		Image image = new BufferedImage(w, h, BufferedImage.TYPE_4BYTE_ABGR);
		Graphics graphics = image.getGraphics();
		graphics.setColor(Color.WHITE);
		graphics.fillRect(0, 0, w, h);
		renderer.setup(atomContainer, new Rectangle(w, h));
		renderer.paint(atomContainer, new AWTDrawVisitor((Graphics2D) graphics));
		graphics.dispose();
		return image;
	}

}
