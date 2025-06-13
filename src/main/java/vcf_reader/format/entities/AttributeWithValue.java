package vcf_reader.format.entities;

import java.util.Objects;
import java.util.Optional;

import vcf_reader.format.converters.AttributeWithValueConverter;

/**
 * This class represents an association between an attribute and its value.
 */
public class AttributeWithValue {

	/**
	 * The attribute of this association.
	 */
	private final Attribute attribute;
	
	/**
	 * The value of this association.
	 */
	private final String value;
	
	/**
	 * Creates an instance of an attribute that hasn't got a value.
	 * @param attribute The attribute. It cannot be <code>null</code>.
	 */
	public AttributeWithValue(Attribute attribute) {
		this(attribute, null);
	}
	
	/**
	 * Creates an instance of an attribute with a value.
	 * @param attribute The attribute. It cannot be <code>null</code>.
	 * @param value The value.
	 */
	public AttributeWithValue(Attribute attribute, String value) {
		Objects.requireNonNull(attribute);
		this.attribute = attribute;
		this.value = value;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public int hashCode() {
		return Objects.hash(attribute);
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
		AttributeWithValue other = (AttributeWithValue) obj;
		return Objects.equals(attribute, other.attribute);
	}
	
	/**
	 * Returns the attribute. This method will never return <code>null</code>.
	 * @return The attribute.
	 */
	public Attribute getAttribute() {
		return attribute;
	}

	/**
	 * Returns the value.
	 * @return The value, or an empty {@link Optional}, but never
	 * <code>null</code>.
	 */
	public Optional<String> getValue() {
		return Optional.ofNullable(value);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return AttributeWithValueConverter.modelToVcf(this);
	}
}
