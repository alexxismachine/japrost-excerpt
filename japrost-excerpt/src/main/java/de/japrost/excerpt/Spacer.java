/**
 * 
 */
package de.japrost.excerpt;

import java.awt.Rectangle;


/**
 *
 */
public class Spacer implements RenderLine {

	private int expand = 1;

	public Spacer(final int expand) {
		super();
		this.expand = expand;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int expand() {
		return expand;
	}

	/**
	 * {@inheritDoc} Spacers have a height of 0.
	 */
	@Override
	public int height() {
		return 0;
	}

	/**
	 * {@inheritDoc} Spacers do not render anything.
	 */
	@Override
	public void render(final Rectangle borders) {
		// A spacer does not render
		// TODO render somebox on debug?
	}

	/**
	 * {@inheritDoc} Spacers align centered.
	 */
	@Override
	public int align() {
		return 0;
	}

	/**
	 * {@inheritDoc} Spacers have no widht.
	 */
	@Override
	public int width() {
		return 0;
	}

}
