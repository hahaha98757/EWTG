package kr.hahaha98757.ewtg.panels;

import kr.hahaha98757.ewtg.Excel;
import kr.hahaha98757.ewtg.MainFrame;
import kr.hahaha98757.ewtg.data.Setting;
import kr.hahaha98757.ewtg.data.CustomJCheckBox;
import kr.hahaha98757.ewtg.utils.Tools;
import kr.hahaha98757.ewtg.utils.AbstractJPanel;

public class MainPanel extends AbstractJPanel {
    private final CustomJCheckBox generateMean = createJCheckBox("뜻 시험 만들기", 0, 101, 584, 50, Setting.getInstance().testGenerateOption == 0);
    private final CustomJCheckBox generateEnglish = createJCheckBox("단어 시험 만들기", 0, 151, 584, 50, Setting.getInstance().testGenerateOption == 1);
    private final CustomJCheckBox generateMix = createJCheckBox("뜻, 단어 섞어서 만들기", 0, 201, 584, 50, Setting.getInstance().testGenerateOption == 2);

    public MainPanel() {
        setBounds(0, 0, 584, 861);
        setLayout(null);

        add(createJButton(0, 261, 584, 200, Tools.savingImage, Tools.savingImagePressed, event -> Setting.saveSetting()));
        add(createJButton(0, 461, 292, 200, Tools.fileIOSettingImage, Tools.fileIOSettingImagePressed, event -> MainFrame.getInstance().setPanel(2)));
        add(createJButton(292, 461, 292, 200, Tools.excelSettingImage, Tools.excelSettingImagePressed, event -> MainFrame.getInstance().setPanel(3)));
        add(createJButton(0, 661, 584, 200, Tools.generateImage, Tools.generateImagePressed, event -> Excel.create()));

        add(createJLabel("생성 옵션 설정", 0, 0, 584, 50, true));
        add(createJLabel("단어 수: ", 0, 50, 140, 50));

        generateMean.addUserItemListener(isSelected -> {
            if (isSelected) {
                Setting.getInstance().testGenerateOption = 0;
                generateEnglish.setSelected(false);
                generateMix.setSelected(false);
            } else generateMean.setSelected(true);
        });
        generateEnglish.addUserItemListener(isSelected -> {
            if (isSelected) {
                Setting.getInstance().testGenerateOption = 1;
                generateMean.setSelected(false);
                generateMix.setSelected(false);
            } else generateEnglish.setSelected(true);
        });
        generateMix.addUserItemListener(isSelected -> {
            if (isSelected) {
                Setting.getInstance().testGenerateOption = 2;
                generateMean.setSelected(false);
                generateEnglish.setSelected(false);
            } else generateMix.setSelected(true);
        });
        add(generateMean);
        add(generateEnglish);
        add(generateMix);

        add(createJSpinner(140, 50, 444, 50, Setting.getInstance().numberOfWord, 1, 10000, 10, value -> Setting.getInstance().numberOfWord = value));
    }
}