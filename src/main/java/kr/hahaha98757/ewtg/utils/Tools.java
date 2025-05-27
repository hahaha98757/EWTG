package kr.hahaha98757.ewtg.utils;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class Tools {
    public static final Font checkFont = new Font("굴림", Font.BOLD, 30);
    public static final Font textAreaFont = new Font("굴림", Font.BOLD, 25);

    public static final ImageIcon changePathImage = new ImageIcon(Objects.requireNonNull(Tools.class.getResource("/image/changePathImage.png")));
    public static final ImageIcon changePathImagePressed = new ImageIcon(Objects.requireNonNull(Tools.class.getResource("/image/changePathImage_Pressed.png")));
    public static final ImageIcon excelSettingImage = new ImageIcon(Objects.requireNonNull(Tools.class.getResource("/image/excelSettingImage.png")));
    public static final ImageIcon excelSettingImagePressed = new ImageIcon(Objects.requireNonNull(Tools.class.getResource("/image/excelSettingImage_Pressed.png")));
    public static final ImageIcon fileIOSettingImage = new ImageIcon(Objects.requireNonNull(Tools.class.getResource("/image/fileIOSettingImage.png")));
    public static final ImageIcon fileIOSettingImagePressed = new ImageIcon(Objects.requireNonNull(Tools.class.getResource("/image/fileIOSettingImage_Pressed.png")));
    public static final ImageIcon generateImage = new ImageIcon(Objects.requireNonNull(Tools.class.getResource("/image/generateImage.png")));
    public static final ImageIcon generateImagePressed = new ImageIcon(Objects.requireNonNull(Tools.class.getResource("/image/generateImage_Pressed.png")));
    public static final ImageIcon settingCompleteImage = new ImageIcon(Objects.requireNonNull(Tools.class.getResource("/image/settingCompleteImage.png")));
    public static final ImageIcon settingCompleteImagePressed = new ImageIcon(Objects.requireNonNull(Tools.class.getResource("/image/settingCompleteImage_Pressed.png")));
    public static final ImageIcon savingImage = new ImageIcon(Objects.requireNonNull(Tools.class.getResource("/image/savingImage.png")));
    public static final ImageIcon savingImagePressed = new ImageIcon(Objects.requireNonNull(Tools.class.getResource("/image/savingImage_Pressed.png")));

    public static void printErr(String text, Throwable e) {
        //noinspection CallToPrintStackTrace
        e.printStackTrace();
        System.err.println(text);
    }
}