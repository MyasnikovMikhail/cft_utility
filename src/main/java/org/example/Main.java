package org.example;

import java.util.Arrays;

import static org.example.cheks.ChecksArguments.checksArguments;

public class Main {
    public static void main(String[] args) {
        System.out.println("Введенные параметры будут переведены в нижний регистр\n");
        args = checksArguments(args);
        System.out.println(Arrays.toString(args));
    }
}