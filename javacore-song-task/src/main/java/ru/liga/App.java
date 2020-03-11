package ru.liga;

import com.leff.midi.MidiFile;
import com.leff.midi.event.meta.Tempo;
import ru.liga.songtask.Analyze;
import ru.liga.songtask.domain.Note;
import ru.liga.songtask.util.SongUtils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

import static ru.liga.songtask.op.EventsToNotes.eventsToNotes;

public class App {

    /**
     * Это пример работы, можете всё стирать и переделывать
     * Пример, чтобы убрать у вас начальный паралич разработки
     * Также посмотрите класс SongUtils, он переводит тики в миллисекунды
     * Tempo может быть только один
     */

    public static void main(String[] args) throws IOException {

        MidiFile midiFile = new MidiFile(new FileInputStream("C:\\Users\\User\\IdeaProjects\\" +
                "liga_homeworkSong\\javacore-song-task\\src\\main\\resources\\Wrecking Ball.mid"));
        new Analyze().findAndAnalyzeVoiceTracks(midiFile);
    }

//    public static void main(String[] args) throws IOException {
//        MidiFile midiFile = new MidiFile(new FileInputStream("C:\\Users\\User\\IdeaProjects\\liga_homeworkSong\\javacore-song-task\\src\\main\\resources\\Wrecking Ball.mid"));
//        List<Note> notes = eventsToNotes(midiFile.getTracks().get(3).getEvents());
//        Tempo last = (Tempo) midiFile.getTracks().get(0).getEvents().last();
//        Note ninthNote = notes.get(8);
//        System.out.println("Длительность девятой ноты (" + ninthNote.sign().fullName() + "): " + SongUtils.tickToMs(last.getBpm(), midiFile.getResolution(), ninthNote.durationTicks()) + "мс");
//        System.out.println("Все ноты:");
//        System.out.println(notes);
//    }

}
