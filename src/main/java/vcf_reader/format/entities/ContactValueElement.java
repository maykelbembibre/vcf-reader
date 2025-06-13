package vcf_reader.format.entities;

import java.util.Optional;

/**
 * Each element of the value of a contact. A value of a contact may have
 * many elements.
 */
public class ContactValueElement {

	/**
	 * The value this element is part of.
	 */
	private final ContactValue contactValue;
	
	/**
	 * The value of this element as in the VCF file.
	 */
	private final String vcfFileValue;
	
	/**
	 * The value of this element in a user-friendly way.
	 */
	private final String displayValue;

	/**
	 * Creates a contact value element.
	 * @param contactValue The value this element is part of.
	 * @param vcfFileValue The value of this element as in the VCF file.
	 * @param displayValue The value of this element in a user-friendly way.
	 */
	public ContactValueElement(ContactValue contactValue, String vcfFileValue, String displayValue) {
		this.contactValue = contactValue;
		this.vcfFileValue = vcfFileValue;
		this.displayValue = displayValue;
	}

	/**
	 * Returns the contact value that this element is part of. This method can never
	 * return <code>null</code>.
	 * @return The contact value that this element is part of.
	 */
	public ContactValue getContactValue() {
		return contactValue;
	}

	/**
	 * Returns the value of this element as in the VCF file.
	 * @return The value of this element as in the VCF file.
	 */
	public String getVcfFileValue() {
		return vcfFileValue;
	}
	
	/**
	 * Returns whether this element is empty.
	 * @return Whether this element is empty.
	 */
	public boolean isEmpty() {
		return Optional.ofNullable(this.vcfFileValue).map(value -> value.isEmpty()).orElse(false) ||
		Optional.ofNullable(this.displayValue).map(value -> value.isEmpty()).orElse(false);
	}
	
	/**
	 * Returns the value of this element in a user-friendly way.
	 * @return The value of this element in a user-friendly way.
	 */
	public String getDisplayValue() {
		return displayValue;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return this.getVcfFileValue();
	}
}
