package org.example.cheks;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ChecksArguments {

    static List<String> tempArgs = new ArrayList<>();
    static boolean addPath = false;
    static boolean prefix = false;
    public static boolean addToFile = false;
    public static boolean isFullStatistics = false;
    public static String filePath;
    public static String prefixForFiles;

    public static String[] checksArguments(String[] args) throws IOException {
        for (String arg : args) {
            switch (arg) {
                case ("-p"):
                    prefix = true;
                    break;
                case ("-o"):
                    addPath = true;
                    break;
                case ("-a"):
                    addToFile = true;
                case ("-f"):
                    isFullStatistics = true;
                    break;
            }
        }
        checksParameters(args);
        args = checksParametersFiles();
        return args;
    }

    private static void checksParameters(String[] args){
        tempArgs.addAll(Arrays.stream(args).distinct().toList());
        String pathProject = System.getProperty("user.dir");
        if (prefix) {
            int indexPrefix = tempArgs.indexOf("-p");
            prefixForFiles = args[indexPrefix + 1].replaceAll("[/.:* ?\"<>| \\\\]", "");
            if (prefixForFiles.length() > 244) {
                prefixForFiles = prefixForFiles.substring(0, 243);
            }
        } else {
            prefixForFiles = "";
        }

        if (addPath) {
            int indexPath = tempArgs.indexOf("-o") + 1;
            String path = args[indexPath].contains("/") ? args[indexPath].replace("/", "\\") : args[indexPath];
            path = path.replaceAll("[/*?\"<>|]", "");
            if (!path.startsWith("\\") && (new File(path).mkdirs() || new File(path).isDirectory())) {
                filePath = path;
                System.out.println(path);
            } else {
                File file = new File(pathProject + (path.startsWith("\\") ? "" : "\\") + path.replaceAll(":",""));
                if (file.mkdirs() || file.isDirectory()) {
                    filePath = file.getPath();
                    System.out.println(file.getPath());
                } else {
                    System.out.println("Не удалось создать нужну дерикторию, файлы будут созданы в дириктори проекта" + pathProject);
                    filePath = pathProject;
                    System.out.println(file.getPath());
                }
            }

        } else {
            System.out.println("Файлы будут созданы в дириктори проекта: " + pathProject);
            filePath = pathProject;
        }
        System.out.println("Итоговый путь где будут храниться результаты: " + filePath);
    }

    public static String[] checksParametersFiles() {
        List<String> filesArgs = new ArrayList<>();
        for (String arg : tempArgs) {
            if(arg.endsWith(".txt")) {
                if(new File(arg).exists() && new File(arg).canRead()) {
                    filesArgs.add(arg);
                }
            }
        }
        return filesArgs.toArray(new String[0]);
    }
}
