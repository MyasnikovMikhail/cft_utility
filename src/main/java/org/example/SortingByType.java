package org.example;

import java.io.*;
import java.util.regex.Pattern;

import static org.example.cheks.ChecksArguments.addFilePath;
import static org.example.cheks.ChecksArguments.prefixForFiles;

public class SortingByType {
    private static final Pattern patternInt = Pattern.compile("\\d+");
    private static final Pattern patternFloat = Pattern.compile("^[-+]?[0-9]*[.,]\\d*(?:[eE][-+]?[0-9]+)?$");
    static boolean createFileInt = false;
    static boolean createFileFloat = false;
    static boolean createFileStr = false;
    static FileWriter writerFloat = null;
    static FileWriter writerInt = null;
    static FileWriter writerStr = null;

    public static void sortingByType(String[] args) {
        for (int i = 0; i < args.length; i = i + 2) {
            try (
                    FileReader fileReader1 = new FileReader(args[i]);
                    FileReader fileReader2 = new FileReader(args[i + 1]);
                    BufferedReader br1 = new BufferedReader(fileReader1);
                    BufferedReader br2 = new BufferedReader(fileReader2)

            ) {
                System.out.println(br1 + "  " + br2);
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
                writerStr.close();
                writerInt.close();
                writerFloat.close();

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private static void writeData(String line) throws IOException {
        if (patternInt.matcher(line).matches()) {
            if (!createFileInt) {
                createFileInt = true;
                writerInt = new FileWriter(addFilePath == null?"":addFilePath /*+ prefixForFiles*/ + "integers.txt");
            }
            writerInt.write(line + "\n");

        } else if (patternFloat.matcher(line).matches()) {
            if (!createFileFloat) {
                createFileFloat = true;
                writerFloat = new FileWriter(addFilePath == null?"":addFilePath /*+ prefixForFiles*/ + "floats.txt");
            }
            writerFloat.write(line + "\n");

        } else {
            if (!createFileStr) {
                createFileStr = true;
                writerStr = new FileWriter(addFilePath == null?"":addFilePath /*+ prefixForFiles*/ + "strings.txt");
            }
            writerStr.write(line + "\n");
        }
    }

    private static String checkLine(BufferedReader br) {
        try {
                String line = br.readLine();
                if (line == null) return null;
                return line;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
