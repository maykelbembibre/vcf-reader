package vcf_reader.format.entities;

public enum ContactFieldType {

	VERSION("VERSION"),
	NAME("N"),
	FULL_NAME("FN"),
	TELEPHONE("TEL"),
	EMAIL("EMAIL"),
	NOTE("NOTE");
	
	private String key;
	
	private ContactFieldType(String key) {
		this.key = key;
	}
	
	public String getKey() {
		return this.key;
	}
	
	public static ContactFieldType from(String key) {
		ContactFieldType[] values = values();
		ContactFieldType value;
		ContactFieldType result = null;
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
