package vcf_reader.improvements;

import java.util.List;

import vcf_reader.format.entities.AttributeType;
import vcf_reader.format.entities.Contact;
import vcf_reader.format.entities.ContactValueElement;

/**
 * This improvement locates telephone fields where there is some non-recognised attribute
 * and in those cases it replaces the whole attribute list with a single attribute that
 * indicates that it is a {@link AttributeType#HOME} (i.e. landline) phone.
 */
public class PhoneOrganisationImprovement extends ReplacementByHomeImprovement {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean apply(Contact contact) {
		List<ContactValueElement> allTelephones = contact.getAllTelephones();
		return super.apply(allTelephones);
	}
}
