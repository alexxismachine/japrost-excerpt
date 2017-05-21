/**
 * 
 */
package de.japrost.excerpt;

import java.awt.Rectangle;


/**
 *
 */
public interface RenderLine {

	/**
	 * Render the real content. <b>This method MUST NOT render larger than the given borders.</b>
	 * 
	 * @param borders The borders allowed for rendering.
	 */
	void render(Rectangle borders);

	/**
	 * How much to expand the line around the real content.
	 * 
	 * @return the expand.
	 */
	int expand();

	/**
	 * The full hight of the real content (Size of the box for the content).
	 * 
	 * @return the height.
	 */
	int height();

	/**
	 * The widht of the real content.
	 * 
	 * @return the width.
	 */
	int width();

	/**
	 * How to align the content. -1 = left; 0 = center; 1 = right
	 * 
	 * @return the alignment.
	 */
	int align();
}
