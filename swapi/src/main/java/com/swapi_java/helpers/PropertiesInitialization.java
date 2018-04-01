/**
 * 
 */
package com.swapi_java.helpers;

import java.io.*;
import java.util.Properties;

/**
 * @author ystadnik
 *
 */
public class PropertiesInitialization {
	private String path;

	private PropertiesInitialization() {}

	public static PropertiesInitialization getInstance(String pathToFile) {
		PropertiesInitialization propertiesInitialization = new PropertiesInitialization();
		propertiesInitialization.path = pathToFile;
		return propertiesInitialization;
	}

	public String getPropertiesFile() {
		return path;
	}

	public void setPropertiesFile(String pathToProperties) {
		path = pathToProperties;
	}

	public void writePropertyToFile(String key, String value) {
		Properties prop = new Properties();
		OutputStream output = null;

		try {

			output = new FileOutputStream(path);

			// set the properties value
			prop.setProperty(key, value);

			// save properties to project root folder
			prop.store(output, null);

		} catch (IOException io) {
			io.printStackTrace();
		} finally {
			if (output != null) {
				try {
					output.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		}
	}

	public String getPropertyConfiguration(String key) {
		Properties prop = new Properties();
		InputStream input = null;
		String value = "";

		try {

			try {
				input = new FileInputStream(path);
				// load a properties file
				prop.load(input);
			} catch (Exception e) {
				System.out.println("Errror loading property from file: " + path);
			}

			// get the property value
			value = System.getProperty(key);	//To have ability to start tests on Jenkins
			if (value == null) {
				if (input != null)
					value = prop.getProperty(key);
			}

		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return value;
	}
}