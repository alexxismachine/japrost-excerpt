/**
 * 
 */
package de.japrost.excerpt;

import java.awt.Rectangle;


/**
 *
 */
public class FixedSpace implements RenderLine {

	private int height = 5;

	public FixedSpace(final int height) {
		super();
		this.height = height;
	}

	/**
	 * {@inheritDoc} FixedSpace has a expand of 0.
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
		return height;
	}

	/**
	 * {@inheritDoc} FixedSpace do not render anything.
	 */
	@Override
	public void render(final Rectangle borders) {
		// A spacer does not render
		// TODO render somebox on debug?
	}

	/**
	 * {@inheritDoc} FixedSpace aligns centered.
	 */
	@Override
	public int align() {
		return 0;
	}

	/**
	 * {@inheritDoc} FixedSpace has no widht.
	 */
	@Override
	public int width() {
		return 0;
	}

}
