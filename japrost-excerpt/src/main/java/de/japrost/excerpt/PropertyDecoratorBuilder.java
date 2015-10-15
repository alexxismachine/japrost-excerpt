/**
 * 
 */
package de.japrost.excerpt;

import java.awt.Graphics2D;
import java.util.Properties;


/**
 *
 */
public class PropertyDecoratorBuilder {

	public PropertyDecoratorBuilder(final Graphics2D g, final ImageMaker m) {
		super();
		this.g = g;
		this.m = m;
	}

	private Graphics2D g;
	private ImageMaker m;

	Decorator buildDecorator(final String prefix, final Properties props) {
		int color = 0;
		color = Integer.decode(props.getProperty(prefix + "color", "#000000"));
		System.out.println("--- Creating Decorator ---");
		Decorator decorator = new Decorator(g, m.getPageWidth());
		decorator.setColor(color);
		System.out.println("--- END Decorator ---");
		return decorator;
	}
}
