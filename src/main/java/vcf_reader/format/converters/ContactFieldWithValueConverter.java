package vcf_reader.format.converters;

import java.nio.charset.Charset;

import vcf_reader.format.entities.ContactFieldWithValue;

public class ContactFieldWithValueConverter {

	public static final char FIELD_VALUE_SEPARATOR = ':';
	static final Charset DEFAULT_CHARSET = Charset.forName("UTF-8");
	static final char QUOTED_PRINTABLE_ESCAPE = '=';
	static final String ELEMENT_SEPARATOR = ";";
	
	public static String modelToVcf(ContactFieldWithValue contactFieldWithValue) {
		return contactFieldWithValue.getAttributedContactField().toString() + FIELD_VALUE_SEPARATOR +
		contactFieldWithValue.getValue().map(value -> value.toString()).orElse("");
	}
}
