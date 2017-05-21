/**
 * 
 */
package de.japrost.excerpt;

import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Properties;

import javax.imageio.ImageIO;


/**
 *
 */
public class Main2 {

	public static void main(final String[] args) {
		System.out.println(Integer.decode("#ffffff"));
	}

	public static void main2(final String[] args) throws IOException {
		Properties props = new Properties();
		props.put("entries", "");
		ImageMaker im = new ImageMaker();
		im.setProps(props);
		im.setHeight(400);
		im.setWidth(800);
		im.setSizeBase(10);
		for (Font font : GraphicsEnvironment.getLocalGraphicsEnvironment().getAllFonts()) {
			System.out.println(font);
			props.put("debug", "true");
			props.put("spacer.1.expand", "1");

			props.put("textblock.1.text", font.getFontName());
			props.put("textblock.1.align", "-1");
			props.put("textblock.1.scale", "2");
			props.put("textblock.1.name", "Times New Roman");

			props.put("textblock.2.text", "the quick brown fox jumpes over the lazy dog. üäöß");
			props.put("textblock.2.align", "-1");
			props.put("textblock.2.scale", "1");
			props.put("textblock.2.name", font.getFontName());

			props.put("textblock.3.text", "the quick brown fox jumpes over the lazy dog. üäöß");
			props.put("textblock.3.align", "-1");
			props.put("textblock.3.scale", "1.2");
			props.put("textblock.3.name", font.getFontName());

			props.put("textblock.4.text", "the quick brown fox jumpes over the lazy dog. üäöß");
			props.put("textblock.4.align", "-1");
			props.put("textblock.4.scale", "1.4");
			props.put("textblock.4.name", font.getFontName());

			props.put("textblock.4a.text", "the quick brown fox jumpes over the lazy dog. üäöß");
			props.put("textblock.4a.align", "-1");
			props.put("textblock.4a.scale", "2");
			props.put("textblock.4a.name", font.getFontName());

			props.put("textblock.4b.text", "the quick brown fox jumpes over the lazy dog. üäöß");
			props.put("textblock.4b.align", "-1");
			props.put("textblock.4b.scale", "4");
			props.put("textblock.4b.name", font.getFontName());

			props.put("textblock.5.text", "THE QUICK BROWN FOX JUMPES OVER THE LAZY DOG. ÜÄÖß");
			props.put("textblock.5.align", "-1");
			props.put("textblock.5.scale", "4");
			props.put("textblock.5.name", font.getFontName());

			props.put("entries", "t1,s1,t2,s1,t3,s1,t4,s1,t4a,s1,t4b,s1,t5");

			BufferedImage bufferedImage = im.createImage();
			File outputDir = new File("./src/de.japrost.excerpt/resources/fonts");
			outputDir.mkdirs();
			String outputName = "font_" + font.getName();

			File outFile = new File(outputDir, outputName + ".png");

			ImageIO.write(bufferedImage, "png", outFile);
		}

	}
}
