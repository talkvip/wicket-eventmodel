package at.cleancode.eventmodel;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.event.IEvent;
import org.apache.wicket.request.cycle.RequestCycle;

@SuppressWarnings("rawtypes")
public class EventModelBehavior extends Behavior {

    private final Class<? extends EventModel> modelClass;

    private Component component;

    private transient Object oldValue;

    public EventModelBehavior(Class<? extends EventModel> modelClass) {
        this.modelClass = modelClass;
    }

    @Override
    public void bind(Component component) {
        if (this.component != null && !this.component.equals(component)) {
            throw new IllegalStateException(getClass().getSimpleName() + " can only be bound to one component");
        }
        this.component = component;
    }

    @Override
    public void onEvent(Component component, IEvent<?> event) {
        if (event.getPayload() instanceof ModelChangedEvent) {
            ModelChangedEvent evt = (ModelChangedEvent) event.getPayload();
            if (evt.getEventClass().equals(modelClass)) {
                update();
            }
        }
    }

    private void update() {
        Object currentValue = component.getDefaultModelObject();
        if (oldValue == null || !oldValue.equals(currentValue)) {
            oldValue = currentValue;
            RequestCycle.get().find(AjaxRequestTarget.class).add(component);
        }
    }

}
