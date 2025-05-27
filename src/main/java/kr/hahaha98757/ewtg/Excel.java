package kr.hahaha98757.ewtg;

import kr.hahaha98757.ewtg.utils.Tools;
import kr.hahaha98757.ewtg.data.Word;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Excel {
    private final List<Word> words = new ArrayList<>();

    public static void create() {
        try {
            var excel = new Excel();
            var success = false;
            if (Setting.getInstance().twoFiles) success =  excel.loadFromTwoFiles();
            else success = excel.loadFromIntegratedFile();

            if (success) excel.run();
        } catch (Exception e) {
            Tools.printErr("엑셀 파일을 생성하는데 실패했습니다.", e);
        }
    }

    private boolean loadFromIntegratedFile() {
        // 단어와 뜻을 읽어오는 부분
        var file = new File(Setting.getInstance().integratedFilePath);
        if (!file.exists()) {
            System.err.println("파일이 존재하지 않습니다.");
            return false;
        }
        if (!file.canRead()) {
            System.err.println("파일을 읽을 수 없습니다.");
            return false;
        }
        try (var it = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = it.readLine()) != null) {
                // 비어있으면 무시
                if (line.trim().isEmpty()) continue;

                // 단어와 뜻을 분리
                String english;
                String mean;
                try {
                    english = line.split("\\|")[0];
                    mean = line.split("\\|")[1];
                } catch (Exception e) {
                    System.err.println("잘못된 형식입니다: " + line);
                    System.err.println("해당 줄을 무시합니다.");
                    continue;
                }

                switch (Setting.getInstance().testGenerateOption) {
                    case 0 -> words.add(new Word(english, mean, true));
                    case 1 -> words.add(new Word(english, mean, false));
                    case 2 -> {
                        if (Math.random() < 0.5) words.add(new Word(line, mean, true));
                        else words.add(new Word(english, mean, false));
                    }
                    default -> {
                        System.err.println("잘못된 생성 옵션입니다: " + Setting.getInstance().testGenerateOption);
                        return false;
                    }
                }
            }

            if (words.isEmpty()) {
                System.err.println("파일이 비어 있습니다.");
                return false;
            }
        } catch (Exception e) {
            Tools.printErr("단어 파일 또는 뜻 파일을 읽는 중 오류가 발생했습니다.", e);
            return false;
        }
        return true;
    }

    private boolean loadFromTwoFiles() {
        // 단어와 뜻을 읽어오는 부분
        var wordsFile = new File(Setting.getInstance().wordsFilePath);
        var meansFile = new File(Setting.getInstance().meansFilePath);
        if (!wordsFile.exists() || !meansFile.exists()) {
            System.err.println("단어 파일 또는 뜻 파일이 존재하지 않습니다.");
            return false;
        }
        if (!wordsFile.canRead() || !meansFile.canRead()) {
            System.err.println("단어 파일 또는 뜻 파일을 읽을 수 없습니다.");
            return false;
        }
        try (var wordsReader = new BufferedReader(new FileReader(wordsFile));
             var meansReader = new BufferedReader(new FileReader(meansFile))) {

            String wordLine;
            String meansLine;

            while (true) {
                wordLine = wordsReader.readLine();
                meansLine = meansReader.readLine();

                if (wordLine == null || meansLine == null) {
                    if (wordLine != null || meansLine != null) {
                        System.err.println("단어 파일과 뜻 파일의 개수가 일치하지 않습니다.");
                        return false;
                    }
                    break; // 파일의 끝에 도달
                }

                // 단어와 뜻이 모두 비어있으면 무시
                if (wordLine.trim().isEmpty() && meansLine.trim().isEmpty()) continue;

                // 단어와 뜻이 하나라도 비어있으면 오류 메시지 출력 후 무시
                if (wordLine.trim().isEmpty() || meansLine.trim().isEmpty()) {
                    System.err.println("단어 또는 뜻이 비어있습니다. 단어: '" + wordLine + "', 뜻: '" + meansLine + "'");
                    System.err.println("해당 줄을 무시합니다.");
                    continue;
                }

                switch (Setting.getInstance().testGenerateOption) {
                    case 0 -> words.add(new Word(wordLine, meansLine, true));
                    case 1 -> words.add(new Word(wordLine, meansLine, false));
                    case 2 -> {
                        if (Math.random() < 0.5) words.add(new Word(wordLine, meansLine, true));
                        else words.add(new Word(wordLine, meansLine, false));
                    }
                    default -> {
                        System.err.println("잘못된 생성 옵션입니다: " + Setting.getInstance().testGenerateOption);
                        return false;
                    }
                }
            }

            if (words.isEmpty()) {
                System.err.println("단어 파일 또는 뜻 파일이 비어 있습니다.");
                return false;
            }
        } catch (Exception e) {
            Tools.printErr("단어 파일 또는 뜻 파일을 읽는 중 오류가 발생했습니다.", e);
            return false;
        }
        return true;
    }

    private void run() {
        // 단어 목록을 랜덤하게 섞고 설정된 개수만큼 선택
        Collections.shuffle(words);
        var temp = new ArrayList<>(words);
        words.clear();
        for (int i = 0; i < Setting.getInstance().numberOfWord && i < temp.size(); i++) words.add(temp.get(i));


        // 단어 목록을 2차원 배열로 변환
        var notProcessedWordArray = new Word[words.size()];
        for (var word : words) notProcessedWordArray[words.indexOf(word)] = word;

        var wordList = processRow(notProcessedWordArray);
        processFirstPageDivision(wordList);
        processPageDivision(wordList);
        processBlank(wordList);

        var testArray = toTestArray(wordList);
        var answerArray = toAnswerArray(wordList);

        // 엑셀 파일 생성
        if (Setting.getInstance().modifyExcel) modifyExcelFile(testArray, answerArray);
        else createExcelFile(Setting.getInstance().outputExcelPath, testArray, answerArray);
    }

    private ArrayList<ArrayList<Word>> processRow(@NotNull Word[] wordArray) {
        var row = Setting.getInstance().numberOfRow;
        var column = (int) Math.ceil((double) wordArray.length / row);
        var newArray = new Word[column][row];

        var index = 0;
        for (int i = 0; i < column; i++) for (int j = 0; j < row; j++)
            newArray[i][j] = index < wordArray.length ? wordArray[index++] : null;

        var list = new ArrayList<ArrayList<Word>>();
        for (var words : newArray) list.add(new ArrayList<>(Arrays.asList(words)));
        return list;
    }

    private void processFirstPageDivision(@NotNull ArrayList<ArrayList<Word>> wordList) {
        if (!Setting.getInstance().firstPageDivision) return;

        for (int i = 0; i < wordList.size(); i++) if (i == Setting.getInstance().firstPageDivisionSize) {
            for (int j = 0; j < Setting.getInstance().pageBlankSize; j++) wordList.add(i, null); // 빈 행 추가
            break;
        }
    }

    private void processPageDivision(@NotNull ArrayList<ArrayList<Word>> wordList) {
        if (!Setting.getInstance().pageDivision) return;

        var startIndex = 0;
        if (Setting.getInstance().firstPageDivision) for (int i = 0; i < wordList.size(); i++) if (wordList.get(i) == null) {
            startIndex = i + Setting.getInstance().pageBlankSize;
            break;
        }

        var wordIndex = startIndex;
        var size = wordList.size();
        for (int i = startIndex; i < size; i++) {
            if ((i - startIndex) % Setting.getInstance().pageDivisionSize == 0 && i != startIndex) {
                wordList.add(wordIndex, null); // 빈 행 추가
                wordList.add(wordIndex, null);
                wordIndex += 2; // 빈 행 추가 후 다음 단어 위치 조정
            }
            wordIndex++;
        }

        // 마지막에 빈 행이 추가되면 제거
        if (wordList.get(wordList.size() - 1) == null) {
            wordList.remove(wordList.size() - 1);
            wordList.remove(wordList.size() - 1);
        }
    }

    private void processBlank(@NotNull ArrayList<ArrayList<Word>> wordList) {
        if (!Setting.getInstance().createBlank) return;

        // 기존 데이터 앞에 빈 행과 빈 열을 추가
        for (int i = 0; i < Setting.getInstance().blankRow; i++) wordList.add(0, null); // 빈 행 추가
        for (var row : wordList) {
            if (row == null) continue; // 빈 행 건너뛰기
            for (int i = 0; i < Setting.getInstance().blankColumn; i++) row.add(0, null); // 빈 열 추가
        }
    }

    @NotNull
    private String[][] toTestArray(@NotNull ArrayList<ArrayList<Word>> wordList) {
        var tempList = new ArrayList<ArrayList<String>>();
        for (var words : wordList) {
            if (words == null) {
                tempList.add(null); // 빈 행 처리
                continue;
            }
            var newWords = new ArrayList<String>();
            for (var word : words)
                if (word == null) newWords.add(null); // 빈 셀 처리
                else {
                    var str = word.toString();
                    if (Setting.getInstance().testGenerateOption != 2) {
                        newWords.add(str.split(" ")[0]);
                        newWords.add(null);
                    } else if (word.englishFirst()) {
                        newWords.add(str.split(" ")[0]);
                        newWords.add(null);
                    } else {
                        newWords.add(null);
                        newWords.add(str.split(" ")[0]);
                    }
                }
            tempList.add(newWords);
        }

        var length = 0;
        for (var words : tempList) if (words != null) {
            length = words.size();
            break;
        }
        var strArray = new String[tempList.size()][length];
        for (int i = 0; i < tempList.size(); i++) {
            if (tempList.get(i) == null) {
                strArray[i] = null; // 빈 행 처리
            } else {
                strArray[i] = tempList.get(i).toArray(new String[length]); // ArrayList를 String 배열로 변환
            }
        }
        return strArray;
    }

    @NotNull
    private String[][] toAnswerArray(@NotNull ArrayList<ArrayList<Word>> wordList) {
        var tempList = new ArrayList<ArrayList<String>>();
        for (var words : wordList) {
            if (words == null) {
                tempList.add(null); // 빈 행 처리
                continue;
            }
            var newWords = new ArrayList<String>();
            for (var word : words)
                if (word == null) newWords.add(null); // 빈 셀 처리
                else {
                    var str = word.toString();
                    if (Setting.getInstance().testGenerateOption != 2) {
                        newWords.add(str.split(" ")[0]);
                        newWords.add(str.split(" ")[1]);
                    } else {
                        newWords.add(str.split(" ")[word.englishFirst() ? 0 : 1]); // 영어 단어
                        newWords.add(str.split(" ")[word.englishFirst() ? 1 : 0]); // 뜻
                    }
                }
            tempList.add(newWords);
        }

        var length = 0;
        for (var words : tempList) if (words != null) {
            length = words.size();
            break;
        }
        var strArray = new String[tempList.size()][length];
        for (int i = 0; i < tempList.size(); i++) {
            if (tempList.get(i) == null) {
                strArray[i] = null; // 빈 행 처리
            } else {
                strArray[i] = tempList.get(i).toArray(new String[length]); // ArrayList를 String 배열로 변환
            }
        }
        return strArray;
    }

    private void createExcelFile(@NotNull String path, @NotNull String[][] testArray, @NotNull String[][] answerArray) {
        try (var workbook = new XSSFWorkbook()) {
            var questionSheet = workbook.createSheet("문제");
            var answerSheet = workbook.createSheet("답지");

            writeSheet(questionSheet, testArray, false);
            writeSheet(answerSheet, answerArray, false);

            try (var it = new FileOutputStream(path)) {
                workbook.write(it);
            }
        } catch (Exception e) {
            Tools.printErr("엑셀 파일을 생성하는 중 오류가 발생했습니다.", e);
        }
    }

    private void modifyExcelFile(@NotNull String[][] testArray, @NotNull String[][] answerArray) {
        try (var fis = new FileInputStream(Setting.getInstance().existedExcelPath);
             var workbook = new XSSFWorkbook(fis)) {

            Sheet questionSheet;
            Sheet answerSheet;
            try {
                questionSheet = workbook.getSheetAt(0);
            } catch (Exception ignored) {
                questionSheet = workbook.createSheet("문제");
            }
            try {
                answerSheet = workbook.getSheetAt(1);
            } catch (Exception ignored) {
                answerSheet = workbook.createSheet("답지");
            }

            writeSheet(questionSheet, testArray, true);
            writeSheet(answerSheet, answerArray, true);

            try (var it = new FileOutputStream(Setting.getInstance().existedExcelPath)) {
                workbook.write(it);
            } catch (FileNotFoundException e) {
                System.err.println("파일이 존재하지 않습니다. 새로 생성합니다.");
                System.err.println("이 오류는 다른 이유로 발생할 수 있습니다. (파일이 열려있음, 권한 문제 등)");
                createExcelFile(Setting.getInstance().existedExcelPath, testArray, answerArray);
            } catch (Exception e) {
                Tools.printErr("엑셀 파일을 저장하는 중 오류가 발생했습니다.", e);
            }
        } catch (Exception e) {
            Tools.printErr("엑셀 파일을 수정하는 중 오류가 발생했습니다.", e);
        }
    }

    private void writeSheet(@NotNull Sheet sheet, @NotNull String[][] dataArray, boolean dataRetention) {
        for (int rowIndex = 0; rowIndex < dataArray.length; rowIndex++) {
            String[] rowData = dataArray[rowIndex];
            if (rowData == null) continue; // 빈 행

            Row row;
            if (dataRetention) {
                row = sheet.getRow(rowIndex);
                if (row == null) row = sheet.createRow(rowIndex); // 행이 없으면 새로 생성
            } else row = sheet.createRow(rowIndex);
            for (int columnIndex = 0; columnIndex < rowData.length; columnIndex++) {
                String cellValue = rowData[columnIndex];
                if (cellValue == null) continue; // 빈 셀

                Cell cell;
                if (dataRetention) {
                    cell = row.getCell(columnIndex);
                    if (cell == null || cell.getCellType() == CellType.BLANK)
                        cell = row.createCell(columnIndex); // 셀이 없으면 새로 생성
                } else cell = row.createCell(columnIndex);
                cell.setCellValue(cellValue);
            }
        }

    }
}