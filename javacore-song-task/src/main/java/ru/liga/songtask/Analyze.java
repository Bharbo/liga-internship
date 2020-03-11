package ru.liga.songtask;

import com.leff.midi.MidiFile;
import com.leff.midi.event.meta.Tempo;
import ru.liga.songtask.domain.Note;
import ru.liga.songtask.domain.NoteSign;

import java.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.liga.songtask.util.SongUtils;

import static ru.liga.songtask.op.EventsToNotes.eventsToNotes;

public class Analyze {

    private final Logger logger =
            LoggerFactory.getLogger(Analyze.class);

    public void findAndAnalyzeVoiceTracks(MidiFile midiFile){
        boolean result;
        for (int i = 0; i < midiFile.getTrackCount(); i++) {
            List<Note> notesOfSingleTrack = eventsToNotes(midiFile.getTracks().get(i).getEvents());
            if (!notesOfSingleTrack.isEmpty()) {
                result = itVoiceTrack(notesOfSingleTrack);
                if (result){
                    logger.info("");
                    logger.info("Трек: " + (i + 1));
                    logger.info("Диапазон: ");
                    logger.info("Верхняя нота: " + highNote(notesOfSingleTrack));
                    logger.info("Нижняя нота: " + lowNote(notesOfSingleTrack));
                    logger.info("Диапазон: " + (findMidiByName(highNote(notesOfSingleTrack))
                            - findMidiByName(lowNote(notesOfSingleTrack))) + " полутона(ов)");

                    logger.info("Количество нот по длительностям:");
                    Tempo tempo = (Tempo) midiFile.getTracks().get(0).getEvents().last();//////милисекунды
                    for (Map.Entry<String, Integer> entry : (numberOfNotesByDuration(notesOfSingleTrack, midiFile, tempo)).entrySet()) {
                        logger.info(entry.getKey() + entry.getValue());
                    }

                    logger.info("Список нот с количеством вхождений:");
                    for (Map.Entry<String, Integer> entry : (numberOfNotesByName(notesOfSingleTrack)).entrySet()) {
                        logger.info(entry.getKey() + entry.getValue());
                    }
                }
            }
        }
    }

    public boolean itVoiceTrack(List<Note> notes) {
        for (int i = 0; i < notes.size() - 1; i++) {
            for (int j = i + 1; j < notes.size(); j++) {
                if (comparingIntervals(notes.get(i), notes.get(j))) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean comparingIntervals (Note Note1, Note Note2){
        if ((Note1.startTick() >= Note2.startTick() && Note1.startTick() <= Note2.endTickInclusive())
                || (Note1.startTick() >= Note2.startTick() && Note1.endTickInclusive() <= Note2.endTickInclusive())) {
            return true;
        }
        return false;
    }

    public String highNote(List<Note> notes) {
        int midi = 0;
        String noteName = "";
        for (Note note : notes) {
            if (note.sign().getMidi() > midi) {
                midi = note.sign().getMidi();
                noteName = note.sign().fullName();
            }
        }
        return noteName;
    }

        public String lowNote(List<Note> notes) {
        int midi = 999;
        String noteName = "";
        for (Note note : notes) {
            if (note.sign().getMidi() < midi) {
                midi = note.sign().getMidi();
                noteName = note.sign().fullName();
            }
        }
        return noteName;
    }
    
    public int findMidiByName(String noteName) {
        NoteSign[] notes = NoteSign.values();

        for (NoteSign note : notes) {
            if (noteName.equals(note.fullName())) {
                return note.getMidi();
            }
        }
        return 0;
    }


    public HashMap<String, Integer> numberOfNotesByDuration(List<Note> notes, MidiFile midiFile, Tempo last) {
        List<String> list = new ArrayList<>();
        HashMap<String, Integer> resMap = new HashMap<>();

        for (Note note : notes) {
            list.add(SongUtils.tickToMs(last.getBpm(), midiFile.getResolution(), note.durationTicks()) + " ms: ");
        }
        Set<String> unique = new HashSet<>(list);
        for (String key : unique) {
            resMap.put(key, Collections.frequency(list, key));
        }
        return resMap;
    }

    public static HashMap<String, Integer> numberOfNotesByName(List<Note> notes) {
        List<String> nameList = new ArrayList<>();
        HashMap<String, Integer> resMap = new HashMap<>();
        for (Note note : notes) {
            nameList.add(note.sign().fullName() + ": ");
        }
        Set<String> unique = new HashSet<>(nameList);
        for (String key : unique) {
            resMap.put(key, Collections.frequency(nameList, key));
        }
        return resMap;
    }
}
