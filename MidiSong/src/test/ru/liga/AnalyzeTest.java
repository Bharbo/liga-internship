package ru.liga;

import com.leff.midi.MidiFile;
import com.leff.midi.event.MidiEvent;
import org.assertj.core.api.Assertions;
import org.jasypt.contrib.org.apache.commons.codec_1_3.binary.Base64;
import org.junit.Test;
import ru.liga.song.domain.Note;
import ru.liga.song.domain.NoteSign;
import ru.liga.song.logic.Analyze;
import ru.liga.song.logic.GetTracks;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

public class AnalyzeTest {

    static MidiFile midiBelle;
    static MidiFile midiUnderneath;
    static MidiFile midiWrecking;

    static final String empty = "TVRoZAAAAAYAAQADAHhNVHJrAAAACwP/UQMHoSAA/y8ATVRyawAABj4A/wMQJkNob3JkcyBhbmQgS2V5cwD/BowPW1swLDAsMCwwLjMsMCwyMDAwMCwwLDIwMDAwLDAsMjAwMDAsMC4wNSwic2luZSIsNC41LDAsdHJ1ZSxmYWxzZSxmYWxzZSwwLDAsNDAwLDUsMCw0MDAsNSwwLDQwMCw1XSxbMCwwLDAsMC4zLDAsMjAwMDAsMCwyMDAwMCwwLDIwMDAwLDAuMDUsInNpbmUiLDQuNSwwLHRydWUsZmFsc2UsZmFsc2UsMCwwLDQwMCw1LDAsNDAwLDUsMCw0MDAsNV0sWzAsMCwwLDAuMywwLDIwMDAwLDAsMjAwMDAsMCwyMDAwMCwwLjA1LCJzaW5lIiw0LjUsMCx0cnVlLGZhbHNlLGZhbHNlLDAsMCw0MDAsNSwwLDQwMCw1LDAsNDAwLDVdLFswLDAsMCwwLjMsMCwyMDAwMCwwLDIwMDAwLDAsMjAwMDAsMC4wNSwic2luZSIsNC41LDAsdHJ1ZSxmYWxzZSxmYWxzZSwwLDAsNDAwLDUsMCw0MDAsNSwwLDQwMCw1XSxbMCwwLDAsMC4zLDAsMjAwMDAsMCwyMDAwMCwwLDIwMDAwLDAuMDUsInNpbmUiLDQuNSwwLHRydWUsZmFsc2UsZmFsc2UsMCwwLDQwMCw1LDAsNDAwLDUsMCw0MDAsNV0sWzAsMCwwLDAuMywwLDIwMDAwLDAsMjAwMDAsMCwyMDAwMCwwLjA1LCJzaW5lIiw0LjUsMCx0cnVlLGZhbHNlLGZhbHNlLDAsMCw0MDAsNSwwLDQwMCw1LDAsNDAwLDVdLFswLDAsMCwwLjMsMCwyMDAwMCwwLDIwMDAwLDAsMjAwMDAsMC4wNSwic2luZSIsNC41LDAsdHJ1ZSxmYWxzZSxmYWxzZSwwLDAsNDAwLDUsMCw0MDAsNSwwLDQwMCw1XSxbMCwwLDAsMC4zLDAsMjAwMDAsMCwyMDAwMCwwLDIwMDAwLDAuMDUsInNpbmUiLDQuNSwwLHRydWUsZmFsc2UsZmFsc2UsMCwwLDQwMCw1LDAsNDAwLDUsMCw0MDAsNV0sWzAsMCwwLDAuMywwLDIwMDAwLDAsMjAwMDAsMCwyMDAwMCwwLjA1LCJzaW5lIiw0LjUsMCx0cnVlLGZhbHNlLGZhbHNlLDAsMCw0MDAsNSwwLDQwMCw1LDAsNDAwLDVdLFswLDAsMCwwLjMsMCwyMDAwMCwwLDIwMDAwLDAsMjAwMDAsMC4wNSwic2luZSIsNC41LDAsdHJ1ZSxmYWxzZSxmYWxzZSwwLDAsNDAwLDUsMCw0MDAsNSwwLDQwMCw1XSxbMCwwLDAsMC4zLDAsMjAwMDAsMCwyMDAwMCwwLDIwMDAwLDAuMDUsInNpbmUiLDQuNSwwLHRydWUsZmFsc2UsZmFsc2UsMCwwLDQwMCw1LDAsNDAwLDUsMCw0MDAsNV0sWzAsMCwwLDAuMywwLDIwMDAwLDAsMjAwMDAsMCwyMDAwMCwwLjA1LCJzaW5lIiw0LjUsMCx0cnVlLGZhbHNlLGZhbHNlLDAsMCw0MDAsNSwwLDQwMCw1LDAsNDAwLDVdLFswLDAsMCwwLjMsMCwyMDAwMCwwLDIwMDAwLDAsMjAwMDAsMC4wNSwic2luZSIsNC41LDAsdHJ1ZSxmYWxzZSxmYWxzZSwwLDAsNDAwLDUsMCw0MDAsNSwwLDQwMCw1XSxbMCwwLDAsMC4zLDAsMjAwMDAsMCwyMDAwMCwwLDIwMDAwLDAuMDUsInNpbmUiLDQuNSwwLHRydWUsZmFsc2UsZmFsc2UsMCwwLDQwMCw1LDAsNDAwLDUsMCw0MDAsNV0sWzAsMCwwLDAuMywwLDIwMDAwLDAsMjAwMDAsMCwyMDAwMCwwLjA1LCJzaW5lIiw0LjUsMCx0cnVlLGZhbHNlLGZhbHNlLDAsMCw0MDAsNSwwLDQwMCw1LDAsNDAwLDVdLFswLDAsMCwwLDAsMjAwMDAsMCwyMDAwMCwwLDIwMDAwLDAuMDUsInNpbmUiLDQuNSwwLHRydWUsZmFsc2UsZmFsc2UsMCwwLDQwMCw1LDAsNDAwLDUsMCw0MDAsNV1dAP8HDnsiRGV0YWlscyI6e319AP8vAE1UcmsAAAAZAP8DBlRyYWNrMQDAAACwB38AsApAAP8vAA==";
    static MidiFile emptyMidi;

    static {
        try {
            midiBelle = new MidiFile(new FileInputStream("src/main/resources/Belle.mid"));
            midiWrecking = new MidiFile(new FileInputStream("src/main/resources/Wrecking Ball.mid"));
            midiUnderneath = new MidiFile(new FileInputStream("src/main/resources/Underneath Your Clothes.mid"));

            InputStream bytes = new ByteArrayInputStream(Base64.decodeBase64(empty.getBytes()));
            emptyMidi = new MidiFile(bytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void numberOfNotesInVoicesTrack() {
        int allVoiceTracks = GetTracks.getVoiceTrack(midiBelle).size();
        Assertions.assertThat(allVoiceTracks).isEqualTo(437);
    }

    @Test
    public void whenEmptyTrackReturnZeroLengthList() {
        Assertions.assertThat(Analyze.loggingProcess(emptyMidi).size()).isEqualTo(0);
    }

    @Test
    public void whenRangesIntersectItsVoiceTrack() {
        List<Note> notes = Arrays.asList(
                new Note(NoteSign.A_1, 1L, 5L),
                new Note(NoteSign.A_2, 11L, 5L),
                new Note(NoteSign.A_3, 21L, 5L),
                new Note(NoteSign.A_4, 31L, 5L));
        Assertions.assertThat((GetTracks.notesBoundsChecking(notes))).isEqualTo(true);
    }

    @Test
    public void whenRangesDontIntersectItsNotVoiceTrack() {
        List<Note> notes = Arrays.asList(
                new Note(NoteSign.A_0, 1L, 15L),
                new Note(NoteSign.A_1, 11L, 15L),
                new Note(NoteSign.A_2, 21L, 15L),
                new Note(NoteSign.A_3, 31L, 15L));
        Assertions.assertThat(GetTracks.notesBoundsChecking(notes)).isEqualTo(false);
    }

    @Test
    public void noteGroupByDurationTest() {
        List<Note> notes = Arrays.asList(
                new Note(NoteSign.A_0, 1L, 15L),
                new Note(NoteSign.A_1, 11L, 15L),
                new Note(NoteSign.A_2, 21L, 20L),
                new Note(NoteSign.A_3, 31L, 20L),
                new Note(NoteSign.A_3, 31L, 30L),
                new Note(NoteSign.A_3, 31L, 35L));
        Assertions.assertThat(Analyze.noteGroupByDuration(notes, midiBelle).size()).isEqualTo(4);
    }

    @Test
    public void noteGroupByNameTest() {
        List<Note> notes = Arrays.asList(
                new Note(NoteSign.A_0, 1L, 15L),
                new Note(NoteSign.A_0, 11L, 15L),
                new Note(NoteSign.A_0, 21L, 20L),
                new Note(NoteSign.A_3, 31L, 20L),
                new Note(NoteSign.A_3, 31L, 30L),
                new Note(NoteSign.A_3, 31L, 35L));
        Assertions.assertThat(Analyze.noteGroupByName(notes).size()).isEqualTo(2);
    }

    @Test
    public void rangeBetweenTopAndBottomNotes() {
        List<Note> notes = Arrays.asList(
                new Note(NoteSign.A_SHARP_2, 1L, 15L),
                new Note(NoteSign.F_3, 21L, 20L),
                new Note(NoteSign.G_SHARP_4, 31L, 20L));
        int high = Analyze.highNote(notes);
        int low = Analyze.lowNote(notes);
        int bounds = high - low;
        Assertions.assertThat(high).isEqualTo(68);
        Assertions.assertThat(low).isEqualTo(46);
        Assertions.assertThat(bounds).isEqualTo(22);
    }

    @Test
    public void numberOfTracksInRingtone() {
        Assertions.assertThat(GetTracks.getAllTracksAsNoteLists(midiBelle).size()).isEqualTo(15);
    }
}

