package vcf_reader.improvements;

import vcf_reader.format.entities.Contact;

/**
 * An automatic improvement that can be applied to contacts.
 */
public interface Improvement {

	/**
	 * Applies this improvement to the given contact.
	 * @param contact A contact.
	 * @return Whether the contact was changed after applying the improvement.
	 */
	boolean apply(Contact contact);
}
