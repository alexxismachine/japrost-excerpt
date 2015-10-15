/**
 * 
 */
package de.japrost.excerpt;

import java.awt.Font;
import java.awt.Graphics2D;
import java.util.Properties;


/**
 *
 */
public class PropertyTextBlockBuilder {

	public PropertyTextBlockBuilder(final Graphics2D g, final ImageMaker m) {
		super();
		this.g = g;
		this.m = m;
	}

	private Graphics2D g;
	private ImageMaker m;

	private String prefix;
	private String blockName;
	private Properties props;
	private String template;

	TextBlock buildTextBlock(final String prefix, final String name, final Properties props) {
		this.prefix = prefix;
		this.props = props;
		blockName = name;
		template = null;
		TextBlock textBlock = new TextBlock();
		// 
		textBlock.setGraphics(g);
		// template
		template = getProperty("template", null);
		System.out.println("Using template: '" + template + "'");
		// text
		String text = "No text found :(";
		text = getProperty("text", text);
		textBlock.setText(text);
		// align
		int align = 0;
		align = Integer.parseInt(getProperty("align", "" + align));
		textBlock.setAlign(align);
		// font
		double factor = 1;
		factor = Double.parseDouble(getProperty("scale", "" + factor));
		int style = 0;
		style = Integer.parseInt(getProperty("style", "" + style));
		String fontName = "Times New Roman";
		fontName = getProperty("name", fontName);
		Font font = new Font(fontName, style, (int)(m.getSizeBase() * factor));
		textBlock.setFont(font);
		System.out.println(font);
		textBlock.setPageWidth(m.getWidth());
		textBlock.init();
		return textBlock;
	}

	private String getProperty(final String property, final String defaultValue) {
		String rValue = props.getProperty(prefix + "." + blockName + "." + property);
		System.out.println(prefix + "." + blockName + "." + property + "=" + rValue);
		if (rValue != null) {
			// we have a real - real value
			return rValue;
		}
		String tValue = props.getProperty(prefix + "." + template + "." + property);
		if (tValue != null) {
			// we have a template value
			return tValue;
		}
		// nothing left, use default
		return defaultValue;
	}
}
