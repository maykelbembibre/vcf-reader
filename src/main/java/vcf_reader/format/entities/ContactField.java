package vcf_reader.format.entities;

import java.util.Objects;
import java.util.Optional;

import vcf_reader.format.converters.ContactFieldConverter;

/**
 * Class that represents a contact field, which always has a non empty name and,
 * if recognised, also has a contact field type.
 */
public class ContactField {

	public static ContactField FULL_NAME = new ContactField(ContactFieldType.FULL_NAME);
	public static ContactField TELEPHONE = new ContactField(ContactFieldType.TELEPHONE);
	public static ContactField EMAIL = new ContactField(ContactFieldType.EMAIL);
	
	private final String name;
	private final ContactFieldType type;
	
	public ContactField(String name) {
		this.name = name;
		this.type = ContactFieldType.from(name);
	}

	ContactField(ContactFieldType type) {
		this.name = type.getKey();
		this.type = type;
	}
	
	public String getName() {
		return name;
	}

	public Optional<ContactFieldType> getType() {
		return Optional.ofNullable(type);
	}

	@Override
	public int hashCode() {
		return Objects.hash(name);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ContactField other = (ContactField) obj;
		return Objects.equals(name, other.name);
	}

	@Override
	public String toString() {
		return ContactFieldConverter.modelToVcf(this);
	}
}
