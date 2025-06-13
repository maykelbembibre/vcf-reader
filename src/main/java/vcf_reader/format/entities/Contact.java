package vcf_reader.format.entities;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import vcf_reader.format.converters.ContactConverter;
import vcf_reader.tools.ListTools;

/**
 * A contact found in a VCF file. It has fields whose values can be queried.
 */
public class Contact {

	private final List<ContactFieldWithValue> fieldValues = new ArrayList<>();
	private final HashMap<ContactField, List<ContactFieldWithValue>> valuesByField = new HashMap<>();
	private final HashMap<AttributedContactField, List<ContactFieldWithValue>> valuesByAttributedField = new HashMap<>();
	private final Set<AttributedContactField> unrecognisedFields = new HashSet<>();
	
	public Iterator<ContactFieldWithValue> getFieldValues() {
		return this.fieldValues.iterator();
	}
	
	public List<ContactValueElement> getFullName() {
		return ListTools.getNonEmpty(this.valuesByField.get(ContactField.FULL_NAME));
	}
	
	public List<ContactValueElement> getCellularTelephone() {
		return ListTools.getNonEmpty(this.valuesByAttributedField.get(
			AttributedContactField.CELLULAR_TELEPHONE
		));
	}
	
	public List<ContactValueElement> getLandlineTelephone() {
		return ListTools.getNonEmpty(this.valuesByAttributedField.get(
			AttributedContactField.HOME_TELEPHONE
		));
	}
	
	public boolean hasCellularOrLandlineTelephone() {
		return !this.getCellularTelephone().isEmpty() || !this.getLandlineTelephone().isEmpty();
	}
	
	public List<ContactValueElement> getAllTelephones() {
		return ListTools.getNonEmpty(this.valuesByField.get(
			ContactField.TELEPHONE
		));
	}
	
	public List<ContactValueElement> getAllEmails() {
		return ListTools.getNonEmpty(this.valuesByField.get(
			ContactField.EMAIL
		));
	}
	
	public Set<AttributedContactField> getUnrecognisedFields() {
		return unrecognisedFields;
	}
	
	/**
	 * Adds a field with its value to this contact.
	 * @param contactFieldWithValue A field with its value.
	 */
	public void add(ContactFieldWithValue contactFieldWithValue) {
		this.fieldValues.add(contactFieldWithValue);
		insertInMap(this.valuesByField, contactFieldWithValue.getAttributedContactField().getContactField(), contactFieldWithValue);
		insertInMap(this.valuesByAttributedField, contactFieldWithValue.getAttributedContactField(), contactFieldWithValue);
		if (!contactFieldWithValue.getAttributedContactField().fieldHasRecognisedType()) {
			this.unrecognisedFields.add(contactFieldWithValue.getAttributedContactField());
		}
	}
	
	/**
	 * Given an attributed contact field that is in this contact, this method will replace
	 * its attributes with a new collection.
	 * @param attributedContactField An attributed contact field.
	 * @param attributes The new collection of attributes.
	 */
	public void replaceFieldAttributes(AttributedContactField attributedContactField, Collection<AttributeWithValue> attributes) {
		List<ContactFieldWithValue> contactFieldsWithValues = this.valuesByAttributedField.remove(attributedContactField);
		if (contactFieldsWithValues != null) {
			ContactField contactField = attributedContactField.getContactField();
			AttributedContactField newAttributedContactField = new AttributedContactField(contactField, attributes);
			for (ContactFieldWithValue contactFieldWithValue : contactFieldsWithValues) {
				contactFieldWithValue.setAttributedContactField(newAttributedContactField);
			}
			this.valuesByAttributedField.put(newAttributedContactField, contactFieldsWithValues);
			/*
			 * The unrecognised field statistic references fields with attributes, and as the
			 * attributes of a field have changed, the statistics must be recalculated.
			 */
			this.recalculateStats();
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return ContactConverter.modelToVcf(this);
	}
	
	private static <K> void insertInMap(Map<K, List<ContactFieldWithValue>> map, K contactField, ContactFieldWithValue contactFieldWithValue) {
		List<ContactFieldWithValue> previousValues = map.get(contactField);
		if (previousValues == null) {
			previousValues = new ArrayList<>();
			map.put(contactField, previousValues);
		}
		previousValues.add(contactFieldWithValue);
	}
	
	/**
	 * Recalculates this contact's statistics.
	 */
	private void recalculateStats() {
		this.unrecognisedFields.clear();
		for (ContactFieldWithValue contactFieldWithValue : this.fieldValues) {
			if (!contactFieldWithValue.getAttributedContactField().fieldHasRecognisedType()) {
				this.unrecognisedFields.add(contactFieldWithValue.getAttributedContactField());
			}
		}
	}
}
