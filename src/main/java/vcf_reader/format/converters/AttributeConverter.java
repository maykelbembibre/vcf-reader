package vcf_reader.format.converters;

import vcf_reader.format.entities.Attribute;

public class AttributeConverter {

	public static Attribute vcfToModel(String name) {
		return new Attribute(name);
	}
	
	public static String modelToVcf(Attribute attribute) {
		return attribute.getName();
	}
}
