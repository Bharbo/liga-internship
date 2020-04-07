package ru.liga.song.logic;

import com.leff.midi.MidiFile;
import com.leff.midi.event.MidiEvent;
import com.leff.midi.event.meta.Text;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.liga.song.domain.Note;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.TreeSet;
import java.util.stream.Collectors;

import static ru.liga.song.util.EventsToNotes.eventsToNotes;

public class GetTracks {

    private static final Logger logger =
            LoggerFactory.getLogger(GetTracks.class);

    //возвращает голосовой трек
    public static List<Note> getVoiceTrack(MidiFile midiFile) {
        logger.trace("Получение голосовой дорожки...");
        List<List<Note>> maybe = getVoiceTracksAsNotes(midiFile);
        logger.trace("Получение списка голосовых дорожек в виде List'ов");
        logger.trace("Получена {} голосовых(ая) дорожек(ка)", maybe.size());
        long countOfTextEvents = getCountOfTextEvents(midiFile);
        List<Long> difference = maybe.stream()
                .map((notes) -> Math.abs((long) notes.size() - countOfTextEvents))
                .collect(Collectors.toList());
        if (difference.size() == 0) {
            return Collections.emptyList();
        }
        long minDif = Collections.min(difference);
        return maybe.get(difference.indexOf(minDif));
    }

    static List<List<Note>> getVoiceTracksAsNotes(MidiFile midiFile) {
        return getAllTracksAsNoteLists(midiFile).stream()
                .filter(GetTracks::notesBoundsChecking)
                .collect(Collectors.toList());
    }

    public static boolean notesBoundsChecking(List<Note> notesOfTrack) {
        if (!(notesOfTrack.isEmpty())) {
            for (int i = 0; i + 1 < notesOfTrack.size(); i++) {
                for (int j = i + 1; j < notesOfTrack.size(); j++) {
                    if ((notesOfTrack.get(i).startTick() > notesOfTrack.get(j).startTick() &&
                            notesOfTrack.get(i).startTick() < notesOfTrack.get(j).endTickInclusive()) ||
                            (notesOfTrack.get(i).endTickInclusive() > notesOfTrack.get(j).startTick() &&
                                    notesOfTrack.get(i).endTickInclusive() < notesOfTrack.get(j).endTickInclusive())) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public static List<List<Note>> getAllTracksAsNoteLists(MidiFile midiFile) {
        logger.trace("Получение списка всех дорожек в виде List'ов");
        List<List<Note>> allTracks = new ArrayList<>();
        for (int i = 0; i < midiFile.getTracks().size(); i++) {
            List<Note> tmp = eventsToNotes(midiFile.getTracks().get(i).getEvents());
            if (tmp.size() > 0) {
                allTracks.add(tmp);
            }
        }
        logger.trace("Получено {} дорожек(ка)", allTracks.size());
        return allTracks;
    }

    public static long getCountOfTextEvents(MidiFile midiFile) {
        return midiFile.getTracks().stream().flatMap((midiTrack) ->
                midiTrack.getEvents().stream()).filter((midiEvent) ->
                midiEvent.getClass().equals(Text.class)).count();
    }
}
