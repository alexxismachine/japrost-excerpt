/**
 * 
 */
package de.japrost.excerpt;

import java.util.Properties;


/**
 *
 */
public class PropertySpacerBuilder {

	Spacer buildSpacer(final String prefix, final Properties props) {
		int expand = 0;
		expand = Integer.parseInt(props.getProperty(prefix + "expand", "" + expand));
		System.out.println("--- Creating Spacer ---");
		System.out.println("Expand :" + expand);
		Spacer spacer = new Spacer(expand);
		System.out.println("--- END Spacer ---");
		return spacer;
	}
}
