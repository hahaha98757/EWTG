import java.io.File;

import com.fasterxml.jackson.databind.ObjectMapper;

public class SettingEditer {

	public static Setting loadSetting(String path) {
		
		try {
			
			ObjectMapper mapper = new ObjectMapper();
			return mapper.readValue(new File(path), Setting.class);
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
	}
	
	public static void saveSetting(String path, Setting set) {
		try {
			
			ObjectMapper mapper = new ObjectMapper();
			mapper.writerWithDefaultPrettyPrinter().writeValue(new File(path), set);;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
