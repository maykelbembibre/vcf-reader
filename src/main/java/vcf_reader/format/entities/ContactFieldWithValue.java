package vcf_reader.format.entities;

import java.util.Optional;

import vcf_reader.format.converters.ContactFieldWithValueConverter;

/**
 * A contact field together with its value.
 */
public class ContactFieldWithValue {
	
	/**
	 * The contact that this field and value association belongs to.
	 */
	private final Contact contact;
	
	/**
	 * The field.
	 */
	private AttributedContactField attributedContactField;
	
	/**
	 * The value.
	 */
	private ContactValue value;
	
	public ContactFieldWithValue(Contact contact, AttributedContactField attributedContactField) {
		this.contact = contact;
		this.attributedContactField = attributedContactField;
	}

	/**
	 * Returns the contact that this field and value association belongs
	 * to. This method can never return <code>null</code>.
	 * @return The contact.
	 */
	public Contact getContact() {
		return contact;
	}

	/**
	 * Returns the field with its attributes that is part of this field and value association.
	 * This method can never return <code>null</code>.
	 * @return The field with its attributes.
	 */
	public AttributedContactField getAttributedContactField() {
		return attributedContactField;
	}
	
	/**
	 * Sets the attributed contact field.
	 * @param attributedContactField The attributed contact field to set.
	 */
	void setAttributedContactField(AttributedContactField attributedContactField) {
		if (attributedContactField != null) {
			this.attributedContactField = attributedContactField;
		}
	}

	/**
	 * Returns the value.
	 * @return The value.
	 */
	public Optional<ContactValue> getValue() {
		return Optional.ofNullable(this.value);
	}
	
	/**
	 * Sets the value.
	 * @param value The value.
	 */
	public void setValue(ContactValue value) {
		this.value = value;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return ContactFieldWithValueConverter.modelToVcf(this); 
	}
}
