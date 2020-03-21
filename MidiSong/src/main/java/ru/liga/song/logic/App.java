package ru.liga.song.logic;

import java.io.IOException;

public class App {

    public static void main(String[] args) throws IOException {
        if (args.length > 1) {
            Reader.readCommandLineArgs(args);
        } else {
            Reader.wrongRequest();
        }
    }
}
