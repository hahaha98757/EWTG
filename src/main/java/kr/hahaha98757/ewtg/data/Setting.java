package kr.hahaha98757.ewtg.data;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import kr.hahaha98757.ewtg.utils.Tools;
import org.jetbrains.annotations.NotNull;

import java.io.*;

public class Setting {
    public int numberOfWord = 1;
    public int testGenerateOption;

    public boolean twoFiles;
    @NotNull
    public String integratedFilePath = System.getProperty("user.home") + "\\Desktop\\Integrated.txt";
    @NotNull
    public String meansFilePath = System.getProperty("user.home") + "\\Desktop\\Means.txt";
    @NotNull
    public String wordsFilePath = System.getProperty("user.home") + "\\Desktop\\Words.txt";
    @NotNull
    public String existedExcelPath = System.getProperty("user.home") + "\\Desktop\\Existed.xlsx";
    @NotNull
    public String outputExcelPath = System.getProperty("user.home") + "\\Desktop\\Output.xlsx";

    public boolean modifyExcel;
    public boolean createBlank;
    public int blankRow = 1;
    public int blankColumn = 1;
    public boolean pageDivision;
    public int pageDivisionSize = 1;
    public boolean firstPageDivision;
    public int firstPageDivisionSize = 1;
    public int pageBlankSize;
    public int numberOfRow = 1;

    private static Setting instance;

    private static final File json = new File("setting.json");

    @SuppressWarnings("ConstantValue")
    public static void loadSetting() {
        instance = new Setting();
        try {
            if (json.createNewFile()) {
                var jsonString = new GsonBuilder().setPrettyPrinting().create().toJson(instance);
                try (var it = new FileWriter(json)) {
                    it.write(jsonString);
                }
            }

            try (var it = new FileReader(json)) {
                instance = new Gson().fromJson(it, Setting.class);
            }
        } catch (IOException e) {
            Tools.printErr("설정을 불러오는데 실패했습니다.", e);
        }
        if (instance.integratedFilePath == null) instance.integratedFilePath = System.getProperty("user.home") + "\\Desktop\\Integrated.txt";
        if (instance.meansFilePath == null) instance.meansFilePath = System.getProperty("user.home") + "\\Desktop\\Means.txt";
        if (instance.wordsFilePath == null) instance.wordsFilePath = System.getProperty("user.home") + "\\Desktop\\Words.txt";
        if (instance.existedExcelPath == null) instance.existedExcelPath = System.getProperty("user.home") + "\\Desktop\\Existed.xlsx";
        if (instance.outputExcelPath == null) instance.outputExcelPath = System.getProperty("user.home") + "\\Desktop\\Output.xlsx";
        if (instance.numberOfRow < 1) instance.numberOfRow = 1;
        if (instance.blankRow < 1) instance.blankRow = 1;
        if (instance.blankColumn < 1) instance.blankColumn = 1;
        if (instance.pageDivisionSize < 1) instance.pageDivisionSize = 1;
        if (instance.firstPageDivisionSize < 1) instance.firstPageDivisionSize = 1;
        if (instance.numberOfRow < 1) instance.numberOfRow = 1;
    }

    public static void saveSetting() {
        try (var it = new FileWriter(json)) {
            var jsonString = new GsonBuilder().setPrettyPrinting().create().toJson(getInstance());
            it.write(jsonString);
        } catch (IOException e) {
            Tools.printErr("설정을 저장하는데 실패했습니다.", e);
        }
    }

    @NotNull
    public static Setting getInstance() {
        if (instance == null) loadSetting();
        return instance;
    }
}