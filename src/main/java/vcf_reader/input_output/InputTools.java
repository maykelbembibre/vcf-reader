package vcf_reader.input_output;

import java.io.File;
import java.util.Optional;

public class InputTools {

	public static Optional<File> readFile(String path) {
		File file;
		if (path == null || path.isEmpty()) {
			file = null;
		} else {
			file = new File(path);
		}
		return Optional.ofNullable(file);
	}
}
