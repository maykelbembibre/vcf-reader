package vcf_reader.input_output.models;

import java.io.File;

import vcf_reader.tools.StringTools;

public class FileName {

	private static final String EXTENSION_SEPARATOR = ".";
	
	private String name;
	private String extension;
	
	public FileName(File file) {
		String originalFileName = StringTools.notNull(file.getName());
		String[] parts = originalFileName.split("\\" + EXTENSION_SEPARATOR);
		this.name = getAt(parts, 0);
		this.extension = getAt(parts, 1);
	}
	
	public String getName() {
		return this.name;
	}
	
	public void rename(String newName) {
		this.name = newName;
	}
	
	public String getNameWithExtension() {
		return this.name + EXTENSION_SEPARATOR + this.extension;
	}
	
	private static String getAt(String[] parts, int index) {
		String result;
		if (index < parts.length) {
			result = parts[index];
		} else {
			result = "";
		}
		return result;
	}
}
