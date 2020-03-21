package ru.liga;

import com.leff.midi.MidiFile;
import org.assertj.core.api.Assertions;
import org.jasypt.contrib.org.apache.commons.codec_1_3.binary.Base64;
import org.junit.Test;
import ru.liga.song.logic.Analyze;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class AnalyzeTest {

    static MidiFile midiFileBelle;
    static MidiFile midiFileWreck;
    static MidiFile midiFileUnd;

    static final String empty = "TVRoZAAAAAYAAQADAHhNVHJrAAAACwP/UQMHoSAA/y8ATVRyawAABj4A/wMQJkNob3JkcyBhbmQgS2V5cwD/BowPW1swLDAsMCwwLjMsMCwyMDAwMCwwLDIwMDAwLDAsMjAwMDAsMC4wNSwic2luZSIsNC41LDAsdHJ1ZSxmYWxzZSxmYWxzZSwwLDAsNDAwLDUsMCw0MDAsNSwwLDQwMCw1XSxbMCwwLDAsMC4zLDAsMjAwMDAsMCwyMDAwMCwwLDIwMDAwLDAuMDUsInNpbmUiLDQuNSwwLHRydWUsZmFsc2UsZmFsc2UsMCwwLDQwMCw1LDAsNDAwLDUsMCw0MDAsNV0sWzAsMCwwLDAuMywwLDIwMDAwLDAsMjAwMDAsMCwyMDAwMCwwLjA1LCJzaW5lIiw0LjUsMCx0cnVlLGZhbHNlLGZhbHNlLDAsMCw0MDAsNSwwLDQwMCw1LDAsNDAwLDVdLFswLDAsMCwwLjMsMCwyMDAwMCwwLDIwMDAwLDAsMjAwMDAsMC4wNSwic2luZSIsNC41LDAsdHJ1ZSxmYWxzZSxmYWxzZSwwLDAsNDAwLDUsMCw0MDAsNSwwLDQwMCw1XSxbMCwwLDAsMC4zLDAsMjAwMDAsMCwyMDAwMCwwLDIwMDAwLDAuMDUsInNpbmUiLDQuNSwwLHRydWUsZmFsc2UsZmFsc2UsMCwwLDQwMCw1LDAsNDAwLDUsMCw0MDAsNV0sWzAsMCwwLDAuMywwLDIwMDAwLDAsMjAwMDAsMCwyMDAwMCwwLjA1LCJzaW5lIiw0LjUsMCx0cnVlLGZhbHNlLGZhbHNlLDAsMCw0MDAsNSwwLDQwMCw1LDAsNDAwLDVdLFswLDAsMCwwLjMsMCwyMDAwMCwwLDIwMDAwLDAsMjAwMDAsMC4wNSwic2luZSIsNC41LDAsdHJ1ZSxmYWxzZSxmYWxzZSwwLDAsNDAwLDUsMCw0MDAsNSwwLDQwMCw1XSxbMCwwLDAsMC4zLDAsMjAwMDAsMCwyMDAwMCwwLDIwMDAwLDAuMDUsInNpbmUiLDQuNSwwLHRydWUsZmFsc2UsZmFsc2UsMCwwLDQwMCw1LDAsNDAwLDUsMCw0MDAsNV0sWzAsMCwwLDAuMywwLDIwMDAwLDAsMjAwMDAsMCwyMDAwMCwwLjA1LCJzaW5lIiw0LjUsMCx0cnVlLGZhbHNlLGZhbHNlLDAsMCw0MDAsNSwwLDQwMCw1LDAsNDAwLDVdLFswLDAsMCwwLjMsMCwyMDAwMCwwLDIwMDAwLDAsMjAwMDAsMC4wNSwic2luZSIsNC41LDAsdHJ1ZSxmYWxzZSxmYWxzZSwwLDAsNDAwLDUsMCw0MDAsNSwwLDQwMCw1XSxbMCwwLDAsMC4zLDAsMjAwMDAsMCwyMDAwMCwwLDIwMDAwLDAuMDUsInNpbmUiLDQuNSwwLHRydWUsZmFsc2UsZmFsc2UsMCwwLDQwMCw1LDAsNDAwLDUsMCw0MDAsNV0sWzAsMCwwLDAuMywwLDIwMDAwLDAsMjAwMDAsMCwyMDAwMCwwLjA1LCJzaW5lIiw0LjUsMCx0cnVlLGZhbHNlLGZhbHNlLDAsMCw0MDAsNSwwLDQwMCw1LDAsNDAwLDVdLFswLDAsMCwwLjMsMCwyMDAwMCwwLDIwMDAwLDAsMjAwMDAsMC4wNSwic2luZSIsNC41LDAsdHJ1ZSxmYWxzZSxmYWxzZSwwLDAsNDAwLDUsMCw0MDAsNSwwLDQwMCw1XSxbMCwwLDAsMC4zLDAsMjAwMDAsMCwyMDAwMCwwLDIwMDAwLDAuMDUsInNpbmUiLDQuNSwwLHRydWUsZmFsc2UsZmFsc2UsMCwwLDQwMCw1LDAsNDAwLDUsMCw0MDAsNV0sWzAsMCwwLDAuMywwLDIwMDAwLDAsMjAwMDAsMCwyMDAwMCwwLjA1LCJzaW5lIiw0LjUsMCx0cnVlLGZhbHNlLGZhbHNlLDAsMCw0MDAsNSwwLDQwMCw1LDAsNDAwLDVdLFswLDAsMCwwLDAsMjAwMDAsMCwyMDAwMCwwLDIwMDAwLDAuMDUsInNpbmUiLDQuNSwwLHRydWUsZmFsc2UsZmFsc2UsMCwwLDQwMCw1LDAsNDAwLDUsMCw0MDAsNV1dAP8HDnsiRGV0YWlscyI6e319AP8vAE1UcmsAAAAZAP8DBlRyYWNrMQDAAACwB38AsApAAP8vAA==";
    static MidiFile emptyMidi;

    static {
        try {
            midiFileBelle = new MidiFile(new FileInputStream("src/main/resources/Belle.mid"));
            midiFileWreck = new MidiFile(new FileInputStream("src/main/resources/Wrecking Ball.mid"));
            midiFileUnd = new MidiFile(new FileInputStream("src/main/resources/Underneath Your Clothes.mid"));

            InputStream is = new ByteArrayInputStream(Base64.decodeBase64(empty.getBytes()));
            emptyMidi = new MidiFile(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //oneVoiceTrack
    @Test
    public void whenBelleReturnOneVoiceTrack() {
        int allVoiceTracks = Analyze.logging_process(midiFileBelle).size();
        Assertions.assertThat(allVoiceTracks).isEqualTo(1);
    }

    @Test
    public void whenWreckReturnOneVoiceTrack() {
        int allVoiceTracks = Analyze.logging_process(midiFileWreck).size();
        Assertions.assertThat(allVoiceTracks).isEqualTo(1);
    }

    @Test
    public void whenUndReturnOneVoiceTrack() {
        int allVoiceTracks = Analyze.logging_process(midiFileUnd).size();
        Assertions.assertThat(allVoiceTracks).isEqualTo(1);
    }

    //emptyfile
    @Test
    public void whenEmptyTrackReturnZeroLengthList() {
        Assertions.assertThat(Analyze.logging_process(emptyMidi).size()).isEqualTo(0);
    }

}