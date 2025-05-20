public class Option {

	private String processType = "S";
	private String testType = "M";
	private String fileType = "N";
	private boolean createBlank = false;
	private boolean pageDivision = false;
	private boolean pageDivision_FirstDifferent = false;

	private int numberOfWord = 10;
	private int blankRow = 0;
	private int blankColumn = 0;
	private int pageDivisionSize = 0;
	private int pageDivisionFirstSize = 0;
	private int pageBlankSize = 0;
	private int numberOfColumn = 1;

	public String getProcessType() { return processType; }
	public String getTestType() { return testType; }
	public String getFileType() { return fileType; }

	public boolean getCreateBlank() { return createBlank; }
	public boolean getPageDivision() { return pageDivision; }
	public boolean getPageDivision_FirstDifferent() { return pageDivision_FirstDifferent; }

	public int getNumberOfWord() { return numberOfWord; }
	public int getBlankRow() { return blankRow; }
	public int getBlankColumn() { return blankColumn; }
	public int getPageDivisionSize() { return pageDivisionSize; }
	public int getPageDivisionFirstSize() { return pageDivisionFirstSize; }
	public int getPageBlankSize() { return pageBlankSize; }
	public int getNumberOfColumn() { return numberOfColumn; }

	public void setProcessType(String processType) { this.processType = processType; }
	public void setTestType(String testType) { this.testType = testType; }
	public void setFileType(String fileType) { this.fileType = fileType; }
	public void setCreateBlank(boolean createBlank) { this.createBlank = createBlank;}
	public void setPageDivision(boolean pageDivision) { this.pageDivision = pageDivision; }
	public void setPageDivision_FirstDifferent(boolean pageDivision_FirstDifferent) { this.pageDivision_FirstDifferent = pageDivision_FirstDifferent; }

	public void setNumberOfWord(int numberOfWord) { this.numberOfWord = numberOfWord; }
	public void setBlankRow(int blankRow) { this.blankRow = blankRow; }
	public void setBlankColumn(int blankColumn) { this.blankColumn = blankColumn; }
	public void setPageDivisionSize(int pageDivisionSize) { this.pageDivisionSize = pageDivisionSize; }
	public void setPageDivisionFirstSize(int pageDivisionFirstSize) { this.pageDivisionFirstSize = pageDivisionFirstSize; }
	public void setPageBlankSize(int pageBlankSize) { this.pageBlankSize = pageBlankSize; }
	public void setNumberOfColumn(int numberOfColumn) { this.numberOfColumn = numberOfColumn; }
}