package vcf_reader.format.entities;

import java.util.ArrayList;
import java.util.List;

import vcf_reader.format.converters.ContactValueConverter;

/**
 * This is the value of a contact field. As it can hold multiple elements, it
 * is implemented as an {@link ArrayList}.
 */
public class ContactValue extends ArrayList<ContactValueElement> {

	private static final long serialVersionUID = 4222162806284029100L;
	
	/**
	 * The contact field with value that this contact value belongs to.
	 */
	private final ContactFieldWithValue contactFieldWithValue;
	
	/**
	 * Creates a new contact value.
	 * @param contactFieldWithValue The contact field with value that this contact
	 * value belongs to.
	 */
	public ContactValue(ContactFieldWithValue contactFieldWithValue) {
		this.contactFieldWithValue = contactFieldWithValue;
	}

	/**
	 * Returns the contact field and value instance this value belongs to.
	 * This method can never return <code>null</code>.
	 * @return The contact field and value instance this value belongs to.
	 */
	public ContactFieldWithValue getContactFieldWithValue() {
		return contactFieldWithValue;
	}

	/**
	 * Returns the list of the elements of this value that are non-empty strings.
	 * @return The non-empty string elements.
	 */
	public List<ContactValueElement> getNonEmptyElements() {
		List<ContactValueElement> result = new ArrayList<>();
		for (ContactValueElement element : this) {
			if (!element.isEmpty()) {
				result.add(element);
			}
		}
		return result;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return ContactValueConverter.modelToVcf(this);
	}
}
