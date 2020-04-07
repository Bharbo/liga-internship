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

    static MidiFile midiBelle;
    static MidiFile midiWrecking;
    static MidiFile midiUnderneath;

    static {
        try {
            midiBelle = new MidiFile(new FileInputStream("src/main/resources/Belle.mid"));
            midiWrecking = new MidiFile(new FileInputStream("src/main/resources/Wrecking Ball.mid"));
            midiUnderneath = new MidiFile(new FileInputStream("src/main/resources/Underneath Your Clothes.mid"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    ///////////////////////////// TEST TRANSPOSE /////////////////////////////////
    @Test
    public void CheckTransBelle() {
        Note noteBeforeTrans = GetNoteListForTest.getVoiceTracksAsNotesForTests(midiBelle).get(0).get(0);
        MidiFile transMidi = Change.transpose(midiBelle, 2);
        Note noteAfterTrans = GetNoteListForTest.getVoiceTracksAsNotesForTests(transMidi).get(0).get(0);

        Assertions.assertThat(noteBeforeTrans.sign().getMidi()).isEqualTo(noteAfterTrans.sign().getMidi() - 2);
    }

    ///////////////////////////// TEST TEMPO /////////////////////////////////
    @Test
    public void CheckTempoBelle() {
        float oldBpm = Analyze.getTempo(midiBelle).getBpm();
        Float newBpm = Analyze.getTempo(Change.accelerate(midiBelle, 20)).getBpm();

        Assertions.assertThat(newBpm).isEqualTo(oldBpm * 1.2f);
    }


}