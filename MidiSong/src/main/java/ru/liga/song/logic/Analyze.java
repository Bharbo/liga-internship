package ru.liga.song.logic;

import com.leff.midi.MidiFile;
import com.leff.midi.event.meta.Tempo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.liga.song.domain.Note;
import ru.liga.song.util.SongUtils;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class Analyze {

    private static final Logger logger =
            LoggerFactory.getLogger(Analyze.class);

    //Создание midi файла по пути к файлу
    public static void analyze(String path) throws IOException {
        logger.info("Подготовка к анализу ......");
        MidiFile midiFile = new MidiFile(new File(path));
        if (midiFile.getTracks().isEmpty()) {
            return;
        }
        loggingProcess(midiFile);
    }

    //логирование результатов
    public static List<Note> loggingProcess(MidiFile midiFile) {

        List<Note> voiceTrack = GetTracks.getVoiceTrack(midiFile);
        if (voiceTrack.isEmpty()) {
            logger.info("Дорожки с голосом не найдены");
            return voiceTrack;
        }
        int bounds = highNote(voiceTrack) - lowNote(voiceTrack);
        if (bounds == 0) {
            logger.info("Дорожка пуста");
        } else {
            logger.info("Диапазон: " + bounds + " полутона(ов)");
        }

        logger.info("Количество нот по длительностям:");
        for (Map.Entry<Integer, Integer> entry : (noteGroupByDuration(voiceTrack, midiFile)).entrySet()) {
            logger.info(entry.getKey() + " ms: " + entry.getValue());
        }

        logger.info("Список нот с количеством вхождений:");
        for (Map.Entry<String, Integer> entry : (noteGroupByName(voiceTrack)).entrySet()) {
            logger.info(entry.getKey() + ": " + entry.getValue());
        }
        logger.info("$$$$$$$$$$$$ Анализ завершён $$$$$$$$$$$$");
        return voiceTrack;
    }

    //возвращает мапу <длительность ноты(ms), количество нот с такой длительностью>
    public static Map<Integer, Integer> noteGroupByDuration(List<Note> notes, MidiFile midiFile) {
        logger.trace("Получаем информацию о количестве нот с одинаковой длительностью");
        Map<Integer, Integer> groupByDuration = new HashMap<>();
        if (notes != null) {
            List<Integer> list = new ArrayList<>();
            Tempo tempo = getTempo(midiFile);
            for (Note note : notes) {
                list.add(SongUtils.tickToMs(tempo.getBpm(), midiFile.getResolution(), note.durationTicks()));
            }
            Set<Integer> unique = new HashSet<>(list);
            logger.trace("Получено {} нот с разной длительностью", unique.size());
            for (Integer key : unique) {
                groupByDuration.put(key, Collections.frequency(list, key));
            }
            return groupByDuration;
        }
        return groupByDuration;
    }

    //возвращает мапу <Имя ноты, количество таких нот>
    public static Map<String, Integer> noteGroupByName(List<Note> notes) {
        logger.trace("Получаем информацию о группах одинаковых нот");
        Map<String, Integer> groupByName = new HashMap<>();
        if (notes != null) {
            List<String> nameList = new ArrayList<>();
            for (Note note : notes) {
                nameList.add(note.sign().fullName());
            }
            Set<String> unique = new HashSet<>(nameList);
            for (String key : unique) {
                groupByName.put(key, Collections.frequency(nameList, key));
            }
            logger.trace("Получено {} разных нот", unique.size());
            return groupByName;
        }
        return groupByName;
    }

    //Возвращает событие Tempo
    public static Tempo getTempo(MidiFile midiFile) {
        return (Tempo) (midiFile.getTracks().get(0).getEvents()).stream()
                .filter(value -> value instanceof Tempo)
                .findFirst()
                .get();
    }

    //возвращает верхнюю ноту
    public static Integer highNote(List<Note> notes) {
        logger.trace("Определение верхней ноты:");
        Integer midi = 0;
        String noteName = "";
        for (Note note : notes) {
            if (note.sign().getMidi() > midi) {
                midi = note.sign().getMidi();
                noteName = note.sign().fullName();
            }
        }
        if (midi == 0) {
            logger.info("Дорожка пуста");
            return 0;
        }
        logger.info("Верхняя нота: " + noteName);
        return midi;
    }

    //возвращает нижнюю ноту
    public static Integer lowNote(List<Note> notes) {
        logger.trace("Определение нижней ноты:");
        Integer midi = 999;
        String noteName = "";
        for (Note note : notes) {
            if (note.sign().getMidi() < midi) {
                midi = note.sign().getMidi();
                noteName = note.sign().fullName();
            }
        }
        if (midi == 999) {
            logger.info("Дорожка пуста");
            return 0;
        }
        logger.info("Нижняя нота: " + noteName);
        return midi;
    }
}
