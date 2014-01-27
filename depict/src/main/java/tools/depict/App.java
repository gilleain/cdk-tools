package tools.depict;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import org.apache.commons.cli.ParseException;
import org.openscience.cdk.ChemFile;
import org.openscience.cdk.exception.CDKException;
import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.interfaces.IChemFile;
import org.openscience.cdk.io.ISimpleChemObjectReader;
import org.openscience.cdk.io.MDLV2000Reader;
import org.openscience.cdk.layout.StructureDiagramGenerator;
import org.openscience.cdk.renderer.AtomContainerRenderer;
import org.openscience.cdk.renderer.IRenderer;
import org.openscience.cdk.renderer.RendererModel;
import org.openscience.cdk.renderer.font.AWTFontManager;
import org.openscience.cdk.renderer.generators.BasicAtomGenerator;
import org.openscience.cdk.renderer.generators.BasicBondGenerator;
import org.openscience.cdk.renderer.generators.BasicSceneGenerator;
import org.openscience.cdk.renderer.generators.IGenerator;
import org.openscience.cdk.renderer.visitor.AWTDrawVisitor;
import org.openscience.cdk.tools.manipulator.ChemFileManipulator;

/**
 * CDK-Tools IO main application class, used for converting between file formats.
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
			String inputFormat = arguments.getInputFormat();
			String outputFormat = arguments.getOutputFormat();
			
			String inputFilename = arguments.getInputFilename();
			String outputFilename = arguments.getOutputFilename();
			
			List<IAtomContainer> inputAtomContainers = null;
			if (inputFilename == null) {
				arguments.missingInputFilename();
				return;
			} else {
				if (inputFormat == null) {
					// use o.o.cdk.io.FormatFactory?
				} else {
					ISimpleChemObjectReader reader = null;
					if (inputFormat.equals("MDL")) {
						try {
							reader = new MDLV2000Reader(new FileReader(new File(inputFilename)));
						} catch (FileNotFoundException e) {
							e.printStackTrace();
							return;
						}
					}
					try {
						IChemFile chemFile = reader.read(new ChemFile());
						inputAtomContainers = ChemFileManipulator.getAllAtomContainers(chemFile);
					} catch (CDKException e) {
						e.printStackTrace();
						return;
					}
				}
			}
			if (inputAtomContainers != null) {
				if (outputFormat.equals("PNG")) {
					try {
						for (IAtomContainer atomContainer : inputAtomContainers) {
							atomContainer = makeDiagram(atomContainer);
							Image image = draw(atomContainer);
							ImageIO.write(
									(RenderedImage) image, "PNG", new File(outputFilename));
						}
					} catch (IOException e) {
						e.printStackTrace();
					} catch (CDKException e) {
						e.printStackTrace();
					}
				}
			}
		} catch (ParseException e) {
			System.err.println(e);
		}
	}
	
	private static List<IGenerator<IAtomContainer>> makeGenerators() {
		List<IGenerator<IAtomContainer>> generators = new ArrayList<IGenerator<IAtomContainer>>();
		generators.add(new BasicSceneGenerator());
		generators.add(new BasicBondGenerator());
		generators.add(new BasicAtomGenerator());
		return generators;
	}
	
	private static IAtomContainer makeDiagram(IAtomContainer atomContainer) throws CDKException {
		StructureDiagramGenerator sdg = new StructureDiagramGenerator();
		sdg.setMolecule(atomContainer);
		sdg.generateCoordinates();
		IAtomContainer resultMolecule = sdg.getMolecule(); 
		return resultMolecule;
	}
	
	private static Image draw(IAtomContainer atomContainer) {
		int w = 500;
		int h = 500;
		List<IGenerator<IAtomContainer>> generators = makeGenerators();
		IRenderer<IAtomContainer> atomContainerRenderer = 
				new AtomContainerRenderer(generators, new AWTFontManager());
		RendererModel model = atomContainerRenderer.getRenderer2DModel();
		model.set(BasicSceneGenerator.BackgroundColor.class, Color.WHITE);
		Image image = new BufferedImage(w, h, BufferedImage.TYPE_4BYTE_ABGR);
		Graphics graphics = image.getGraphics();
		graphics.setColor(Color.WHITE);
		graphics.fillRect(0, 0, w, h);
		atomContainerRenderer.setup(atomContainer, new Rectangle(w, h));
		atomContainerRenderer.paint(atomContainer, new AWTDrawVisitor((Graphics2D) graphics));
		graphics.dispose();
		return image;
	}

}
