package ru.liga;

import com.leff.midi.MidiFile;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import ru.liga.song.domain.Note;
import ru.liga.song.logic.Analyze;
import ru.liga.song.logic.Change;
import ru.liga.song.util.GetNoteListForTest;
import java.io.FileInputStream;
import java.io.IOException;

public class ChangeTest {

    static MidiFile midiFileBelle;
    static MidiFile midiFileWreck;
    static MidiFile midiFileUnd;

    static {
        try {
            midiFileBelle = new MidiFile(new FileInputStream("src/main/resources/Belle.mid"));
            midiFileWreck = new MidiFile(new FileInputStream("src/main/resources/Wrecking Ball.mid"));
            midiFileUnd = new MidiFile(new FileInputStream("src/main/resources/Underneath Your Clothes.mid"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    ///////////////////////////// TEST TRANSPOSE /////////////////////////////////
    @Test
    public void CheckTransBelle() {
        Note noteBeforeTrans = GetNoteListForTest.getVoiceTracksAsNotesForTests(midiFileBelle).get(0).get(0);
        MidiFile transMidi = Change.transpose(midiFileBelle, 2);
        Note noteAfterTrans = GetNoteListForTest.getVoiceTracksAsNotesForTests(transMidi).get(0).get(0);

        Assertions.assertThat(noteBeforeTrans.sign().getMidi()).isEqualTo(noteAfterTrans.sign().getMidi() - 2);
    }

    @Test
    public void CheckTransWreck() {
        Note noteBeforeTrans = GetNoteListForTest.getVoiceTracksAsNotesForTests(midiFileWreck).get(0).get(0);
        MidiFile transMidi = Change.transpose(midiFileWreck, 2);
        Note noteAfterTrans = GetNoteListForTest.getVoiceTracksAsNotesForTests(transMidi).get(0).get(0);

        Assertions.assertThat(noteBeforeTrans.sign().getMidi()).isEqualTo(noteAfterTrans.sign().getMidi() - 2);
    }

    @Test
    public void CheckTransUnd() {
        Note noteBeforeTrans = GetNoteListForTest.getVoiceTracksAsNotesForTests(midiFileUnd).get(0).get(0);
        MidiFile transMidi = Change.transpose(midiFileUnd, 2);
        Note noteAfterTrans = GetNoteListForTest.getVoiceTracksAsNotesForTests(transMidi).get(0).get(0);

        Assertions.assertThat(noteBeforeTrans.sign().getMidi()).isEqualTo(noteAfterTrans.sign().getMidi() - 2);
    }



    ///////////////////////////// TEST TEMPO /////////////////////////////////
    @Test
    public void CheckTempoBelle() {
        Float oldBpm = Analyze.getTempo(midiFileBelle).getBpm();
        Float newBpm = Analyze.getTempo(Change.accelerate(midiFileBelle, 20)).getBpm();

        Assertions.assertThat(newBpm).isEqualTo(oldBpm * 1.2f);
    }

    @Test
    public void CheckTempoWreck() {
        Float oldBpm = Analyze.getTempo(midiFileWreck).getBpm();
        Float newBpm = Analyze.getTempo(Change.accelerate(midiFileWreck, 20)).getBpm();

        Assertions.assertThat(newBpm).isEqualTo(oldBpm * 1.2f);
    }

    @Test
    public void CheckTempoUnd() {
        Float oldBpm = Analyze.getTempo(midiFileUnd).getBpm();
        Float newBpm = Analyze.getTempo(Change.accelerate(midiFileUnd, 20)).getBpm();

        Assertions.assertThat(newBpm).isEqualTo(oldBpm * 1.2f);
    }
}