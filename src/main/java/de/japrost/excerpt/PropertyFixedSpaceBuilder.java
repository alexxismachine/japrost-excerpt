/**
 * 
 */
package de.japrost.excerpt;

import java.util.Properties;


/**
 *
 */
public class PropertyFixedSpaceBuilder {

	FixedSpace buildFixedSpace(final String prefix, final Properties props) {
		int heigth = 0;
		heigth = Integer.parseInt(props.getProperty(prefix + "height", "" + heigth));
		System.out.println("--- Creating FixedSpace ---");
		System.out.println("Height :" + heigth);
		FixedSpace fixedSpace = new FixedSpace(heigth);
		System.out.println("--- END FixedSpace ---");
		return fixedSpace;
	}
}
