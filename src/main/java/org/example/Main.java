package org.example;

import java.io.IOException;

import static org.example.SortingByType.sortingByType;
import static org.example.cheks.ChecksArguments.checksArguments;

public class Main {
    public static void main(String[] args) throws IOException {
        args = checksArguments(args);
        sortingByType(args);
    }
}