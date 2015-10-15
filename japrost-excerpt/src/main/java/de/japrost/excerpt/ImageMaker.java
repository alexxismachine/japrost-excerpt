package de.japrost.excerpt;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.StringTokenizer;


/*
 * Einzelne Text/Grafik-bausteine machen und Zeilenweise anordnen (und mit spacern Abstände einbauen)
 */

public class ImageMaker {

	private Properties props;

	private boolean debug = true;

	private int width = 800;
	private int height = 600;
	private int sizeBase = 48;
	// TODO set and use margins
	private int marginRight = 5;
	private int marginBottom = 5;
	private int marginLeft = 5;
	private int marginTop = 5;
	private String entries = "";

	double spacerHight;

	BufferedImage createImage() {
		BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		img.createGraphics();
		Graphics2D g = (Graphics2D)img.getGraphics();
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		List<RenderLine> renderLines = new ArrayList<RenderLine>();

		debug = Boolean.parseBoolean(props.getProperty("debug"));
		PropertyTextBlockBuilder ptb = new PropertyTextBlockBuilder(g, this);
		PropertySpacerBuilder psb = new PropertySpacerBuilder();
		PropertyDecoratorBuilder pdb = new PropertyDecoratorBuilder(g, this);
		PropertyFixedSpaceBuilder pfb = new PropertyFixedSpaceBuilder();

		StringTokenizer st = new StringTokenizer(entries, ",");

		while (st.hasMoreTokens()) {
			String entry = st.nextToken().trim();
			System.out.println("'" + entry + "'");
			if (entry.startsWith("t.")) {
				// a text block
				renderLines.add(ptb.buildTextBlock("textblock", entry.substring(2), props));
			} else if (entry.startsWith("s.")) {
				// a spacer
				renderLines.add(psb.buildSpacer("spacer." + entry.substring(2) + ".", props));
			} else if (entry.startsWith("f.")) {
				// a fixed space
				renderLines.add(pfb.buildFixedSpace("fixedspace." + entry.substring(2) + ".", props));
			} else if (entry.startsWith("d.")) {
				// a decorator
				renderLines.add(pdb.buildDecorator("decorator." + entry.substring(2) + ".", props));
			} else {
				System.out.println("Whatz to do wiz '" + entry + "'?");
			}
		}
		spacerHight = calculateSpacerHeight(renderLines);

		int topSpace = marginTop;
		g.setColor(Color.black);
		if (debug) {
			debugDrawMargin(g);
		}
		if (debug) {
			debugDrawCross(g);
		}
		for (RenderLine renderLine : renderLines) {
			Rectangle borders = new Rectangle();

			int boxHight = renderLine.height() + (int)(spacerHight * renderLine.expand());
			// FIXME calculate based on render line size
			int boxWidth = width - marginLeft - marginRight;
			// FIXME calculate based on render line size
			int boxUpperLeftX = marginLeft;
			int boxUpperLeftY = topSpace;

			borders.setBounds(boxUpperLeftX, boxUpperLeftY, boxWidth, boxHight);
			renderLine.render(borders);
			if (debug) {
				debugDrawBox(g, borders, renderLine);
			}

			topSpace = advanceTopSpace(topSpace, renderLine);
		}
		return img;
	}

	public int getPageWidth() {
		return width - marginLeft - marginRight;
	}

	private double calculateSpacerHeight(final List<RenderLine> renderLines) {
		System.out.println("Calculating SpacerHeigth");
		int textHeigth = 0;
		int spacers = 0;
		for (RenderLine renderLine : renderLines) {
			int height = renderLine.height();
			if (height > 0) {
				textHeigth += height;
			}
			spacers += renderLine.expand();
		}
		int pageHeight = height - marginTop - marginBottom;
		int heightSpace = pageHeight - textHeigth;
		System.out.println("-> Spacer PageHeight=" + pageHeight + " TextHeight=" + textHeigth);
		if (spacers == 0) {
			return 0;
		}
		System.out.println("-> SpacerHeigth:" + ((double)heightSpace / spacers));
		return (double)heightSpace / spacers;

	}

	private int advanceTopSpace(final int topSpace, final RenderLine renderLine) {
		return topSpace + renderLine.height() + (int)(spacerHight * renderLine.expand());
	}

	private void debugDrawBox(final Graphics2D g, final Rectangle box, final RenderLine renderLine) {

		//System.out.println("topSpace: " + topSpace + " height:" + (topSpace - oldSpice));
		Color c = g.getColor();
		String addon = "UKN";
		if (renderLine instanceof TextBlock) {
			g.setColor(Color.black);
			addon = "TB" + ((TextBlock)renderLine).getText();
		} else if (renderLine instanceof Spacer) {
			g.setColor(Color.pink);
			addon = "SP";
		} else if (renderLine instanceof Decorator) {
			g.setColor(Color.yellow);
			addon = "DE";
		} else if (renderLine instanceof FixedSpace) {
			g.setColor(Color.blue);
			addon = "FS";
		} else {
			g.setColor(Color.magenta);
			addon = "UN";
		}
		g.drawRect(box.x, box.y, box.width, box.height);

		g.setColor(Color.white);
		g.drawString(box.toString() + " " + addon, box.x + 10, box.y);
		g.setColor(c);

	}

	private void debugDrawCross(final Graphics2D g) {
		Color c = g.getColor();
		g.setColor(Color.red);
		g.drawLine(0, height / 2, width, height / 2);
		g.drawLine(width / 2, 0, width / 2, height);
		g.setColor(c);

	}

	private void debugDrawMargin(final Graphics2D g) {
		Color c = g.getColor();
		g.setColor(Color.green);
		g.drawRect(marginLeft, marginTop, getPageWidth(), height - marginBottom - marginTop);
		g.setColor(c);

	}

	public int getWidth() {
		return width;
	}

	public void setWidth(final int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(final int height) {
		this.height = height;
	}

	public int getSizeBase() {
		return sizeBase;
	}

	public void setSizeBase(final int sizeBase) {
		this.sizeBase = sizeBase;
	}

	public int getMarginRight() {
		return marginRight;
	}

	public void setMarginRight(final int marginRight) {
		this.marginRight = marginRight;
	}

	public int getMarginBottom() {
		return marginBottom;
	}

	public void setMarginBottom(final int marginBottom) {
		this.marginBottom = marginBottom;
	}

	public int getMarginLeft() {
		return marginLeft;
	}

	public void setMarginLeft(final int marginLeft) {
		this.marginLeft = marginLeft;
	}

	public int getMarginTop() {
		return marginTop;
	}

	public void setMarginTop(final int marginTop) {
		this.marginTop = marginTop;
	}

	public void setProps(final Properties props) {
		this.props = props;
	}

	public String getEntries() {
		return entries;
	}

	public void setEntries(final String entries) {
		this.entries = entries;
	}

}
