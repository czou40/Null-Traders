package cores;

import javafx.event.Event;
import javafx.event.EventType;

public class MyEvent extends Event {
    public static final EventType<MyEvent> ENCOUNTER_FINISHED
            = new EventType<>("Encounter Finished");

    public MyEvent(EventType<? extends Event> eventType) {
        super(eventType);
    }
}
