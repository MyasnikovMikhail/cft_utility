package org.example;

import java.io.*;
import java.util.regex.Pattern;

import static org.example.cheks.ChecksArguments.*;

public class SortingByType {
    private static final Pattern patternInt = Pattern.compile("\\d+");
    private static final Pattern patternFloat = Pattern.compile("^[-+]?[0-9]*[.,]\\d*(?:[eE][-+]?[0-9]+)?$");
    static boolean createFileInt = false;
    static boolean createFileFloat = false;
    static boolean createFileStr = false;
    static FileWriter writerFloat = null;
    static FileWriter writerInt = null;
    static FileWriter writerStr = null;

    static long numElementsInt = 0;
    static long numElementsStr = 0;
    static long numElementsFloat = 0;

    static long maxInt = 0;
    static long minInt = 0;
    static long sumInt = 0;
    static float maxFloat = 0;
    static float minFloat = 0;
    static float sumFloat = 0;
    static long lenMaxStr = 0;
    static long lenMinStr = 0;


    public static void sortingByType(String[] args) throws IOException {

        if (args == null) {
            System.out.println("Файлы для обработки не найдены. Повторите попытку изменив входные данные");
            System.exit(0);
        } else if (args.length == 1) {
            try (
                    FileReader fileReader1 = new FileReader(args[0]);
                    BufferedReader br1 = new BufferedReader(fileReader1)
            ) {
                String line1 = checkLine(br1);
                while (line1 != null) {
                    writeData(line1);
                    line1 = checkLine(br1);
                }
                closeWriters();

            } catch (IOException e) {
                closeWriters();
                System.out.println("Проблема с файлом!");
            }
        } else {
            for (int i = 0; i < args.length; i = i + 2) {
                try (
                        FileReader fileReader1 = new FileReader(args[i]);
                        FileReader fileReader2 = new FileReader(args[i + 1]);
                        BufferedReader br1 = new BufferedReader(fileReader1);
                        BufferedReader br2 = new BufferedReader(fileReader2)

                ) {
                    String line1 = checkLine(br1);
                    String line2 = checkLine(br2);
                    while (line1 != null || line2 != null) {
                        if (line1 == null) {
                            writeData(line2);
                            line2 = checkLine(br2);
                        } else if (line2 == null) {
                            writeData(line1);
                            line1 = checkLine(br1);
                        } else {
                            writeData(line1);
                            line1 = checkLine(br1);
                            writeData(line2);
                            line2 = checkLine(br2);
                        }
                    }
                    closeWriters();
                } catch (IOException e) {
                    closeWriters();
                }
            }
        }
        if(!isFullStatistics) {
            System.out.println("Элементов integer записано: " + numElementsInt);
            System.out.println("Элементов float записано: " + numElementsFloat);
            System.out.println("Элементов string записано: " + numElementsStr);
        } else {
            System.out.println("Элементов integer записано: " + numElementsInt);
            System.out.println("Максимальное число: " + maxInt);
            System.out.println("Минимальное число: " + minInt);
            System.out.println("Сумма чисел: " + sumInt);
            System.out.println("Среднее число: " + (float)sumInt/numElementsInt + "\n");

            System.out.println("Элементов float записано: " + numElementsFloat);
            System.out.println("Максимальное число: " + maxFloat);
            System.out.println("Минимальное число: " + minFloat);
            System.out.println("Сумма чисел: " + sumFloat);
            System.out.println("Среднее число: " + sumFloat/numElementsFloat + "\n");

            System.out.println("Элементов string записано: " + numElementsStr);
            System.out.println("Максимальная длина строки: " + lenMaxStr);
            System.out.println("Минимальная длина строки: " + lenMinStr);

        }
    }

    private static void closeWriters() throws IOException {
        try {
            if (!(writerStr == null)) {
                writerStr.flush();
                writerStr.close();
            }
            if (!(writerInt == null)) {
                writerInt.flush();
                writerInt.close();
            }
            if (!(writerFloat == null)) {
                writerFloat.flush();
                writerFloat.close();
            }
        }   catch (IOException e) {
            System.out.println("Невозможно закончить работу программы корректно");
            System.exit(0);
        }
    }

    private static void writeData(String line) throws IOException {
        if (patternInt.matcher(line).matches()) {
            if (!createFileInt) {
                createFileInt = true;
                writerInt = new FileWriter(filePath + (filePath.endsWith("\\") ? "" : "\\") + prefixForFiles + "integers.txt", addToFile);
            }
            numElementsInt++;
            if(isFullStatistics){
                if(numElementsInt == 1){
                    maxInt = Long.parseLong(line);
                    minInt = Long.parseLong(line);
                }
                if(Long.parseLong(line) > maxInt) {
                    maxInt = Long.parseLong(line);
                }
                if(Long.parseLong(line) < minInt){
                    minInt = Long.parseLong(line);
                }
                sumInt += Long.parseLong(line);

            }
            if(numElementsInt % 10 == 0){
                writerInt.flush();
            }
            writerInt.write(line + "\n");

        } else if (patternFloat.matcher(line).matches()) {
            if (!createFileFloat) {
                createFileFloat = true;
                writerFloat = new FileWriter(filePath + (filePath.endsWith("\\") ? "" : "\\") + prefixForFiles + "floats.txt", addToFile);
            }
            numElementsFloat++;
            line = line.replace(",",".");
            if(isFullStatistics){
                if(numElementsFloat == 1){
                    maxFloat = Float.parseFloat(line);
                    minFloat = Float.parseFloat(line);
                }
                if(Float.parseFloat(line) > maxFloat) {
                    maxFloat = Float.parseFloat(line);
                }
                if(Float.parseFloat(line) < minFloat){
                    minFloat = Float.parseFloat(line);
                }
                sumFloat += Float.parseFloat(line);

            }
            writerFloat.write(line + "\n");

        } else {
            if (!createFileStr) {
                createFileStr = true;
                writerStr = new FileWriter(filePath + (filePath.endsWith("\\") ? "" : "\\") + prefixForFiles + "strings.txt", addToFile);
            }
            numElementsStr++;
            if(isFullStatistics){
                if(numElementsStr == 1){
                    lenMaxStr = line.length();
                    lenMinStr = line.length();
                }
                if(line.length() > lenMaxStr) {
                    lenMaxStr = line.length();
                }
                if(line.length() < lenMinStr){
                    lenMinStr = line.length();
                }

            }
            writerStr.write(line + "\n");
        }
    }

    private static String checkLine(BufferedReader br) throws IOException {
        try {
            return br.readLine();
        } catch (IOException e) {
            closeWriters();
        }
        return null;
    }
}
