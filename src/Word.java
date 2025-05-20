
public class Word {

	private String english;
	private String mean;
	
	public Word(String enlish, String mean) {
		this.english = enlish;
		this.mean = mean;
	}
	
	public Word() {
		this.english = null;
		this.mean = null;
	}
	
	public String getEnglish() {return english;}
	public String getMean() {return mean;}
	
	public void setEnglish(String english) {this.english = english;}
	public void setMean(String mean) {this.mean = mean;}
	
}
