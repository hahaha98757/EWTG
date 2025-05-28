package kr.hahaha98757.ewtg.panels;

import kr.hahaha98757.ewtg.MainFrame;
import kr.hahaha98757.ewtg.data.Setting;
import kr.hahaha98757.ewtg.data.CustomJCheckBox;
import kr.hahaha98757.ewtg.utils.Tools;
import kr.hahaha98757.ewtg.utils.AbstractJPanel;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

public class FileIOPanel extends AbstractJPanel {
    private final JTextArea integratedFilePath = createJTextArea("통합 파일 경로: " + Setting.getInstance().integratedFilePath, 100);
    private final JTextArea meansFilePath = createJTextArea("뜻 파일 경로: " + Setting.getInstance().meansFilePath, 250);
    private final JTextArea wordsFilePath = createJTextArea("단어 파일 경로: " + Setting.getInstance().wordsFilePath, 350);
    private final JTextArea existedExcelPath = createJTextArea("기존 엑셀 파일 경로: " + Setting.getInstance().existedExcelPath, 450);
    private final JTextArea outputExcelPath = createJTextArea("생성 엑셀 파일 경로: " + Setting.getInstance().outputExcelPath, 550);

    private final CustomJCheckBox oneFile = createJCheckBox("한 개의 파일로 처리", 0, 50, 584, 50, !Setting.getInstance().twoFiles);
    private final CustomJCheckBox twoFiles = createJCheckBox("두 개의 파일로 처리", 0, 200, 584, 50, Setting.getInstance().twoFiles);

    private final JFileChooser txtFileChooser = new JFileChooser();
    private final JFileChooser xlsxFileChooser = new JFileChooser();

    public FileIOPanel() {
        txtFileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        txtFileChooser.setAcceptAllFileFilterUsed(false);
        txtFileChooser.addChoosableFileFilter(new FileNameExtensionFilter("텍스트 파일 (*.txt)", "txt"));

        xlsxFileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        xlsxFileChooser.setAcceptAllFileFilterUsed(false);
        xlsxFileChooser.addChoosableFileFilter(new FileNameExtensionFilter("엑셀 파일 (*.xlsx)", "xlsx"));

        setBounds(0, 0, 584, 861);
        setLayout(null);
        setVisible(false);

        add(createJButton(484, 170, 100, 30, Tools.changePathImage, Tools.changePathImagePressed, event -> {
            if (txtFileChooser.showOpenDialog(null) != JFileChooser.APPROVE_OPTION) return;
            var path = txtFileChooser.getSelectedFile().getAbsolutePath();
            Setting.getInstance().integratedFilePath = path;
            integratedFilePath.setText("통합 파일 경로: " + path);
        }));
        add(createJButton(484, 320, 100, 30, Tools.changePathImage, Tools.changePathImagePressed, event -> {
            if (txtFileChooser.showOpenDialog(null) != JFileChooser.APPROVE_OPTION) return;
            var path = txtFileChooser.getSelectedFile().getAbsolutePath();
            Setting.getInstance().meansFilePath = path;
            meansFilePath.setText("뜻 파일 경로: " + path);
        }));
        add(createJButton(484, 420, 100, 30, Tools.changePathImage, Tools.changePathImagePressed, event -> {
            if (txtFileChooser.showOpenDialog(null) != JFileChooser.APPROVE_OPTION) return;
            var path = txtFileChooser.getSelectedFile().getAbsolutePath();
            Setting.getInstance().wordsFilePath = path;
            wordsFilePath.setText("단어 파일 경로: " + path);
        }));
        add(createJButton(484, 520, 100, 30, Tools.changePathImage, Tools.changePathImagePressed, event -> {
            if (xlsxFileChooser.showOpenDialog(null) != JFileChooser.APPROVE_OPTION) return;
            var path = xlsxFileChooser.getSelectedFile().getAbsolutePath();
            Setting.getInstance().existedExcelPath = path;
            existedExcelPath.setText("기존 엑셀 파일 경로: " + path);
        }));
        add(createJButton(484, 620, 100, 30, Tools.changePathImage, Tools.changePathImagePressed, event -> {
            if (xlsxFileChooser.showOpenDialog(null) != JFileChooser.APPROVE_OPTION) return;
            var path = xlsxFileChooser.getSelectedFile().getAbsolutePath();
            Setting.getInstance().outputExcelPath = path;
            outputExcelPath.setText("생성 엑셀 파일 경로: " + path);
        }));
        add(createJButton(0, 661, 584, 200, Tools.settingCompleteImage, Tools.settingCompleteImagePressed, event -> {
            Setting.saveSetting();
            MainFrame.getInstance().setPanel(1);
        }));

        add(createJLabel("File IO 설정", 0, 0, 584, 50, true));

        oneFile.addUserItemListener(isSelected -> {
            Setting.getInstance().twoFiles = !isSelected;
            twoFiles.setSelected(!isSelected);
        });
        twoFiles.addUserItemListener(isSelected -> {
            Setting.getInstance().twoFiles = isSelected;
            oneFile.setSelected(!isSelected);
        });

        add(integratedFilePath);
        add(meansFilePath);
        add(wordsFilePath);
        add(existedExcelPath);
        add(outputExcelPath);

        add(oneFile);
        add(twoFiles);
    }

    private JTextArea createJTextArea(String text, int y) {
        return createJTextArea(text, 0, y, 584, 100);
    }
}