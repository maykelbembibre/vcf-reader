package vcf_reader.format.converters;

import java.util.Iterator;

import vcf_reader.format.entities.AttributeWithValue;
import vcf_reader.format.entities.AttributedContactField;
import vcf_reader.format.entities.ContactField;
import vcf_reader.format.exceptions.InvalidFieldException;

public class AttributedContactFieldConverter {

	public static AttributedContactField vcfToModel(String rawString) throws InvalidFieldException {
		ContactField contactField = ContactFieldConverter.vcfToModel(rawString);
		AttributedContactField attributedContactField = new AttributedContactField(contactField);
		String[] parts = ContactFieldConverter.separateRawString(rawString);
		int i;
		for (i = 1;i < parts.length;i++) {
			attributedContactField.addAttributeWithValue(AttributeWithValueConverter.vcfToModel(parts[i]));
		}
		return attributedContactField;
	}
	
	public static String modelToVcf(AttributedContactField attributedContactField) {
		StringBuilder result = new StringBuilder(attributedContactField.getContactField().toString());
		Iterator<AttributeWithValue> attributesWithValues = attributedContactField.getAttributesWithValues();
		AttributeWithValue attributeWithValue;
		while (attributesWithValues.hasNext()) {
			attributeWithValue = attributesWithValues.next();
			result.append(ContactFieldWithValueConverter.ELEMENT_SEPARATOR);
			result.append(attributeWithValue.toString());
		}
		return result.toString();
	}
}
