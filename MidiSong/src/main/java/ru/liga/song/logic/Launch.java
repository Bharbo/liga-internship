package ru.liga.song.logic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Launch {

    private static final Logger logger =
            LoggerFactory.getLogger(Launch.class);

    static String errorCommands = "Used the incorrect command to run. Try again." +
            " For example: java -jar appname.jar \"Path to the melody\" change -trans 2 -tempo 20";

    public static void launchApp(String[] args) throws IOException {
        try {
            if (args[1].equals("change") && args[2].equals("-trans") && args[4].equals("-tempo")) {
                Change.change(args[0], Integer.parseInt(args[3]), Float.parseFloat(args[5]));
            } else if (args[1].equals("analyze")) {
                Analyze.analyze(args[0]);
            } else {
                logger.info(errorCommands);
            }
        } catch (IndexOutOfBoundsException | NumberFormatException e) {
            e.printStackTrace();
            logger.info(errorCommands);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    //возврат нового пути с новым именем файла
    static String getSavePath(int trans, float tempo, File file) {
        logger.trace("Создание пути для нового файла");
        String newName = file.getName().replace(".mid", "") + "-trans" + trans + "-tempo" + (int) tempo + ".mid";
        logger.trace("Путь для нового файла создан");
        logger.trace("$$$$$$$$$$$$ Изменение мелодии завершено $$$$$$$$$$$$");
        return file.getParentFile().getAbsolutePath() + File.separator + newName;
    }
}
