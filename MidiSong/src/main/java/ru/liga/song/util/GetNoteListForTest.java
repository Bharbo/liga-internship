package ru.liga.song.util;

import com.leff.midi.MidiFile;
import ru.liga.song.logic.GetTracks;
import ru.liga.song.domain.Note;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static ru.liga.song.util.EventsToNotes.eventsToNotes;

public class GetNoteListForTest {

    public static List<List<Note>> getVoiceTracksAsNotesForTests(MidiFile midiFile) {
        return getAllTracksAsNoteListsForTests(midiFile).stream()
                .filter(GetTracks::notesBoundsChecking)
                .collect(Collectors.toList());
    }

    public static List<List<Note>> getAllTracksAsNoteListsForTests(MidiFile midiFile) {
        List<List<Note>> allTracks = new ArrayList<>();
        for (int i = 0; i < midiFile.getTracks().size(); i++) {
            List<Note> tmp = eventsToNotes(midiFile.getTracks().get(i).getEvents());
            if (tmp.size() > 0) {
                allTracks.add(tmp);
            }
        }
        return allTracks;
    }
}
