import java.io.File;

import com.fasterxml.jackson.databind.ObjectMapper;

public class OptionEditer {

	public static Option loadOption(String path) {
		
		try {
			
			ObjectMapper mapper = new ObjectMapper();
			return mapper.readValue(new File(path), Option.class);
			
		} catch(Exception e) {
			e.printStackTrace();
			return null;
		}
		
	}
	
	public static void saveOption(String path, Option op) {
		
		try {
			
			ObjectMapper mapper = new ObjectMapper();
			mapper.writerWithDefaultPrettyPrinter().writeValue(new File(path), op);
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
}
