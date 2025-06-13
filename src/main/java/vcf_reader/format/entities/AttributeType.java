package vcf_reader.format.entities;

public enum AttributeType {

	CHARSET("CHARSET"),
	ENCODING("ENCODING"),

	/**
	 * Indicates that a telephone number field refers to the cellular phone.
	 */
	CELL("CELL"),
	
	/**
	 * Indicates that a telephone number field refers to the home phone.
	 */
	HOME("HOME"),
	
	/**
	 * Indicates that a telephone number field refers to the work phone.
	 */
	WORK("WORK"),
	
	/**
	 * Indicates that a telephone number field is the preferred number to
	 * call this person.
	 */
	PREF("PREF"),
	
	X("X");
	
	private String key;
	
	private AttributeType(String key) {
		this.key = key;
	}
	
	public String getKey() {
		return this.key;
	}
	
	public static AttributeType from(String key) {
		AttributeType[] values = values();
		AttributeType value;
		AttributeType result = null;
		int i;
		for (i = 0;i < values.length && result == null;i++) {
			value = values[i];
			if (value.key.equals(key)) {
				result = value;
			}
		}
		return result;
	}
}
