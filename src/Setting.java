
public class Setting {
	private String exportFilePath;
	private String singleImportFilePath;
	private String doubleImportFileMeanPath;
	private String doubleImportFileEnglishPath;
	private String existExcelFilePath;

	public String getExportFilePath() { return exportFilePath; }
	public String getSingleImportFilePath() { return singleImportFilePath; }
	public String getDoubleImportFileMeanPath() { return doubleImportFileMeanPath; }
	public String getDoubleImportFileEnglishPath() { return doubleImportFileEnglishPath; }
	public String getExistExcelFilePath() { return existExcelFilePath; }
	    
	public void setSingleImportFilePath(String singleImportFilePath) { this.singleImportFilePath = singleImportFilePath; }
	public void setExportFilePath(String exportFilePath) { this.exportFilePath = exportFilePath; }
	public void setDoubleImportFileMeanPath(String doubleImportFileMeanPath) { this.doubleImportFileMeanPath = doubleImportFileMeanPath; }
	public void setDoubleImportFileEnglishPath(String doubleImportFileEnglishPath) { this.doubleImportFileEnglishPath = doubleImportFileEnglishPath; }
	public void setExistExcelFilePath(String existExcelFilePath) { this.existExcelFilePath = existExcelFilePath; }
	
}
