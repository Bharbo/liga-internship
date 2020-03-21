package ru.liga.song.logic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class Reader {

    private static final Logger logger =
            LoggerFactory.getLogger(Reader.class);

    //Возвращает значения trans и tempo в мапе
    public static HashMap<Integer, Float> readCommandLineArgs(String[] args) throws IOException {
        Integer trans = null;
        Float tempo = null;
        HashMap<Integer, Float> transAndTempo = new HashMap<>(1);
        if (args.length == 6 && args[1].equals("change")) {
            if (args[2].equals("-trans")) {
                try {
                    trans = Integer.parseInt(args[3]);
                } catch (Exception e) {
                    e.getStackTrace();
                }
            }
            if (args[4].equals("-tempo")) {
                try {
                    tempo = Float.parseFloat(args[5]);
                } catch (Exception e) {
                    e.getStackTrace();
                }
            }
            transAndTempo.put(trans, tempo);
        } else if (args.length == 2 && args[1].equals("analyze")) {
            Analyze.analyze(args[0]);
            return transAndTempo;
        }
        if (tempo == null || trans == null) {
            logger.info("Некорректно введены данные командной строки");
            return transAndTempo;
        }
        Change.change(args[0], trans, tempo);
        return transAndTempo;
    }

    //возврат нового пути с новым именем файла
    static String getSavePath(int trans, float tempo, File file) {
        logger.trace("Создание пути для нового файла");
        String newName = file.getName().replace(".mid", "") + "-trans" + trans + "-tempo" + (int)tempo + ".mid";
        logger.trace("Путь для нового файла создан");
        return file.getParentFile().getAbsolutePath() + File.separator + newName;
    }

    public static void wrongRequest() {
        logger.info("Некорректно введены данные командной строки");
    }

}
