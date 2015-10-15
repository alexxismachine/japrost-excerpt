/**
 * 
 */
package de.japrost.excerpt;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Properties;
import java.util.StringTokenizer;

import javax.imageio.ImageIO;


/**
 *
 */
public class Main {

	static Properties props;

	public static void main(final String[] args) throws IOException {
		ConfigurationParser parser = new ConfigurationParser();
		props = parser.readConfiguration();

		ImageMaker im = new ImageMaker();
		im.setProps(props);
		// for each flyer
		String flyers = (String)props.get("flyers");
		StringTokenizer flyerT = new StringTokenizer(flyers, ",");
		while (flyerT.hasMoreTokens()) {
			String flyer = flyerT.nextToken().trim();
			System.out.println("Going for flyer '" + flyer + "'");
			String flyerName = (String)props.get("flyer." + flyer + ".name");
			String entries = resolveEntries(flyer);
			im.setEntries(entries);
			// for each size
			String sizes = (String)props.get("sizes");
			StringTokenizer st = new StringTokenizer(sizes, ",");
			int widht = 1280;
			int height = 1024;
			int sizeBase = 48;
			while (st.hasMoreTokens()) {
				String toc = st.nextToken();
				if (toc.contains("x")) {
					String[] size = toc.split("x");
					widht = Integer.parseInt(size[0]);
					height = Integer.parseInt(size[1]);
					sizeBase = Integer.parseInt(size[2]);
					im.setHeight(height);
					im.setWidth(widht);
					im.setSizeBase(sizeBase);
				} else if (toc.startsWith("f")) {
					double scale = Double.parseDouble(toc.substring(1));
					im.setHeight((int)(height * scale));
					im.setWidth((int)(widht * scale));
					im.setSizeBase((int)(sizeBase * scale));
				}
				BufferedImage bufferedImage = im.createImage();
				File outputDir = new File("./src/de.japrost.excerpt/resources/poster");
				outputDir.mkdirs();
				String outputName = flyerName + "_" + im.getWidth() + "x" + im.getHeight();

				File outFile = new File(outputDir, outputName + ".png");

				ImageIO.write(bufferedImage, "png", outFile);
			}
		}

	}

	/**
	 * @param flyer
	 * @return
	 */
	private static String resolveEntries(final String flyer) {
		String entries = (String)props.get("flyer." + flyer + ".entries");
		if (entries != null) {
			// found real entries
			return entries;
		}
		// try a template
		String template = (String)props.get("flyer." + flyer + ".template");
		if (template != null) {
			// get the template
			String tEntries = (String)props.get("flyer." + template + ".entries");
			// replace (currently only) ${name}
			String rEntries = tEntries.replaceAll("<name>", flyer);
			System.out.println("replaced template is: " + rEntries);
			return rEntries;
		}
		//  nothing found
		return "";
	}
}
