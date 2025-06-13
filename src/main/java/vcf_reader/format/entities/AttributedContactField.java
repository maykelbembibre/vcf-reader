package vcf_reader.format.entities;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import vcf_reader.format.converters.AttributedContactFieldConverter;

/**
 * A contact field plus its attributes.
 */
public class AttributedContactField {

	public static AttributedContactField CELLULAR_TELEPHONE = new AttributedContactField(
		ContactFieldType.TELEPHONE, new AttributeWithValue(Attribute.CELL)
	);
	public static AttributedContactField HOME_TELEPHONE = new AttributedContactField(
		ContactFieldType.TELEPHONE, new AttributeWithValue(Attribute.HOME)
	);
	
	private static final Set<Attribute> PHONE_TYPES = new HashSet<>();
	static {
		PHONE_TYPES.add(Attribute.CELL);
		PHONE_TYPES.add(Attribute.HOME);
		PHONE_TYPES.add(Attribute.X);
	}
	
	/**
	 * The contact field.
	 */
	private final ContactField contactField;

	/**
	 * The list of all attributes with their values in the same order as found in
	 * the VCF file, useful for writing or re-writing the file with user modifications
	 * but respecting the order of things in the original file as much as possible.
	 */
	private final List<AttributeWithValue> attributesWithValues = new ArrayList<>();
	
	/**
	 * Map of values by attribute, useful for searching for the value of a
	 * specific attribute.
	 */
	private final Map<Attribute, AttributeWithValue> valuesByAttribute = new HashMap<>();
	
	public AttributedContactField(ContactField contactField) {
		this.contactField = contactField;
	}
	
	AttributedContactField(ContactField contactField, Collection<AttributeWithValue> attributes) {
		this.contactField = contactField;
		for (AttributeWithValue attribute : attributes) {
			this.insertEverywhere(attribute);
		}
	}
	
	private AttributedContactField(ContactFieldType type, AttributeWithValue... attributes) {
		this.contactField = new ContactField(type);
		for (AttributeWithValue attribute : attributes) {
			this.insertEverywhere(attribute);
		}
	}

	/**
	 * Returns the field of this field and attributes association.
	 * This method can never return <code>null</code>.
	 * @return The field.
	 */
	public ContactField getContactField() {
		return contactField;
	}

	/**
	 * Returns an iterator of the attributes with values of this
	 * attributed contact field. This method can never return <code>null</code>.
	 * @return An iterator of attributes with values.
	 */
	public Iterator<AttributeWithValue> getAttributesWithValues() {
		return attributesWithValues.iterator();
	}

	public boolean fieldHasRecognisedType() {
		return this.contactField.getType().isPresent();
	}
	
	/**
	 * Returns the phone type of this attributed contact field, or an empty
	 * {@link Optional} if this field is not related to phone numbers.
	 * @return The phone type or an empty {@link Optional}.
	 */
	public Optional<Attribute> getPhoneType() {
		Attribute result = null;
		Iterator<Attribute> iterator = this.valuesByAttribute.keySet().iterator();
		Attribute next;
		while (result == null && iterator.hasNext()) {
			next = iterator.next();
			if (PHONE_TYPES.contains(next)) {
				result = next;
			}
		}
		return Optional.ofNullable(result);
	}
	
	public Optional<AttributeWithValue> getAttributeValue(Attribute attribute) {
		return Optional.ofNullable(this.valuesByAttribute.get(attribute));
	}
	
	public void addAttributeWithValue(AttributeWithValue attributeWithValue) {
		this.insertEverywhere(attributeWithValue);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public int hashCode() {
		return Objects.hash(valuesByAttribute.keySet(), contactField);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AttributedContactField other = (AttributedContactField) obj;
		return Objects.equals(valuesByAttribute.keySet(), other.valuesByAttribute.keySet()) && Objects.equals(contactField, other.contactField);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return AttributedContactFieldConverter.modelToVcf(this);
	}
	
	private void insertEverywhere(AttributeWithValue attributeWithValue) {
		this.attributesWithValues.add(attributeWithValue);
		this.valuesByAttribute.put(attributeWithValue.getAttribute(), attributeWithValue);
	}
}
