package org.example;

import java.io.IOException;
import java.util.Arrays;

import static org.example.SortingByType.sortingByType;
import static org.example.cheks.ChecksArguments.checksArguments;

public class Main {
    public static void main(String[] args) throws IOException {
        //System.out.println("Введенные параметры будут переведены в нижний регистр\n");
        args = checksArguments(args);
        sortingByType(args);
    }
}