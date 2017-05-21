/**
 * 
 */
package de.japrost.excerpt;

import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.font.FontRenderContext;
import java.awt.font.LineBreakMeasurer;
import java.awt.font.TextAttribute;
import java.awt.font.TextLayout;
import java.text.AttributedCharacterIterator;
import java.text.AttributedString;
import java.util.ArrayList;
import java.util.List;


/**
 *
 */
public class TextBlock implements RenderLine {

	// +-+-+-+ "final" values
	private String text;
	private Font font;
	private Graphics2D graphics;
	private int pageWidth;
	private int lineSpace = 8;

	// -+-+-+- properties
	private int align = 0;

	// -+-+-+-+ other fields
	private Rectangle bounds = null;
	private TextLayout tl;
	private List<Line> textLines = new ArrayList<Line>();
	private boolean initialized = false;
	private int paragraphStart;
	private int paragraphEnd;
	private LineBreakMeasurer lineMeasurer;

	public TextBlock() {
	};

	public TextBlock(final String text, final ImageMaker maker, final Graphics2D g, final int align) {
		super();
		this.text = text;
		graphics = g;
		this.align = align;
		font = new Font("Times New Roman", Font.ITALIC, maker.getSizeBase());
		init();
	}

	public void init() {
		FontRenderContext frc = graphics.getFontRenderContext();
		float heigth = 0;
		float width = 0;
		// TODO allow empty lines?
		// TODO allow higher line width
		// TODO line hight more constant!
		String[] forcedLines = text.split("\\\\"); //$NON-NLS-1$
		for (String forcedLine : forcedLines) {
			if (forcedLine.startsWith("/")) {
				forcedLine = forcedLine.substring(1);
				textLines.get(textLines.size() - 1).lineSpace = lineSpace * 2;
				heigth += lineSpace;
			}

			AttributedString aText = new AttributedString(forcedLine.trim());
			aText.addAttribute(TextAttribute.FONT, font);
			AttributedCharacterIterator paragraph = aText.getIterator();
			paragraphStart = paragraph.getBeginIndex();
			paragraphEnd = paragraph.getEndIndex();

			lineMeasurer = new LineBreakMeasurer(paragraph, frc);
			lineMeasurer.setPosition(paragraphStart);
			while (lineMeasurer.getPosition() < paragraphEnd) {
				TextLayout layout = lineMeasurer.nextLayout(pageWidth);

				System.out.println("###### " + layout.toString());
				Line line = new Line();
				line.lineSpace = lineSpace;
				line.textLayout = layout;
				textLines.add(line);
				// FIXME hight / BASE calculation is not accurate
				heigth += layout.getPixelBounds(frc, 0f, 0f).height + line.lineSpace;
				width = layout.getPixelBounds(frc, 0f, 0f).width > width ? layout.getPixelBounds(frc, 0f, 0f).width : width;
			}

		}
		bounds = new Rectangle((int)width, (int)heigth);
		initialized = true;
	}

	public void render(final Rectangle borders) {
		System.out.println("tb start " + borders);
		int currX = borders.x;
		int currY = borders.y;
		for (Line line : textLines) {
			TextLayout textLayout = line.textLayout;
			// FIXME later: rethink correctness (on pixelbase) of calculations
			float realY = (float)(currY - textLayout.getBounds().getY());
			System.out.println("L-B" + textLayout.getBounds());
			System.out.println("L-X" + textLayout.getBounds().getX());
			float realX = currX;
			if (align == 0) {
				realX = (float)((currX + borders.width / 2) - (textLayout.getBounds().getWidth() / 2));
			} else if (align == 1) {
				realX = (float)((currX + borders.width) - (textLayout.getBounds().getWidth()));
			}

			System.out.println("L-Rx" + realX);
			textLayout.draw(graphics, realX, realY);
			currY = currY + textLayout.getPixelBounds(graphics.getFontRenderContext(), 0f, 0f).height + line.lineSpace;
		}
	}

	public Rectangle getBounds() {
		System.out.println("tb bounds" + bounds);
		return bounds;
	}

	/**
	 * {@inheritDoc}
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
		System.out.println("TextBlock-Bounds:" + bounds);
		System.out.println("TextBlock-height:" + bounds.height);
		return bounds.height;
	}

	/**
	 * {@inheritDoc}
	 */
	public int align() {
		return getAlign();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int width() {
		return bounds.width;
	}

	// +-+-+-+-+- Setters for inital configuration (with getters) +-+-+-+-+-+-

	public String getText() {
		return text;
	}

	public void setText(final String text) {
		if (initialized) {
			throw new IllegalStateException("Text of TextBlock already initialized");
		}
		this.text = text;
	}

	public Font getFont() {
		return font;
	}

	public void setFont(final Font font) {
		if (initialized) {
			throw new IllegalStateException("Text of TextBlock already initialized");
		}
		this.font = font;
	}

	public Graphics2D getGraphics() {
		return graphics;
	}

	public void setGraphics(final Graphics2D graphics) {
		if (initialized) {
			throw new IllegalStateException("Text of TextBlock already initialized");
		}
		this.graphics = graphics;
	}

	// +-+-+-+-+- Getters and Setters  +-+-+-+-+-+-

	public int getAlign() {
		return align;
	}

	public void setAlign(final int align) {
		this.align = align;
	}

	/**
	 * @param pageWidth the pageWidth to set
	 */
	public void setPageWidth(final int pageWidth) {
		this.pageWidth = pageWidth;
	}

	/**
	 * Get the pageWidth.
	 * 
	 * @return the pageWidth.
	 */
	public int getPageWidth() {
		return pageWidth;
	}

	class Line {

		TextLayout textLayout;
		int lineSpace;
	}
}
