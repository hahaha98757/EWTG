package kr.hahaha98757.ewtg;

import kr.hahaha98757.ewtg.data.Setting;

import java.awt.*;

public class Main {
    public static void main(String[] args) {
        Setting.loadSetting();
        EventQueue.invokeLater(MainFrame::new);
    }
}