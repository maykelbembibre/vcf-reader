package vcf_reader.input_output;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import vcf_reader.format.converters.ContactConverter;
import vcf_reader.format.entities.Contact;

public class OutputTools {

	public static void write(Iterable<Contact> contacts, File file) {
		FileWriter fileWriter = null;
		PrintWriter printWriter = null;
		try {
			fileWriter = new FileWriter(file);
			printWriter = new PrintWriter(fileWriter);
			for (Contact contact : contacts) {
				printWriter.print(ContactConverter.modelToVcf(contact));
				printWriter.println();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (printWriter != null) {
				printWriter.close();
			}
			if (fileWriter != null) {
				try {
					fileWriter.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
