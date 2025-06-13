package vcf_reader.format.converters;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import vcf_reader.format.entities.ContactValue;
import vcf_reader.format.entities.ContactValueElement;
import vcf_reader.format.exceptions.InvalidFieldException;
import vcf_reader.tools.QuotedPrintableTools;

public class ContactValueElementConverter {

	/**
	 * Creates a contact value element from the VCF file value.
	 * @param contactValue The value this element is part of. It cannot be <code>null</code>.
	 * @param vcfFileValue The VCF file value. It cannot be <code>null</code>.
	 * @param charset A charset will generate the display value by decoding it from
	 * the VCF file value by using quoted-printable encoding.
	 * @throws InvalidFieldException If a charset has been given and the decoding is not
	 * possible.
	 */
	public static ContactValueElement vcfToModel(
		ContactValue contactValue, String vcfFileValue, Charset charset
	) throws InvalidFieldException {
		Objects.requireNonNull(contactValue);
		Objects.requireNonNull(vcfFileValue);
		String displayValue;
		if (charset == null) {
			displayValue = vcfFileValue;
		} else {
			displayValue = QuotedPrintableTools.decode(vcfFileValue, charset);
		}
		return new ContactValueElement(contactValue, vcfFileValue, displayValue);
	}
	
	/**
	 * Returns the VCF representation of the given contact value element.
	 * @param contactValueElement A contact value element.
	 * @return The VCF representation.
	 */
	public static String modelToVcf(ContactValueElement contactValueElement) {
		return contactValueElement.getVcfFileValue();
	}
	
	/**
	 * Parses the list of complicated contact value element objects into
	 * a more manageable list of strings, taking care of getting rid of
	 * any empty strings before calculating the result.
	 * @param elements Elements.
	 * @return A list of non-empty strings.
	 */
	public static List<String> parse(Iterable<ContactValueElement> elements) {
		List<String> values = new ArrayList<>();
    	for (ContactValueElement element : elements) {
    		for (ContactValueElement value : element.getContactValue()) {
    			if (!value.isEmpty()) {
    				values.add(value.getDisplayValue());
    			}
    		}
    	}
    	return values;
	}
}
