import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class CreateExcel  {
	
	public static int lineCount = 0;
	public static Word[] wordList;
	public static Word[] randomWordList;
	public static String[][] lastList;

	public static void lineCount() {
		
		if(Main.singleFileProcess.isSelected()) {
			try(Scanner sc = new Scanner(new FileReader(Main.fileManagement.singleImportFile.getAbsolutePath()))) {
			
				int count = 0;
				while(sc.hasNextLine()) {
					sc.nextLine();
					count++;
				}
				lineCount = count;
			} catch(Exception e) {
				System.out.println("lineCount 함수(single에서 오류 발생 / 상세 내용 : " + e.getMessage());
			}
		} else if(Main.doubleFileProcess.isSelected()) {
			try(Scanner scE = new Scanner(new InputStreamReader(new FileInputStream(Main.fileManagement.doubleImportFileEnglish), "UTF-8"));
				Scanner scM = new Scanner(new InputStreamReader(new FileInputStream(Main.fileManagement.doubleImportFileMean), "UTF-8"))) {
			
				int countE = 0;
				while(scE.hasNextLine()) {
					scE.nextLine();
					countE++;
				}
				
				int countM = 0;
				while(scM.hasNextLine()) {
					scM.nextLine();
					countM++;
				}
			
				if(countE == countM) {
				    lineCount = countE;
				} else {
				    lineCount = -1;
				}
			} catch(Exception e) {
				System.out.println("lineCount 함수(double)에서 오류 발생 / 상세 내용 : " + e.getMessage());
			}
		} else {
			lineCount = -1;
		}
		
	}
	
	public static boolean numberOfWordTest() {
		if(lineCount < 0) {System.exit(0);}
		return lineCount >= (int) Main.numberOfWord.getValue();
	}
	
	public static void createWordArraySingle() {
		try(Scanner scI = new Scanner(new InputStreamReader(new FileInputStream(Main.fileManagement.singleImportFile), "UTF-8"))) {
			
			Word result[] = new Word[lineCount];
			for(int i = 0; i < result.length; i++) {
				result[i] = new Word();
			}
		
			int i = 0;
			while(scI.hasNextLine()) {
				String line = scI.nextLine();
				String[] parts = line.split("\\|");
			
				result[i].setEnglish(parts[0].trim());
				result[i].setMean(parts[1].trim());
				i++;
			}
		
			wordList = result;
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void createWordArrayDouble() {
		try(Scanner scE = new Scanner(new InputStreamReader(new FileInputStream(Main.fileManagement.doubleImportFileEnglish), "UTF-8"));
			Scanner scM = new Scanner(new InputStreamReader(new FileInputStream(Main.fileManagement.doubleImportFileMean), "UTF-8"))) {
			
			Word result[] = new Word[lineCount];
			for(int i = 0; i < result.length; i++) {
				result[i] = new Word();
			}
		
			int i = 0;
			while(scE.hasNextLine()) {
				result[i].setEnglish(scE.nextLine().trim());
				result[i].setMean(scM.nextLine().trim());
				i++;
			}
		
			wordList = result;
		} catch(Exception e) {
			System.out.println("createWordArrayDouble 함수에서 오류 발생 : " + e.getMessage());
		}
	}
	
	public static void createRadomList() {
		int limit = (int) Main.numberOfWord.getValue();
		
		List<Word> temp = new ArrayList<Word>(Arrays.asList(wordList));
		Collections.shuffle(temp);
		randomWordList = temp.subList(0, limit).toArray(new Word[0]);
	}
	
	public static void cutList() {
		
		int columnSize = 2 * (int) Main.numberOfColumn.getValue();
		double rowSizeD = ((double) lineCount) / (int) Main.numberOfColumn.getValue();
		int rowSize = (int) Math.ceil(rowSizeD);
		
		String result[][] = new String[rowSize][columnSize];
		int row = 0;
		int column = 0;
		int index = 0;
		boolean complete = false;
		
		while(!complete && index < randomWordList.length) {
			
			result[row][column] = randomWordList[index].getEnglish();
			result[row][column + 1] = randomWordList[index].getMean();
			row++;
			
			if(row == rowSize) {
				column += 2;
				row = 0;
			}
			
			if(column >= columnSize) {
				complete = true;
			}
			index++;
			
		}
		lastList = result;
		
	}
	
	public static void createNewExcel() {
		
		XSSFWorkbook workbook = new XSSFWorkbook();
		XSSFSheet questionSheet = workbook.createSheet("문제");
		XSSFSheet answerSheet = workbook.createSheet("답지");
		
		int blankColumn = (int) Main.blankSizeColumnSpinner.getValue();
		int blankRow = (int) Main.blankSizeRowSpinner.getValue();
		
		int testType = -1;
		if(Main.generateOptionMean.isSelected()) {
			testType = 0;
		} else if(Main.generateOptionEnglish.isSelected()) {
			testType = 1;
		} else if(Main.generateOptionMix.isSelected()) {
			testType = 2;
		}
		
		int division = (int) Main.pageBlankSizeSpinner.getValue();
		int page = 0;
		int line = 0;
		
		if(testType == -1) {
			System.exit(0);
		} else if(testType == 0) {
			for(int i = 0; i < lastList.length; i++) {
				XSSFRow row = questionSheet.createRow(i + blankRow + division * page);
				XSSFRow row2 = answerSheet.createRow(i + blankRow + division * page);
				for(int j = 0; j < lastList[i].length; j += 2) {
					row.createCell(j + blankColumn).setCellValue(lastList[i][j]);
					row2.createCell(j + blankColumn).setCellValue(lastList[i][j]);
					row2.createCell(j + 1 + blankColumn).setCellValue(lastList[i][j + 1]);
				}
				
				if(Main.pageDivision.isSelected()) {
					
					if(page == 0 && Main.pageDivisionFirstDifferent.isSelected()) {
						line++;
						if(line == (int) Main.pageDivisionFirstSizeSpinner.getValue()) {
							line = 0;
							page = 1;
						}
					} else {
						line++;
						if(line == (int) Main.pageDivisionSizeSpinner.getValue()) {
							line = 0;
							page++;
						}
					}
					
				}
			}
		} else if(testType == 1) {
			for(int i = 0; i < lastList.length; i++) {
				XSSFRow row = questionSheet.createRow(i + blankRow + division * page);
				XSSFRow row2 = answerSheet.createRow(i + blankRow + division * page);
				for(int j = 0; j < lastList[i].length; j += 2) {
					row.createCell(j + blankColumn).setCellValue(lastList[i][j + 1]);
					row2.createCell(j + blankColumn).setCellValue(lastList[i][j + 1]);
					row2.createCell(j + 1 + blankColumn).setCellValue(lastList[i][j]);
				}
				
				if(Main.pageDivision.isSelected()) {
					
					if(page == 0 && Main.pageDivisionFirstDifferent.isSelected()) {
						line++;
						if(line == (int) Main.pageDivisionFirstSizeSpinner.getValue()) {
							line = 0;
							page = 1;
						}
					} else {
						line++;
						if(line == (int) Main.pageDivisionSizeSpinner.getValue()) {
							line = 0;
							page++;
						}
					}
					
				}
			}
		} else if(testType == 2) {
			
			Random ran = new Random();
			for(int i = 0; i < lastList.length; i++) {
				
				XSSFRow row = questionSheet.createRow(i + blankRow + division * page);
				XSSFRow row2 = answerSheet.createRow(i + blankRow + division * page);
				for(int j = 0; j < lastList[i].length; j += 2) {
					
					int random = ran.nextInt(2);
					if(random == 0) {
						row.createCell(j + blankColumn).setCellValue(lastList[i][j]);
						row2.createCell(j + blankColumn).setCellValue(lastList[i][j]);
						row2.createCell(j + 1 + blankColumn).setCellValue(lastList[i][j + 1]);
					} else {
						row.createCell(j + 1 + blankColumn).setCellValue(lastList[i][j + 1]);
						row2.createCell(j + blankColumn).setCellValue(lastList[i][j]);
						row2.createCell(j + 1 + blankColumn).setCellValue(lastList[i][j + 1]);
					}
					
				}
				
				if(Main.pageDivision.isSelected()) {
					
					if(page == 0 && Main.pageDivisionFirstDifferent.isSelected()) {
						line++;
						if(line == (int) Main.pageDivisionFirstSizeSpinner.getValue()) {
							line = 0;
							page = 1;
						}
					} else {
						line++;
						if(line == (int) Main.pageDivisionSizeSpinner.getValue()) {
							line = 0;
							page++;
						}
					}
					
				}
				
			}
			
		}
		try(FileOutputStream fos = new FileOutputStream(Main.fileManagement.exportFile.getAbsolutePath())) {			
			workbook.write(fos);
			workbook.close();
		} catch(Exception e) {
			System.out.println("fos에서 오류 발생 / 상세 내용 : " + e.getMessage());
		}
	}
	
	public static void createExistExcel() {
		
		try(FileInputStream fis = new FileInputStream(Main.fileManagement.existExcelFile);
			Workbook workbook = new XSSFWorkbook(fis)) {
			
			Sheet sheet = null, sheet2 = null;
			try {
				sheet = workbook.getSheetAt(0);
				sheet2 = workbook.getSheetAt(1);
			} catch(Exception e) {
			}
			int blankColumn = (int) Main.blankSizeColumnSpinner.getValue();
			int blankRow = (int) Main.blankSizeRowSpinner.getValue();
			int division = (int) Main.pageBlankSizeSpinner.getValue();
			int page = 0;
			int line = 0;
			
			int testType = -1;
			if(Main.generateOptionMean.isSelected()) {
				testType = 0;
			} else if(Main.generateOptionEnglish.isSelected()) {
				testType = 1;
			} else if(Main.generateOptionMix.isSelected()) {
				testType = 2;
			}
			
			if(sheet == null) {
				createNewExcel();
			} else if (sheet != null && sheet2 == null){
				
				sheet2 = workbook.createSheet("답지");
				
				if(testType == -1) {
					System.exit(0);
				} else if(testType == 0) {
					
					for(int i = 0; i < lastList.length; i++) {
						
						Row row = sheet.getRow(i + blankRow + division * page);
						if(row == null) {
							row = sheet.createRow(i + blankRow + division * page);
						}
						Row row2 = sheet.createRow(i + blankRow + division * page);
						
						for(int j = 0; j < lastList[i].length; j += 2) {
							if(row.getCell(j + blankColumn) == null) {
								row.createCell(j + blankColumn).setCellValue(lastList[i][j]);
								row2.createCell(j + blankColumn).setCellValue(lastList[i][j]);
								row2.createCell(j + blankColumn + 1).setCellValue(lastList[i][j + 1]);
							} else {
								row.getCell(j + blankColumn).setCellValue(lastList[i][j]);
								row2.createCell(j + blankColumn).setCellValue(lastList[i][j]);
								row2.createCell(j + blankColumn + 1).setCellValue(lastList[i][j + 1]);
							}
						}
						
						if(Main.pageDivision.isSelected()) {
							
							if(page == 0 && Main.pageDivisionFirstDifferent.isSelected()) {
								line++;
								if(line == (int) Main.pageDivisionFirstSizeSpinner.getValue()) {
									line = 0;
									page = 1;
								}
							} else {
								line++;
								if(line == (int) Main.pageDivisionSizeSpinner.getValue()) {
									line = 0;
									page++;
								}
							}
							
						}
					}
					
				} else if(testType == 1) {
					for(int i = 0; i < lastList.length; i++) {
						
						Row row = sheet.getRow(i + blankRow + division * page);
						if(row == null) {
							row = sheet.createRow(i + blankRow + division * page);
						}
						Row row2 = sheet2.createRow(i + blankRow + division * page);
						
						for(int j = 0; j < lastList[i].length; j += 2) {
							
							if(row.getCell(j + blankColumn) == null) {
								row.createCell(j + blankColumn).setCellValue(lastList[i][j + 1]);
								row2.createCell(j + blankColumn).setCellValue(lastList[i][j + 1]);
								row2.createCell(j + blankColumn + 1).setCellValue(lastList[i][j]);
							} else {
								row.getCell(j + blankColumn).setCellValue(lastList[i][j + 1]);
								row2.createCell(j + blankColumn).setCellValue(lastList[i][j + 1]);
								row2.createCell(j + blankColumn + 1).setCellValue(lastList[i][j]);
							}
							
						}
						
						if(Main.pageDivision.isSelected()) {
							
							if(page == 0 && Main.pageDivisionFirstDifferent.isSelected()) {
								line++;
								if(line == (int) Main.pageDivisionFirstSizeSpinner.getValue()) {
									line = 0;
									page = 1;
								}
							} else {
								line++;
								if(line == (int) Main.pageDivisionSizeSpinner.getValue()) {
									line = 0;
									page++;
								}
							}
							
						}
						
					} 
				} else {
					for(int i = 0; i < lastList.length; i++) {
						
						Random ran = new Random();
						int ranInt = ran.nextInt(2);
						
						Row row = sheet.getRow(i + blankRow + division * page);
						if(row == null) {
							row = sheet.createRow(i + blankRow + division * page);
						}
						Row row2 = sheet2.createRow(i + blankRow + division * page);
						
						for(int j = 0; j < lastList[i].length; j += 2) {
							
							if(row.getCell(j + blankColumn + ranInt) == null) {
								if(ranInt == 0) {
									row.createCell(j + blankColumn).setCellValue(lastList[i][j]);
								} else {
									row.createCell(j + blankColumn + 1).setCellValue(lastList[i][j + 1]);
								}
								row2.createCell(j + blankColumn).setCellValue(lastList[i][j]);
								row2.createCell(j + blankColumn + 1).setCellValue(lastList[i][j + 1]);
							} else {
								if(ranInt == 0) {
									row.getCell(j + blankColumn).setCellValue(lastList[i][j]);
								} else {
									row.getCell(j + blankColumn + 1).setCellValue(lastList[i][j + 1]);
								}
								row2.createCell(j + blankColumn).setCellValue(lastList[i][j]);
								row2.createCell(j + blankColumn + 1).setCellValue(lastList[i][j + 1]);
							}
							
						}
						
						if(Main.pageDivision.isSelected()) {
							
							if(page == 0 && Main.pageDivisionFirstDifferent.isSelected()) {
								line++;
								if(line == (int) Main.pageDivisionFirstSizeSpinner.getValue()) {
									line = 0;
									page = 1;
								}
							} else {
								line++;
								if(line == (int) Main.pageDivisionSizeSpinner.getValue()) {
									line = 0;
									page++;
								}
							}
							
						}
						
					}
					
					
				
				}
			
			} else {
				if(testType == -1) {
					System.exit(0);
				} else if(testType == 0) {
					for(int i = 0; i < lastList.length; i++) {
						
						Row row = sheet.getRow(i + blankRow + division * page);
						if(row == null) {
							row = sheet.createRow(i + blankRow + division * page);
						}
						Row row2 = sheet2.getRow(i + blankRow + division * page);
						if(row2 == null) {
							row2 = sheet2.createRow(i + blankRow + division * page);
						}
						for(int j = 0; j < lastList[i].length; j += 2) {
							if(row.getCell(j + blankColumn) == null) {
								row.createCell(j + blankColumn).setCellValue(lastList[i][j]);
								if(row2.getCell(j + blankColumn) == null) {
									if(row2.getCell(j + blankColumn + 1) == null) {
										row2.createCell(j + blankColumn).setCellValue(lastList[i][j]);
										row2.createCell(j + blankColumn + 1).setCellValue(lastList[i][j + 1]);
									} else {
										row2.createCell(j + blankColumn).setCellValue(lastList[i][j]);
										row2.getCell(j + blankColumn + 1).setCellValue(lastList[i][j + 1]);
									}
								} else {
									if(row2.getCell(j + blankColumn + 1) == null) {
										row2.getCell(j + blankColumn).setCellValue(lastList[i][j]);
										row2.createCell(j + blankColumn + 1).setCellValue(lastList[i][j + 1]);
									} else {
										row2.getCell(j + blankColumn).setCellValue(lastList[i][j]);
										row2.getCell(j + blankColumn + 1).setCellValue(lastList[i][j + 1]);
									}
								}
							} else {
								row.getCell(j + blankColumn).setCellValue(lastList[i][j]);
								if(row2.getCell(j + blankColumn) == null) {
									if(row2.getCell(j + blankColumn + 1) == null) {
										row2.createCell(j + blankColumn).setCellValue(lastList[i][j]);
										row2.createCell(j + blankColumn + 1).setCellValue(lastList[i][j + 1]);
									} else {
										row2.createCell(j + blankColumn).setCellValue(lastList[i][j]);
										row2.getCell(j + blankColumn + 1).setCellValue(lastList[i][j + 1]);
									}
								} else {
									if(row2.getCell(j + blankColumn + 1) == null) {
										row2.getCell(j + blankColumn).setCellValue(lastList[i][j]);
										row2.createCell(j + blankColumn + 1).setCellValue(lastList[i][j + 1]);
									} else {
										row2.getCell(j + blankColumn).setCellValue(lastList[i][j]);
										row2.getCell(j + blankColumn + 1).setCellValue(lastList[i][j + 1]);
									}
								}
							}
							
							
						}
						
						if(Main.pageDivision.isSelected()) {
							
							if(page == 0 && Main.pageDivisionFirstDifferent.isSelected()) {
								line++;
								if(line == (int) Main.pageDivisionFirstSizeSpinner.getValue()) {
									line = 0;
									page = 1;
								}
							} else {
								line++;
								if(line == (int) Main.pageDivisionSizeSpinner.getValue()) {
									line = 0;
									page++;
								}
							}
							
						}
						
						
					}
				} else if(testType == 1) {
					for(int i = 0; i < lastList.length; i++) {
						
						Row row = sheet.getRow(i + blankRow + division * page);
						if(row == null) {
							row = sheet.createRow(i + blankRow + division * page);
						}
						Row row2 = sheet2.getRow(i + blankRow + division * page);
						if(row2 == null) {
							row2 = sheet2.createRow(i + blankRow + division * page);
						}
						for(int j = 0; j < lastList[i].length; j += 2) {
							if(row.getCell(j + blankColumn) == null) {
								row.createCell(j + blankColumn).setCellValue(lastList[i][j + 1]);
								if(row2.getCell(j + blankColumn) == null) {
									if(row2.getCell(j + blankColumn + 1) == null) {
										row2.createCell(j + blankColumn).setCellValue(lastList[i][j + 1]);
										row2.createCell(j + blankColumn + 1).setCellValue(lastList[i][j]);
									} else {
										row2.createCell(j + blankColumn).setCellValue(lastList[i][j + 1]);
										row2.getCell(j + blankColumn + 1).setCellValue(lastList[i][j]);
									}
								} else {
									if(row2.getCell(j + blankColumn + 1) == null) {
										row2.getCell(j + blankColumn).setCellValue(lastList[i][j + 1]);
										row2.createCell(j + blankColumn + 1).setCellValue(lastList[i][j]);
									} else {
										row2.getCell(j + blankColumn).setCellValue(lastList[i][j + 1]);
										row2.getCell(j + blankColumn + 1).setCellValue(lastList[i][j]);
									}
								}
							} else {
								row.getCell(j + blankColumn).setCellValue(lastList[i][j + 1]);
								if(row2.getCell(j + blankColumn) == null) {
									if(row2.getCell(j + blankColumn + 1) == null) {
										row2.createCell(j + blankColumn).setCellValue(lastList[i][j + 1]);
										row2.createCell(j + blankColumn + 1).setCellValue(lastList[i][j]);
									} else {
										row2.createCell(j + blankColumn).setCellValue(lastList[i][j + 1]);
										row2.getCell(j + blankColumn + 1).setCellValue(lastList[i][j]);
									}
								} else {
									if(row2.getCell(j + blankColumn + 1) == null) {
										row2.getCell(j + blankColumn).setCellValue(lastList[i][j + 1]);
										row2.createCell(j + blankColumn + 1).setCellValue(lastList[i][j]);
									} else {
										row2.getCell(j + blankColumn).setCellValue(lastList[i][j + 1]);
										row2.getCell(j + blankColumn + 1).setCellValue(lastList[i][j]);
									}
								}
							}
							
							
						}
						
						if(Main.pageDivision.isSelected()) {
							
							if(page == 0 && Main.pageDivisionFirstDifferent.isSelected()) {
								line++;
								if(line == (int) Main.pageDivisionFirstSizeSpinner.getValue()) {
									line = 0;
									page = 1;
								}
							} else {
								line++;
								if(line == (int) Main.pageDivisionSizeSpinner.getValue()) {
									line = 0;
									page++;
								}
							}
							
						}
						
					}
				} else {
					for(int i = 0; i < lastList.length; i++) {
						
						Random ran = new Random();
						int ranInt = ran.nextInt(2);
						
						Row row = sheet.getRow(i + blankRow + division * page);
						if(row == null) {
							row = sheet.createRow(i + blankRow + division * page);
						}
						Row row2 = sheet2.getRow(i + blankRow + division * page);
						if(row2 == null) {
							row2 = sheet2.createRow(i + blankRow + division * page);
						}
						for(int j = 0; j < lastList[i].length; j += 2) {
							if(row.getCell(j + blankColumn + ranInt) == null) {
								if(ranInt == 0) {
									row.createCell(j + blankColumn).setCellValue(lastList[i][j]);
								} else {
									row.createCell(j + blankColumn + 1).setCellValue(lastList[i][j + 1]);
								}
								if(row2.getCell(j + blankColumn) == null) {
									if(row2.getCell(j + blankColumn + 1) == null) {
										row2.createCell(j + blankColumn).setCellValue(lastList[i][j]);
										row2.createCell(j + blankColumn + 1).setCellValue(lastList[i][j + 1]);
									} else {
										row2.createCell(j + blankColumn).setCellValue(lastList[i][j]);
										row2.getCell(j + blankColumn + 1).setCellValue(lastList[i][j + 1]);
									}
								} else {
									if(row2.getCell(j + blankColumn + 1) == null) {
										row2.getCell(j + blankColumn).setCellValue(lastList[i][j]);
										row2.createCell(j + blankColumn + 1).setCellValue(lastList[i][j + 1]);
									} else {
										row2.getCell(j + blankColumn).setCellValue(lastList[i][j]);
										row2.getCell(j + blankColumn + 1).setCellValue(lastList[i][j + 1]);
									}
								}
							} else {
								if(ranInt == 0) {
									row.getCell(j + blankColumn).setCellValue(lastList[i][j]);
								} else {
									row.getCell(j + blankColumn + 1).setCellValue(lastList[i][j + 1]);
								}
								if(row2.getCell(j + blankColumn) == null) {
									if(row2.getCell(j + blankColumn + 1) == null) {
										row2.createCell(j + blankColumn).setCellValue(lastList[i][j]);
										row2.createCell(j + blankColumn + 1).setCellValue(lastList[i][j + 1]);
									} else {
										row2.createCell(j + blankColumn).setCellValue(lastList[i][j]);
										row2.getCell(j + blankColumn + 1).setCellValue(lastList[i][j + 1]);
									}
								} else {
									if(row2.getCell(j + blankColumn + 1) == null) {
										row2.getCell(j + blankColumn).setCellValue(lastList[i][j]);
										row2.createCell(j + blankColumn + 1).setCellValue(lastList[i][j + 1]);
									} else {
										row2.getCell(j + blankColumn).setCellValue(lastList[i][j]);
										row2.getCell(j + blankColumn + 1).setCellValue(lastList[i][j + 1]);
									}
								}
							}
						}
						
						if(Main.pageDivision.isSelected()) {
							
							if(page == 0 && Main.pageDivisionFirstDifferent.isSelected()) {
								line++;
								if(line == (int) Main.pageDivisionFirstSizeSpinner.getValue()) {
									line = 0;
									page = 1;
								}
							} else {
								line++;
								if(line == (int) Main.pageDivisionSizeSpinner.getValue()) {
									line = 0;
									page++;
								}
							}
							
						}
					}
				}
			}
			
			try(FileOutputStream fos = new FileOutputStream(Main.fileManagement.exportFile.getAbsolutePath())) {			
				workbook.write(fos);
				workbook.close();
			} catch(Exception e) {
				System.out.println("fos에서 오류 발생 / 상세 내용 : " + e.getMessage());
			}
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		
	}

	public static void singleProcess() throws Exception {
		lineCount();
		if(!numberOfWordTest()) {System.exit(0);}
		createWordArraySingle();
		createRadomList();
		cutList();

		if(Main.generateOptionExist.isSelected()) {
			createExistExcel();
		} else {
			createNewExcel();
		}
	}
	
	public static void doubleProcess() throws Exception {
		lineCount();
		if(!numberOfWordTest()) {System.exit(0);}
		createWordArrayDouble();
		createRadomList();
		cutList();
		
		if(Main.generateOptionExist.isSelected()) {
			createExistExcel();
		} else {
			createNewExcel();
		}
	}
	
	public static void excute() throws Exception{
		if(Main.singleFileProcess.isSelected()) {
			singleProcess();
		} else if(Main.doubleFileProcess.isSelected()) {
			doubleProcess();
		}
	}
	
}
