import java.io.File;

public class OptionManage {

	public Option op;
	
	public OptionManage() {
		
		File optionFile = new File("./Option.json");
		
		if(!optionFile.exists()) {
			op = new Option();
		} else {
			op = OptionEditer.loadOption("./Option.json");
		}
		
	}
	
	public void saveOption() {
		if(Main.doubleFileProcess.isSelected()) {
			op.setProcessType("D");
		} else {
			op.setProcessType("S");
		}
		
		if(Main.generateOptionEnglish.isSelected()) {
			op.setTestType("E");
		} else if(Main.generateOptionMix.isSelected()) {
			op.setTestType("X");
		} else {
			op.setTestType("M");
		}
		
		if(Main.generateOptionExist.isSelected()) {
			op.setFileType("E");
		} else {
			op.setFileType("N");
		}
		
		op.setCreateBlank(Main.generateOptionBlank.isSelected());
		op.setPageDivision(Main.pageDivision.isSelected());
		op.setPageDivision_FirstDifferent(Main.pageDivisionFirstDifferent.isSelected());
		
		op.setNumberOfWord((int) Main.numberOfWord.getValue());
		op.setBlankRow((int) Main.blankSizeRowSpinner.getValue());
		op.setBlankColumn((int) Main.blankSizeColumnSpinner.getValue());
		op.setPageDivisionSize((int) Main.pageDivisionSizeSpinner.getValue());
		op.setPageDivisionFirstSize((int) Main.pageDivisionFirstSizeSpinner.getValue());
		op.setPageBlankSize((int) Main.pageBlankSizeSpinner.getValue());
		op.setNumberOfColumn((int) Main.numberOfColumn.getValue());
		
		OptionEditer.saveOption("./Option.json", op);
		
	}
	
}
