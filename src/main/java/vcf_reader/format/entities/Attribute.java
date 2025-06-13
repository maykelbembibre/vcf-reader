package vcf_reader.format.entities;

import java.util.Objects;
import java.util.Optional;

import vcf_reader.format.converters.AttributeConverter;

/**
 * This class represents an attribute in a field of a contact.
 * Some contact fields may have attributes where each attribute has a name and optionally
 * can have a value.
 */
public class Attribute {

	public static final Attribute CHARSET = new Attribute(AttributeType.CHARSET);
	public static final Attribute ENCODING = new Attribute(AttributeType.ENCODING);
	public static final Attribute CELL = new Attribute(AttributeType.CELL);
	public static final Attribute HOME = new Attribute(AttributeType.HOME);
	public static final Attribute X = new Attribute(AttributeType.X);
	
	/**
	 * A special type of attribute that includes a random string.
	 */
	public static final String X_DASH = "X-";
	
	/**
	 * The quoted printable value that the {@link AttributeType#ENCODING}
	 * can have.
	 */
	public static final String ENCODING_QUOTED_PRINTABLE = "QUOTED-PRINTABLE";
	
	/**
	 * The base64 value that the {@link AttributeType#ENCODING}
	 * can have.
	 */
	public static final String ENCODING_BASE64 = "BASE64";
	
	/**
	 * The name of this attribute. It can never be <code>null</code>.
	 */
	private final String name;
	
	/**
	 * The type of this attribute. It is optional. An attribute with
	 * a weird name would not have a recognised type.
	 */
	private final AttributeType type;
	
	/**
	 * If the name of the attribute is X-(something), the value of this attribute will be
	 * "something". Otherwise it will be null;
	 */
	private final String other;
	
	/**
	 * Creates an attribute.
	 * @param name The name of the attribute. It cannot be <code>null</code>.
	 * The attribute type will be worked out from this name.
	 */
	public Attribute(String name) {
		Objects.requireNonNull(name);
		this.name = name;
		AttributeType type = AttributeType.from(name);
		if (type == null && name.startsWith(X_DASH)) {
			type = AttributeType.X;
			this.other = name.substring(X_DASH.length());
		} else {
			this.other = null;
		}
		this.type = type;
	}
	
	/**
	 * Private constructor.
	 * @param type The type of the attribute.
	 */
	private Attribute(AttributeType type) {
		this.type = type;
		this.name = type.getKey();
		this.other = null;
	}

	/**
	 * Returns the name of this attribute. This method will never return
	 * <code>null</code>.
	 * @return The name of this attribute.
	 */
	public String getName() {
		return name;
	}

	/**
	 * Returns the type of this attribute.
	 * @return The type of this attribute or an empty {@link Optional}, but
	 * never <code>null</code>.
	 */
	public Optional<AttributeType> getType() {
		return Optional.ofNullable(type);
	}
	
	/**
	 * If the name of the attribute is X-(something), the value of this attribute will be
	 * "something". Otherwise it will be an empty {@link Optional};
	 * @return The string after X- or an empty {@link Optional}.
	 */
	public Optional<String> getOther() {
		return Optional.ofNullable(other);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int hashCode() {
		return Objects.hash(type);
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
		Attribute other = (Attribute) obj;
		return type == other.type;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return AttributeConverter.modelToVcf(this);
	}
}
