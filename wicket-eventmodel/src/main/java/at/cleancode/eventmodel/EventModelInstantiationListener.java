package at.cleancode.eventmodel;

import org.apache.wicket.Component;
import org.apache.wicket.application.IComponentInitializationListener;
import org.apache.wicket.markup.repeater.AbstractRepeater;
import org.apache.wicket.model.IModel;

public class EventModelInstantiationListener implements IComponentInitializationListener {

    @Override
    public void onInitialize(Component component) {
        IModel<?> model = component.getDefaultModel();
        while (model instanceof IReferenceModel) {
            model = ((IReferenceModel<?>) model).getReferenceModel();
        }

        if (model instanceof EventModel<?>) {
            EventModel<?> eventModel = (EventModel<?>) model;

            while (component instanceof AbstractRepeater) {
                component = component.getParent();
            }

            component.setOutputMarkupId(true);
            eventModel.bind(component);
        }
    }

}
