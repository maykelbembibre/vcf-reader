package vcf_reader.format.converters;

import vcf_reader.format.entities.ContactField;
import vcf_reader.format.exceptions.InvalidFieldException;
import vcf_reader.tools.StringTools;

public class ContactFieldConverter {

	public static ContactField vcfToModel(String rawString) throws InvalidFieldException {
		String[] parts = separateRawString(rawString);
		String name;
		if (parts.length > 0) {
			name = parts[0];
		} else {
			name = null;
		}
		if (name == null || name.length() < 1) {
			throw new InvalidFieldException("The raw string \"" + rawString + "\" doesn't represent a valid contact field.");
		}
		return new ContactField(name);
	}
	
	public static String modelToVcf(ContactField contactField) {
		return contactField.getName();
	}
	
	static String[] separateRawString(String rawString) {
		return StringTools.splitWithEmptyStrings(rawString, ContactFieldWithValueConverter.ELEMENT_SEPARATOR);
	}
}
