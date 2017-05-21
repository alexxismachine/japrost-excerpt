/**
 * 
 */
package de.japrost.excerpt;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Stroke;


/**
 *
 */
public class Decorator implements RenderLine {

	// points to add above and blow
	int border = 2;
	// hight of the real content
	int height = 5;
	// usually decorators render centered.
	int align = -1;

	private Graphics2D graphics;
	private final int pageWidht;
	private int color = 0;

	public Decorator(final Graphics2D graphics2D, final int pageWidht) {
		super();
		graphics = graphics2D;
		this.pageWidht = pageWidht;
	}

	/**
	 * {@inheritDoc} Decorators do not expand!
	 */
	@Override
	public int expand() {
		return 0;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int height() {
		return height + 2 * border;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void render(final Rectangle borders) {
		Color c = graphics.getColor();
		Stroke s = graphics.getStroke();
		graphics.setColor(new Color(color));
		BasicStroke bs = new BasicStroke(2);
		graphics.setStroke(bs);
		graphics.drawLine(borders.x, borders.y + border, borders.x + borders.width, borders.y + border);
		graphics.drawLine(borders.x, borders.y + border + height, borders.x + borders.width, borders.y + border + height);
		graphics.setColor(c);
		graphics.setStroke(s);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int align() {
		return align;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int width() {
		// TODO a bit more flexible, please!
		return pageWidht;
	}

	/**
	 * @param color the color to use.
	 */
	public void setColor(final int color) {
		this.color = color;

	}

}
