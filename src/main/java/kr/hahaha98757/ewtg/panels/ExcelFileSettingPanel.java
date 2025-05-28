package kr.hahaha98757.ewtg.panels;

import kr.hahaha98757.ewtg.MainFrame;
import kr.hahaha98757.ewtg.data.Setting;
import kr.hahaha98757.ewtg.data.CustomJCheckBox;
import kr.hahaha98757.ewtg.utils.Tools;
import kr.hahaha98757.ewtg.utils.AbstractJPanel;

import javax.swing.*;

public class ExcelFileSettingPanel extends AbstractJPanel {
    private final CustomJCheckBox createExcel = createJCheckBox("새로운 엑셀 파일 만들기", 0, 50, 584, 50, !Setting.getInstance().modifyExcel);
    private final CustomJCheckBox modifyExcel = createJCheckBox("기존 엑셀 파일 수정", 0, 100, 584, 50, Setting.getInstance().modifyExcel);

    public ExcelFileSettingPanel() {
        setBounds(0, 0, 584, 861);
        setLayout(null);
        setVisible(false);

        add(createJButton(0, 660, 584, 200, Tools.settingCompleteImage, Tools.settingCompleteImagePressed, event -> {
            Setting.saveSetting();
            MainFrame.getInstance().setPanel(1);
        }));

        add(createJLabel("엑셀 파일 옵션", 0, 0, 584, 50, true));
        add(createJLabel("열: ", 200, 70));
        add(createJLabel("행: ", 250, 70));
        add(createJLabel("칸 수: ", 350, 110));
        add(createJLabel("첫 칸 수: ", 450, 150));
        add(createJLabel("띌 칸 수: ", 500, 150));
        add(createJLabel("행 수: ", 600, 110));

        createExcel.addUserItemListener(isSelected -> {
            Setting.getInstance().modifyExcel = !isSelected;
            modifyExcel.setSelected(!isSelected);
        });
        modifyExcel.addUserItemListener(isSelected -> {
            Setting.getInstance().modifyExcel = isSelected;
            createExcel.setSelected(!isSelected);
        });

        add(createExcel);
        add(modifyExcel);
        add(createJCheckBox("여백 생성", 0, 150, 584, 50, Setting.getInstance().createBlank, isSelected -> Setting.getInstance().createBlank = isSelected));
        add(createJCheckBox("페이지 구분", 0, 300, 584, 50, Setting.getInstance().pageDivision, isSelected -> Setting.getInstance().pageDivision = isSelected));
        add(createJCheckBox("첫 페이지 구분 크기 변경", 0, 400, 584, 50, Setting.getInstance().firstPageDivision, isSelected -> Setting.getInstance().firstPageDivision = isSelected));

        add(createJSpinner(70, 200, 514, 50, Setting.getInstance().blankColumn, 1, 1000, 1, value -> Setting.getInstance().blankColumn = value));
        add(createJSpinner(70, 250, 514, 50, Setting.getInstance().blankRow, 1, 1000, 1, value -> Setting.getInstance().blankRow = value));
        add(createJSpinner(110, 350, 474, 50, Setting.getInstance().pageDivisionSize, 1, 1000, 10, value -> Setting.getInstance().pageDivisionSize = value));
        add(createJSpinner(150, 450, 434, 50, Setting.getInstance().firstPageDivisionSize, 1, 1000, 1, value -> Setting.getInstance().firstPageDivisionSize = value));
        add(createJSpinner(150, 500, 434, 50, Setting.getInstance().pageBlankSize, 0, 1000, 1, value -> Setting.getInstance().pageBlankSize = value));
        add(createJSpinner(110, 600, 474, 50, Setting.getInstance().numberOfRow, 1, 3, 1, value -> Setting.getInstance().numberOfRow = value));
    }

    private JLabel createJLabel(String text, int y, int width) {
        return createJLabel(text, 0, y, width, 50, false);
    }
}