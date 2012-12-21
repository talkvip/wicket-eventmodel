package at.cleancode.eventmodel;

import java.util.HashSet;
import java.util.Set;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.event.IEvent;
import org.apache.wicket.request.cycle.RequestCycle;

@SuppressWarnings("rawtypes")
public class EventModelBehavior extends Behavior {

    private final Class<? extends EventModel> modelClass;

    private final Set<Component> components = new HashSet<Component>();

    public EventModelBehavior(Class<? extends EventModel> modelClass) {
        this.modelClass = modelClass;
    }

    @Override
    public void bind(Component component) {
        components.add(component);
    }

    @Override
    public void onEvent(Component component, IEvent<?> event) {
        if (event.getPayload() instanceof ModelChangedEvent) {
            ModelChangedEvent evt = (ModelChangedEvent) event.getPayload();
            if (evt.getEventClass().equals(modelClass)) {
                for (Component c : components) {
                    update(c);
                }
            }
        }
    }

    private void update(Component c) {
        RequestCycle.get().find(AjaxRequestTarget.class).add(c);
    }

}
