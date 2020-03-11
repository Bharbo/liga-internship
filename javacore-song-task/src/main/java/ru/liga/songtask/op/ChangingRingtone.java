package ru.liga.songtask.op;

import com.leff.midi.MidiFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.liga.songtask.Analyze;
import ru.liga.songtask.domain.Note;

import java.util.List;
import java.util.Map;

import static ru.liga.songtask.op.EventsToNotes.eventsToNotes;

public class ChangingRingtone {

    private final Logger logger =
            LoggerFactory.getLogger(Analyze.class);

    public void transposition(MidiFile midiFile) {
        logger.info("Мелодия после транспонирования:");
        for (int i = 0; i < midiFile.getTrackCount(); i++) {
            List<Note> notesOfSingleTrack = eventsToNotes(midiFile.getTracks().get(i).getEvents());
            if (!notesOfSingleTrack.isEmpty()) {
//                tempo.setBpm(tempo.getBpm() + (tempo.getBpm() / 5));
                logger.info("");
                logger.info("Трек: " + (i + 1));
                logger.info("Список нот с количеством вхождений:");

                for (Note note : notesOfSingleTrack) {

                }

                for (Map.Entry<String, Integer> entry : (Analyze.numberOfNotesByName(notesOfSingleTrack)).entrySet()) {
                    logger.info(entry.getKey() + entry.getValue());
                }
            }
        }
    }
}
