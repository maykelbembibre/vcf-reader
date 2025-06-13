package vcf_reader.format.converters;

import java.nio.charset.Charset;
import java.util.Objects;

import vcf_reader.format.entities.ContactFieldWithValue;
import vcf_reader.format.entities.ContactValue;
import vcf_reader.format.entities.ContactValueElement;
import vcf_reader.format.exceptions.InvalidFieldException;
import vcf_reader.tools.StringTools;

public class ContactValueConverter {

	/**
	 * Creates a contact value.
	 * @param contactFieldWithValue The contact field and value instance this value belongs to.
	 * It cannot be <code>null</code>.
	 * @param vcfFileValues The VCF file values.
	 * @param charset A charset means that this value needs to be decoded from quoted-printable.
	 * @return The contact value that has been created.
	 * @throws InvalidFieldException If there is a problem with the provided values.
	 */
	public static ContactValue vcfToModel(
			ContactFieldWithValue contactFieldWithValue, String vcfFileValues, Charset charset
	) throws InvalidFieldException {
		Objects.requireNonNull(contactFieldWithValue);
		String[] vcfFileValueParts = StringTools.splitWithEmptyStrings(vcfFileValues, ContactFieldWithValueConverter.ELEMENT_SEPARATOR);
		int i;
		String vcfFileValue;
		ContactValueElement contactValueElement;
		ContactValue contactValue = new ContactValue(contactFieldWithValue);
		for (i = 0;i < vcfFileValueParts.length;i++) {
			vcfFileValue = vcfFileValueParts[i];
			contactValueElement = ContactValueElementConverter.vcfToModel(contactValue, vcfFileValue, charset);
			contactValue.add(contactValueElement);
		}
		return contactValue;
	}
	
	/**
	 * Returns the VCF representation for the given contact value.
	 * @param contactValue A contact value.
	 * @return The VCF representation.
	 */
	public static String modelToVcf(ContactValue contactValue) {
		StringBuilder result = new StringBuilder();
		boolean first = true;
		for (ContactValueElement element : contactValue) {
			if (!first) {
				result.append(ContactFieldWithValueConverter.ELEMENT_SEPARATOR);
			}
			result.append(ContactValueElementConverter.modelToVcf(element));
			first = false;
		}
		return result.toString();
	}
}
