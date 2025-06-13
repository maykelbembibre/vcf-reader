package vcf_reader.format.converters;

import vcf_reader.format.entities.Attribute;
import vcf_reader.format.entities.AttributeWithValue;
import vcf_reader.format.exceptions.InvalidAttributeException;

public class AttributeWithValueConverter {

	private static final String NAME_VALUE_SEPARATOR = "=";
	
	public static AttributeWithValue vcfToModel(String rawString) throws InvalidAttributeException {
		String[] parts = separateRawString(rawString);
		String name;
		if (parts.length > 0) {
			name = parts[0];
		} else {
			name = null;
		}
		if (name == null || name.length() < 1) {
			throw new InvalidAttributeException("The raw string \"" + rawString + "\" doesn't represent a valid contact field attribute.");
		}
		Attribute attribute = AttributeConverter.vcfToModel(name);
		String value;
		if (parts.length > 1) {
			value = parts[1];
		} else {
			value = null;
		}
		return new AttributeWithValue(attribute, value);
	}

	private static String[] separateRawString(String rawString) {
		String[] parts;
		if (rawString == null) {
			parts = new String[0];
		} else {
			parts = rawString.split(NAME_VALUE_SEPARATOR);
		}
		return parts;
	}

	public static String modelToVcf(AttributeWithValue attributeWithValue) {
		Attribute attribute = attributeWithValue.getAttribute();
		StringBuilder result = new StringBuilder(AttributeConverter.modelToVcf(attribute));
		if (attributeWithValue.getValue().isPresent()) {
			result.append(NAME_VALUE_SEPARATOR);
			result.append(attributeWithValue.getValue().get());
		}
		return result.toString();
	}
}
