import java.io.File;
import java.io.IOException;

public class fileManage {

	public File exportFile;
	public File singleImportFile;
	public File doubleImportFileMean;
	public File doubleImportFileEnglish;
	public File existExcelFile;
	
	
	public fileManage() throws IOException {
		File settingFile = new File("./Setting.json");
		Setting set;
		
		if(!settingFile.exists()) {
			set = new Setting();
		} else {
			set = SettingEditer.loadSetting("./Setting.json");
		}
		
		if(set == null || isAnyFieldEmpty(set)) {
			set.setExportFilePath(System.getProperty("user.home") + "\\Desktop\\TestFile.xlsx");
			set.setSingleImportFilePath(System.getProperty("user.home") + "\\Desktop\\MeanAndEnglish.txt");
			set.setDoubleImportFileMeanPath(System.getProperty("user.home") + "\\Desktop\\Mean.txt");
			set.setDoubleImportFileEnglishPath(System.getProperty("user.home") + "\\Desktop\\English.txt");
			set.setExistExcelFilePath(System.getProperty("user.home") + "\\Desktop\\exist.xlsx");
			
			SettingEditer.saveSetting("./Setting.json", set);
			
		}
		exportFile = new File(set.getExportFilePath());
		singleImportFile = new File(set.getSingleImportFilePath());
		doubleImportFileMean = new File(set.getDoubleImportFileMeanPath());
		doubleImportFileEnglish = new File(set.getDoubleImportFileEnglishPath());
		existExcelFile = new File(set.getExistExcelFilePath());
		
	}
	
	private boolean isEmpty(String str) {
		return str == null || str.trim().isEmpty();
	}
	
	private boolean isAnyFieldEmpty(Setting set) {
		return isEmpty(set.getExportFilePath()) ||
			   isEmpty(set.getSingleImportFilePath()) ||
	           isEmpty(set.getDoubleImportFileMeanPath()) ||
	           isEmpty(set.getDoubleImportFileEnglishPath()) ||
	           isEmpty(set.getExistExcelFilePath());
	}
	
	public void savePath() {
		Setting set = new Setting();
		set.setExportFilePath(exportFile.getAbsolutePath());
		set.setSingleImportFilePath(singleImportFile.getAbsolutePath());
		set.setDoubleImportFileMeanPath(doubleImportFileMean.getAbsolutePath());
		set.setDoubleImportFileEnglishPath(doubleImportFileEnglish.getAbsolutePath());
		set.setExistExcelFilePath(existExcelFile.getAbsolutePath());
		SettingEditer.saveSetting("./Setting.json", set);
	}
}
