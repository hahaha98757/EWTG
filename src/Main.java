import java.awt.Cursor;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import javax.swing.filechooser.FileNameExtensionFilter;

public class Main {
	
	//frame 생성부
	private JFrame frame;
	
	//panel 생성부
	private JPanel mainPanel;
	private JPanel fileIOPanel;
	private JPanel excelFileSettingPanel;
	
	//check box 생성부
	public static JCheckBox generateOptionMean;
	public static JCheckBox generateOptionEnglish;
	public static JCheckBox generateOptionMix;
	public static JCheckBox generateOptionNew;
	public static JCheckBox generateOptionExist;
	public static JCheckBox generateOptionBlank;
	public static JCheckBox pageDivision;
	public static JCheckBox pageDivisionFirstDifferent;
	public static JCheckBox singleFileProcess;
	public static JCheckBox doubleFileProcess;
	
	//label 생성부
	
	//default font
	private Font cheakFont = new Font("굴림", Font.BOLD, 30);
	private Font textAreaFont = new Font("굴림", Font.BOLD, 25);
	
	//text filed and area 생성부
	private JTextArea exportFilePathArea;
	private JTextArea singleImportFilePathArea;
	private JTextArea doubleImportFileMeanPathArea;
	private JTextArea doubleImportFileEnglishPathArea;
	private JTextArea existExcelFilePathArea;
	
	//image 불러오기
	private ImageIcon changePathImage = new ImageIcon(Main.class.getResource("/Image/changePathImage.png"));
	private ImageIcon changePathImahe_Pressed = new ImageIcon(Main.class.getResource("/Image/changePathImage_Pressed.png"));
	private ImageIcon excelSettingImage = new ImageIcon(Main.class.getResource("/Image/excelSettingImage.png"));
	private ImageIcon excelSettingImage_Pressed = new ImageIcon(Main.class.getResource("/Image/excelSettingImage_Pressed.png"));
	private ImageIcon fileIOSettingImage = new ImageIcon(Main.class.getResource("/Image/fileIOSettingImage.png"));
	private ImageIcon fileIOSettingImage_Pressed = new ImageIcon(Main.class.getResource("/Image/fileIOSettingImage_Pressed.png"));
	private ImageIcon generateImage = new ImageIcon(Main.class.getResource("/Image/generateImage.png"));
	private ImageIcon generateImage_Pressed = new ImageIcon(Main.class.getResource("/Image/generateImage_Pressed.png"));
	private ImageIcon settingCompleteImage = new ImageIcon(Main.class.getResource("/Image/settingCompleteImage.png"));
	private ImageIcon settingCompleteImage_Pressed = new ImageIcon(Main.class.getResource("/Image/settingCompleteImage_Pressed.png"));
	private ImageIcon savingImage = new ImageIcon(Main.class.getResource("/Image/savingImage.png"));
	private ImageIcon savingImage_Pressed = new ImageIcon(Main.class.getResource("/Image/savingImage_Pressed.png"));
	
	//기타 swing instance 생성부
	public static JSpinner blankSizeColumnSpinner;
	public static JSpinner blankSizeRowSpinner;
	public static JSpinner pageDivisionSizeSpinner;
	public static JSpinner pageDivisionFirstSizeSpinner;
	public static JSpinner pageBlankSizeSpinner;
	public static JSpinner numberOfColumn;
	public static JSpinner numberOfWord;
	
	private JFileChooser fileSelecterTxt = new JFileChooser();
	private JFileChooser fileSelecterXlsx = new JFileChooser();
	
	//instance of user definition class 생성부
	public static fileManage fileManagement;
	public static OptionManage optionManagement;
	
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		
		try {
			fileManagement = new fileManage();
			optionManagement = new OptionManage();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Main window = new Main();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Main() {
		initialize();
	}
	
	/** frame instance 에 대한 설정 */
	private void frameSetting() {
		frame = new JFrame();
		frame.setSize(600, 900);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.setTitle("EWTG V2.0 - English Word Test Generater");	
		frame.getContentPane().setLayout(null); 
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	}
	
	private void panelAdd() {
		frame.getContentPane().add(mainPanel);
		frame.getContentPane().add(fileIOPanel);
		frame.getContentPane().add(excelFileSettingPanel);
	}
	
	private void panelSetting() {
		mainPanel = new JPanel();
		mainPanel.setBounds(0, 0, 584, 861);
		mainPanel.setLayout(null);
		
		fileIOPanel = new JPanel();
		fileIOPanel.setBounds(0, 0, 584, 861);
		fileIOPanel.setLayout(null);
		fileIOPanel.setVisible(false);
		
		excelFileSettingPanel = new JPanel();
		excelFileSettingPanel.setBounds(0, 0, 584, 861);
		excelFileSettingPanel.setLayout(null);
		excelFileSettingPanel.setVisible(false);
	}

	private void buttonSetting() {
		
		JButton fileIOBtn = new JButton();
		fileIOBtn.setFocusPainted(false);
		fileIOBtn.setContentAreaFilled(false);
		fileIOBtn.setBorderPainted(false);
		fileIOBtn.setBounds(0, 461, 292, 200);
		fileIOBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
		fileIOBtn.setIcon(fileIOSettingImage);
		fileIOBtn.addMouseListener(new MouseAdapter() {
			
			@Override
			public void mouseEntered(MouseEvent e) {
				fileIOBtn.setIcon(fileIOSettingImage_Pressed);
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				fileIOBtn.setIcon(fileIOSettingImage);
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				mainPanel.setVisible(false);
				fileIOPanel.setVisible(true);
			}
			
		});
		mainPanel.add(fileIOBtn);
		
		JButton excelFileSettingBtn = new JButton();
		excelFileSettingBtn.setFocusPainted(false);
		excelFileSettingBtn.setContentAreaFilled(false);
		excelFileSettingBtn.setBorderPainted(false);
		excelFileSettingBtn.setBounds(292, 461, 292, 200);
		excelFileSettingBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
		excelFileSettingBtn.setIcon(excelSettingImage);
		excelFileSettingBtn.addMouseListener(new MouseAdapter() {
			
			@Override
			public void mouseEntered(MouseEvent e) {
				excelFileSettingBtn.setIcon(excelSettingImage_Pressed);
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				excelFileSettingBtn.setIcon(excelSettingImage);
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				mainPanel.setVisible(false);
				excelFileSettingPanel.setVisible(true);
			}
			
		});
		mainPanel.add(excelFileSettingBtn);
		
		JButton generateBtn = new JButton();
		generateBtn.setBounds(0, 661, 584, 200);
		generateBtn.setBorderPainted(false);
		generateBtn.setContentAreaFilled(false);
		generateBtn.setFocusPainted(false);
		generateBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
		generateBtn.setIcon(generateImage);
		generateBtn.addMouseListener(new MouseAdapter() {
			
			@Override
			public void mouseEntered(MouseEvent e) {
				generateBtn.setIcon(generateImage_Pressed);
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				generateBtn.setIcon(generateImage);
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				try {
					CreateExcel.excute();
					fileManagement.savePath();
					optionManagement.saveOption();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
			
		});
		mainPanel.add(generateBtn);
		
		JButton settingCompleteBtn = new JButton();
		settingCompleteBtn.setBounds(0, 661, 584, 200);
		settingCompleteBtn.setBorderPainted(false);
		settingCompleteBtn.setContentAreaFilled(false);
		settingCompleteBtn.setFocusPainted(false);
		settingCompleteBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
		settingCompleteBtn.setIcon(settingCompleteImage);
		settingCompleteBtn.addMouseListener(new MouseAdapter() {
			
			@Override
			public void mouseEntered(MouseEvent e) {
				settingCompleteBtn.setIcon(settingCompleteImage_Pressed);
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				settingCompleteBtn.setIcon(settingCompleteImage);
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				fileIOPanel.setVisible(false);
				excelFileSettingPanel.setVisible(false);
				mainPanel.setVisible(true);
			}
			
		});
		fileIOPanel.add(settingCompleteBtn);
		
		JButton settingCompleteBtn2 = new JButton();
		settingCompleteBtn2.setBounds(0, 660, 584, 200);
		settingCompleteBtn2.setBorderPainted(false);
		settingCompleteBtn2.setContentAreaFilled(false);
		settingCompleteBtn2.setFocusPainted(false);
		settingCompleteBtn2.setCursor(new Cursor(Cursor.HAND_CURSOR));
		settingCompleteBtn2.setIcon(settingCompleteImage);
		settingCompleteBtn2.addMouseListener(new MouseAdapter() {
			
			@Override
			public void mouseEntered(MouseEvent e) {
				settingCompleteBtn2.setIcon(settingCompleteImage_Pressed);
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				settingCompleteBtn2.setIcon(settingCompleteImage);
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				excelFileSettingPanel.setVisible(false);
				mainPanel.setVisible(true);
			}
			
		});
		excelFileSettingPanel.add(settingCompleteBtn2);
		
		JButton singleImportFileChangeBtn = new JButton();
		singleImportFileChangeBtn.setBounds(484, 170, 100, 30);
		singleImportFileChangeBtn.setBorderPainted(false);
		singleImportFileChangeBtn.setContentAreaFilled(false);
		singleImportFileChangeBtn.setFocusPainted(false);
		singleImportFileChangeBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
		singleImportFileChangeBtn.setIcon(changePathImage);
		singleImportFileChangeBtn.addMouseListener(new MouseAdapter() {
			
			@Override
			public void mouseEntered(MouseEvent e) {
				singleImportFileChangeBtn.setIcon(changePathImahe_Pressed);
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				singleImportFileChangeBtn.setIcon(changePathImage);
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				int result = fileSelecterTxt.showOpenDialog(null);
				
				if(result == JFileChooser.APPROVE_OPTION) {
					fileManagement.singleImportFile = fileSelecterTxt.getSelectedFile();
					singleImportFilePathArea.setText("통합 파일 경로 : " + fileManagement.singleImportFile.getAbsolutePath());
				}
			}
			
		});
		fileIOPanel.add(singleImportFileChangeBtn);
		
		JButton doubleImportFileChangeMeanBtn = new JButton();
		doubleImportFileChangeMeanBtn.setBounds(484, 320, 100, 30);
		doubleImportFileChangeMeanBtn.setBorderPainted(false);
		doubleImportFileChangeMeanBtn.setContentAreaFilled(false);
		doubleImportFileChangeMeanBtn.setFocusPainted(false);
		doubleImportFileChangeMeanBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
		doubleImportFileChangeMeanBtn.setIcon(changePathImage);
		doubleImportFileChangeMeanBtn.addMouseListener(new MouseAdapter() {
			
			@Override
			public void mouseEntered(MouseEvent e) {
				doubleImportFileChangeMeanBtn.setIcon(changePathImahe_Pressed);
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				doubleImportFileChangeMeanBtn.setIcon(changePathImage);
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				int result = fileSelecterTxt.showOpenDialog(null);
				
				if(result == JFileChooser.APPROVE_OPTION) {
					fileManagement.doubleImportFileMean = fileSelecterTxt.getSelectedFile();
					doubleImportFileMeanPathArea.setText("뜻 파일 경로 : " + fileManagement.doubleImportFileMean.getAbsolutePath());
				}
			}
			 
		});
		fileIOPanel.add(doubleImportFileChangeMeanBtn);
		
		JButton doubleImportFileChangeEnglishBtn = new JButton();
		doubleImportFileChangeEnglishBtn.setBounds(484, 420, 100, 30);
		doubleImportFileChangeEnglishBtn.setBorderPainted(false);
		doubleImportFileChangeEnglishBtn.setContentAreaFilled(false);
		doubleImportFileChangeEnglishBtn.setFocusPainted(false);
		doubleImportFileChangeEnglishBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
		doubleImportFileChangeEnglishBtn.setIcon(changePathImage);
		doubleImportFileChangeEnglishBtn.addMouseListener(new MouseAdapter() {
			
			@Override
			public void mouseEntered(MouseEvent e) {
				doubleImportFileChangeEnglishBtn.setIcon(changePathImahe_Pressed);
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				doubleImportFileChangeEnglishBtn.setIcon(changePathImage);
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				int result = fileSelecterTxt.showOpenDialog(null);
				
				if(result == JFileChooser.APPROVE_OPTION) {
					fileManagement.doubleImportFileEnglish = fileSelecterTxt.getSelectedFile();
					doubleImportFileEnglishPathArea.setText("단어 파일 경로 : " + fileManagement.doubleImportFileEnglish.getAbsolutePath());
				}
			}
			
		});
		fileIOPanel.add(doubleImportFileChangeEnglishBtn);
		
		JButton existExcelFileChangeBtn = new JButton();
		existExcelFileChangeBtn.setBounds(484, 520, 100, 30);
		existExcelFileChangeBtn.setBorderPainted(false);
		existExcelFileChangeBtn.setContentAreaFilled(false);
		existExcelFileChangeBtn.setFocusPainted(false);
		existExcelFileChangeBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
		existExcelFileChangeBtn.setIcon(changePathImage);
		existExcelFileChangeBtn.addMouseListener(new MouseAdapter() {
			
			@Override
			public void mouseEntered(MouseEvent e) {
				existExcelFileChangeBtn.setIcon(changePathImahe_Pressed);
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				existExcelFileChangeBtn.setIcon(changePathImage);
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				int result = fileSelecterXlsx.showOpenDialog(null);
				
				if(result == JFileChooser.APPROVE_OPTION) {
					fileManagement.existExcelFile = fileSelecterXlsx.getSelectedFile();
					existExcelFilePathArea.setText("기존 엑셀 파일 경로 : " + fileManagement.existExcelFile.getAbsolutePath());
				}
			}
			
		});
		fileIOPanel.add(existExcelFileChangeBtn);
		
		JButton exportFileChangeBtn = new JButton();
		exportFileChangeBtn.setBounds(484, 620, 100, 30);
		exportFileChangeBtn.setBorderPainted(false);
		exportFileChangeBtn.setContentAreaFilled(false);
		exportFileChangeBtn.setFocusPainted(false);
		exportFileChangeBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
		exportFileChangeBtn.setIcon(changePathImage);
		exportFileChangeBtn.addMouseListener(new MouseAdapter() {
			
			@Override
			public void mouseEntered(MouseEvent e) {
				exportFileChangeBtn.setIcon(changePathImahe_Pressed);
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				exportFileChangeBtn.setIcon(changePathImage);
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				int result = fileSelecterXlsx.showOpenDialog(null);
				
				if(result == JFileChooser.APPROVE_OPTION) {
					fileManagement.exportFile = fileSelecterXlsx.getSelectedFile();
					exportFilePathArea.setText("생성 파일 생성 경로 : " + fileManagement.exportFile.getAbsolutePath());
				}
			}
			
		});
		fileIOPanel.add(exportFileChangeBtn);
		
		JButton savingBtn = new JButton();
		savingBtn.setBounds(0, 261, 584, 200);
		savingBtn.setBorderPainted(false);
		savingBtn.setContentAreaFilled(false);
		savingBtn.setFocusPainted(false);
		savingBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
		savingBtn.setIcon(savingImage);
		savingBtn.addMouseListener(new MouseAdapter() {
			
			@Override
			public void mouseEntered(MouseEvent e) {
				savingBtn.setIcon(savingImage_Pressed);
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				savingBtn.setIcon(savingImage);
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				fileManagement.savePath();
				optionManagement.saveOption();
			}
			
		});
		mainPanel.add(savingBtn);
	}
	
	private void labelSetting() {
		JLabel generateOptionLabel = new JLabel("생성 옵션 설정");
		generateOptionLabel.setHorizontalAlignment(SwingConstants.CENTER);
		generateOptionLabel.setFont(cheakFont);
		generateOptionLabel.setBounds(0, 0, 584, 50);
		mainPanel.add(generateOptionLabel);
		
		JLabel excelFileGenerateOptionLabel = new JLabel("엑셀 파일 옵션");
		excelFileGenerateOptionLabel.setHorizontalAlignment(SwingConstants.CENTER);
		excelFileGenerateOptionLabel.setFont(cheakFont);
		excelFileGenerateOptionLabel.setBounds(0, 0, 584, 50);
		excelFileSettingPanel.add(excelFileGenerateOptionLabel);
		
		JLabel blankSizeColumnLabel = new JLabel("열 : ");
		blankSizeColumnLabel.setFont(cheakFont);
		blankSizeColumnLabel.setBounds(0, 200, 70, 50);
		excelFileSettingPanel.add(blankSizeColumnLabel);
		
		JLabel blankSizeRowLabel = new JLabel("행 : ");
		blankSizeRowLabel.setFont(cheakFont);
		blankSizeRowLabel.setBounds(0, 250, 70, 50);
		excelFileSettingPanel.add(blankSizeRowLabel);
		
		JLabel pageDivisionNumberLabel = new JLabel("칸 수 : ");
		pageDivisionNumberLabel.setFont(cheakFont);
		pageDivisionNumberLabel.setBounds(0, 350, 110, 50);
		excelFileSettingPanel.add(pageDivisionNumberLabel);
		
		JLabel pageDivisionFirstNumberLabel = new JLabel("첫 칸 수 : ");
		pageDivisionFirstNumberLabel.setFont(cheakFont);
		pageDivisionFirstNumberLabel.setBounds(0, 450, 150, 50);
		excelFileSettingPanel.add(pageDivisionFirstNumberLabel);
		
		JLabel pageNumberOfBlankLabel = new JLabel("띌 칸 수 : ");
		pageNumberOfBlankLabel.setFont(cheakFont);
		pageNumberOfBlankLabel.setBounds(0, 500, 150, 50);
		excelFileSettingPanel.add(pageNumberOfBlankLabel);
		
		JLabel numberOfColumnLabel = new JLabel("열 수 : ");
		numberOfColumnLabel.setFont(cheakFont);
		numberOfColumnLabel.setBounds(0, 550, 110, 50);
		excelFileSettingPanel.add(numberOfColumnLabel);
		
		JLabel fileIOSettingPanelLabel = new JLabel("File IO 설정");
		fileIOSettingPanelLabel.setHorizontalAlignment(SwingConstants.CENTER);
		fileIOSettingPanelLabel.setFont(cheakFont);
		fileIOSettingPanelLabel.setBounds(0, 0, 584, 50);
		fileIOPanel.add(fileIOSettingPanelLabel);
		
		JLabel numberOfWordLabel = new JLabel("단어 수 : ");
		numberOfWordLabel.setFont(cheakFont);
		numberOfWordLabel.setBounds(0, 50, 140, 50);
		mainPanel.add(numberOfWordLabel);
		
	}
	
	private void textEditerSetting() {
		
		singleImportFilePathArea = new JTextArea("통합 파일 경로 : " + fileManagement.singleImportFile.getAbsolutePath());
		singleImportFilePathArea.setLineWrap(true);
		singleImportFilePathArea.setWrapStyleWord(true);
		singleImportFilePathArea.setEditable(false);
		singleImportFilePathArea.setFocusable(false);
		singleImportFilePathArea.setOpaque(false);
		singleImportFilePathArea.setBorder(null);
		singleImportFilePathArea.setBounds(0, 100, 584, 100);
		singleImportFilePathArea.setFont(textAreaFont);
		fileIOPanel.add(singleImportFilePathArea);
		
		doubleImportFileMeanPathArea = new JTextArea("뜻 파일 경로 : " + fileManagement.doubleImportFileMean.getAbsolutePath());
		doubleImportFileMeanPathArea.setLineWrap(true);
		doubleImportFileMeanPathArea.setWrapStyleWord(true);
		doubleImportFileMeanPathArea.setEditable(false);
		doubleImportFileMeanPathArea.setFocusable(false);
		doubleImportFileMeanPathArea.setOpaque(false);
		doubleImportFileMeanPathArea.setBorder(null);
		doubleImportFileMeanPathArea.setBounds(0, 250, 584, 100);
		doubleImportFileMeanPathArea.setFont(textAreaFont);
		fileIOPanel.add(doubleImportFileMeanPathArea);
		
		doubleImportFileEnglishPathArea = new JTextArea("단어 파일 경로 : " + fileManagement.doubleImportFileEnglish.getAbsolutePath());
		doubleImportFileEnglishPathArea.setLineWrap(true);
		doubleImportFileEnglishPathArea.setWrapStyleWord(true);
		doubleImportFileEnglishPathArea.setEditable(false);
		doubleImportFileEnglishPathArea.setFocusable(false);
		doubleImportFileEnglishPathArea.setOpaque(false);
		doubleImportFileEnglishPathArea.setBorder(null);
		doubleImportFileEnglishPathArea.setBounds(0, 350, 584, 100);
		doubleImportFileEnglishPathArea.setFont(textAreaFont);
		fileIOPanel.add(doubleImportFileEnglishPathArea);
		
		existExcelFilePathArea = new JTextArea("기존 엑셀 파일 경로 : " + fileManagement.existExcelFile.getAbsolutePath());
		existExcelFilePathArea.setLineWrap(true);
		existExcelFilePathArea.setWrapStyleWord(true);
		existExcelFilePathArea.setEditable(false);
		existExcelFilePathArea.setFocusable(false);
		existExcelFilePathArea.setOpaque(false);
		existExcelFilePathArea.setBorder(null);
		existExcelFilePathArea.setBounds(0, 450, 584, 100);
		existExcelFilePathArea.setFont(textAreaFont);
		fileIOPanel.add(existExcelFilePathArea);
		
		exportFilePathArea = new JTextArea("생성 파일 생성 경로 : " + fileManagement.exportFile.getAbsolutePath());
		exportFilePathArea.setLineWrap(true);
		exportFilePathArea.setWrapStyleWord(true);
		exportFilePathArea.setEditable(false);
		exportFilePathArea.setFocusable(false);
		exportFilePathArea.setOpaque(false);
		exportFilePathArea.setBorder(null);
		exportFilePathArea.setBounds(0, 550, 584, 100);
		exportFilePathArea.setFont(textAreaFont);
		fileIOPanel.add(exportFilePathArea);
		
	}
	
	private void cheakBoxSetting() {
		
		generateOptionMean = new JCheckBox("뜻 시험 만들기");
		generateOptionMean.setFont(cheakFont);
		generateOptionMean.setBounds(0, 101, 584, 50);
		generateOptionMean.setBorderPainted(false);
		generateOptionMean.setContentAreaFilled(false);
		generateOptionMean.setFocusPainted(false);
		generateOptionMean.addMouseListener(new MouseAdapter() {
			
			@Override
			public void mousePressed(MouseEvent e) {
				generateOptionEnglish.setSelected(false);
				generateOptionMix.setSelected(false);
			}
			
		});
		mainPanel.add(generateOptionMean);
		
		generateOptionEnglish = new JCheckBox("단어 시험 만들기");
		generateOptionEnglish.setFont(cheakFont);
		generateOptionEnglish.setBounds(0, 151, 584, 50);
		generateOptionEnglish.setBorderPainted(false);
		generateOptionEnglish.setContentAreaFilled(false);
		generateOptionEnglish.setFocusPainted(false);
		generateOptionEnglish.addMouseListener(new MouseAdapter() {
			
			@Override
			public void mousePressed(MouseEvent e) {
				generateOptionMean.setSelected(false);
				generateOptionMix.setSelected(false);
			}
			
		});
		mainPanel.add(generateOptionEnglish);
		
		generateOptionMix = new JCheckBox("뜻, 단어 섞어서 만들기");
		generateOptionMix.setFont(cheakFont);
		generateOptionMix.setBounds(0, 201, 584, 50);
		generateOptionMix.setBorderPainted(false);
		generateOptionMix.setContentAreaFilled(false);
		generateOptionMix.setFocusPainted(false);
		generateOptionMix.addMouseListener(new MouseAdapter() {
			
			@Override
			public void mousePressed(MouseEvent e) {
				generateOptionMean.setSelected(false);
				generateOptionEnglish.setSelected(false);
			}
			
		});
		mainPanel.add(generateOptionMix);
		
		generateOptionNew = new JCheckBox("새로운 EXCEL 파일 만들기");
		generateOptionNew.setFont(new Font("굴림", Font.BOLD, 30));
		generateOptionNew.setFocusPainted(false);
		generateOptionNew.setContentAreaFilled(false);
		generateOptionNew.setBorderPainted(false);
		generateOptionNew.setBounds(0, 50, 584, 50);
		generateOptionNew.addMouseListener(new MouseAdapter() {
			
			@Override
			public void mousePressed(MouseEvent e) {
				generateOptionExist.setSelected(false);
			}
			
		});
		excelFileSettingPanel.add(generateOptionNew);
		
		generateOptionExist = new JCheckBox("기존 EXCEL 파일 수정");
		generateOptionExist.setFont(new Font("굴림", Font.BOLD, 30));
		generateOptionExist.setFocusPainted(false);
		generateOptionExist.setContentAreaFilled(false);
		generateOptionExist.setBorderPainted(false);
		generateOptionExist.setBounds(0, 100, 584, 50);
		generateOptionExist.addMouseListener(new MouseAdapter() {
			
			@Override
			public void mousePressed(MouseEvent e) {
				generateOptionNew.setSelected(false);
			}
			
		});
		excelFileSettingPanel.add(generateOptionExist);
		
		generateOptionBlank = new JCheckBox("여백 생성");
		generateOptionBlank.setFont(cheakFont);
		generateOptionBlank.setFocusPainted(false);
		generateOptionBlank.setContentAreaFilled(false);
		generateOptionBlank.setBorderPainted(false);
		generateOptionBlank.setBounds(0, 150, 584, 50);
		excelFileSettingPanel.add(generateOptionBlank);
		
		pageDivision = new JCheckBox("페이지 구분");
		pageDivision.setFont(cheakFont);
		pageDivision.setFocusPainted(false);
		pageDivision.setContentAreaFilled(false);
		pageDivision.setBorderPainted(false);
		pageDivision.setBounds(0, 300, 584, 50);
		excelFileSettingPanel.add(pageDivision);
		
		pageDivisionFirstDifferent = new JCheckBox("첫 페이지 구분 크기 변경");
		pageDivisionFirstDifferent.setFont(cheakFont);
		pageDivisionFirstDifferent.setFocusPainted(false);
		pageDivisionFirstDifferent.setContentAreaFilled(false);
		pageDivisionFirstDifferent.setBorderPainted(false);
		pageDivisionFirstDifferent.setBounds(0, 400, 584, 50);
		excelFileSettingPanel.add(pageDivisionFirstDifferent);
		
		singleFileProcess = new JCheckBox("한 개의 파일로 처리");
		singleFileProcess.setFont(cheakFont);
		singleFileProcess.setFocusPainted(false);
		singleFileProcess.setContentAreaFilled(false);
		singleFileProcess.setBorderPainted(false);
		singleFileProcess.setBounds(0, 50, 584, 50);
		singleFileProcess.addMouseListener(new MouseAdapter() {
			
			@Override
			public void mousePressed(MouseEvent e) {
				doubleFileProcess.setSelected(false);
			}
			
		});
		fileIOPanel.add(singleFileProcess);
		
		doubleFileProcess = new JCheckBox("두 개의 파일로 처리");
		doubleFileProcess.setFont(cheakFont);
		doubleFileProcess.setFocusPainted(false);
		doubleFileProcess.setContentAreaFilled(false);
		doubleFileProcess.setBorderPainted(false);
		doubleFileProcess.setBounds(0, 200, 584, 50);
		doubleFileProcess.addMouseListener(new MouseAdapter() {
			
			@Override
			public void mousePressed(MouseEvent e) {
				singleFileProcess.setSelected(false);
			}
			
		});
		fileIOPanel.add(doubleFileProcess);
	
		if(optionManagement.op.getProcessType().equals("D")) {
			doubleFileProcess.setSelected(true);
		} else {
			singleFileProcess.setSelected(true);
		}
		
		if(optionManagement.op.getTestType().equals("E")) {
			generateOptionEnglish.setSelected(true);
		} else if(optionManagement.op.getTestType().equals("X")) {
			generateOptionMix.setSelected(true);
		} else {
			generateOptionMean.setSelected(true);
		}
		
		if(optionManagement.op.getFileType().equals("E")) {
			generateOptionExist.setSelected(true);
		} else {
			generateOptionNew.setSelected(true);
		}
		
		generateOptionBlank.setSelected(optionManagement.op.getCreateBlank());
		pageDivision.setSelected(optionManagement.op.getPageDivision());
		pageDivisionFirstDifferent.setSelected(optionManagement.op.getPageDivision_FirstDifferent());
	}
	
	private void otherInstanceSetting() {
		
		//JSpinner
		blankSizeColumnSpinner = new JSpinner(new SpinnerNumberModel(optionManagement.op.getBlankRow(), 0, 1000, 1));
		blankSizeColumnSpinner.setBounds(70, 200, 514, 50);
		blankSizeColumnSpinner.setFont(cheakFont);
		excelFileSettingPanel.add(blankSizeColumnSpinner);
		
		blankSizeRowSpinner = new JSpinner(new SpinnerNumberModel(optionManagement.op.getBlankColumn(), 0, 1000, 1));
		blankSizeRowSpinner.setBounds(70, 250, 514, 50);
		blankSizeRowSpinner.setFont(cheakFont);
		excelFileSettingPanel.add(blankSizeRowSpinner);
		
		pageDivisionSizeSpinner = new JSpinner(new SpinnerNumberModel(optionManagement.op.getPageDivisionSize(), 0, 1000, 10));
		pageDivisionSizeSpinner.setBounds(110, 350, 474, 50);
		pageDivisionSizeSpinner.setFont(cheakFont);
		excelFileSettingPanel.add(pageDivisionSizeSpinner);
		
		pageDivisionFirstSizeSpinner = new JSpinner(new SpinnerNumberModel(optionManagement.op.getPageDivisionFirstSize(), 0, 1000, 1));
		pageDivisionFirstSizeSpinner.setBounds(150, 450, 434, 50);
		pageDivisionFirstSizeSpinner.setFont(cheakFont);
		excelFileSettingPanel.add(pageDivisionFirstSizeSpinner);
		
		pageBlankSizeSpinner = new JSpinner(new SpinnerNumberModel(optionManagement.op.getPageBlankSize(), 0, 1000, 1));
		pageBlankSizeSpinner.setBounds(150, 500, 434, 50);
		pageBlankSizeSpinner.setFont(cheakFont);
		excelFileSettingPanel.add(pageBlankSizeSpinner);
		
		numberOfColumn = new JSpinner(new SpinnerNumberModel(optionManagement.op.getNumberOfColumn(), 0, 3, 1));
		numberOfColumn.setBounds(110, 550, 474, 50);
		numberOfColumn.setFont(cheakFont);
		excelFileSettingPanel.add(numberOfColumn);
		
		numberOfWord = new JSpinner(new SpinnerNumberModel(optionManagement.op.getNumberOfWord(), 1, 10000, 10));
		numberOfWord.setBounds(140, 50, 444, 50);
		numberOfWord.setFont(cheakFont);
		mainPanel.add(numberOfWord);
		
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		fileSelecterTxt.setFileSelectionMode(JFileChooser.FILES_ONLY);
		fileSelecterTxt.setFileFilter(new FileNameExtensionFilter("텍스트 파일 (*.txt)", "txt"));
		fileSelecterTxt.setAcceptAllFileFilterUsed(false);
		
		fileSelecterXlsx.setFileSelectionMode(JFileChooser.FILES_ONLY);
		fileSelecterXlsx.setFileFilter(new FileNameExtensionFilter("엑셀 파일 (*.xlsx)", "xlsx"));
		fileSelecterXlsx.setAcceptAllFileFilterUsed(false);
		
		frameSetting();
		panelSetting();
		panelAdd();
		buttonSetting();
		labelSetting();
		textEditerSetting();
		cheakBoxSetting();
		otherInstanceSetting();
	}
}
