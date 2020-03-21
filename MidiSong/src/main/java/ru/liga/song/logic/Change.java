package ru.liga.song.logic;

import com.leff.midi.MidiFile;
import com.leff.midi.MidiTrack;
import com.leff.midi.event.MidiEvent;
import com.leff.midi.event.NoteOff;
import com.leff.midi.event.NoteOn;
import com.leff.midi.event.meta.Tempo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.liga.song.domain.NoteSign;
import ru.liga.song.util.GetNoteListForTest;
import java.io.File;
import java.io.IOException;

public class Change {

    private static final Logger logger =
            LoggerFactory.getLogger(Change.class);

    public static void change(String path, int trans, float tempo) throws IOException {
        logger.info("Подготовка к изменению трека ......");
        logger.info("Стартууем!");
        File file = new File(path);
        MidiFile midiFile = new MidiFile(file);
        MidiFile newMidi = transpose(midiFile, trans);
        accelerate(newMidi, tempo);
        String newPath = Reader.getSavePath(trans, tempo, file);
        newMidi.writeToFile(new File(newPath));
    }

    //возврат транспонированного трека
    public static MidiFile transpose(MidiFile midiFile, int trans) {
        logger.info("Старт транспонирования");
        logger.info("Первая нота до транспонирования: " + GetNoteListForTest.getVoiceTracksAsNotesForTests(midiFile).get(0).get(0));
        for (MidiTrack midiTrack : midiFile.getTracks()) {
            for (MidiEvent event : midiTrack.getEvents()) {
                transpositionOfNoteBorders(event, trans);
            }
        }
        logger.info("Первая нота после транспонирования: " + GetNoteListForTest.getVoiceTracksAsNotesForTests(midiFile).get(0).get(0));
        logger.info("Конец транспонирования");
        return midiFile;
    }

    //возвращает новое значение midiNumber,
    //если нота не была транспонирована, возвращается изначальное значение midiNumber,
    //если несоответствие типа ивента, то вернется 0
    private static int transpositionOfNoteBorders(MidiEvent midiEvent, int trans) {
        if (midiEvent instanceof NoteOn) {
            NoteOn noteOn = (NoteOn) midiEvent;
            noteOn.setNoteValue(noteOn.getNoteValue() + trans);
            if (NoteSign.fromMidiNumber(noteOn.getNoteValue()) == NoteSign.NULL_VALUE) {
                logger.info("не удалоcь транспонировать ноту");
                noteOn.setNoteValue(noteOn.getNoteValue() - trans);
            }
            return noteOn.getNoteValue();
        } else if (midiEvent instanceof NoteOff) {
            NoteOff noteOff = (NoteOff) midiEvent;
            noteOff.setNoteValue(noteOff.getNoteValue() + trans);
            if (NoteSign.fromMidiNumber(noteOff.getNoteValue()) == NoteSign.NULL_VALUE) {
                logger.info("не удалоcь транспонировать ноту");
                noteOff.setNoteValue(noteOff.getNoteValue() - trans);
            }
            return noteOff.getNoteValue();
        }
        return 0;
    }

    //возвращает транспонированный и ускоренный трек
    public static MidiFile accelerate(MidiFile midiFile, float tempo) {
        logger.info("Старт ускорения трека >>>>>>>>>>>>>>>>>>>>>>>");
        float coefAcceleration = tempo / 100 + 1;
        for (MidiTrack midiTrack : midiFile.getTracks()) {
            for (MidiEvent event : midiTrack.getEvents()) {
                accelerateTempo(event, coefAcceleration);
            }
        }
        logger.info("Конец ускорения трека");
        return midiFile;
    }

    //возвращает новую скорость трека или 0
    private static float accelerateTempo(MidiEvent midiEvent, float coefTempo) {
        if (midiEvent instanceof Tempo) {
            Tempo tempo1 = (Tempo) midiEvent;
            logger.info("Скорость до изменения - {}", tempo1.getBpm());
            tempo1.setBpm(tempo1.getBpm() * coefTempo);
            logger.info("Скорость после изменения - {}", tempo1.getBpm());
            return tempo1.getBpm();
        }
        return 0;
    }

}
