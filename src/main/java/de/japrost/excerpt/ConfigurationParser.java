/**
 * 
 */
package de.japrost.excerpt;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.Properties;


/**
 *
 */
public class ConfigurationParser {

	File file = new File("./src/test/resources/in.properties");

	Properties readConfiguration() {
		Properties p = new Properties();
		try {
			p.load(new InputStreamReader(new FileInputStream(file), Charset.forName("ISO8859_15")));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return p;
	}

}
