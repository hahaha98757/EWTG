package kr.hahaha98757.ewtg;

import kr.hahaha98757.ewtg.panels.ExcelFileSettingPanel;
import kr.hahaha98757.ewtg.panels.FileIOPanel;
import kr.hahaha98757.ewtg.panels.MainPanel;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    public final JPanel mainPanel = new MainPanel();
    public final JPanel fileIOPanel = new FileIOPanel();
    public final JPanel excelFileSettingPanel = new ExcelFileSettingPanel();

    private static MainFrame instance;

    public static void main(String[] args) {
        Setting.loadSetting();
        EventQueue.invokeLater(MainFrame::new);
    }

    @NotNull
    public static MainFrame getInstance() {
        if (instance == null) throw new RuntimeException("instance is null.");
        return instance;
    }

    public MainFrame() {
        instance = this;

        setSize(600, 900);
        setLocationRelativeTo(null);
        setResizable(false);
        setTitle("EWTG v2.0-remake - English Word Test Generator");
        getContentPane().setLayout(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        getContentPane().add(mainPanel);
        getContentPane().add(fileIOPanel);
        getContentPane().add(excelFileSettingPanel);

        setVisible(true);
    }

    public void setPanel(int i) {
        switch (i) {
            case 1:
                mainPanel.setVisible(true);
                fileIOPanel.setVisible(false);
                excelFileSettingPanel.setVisible(false);
                break;
            case 2:
                mainPanel.setVisible(false);
                fileIOPanel.setVisible(true);
                excelFileSettingPanel.setVisible(false);
                break;
            case 3:
                mainPanel.setVisible(false);
                fileIOPanel.setVisible(false);
                excelFileSettingPanel.setVisible(true);
                break;
        }
    }
}
