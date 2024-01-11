package org.example.cheks;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ChecksArguments {

    static List<String> tempArgs = new ArrayList<>();
    static boolean addPath = false;
    static boolean prefix = false;
    static boolean addToFile = false;
    static boolean isFullStatistics = false;
    static String addFilePath;
    static String prefixForFiles;

    public static String[] checksArguments(String[] args) {
        //args = filterParameters(Arrays.stream(args).map(String::toLowerCase).toList()).toArray(new String[0]);
        for(String arg : args) {
            switch (arg) {
                case ("-o"):
                    addPath = true;
                    break;
                case ("-p"):
                    prefix = true;
                    break;
                case ("-a"):
                    addToFile = true;
                case("-f"):
                    isFullStatistics = true;
                    break;
            }
        }
        checksParameters(args);
        args = checksParametersFiles();
        return args;
    }

    private static void checksParameters(String[] args) {
        tempArgs.addAll(Arrays.stream(args).distinct().toList());
        String pathProject = System.getProperty("user.dir");
        if (addPath) {
            int indexPath = tempArgs.indexOf("-o");
            if(!args[indexPath + 1].startsWith("-") && (new File(pathProject + args[indexPath + 1]).exists() || new File(args[indexPath + 1]).exists())) {
                addFilePath = args[indexPath + 1];
            } else {
                addPath = false;
            }
        }

        if (prefix) {
            int indexPrefix = tempArgs.indexOf("-p");
            prefixForFiles = args[indexPrefix + 1];
        }
    }

    public static String[] checksParametersFiles() {
        List<String> filesArgs = new ArrayList<>();
        for (String arg : tempArgs) {
            if(arg.endsWith(".txt")) {
                if(new File(arg).exists()) {
                    filesArgs.add(arg);
                }
            }
        }
        return filesArgs.toArray(new String[0]);
    }
}
