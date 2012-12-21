package at.cleancode.eventmodel;

public class ModelChangedEvent {

    private final Class<? extends EventModel<?>> eventClass;

    @SuppressWarnings("unchecked")
    public ModelChangedEvent(EventModel<?> eventModel) {
        this.eventClass = (Class<? extends EventModel<?>>) eventModel.getClass();
    }

    public Class<? extends EventModel<?>> getEventClass() {
        return eventClass;
    }

}
