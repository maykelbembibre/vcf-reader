package vcf_reader.format.converters;

import java.nio.charset.Charset;
import java.util.Objects;
import java.util.Optional;

import vcf_reader.format.entities.Attribute;
import vcf_reader.format.entities.AttributedContactField;
import vcf_reader.format.entities.Contact;
import vcf_reader.format.entities.ContactFieldWithValue;
import vcf_reader.format.entities.ContactValue;
import vcf_reader.format.exceptions.InvalidFieldException;
import vcf_reader.tools.QuotedPrintableTools;

public class ContactFieldWithValueBuilder {

	private final Contact contact;
	private final AttributedContactField attributedContactField;
	private final String encoding;
	private final String charset;
	private final StringBuilder valueRawString;
	
	public ContactFieldWithValueBuilder(
		Contact contact, AttributedContactField attributedContactField
	) throws InvalidFieldException {
		Objects.requireNonNull(contact);
		this.contact = contact;
		Objects.requireNonNull(attributedContactField);
		this.attributedContactField = attributedContactField;
		this.encoding = attributedContactField.getAttributeValue(Attribute.ENCODING)
		.map(attributeWithValue -> attributeWithValue.getValue()).orElse(Optional.empty())
		.orElse(null);
		this.charset = attributedContactField.getAttributeValue(Attribute.CHARSET)
		.map(attributeWithValue -> attributeWithValue.getValue()).orElse(Optional.empty())
		.orElse(null);
		this.valueRawString = new StringBuilder();
	}
	
	public ContactFieldWithValue addToValueRawString(String piece) throws InvalidFieldException {
		this.valueRawString.append(piece);
		boolean buildingComplete;
		if (piece.isEmpty()) {
			buildingComplete = true;
		} else if (
			ContactFieldWithValueConverter.QUOTED_PRINTABLE_ESCAPE ==
			this.valueRawString.charAt(this.valueRawString.length() - 1)
		) {
			buildingComplete = false;
			// We must respect the end of line.
			this.valueRawString.append(ContactConverter.LINE_END);
		} else if (Attribute.ENCODING_BASE64.equals(encoding) || ' ' == this.valueRawString.charAt(0)) {
			// This happens when there is a photo encoded in JPEG base64.
			buildingComplete = false;
			// We must respect the end of line.
			this.valueRawString.append(ContactConverter.LINE_END);
		} else {
			buildingComplete = true;
		}
		ContactFieldWithValue contactFieldWithValue;
		if (buildingComplete) {
			contactFieldWithValue = new ContactFieldWithValue(this.contact, this.attributedContactField);
		} else {
			contactFieldWithValue = null;
		}
		if (contactFieldWithValue != null) {
			ContactValue contactValue = ContactValueConverter.vcfToModel(
				contactFieldWithValue, this.valueRawString.toString(), this.getQuotedPrintableCharset()
			);
			contactFieldWithValue.setValue(contactValue);
		}
		return contactFieldWithValue;
	}
	
	/**
	 * If this contact field has a value encoded in quoted printable, this method returns the
	 * charset it is represented with, otherwise it returns <code>null</code>.
	 * @throws InvalidFieldException If the field of this contact has something that makes it
	 * impossible to process.
	 */
	private Charset getQuotedPrintableCharset() throws InvalidFieldException {
		Charset result;
		if (Attribute.ENCODING_QUOTED_PRINTABLE.equals(this.encoding)) {
			if (this.charset == null) {
				// When quoted-printable is found and there's no charset present, let's assume a default.
				result = ContactFieldWithValueConverter.DEFAULT_CHARSET;
			} else {
				result = QuotedPrintableTools.tryToGetCharset(this.charset);
			}
		} else {
			result = null;
		}
		return result;
	}
}
