package org.example.cheks;

import java.io.File;
import java.io.FileWriter;
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
    static boolean addToFile = false;
    static boolean isFullStatistics = false;
    public static String addFilePath;
    public static String prefixForFiles;

    public static String[] checksArguments(String[] args) throws IOException {
        //args = filterParameters(Arrays.stream(args).map(String::toLowerCase).toList()).toArray(new String[0]);
        for(String arg : args) {
            switch (arg) {
                case ("-p"):
                    prefix = true;
                    break;
                case ("-o"):
                    addPath = true;
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

    private static void checksParameters(String[] args) throws IOException {
        tempArgs.addAll(Arrays.stream(args).distinct().toList());
        String pathProject = System.getProperty("user.dir");
        if (prefix) {
            int indexPrefix = tempArgs.indexOf("-p");
            prefixForFiles = args[indexPrefix + 1].replaceAll("[/.:* ?\"<>| \\\\]","");
            System.out.println("Проверка на ");
            if(prefixForFiles.length() > 247){
                prefixForFiles = prefixForFiles.substring(0, 243);
                System.out.println("Новый прификс: " + prefixForFiles);
//                new FileWriter(prefixForFiles + "integers.txt");
            }
        }




        if (addPath) {
            int indexPath = tempArgs.indexOf("-o");
            String path = args[indexPath + 1].contains("/") ? args[indexPath + 1].replace("/","\\"):args[indexPath + 1];
            path = path.replaceAll("[/:*?\"<>|]","");
            System.out.println(path);
            if(!args[indexPath + 1].startsWith("-") && ((new File(pathProject + path).mkdirs() || new File(path).mkdirs()))) {
                addFilePath = args[indexPath + 1];
            } else {
                addPath = false;
            }
        }
    }

    public static String[] checksParametersFiles() {
        List<String> filesArgs = new ArrayList<>();
        for (String arg : tempArgs) {
            if(arg.endsWith(".txt")) {
                Path path = Path.of(arg);
                if(Files.exists(path) && Files.isReadable(path)) {
                    filesArgs.add(arg);
                }
            }
        }
        return filesArgs.toArray(new String[0]);
    }
}
