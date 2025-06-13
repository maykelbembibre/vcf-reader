package vcf_reader.format.converters;

import java.util.Iterator;

import vcf_reader.format.entities.AttributedContactField;
import vcf_reader.format.entities.Contact;
import vcf_reader.format.entities.ContactFieldWithValue;
import vcf_reader.format.exceptions.InvalidFieldException;

public class ContactConverter {

	public static final String BEGIN = "BEGIN:VCARD";
	public static final String END = "END:VCARD";
	static final String LINE_END = "\r\n";
	
	public static Contact vcfToModel(Iterable<String> fieldLines) throws InvalidFieldException {
		String field, value;
		int position;
		AttributedContactField attributedContactField;
		ContactFieldWithValueBuilder builder = null;
		ContactFieldWithValue contactFieldWithValue;
		Contact contact = new Contact();
		for (String fieldLine : fieldLines) {
			if (builder == null) {
				position = fieldLine.indexOf(ContactFieldWithValueConverter.FIELD_VALUE_SEPARATOR);
				if (position > 0) {
					field = fieldLine.substring(0, position);
					value = fieldLine.substring(position + 1);
					attributedContactField = AttributedContactFieldConverter.vcfToModel(field);
					builder = new ContactFieldWithValueBuilder(contact, attributedContactField);
					contactFieldWithValue = builder.addToValueRawString(value);
				} else {
					contactFieldWithValue = null;
				}
			} else {
				contactFieldWithValue = builder.addToValueRawString(fieldLine);
			}
			if (contactFieldWithValue != null) {
				contact.add(contactFieldWithValue);
				builder = null;
			}
		}
		return contact;
	}
	
	public static String modelToVcf(Contact contact) {
		StringBuilder result = new StringBuilder(BEGIN);
		Iterator<ContactFieldWithValue> contactFieldsWithValues = contact.getFieldValues();
		ContactFieldWithValue contactFieldWithValue;
		while (contactFieldsWithValues.hasNext()) {
			contactFieldWithValue = contactFieldsWithValues.next();
			result.append(LINE_END);
			result.append(ContactFieldWithValueConverter.modelToVcf(contactFieldWithValue));
		}
		result.append(LINE_END);
		result.append(END);
		return result.toString();
	}
}
