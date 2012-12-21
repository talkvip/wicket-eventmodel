package at.cleancode.eventmodel.example;

import java.util.List;
import java.util.Random;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.PropertyModel;

import at.cleancode.eventmodel.ReferenceModel;

// demonstrates eventmodel: every time the PersonModel (which extends EventModel) changes
// all components which are bound to it are automatically updated
public class HomePage extends WebPage {

    @Override
    protected void onInitialize() {
        super.onInitialize();

        final PersonModel model = new PersonModel();

        // gets automatically updated when the underlying model changes
        WebMarkupContainer personsContainer = new WebMarkupContainer("personsContainer");

        ListView<Person> persons = new ListView<Person>("persons", model) {
            @Override
            protected void populateItem(ListItem<Person> item) {
                item.add(new Label("name", new PropertyModel<String>(item.getModel(), "name")));
                item.add(new AjaxLink<Person>("remove", item.getModel()) {
                    @Override
                    public void onClick(AjaxRequestTarget target) {
                        model.getObject().remove(getModelObject());
                        // no target.add but auto update
                    }
                });
                item.add(new AjaxLink<Person>("rename", item.getModel()) {
                    @Override
                    public void onClick(AjaxRequestTarget target) {
                        getModelObject().setName("Person " + new Random().nextInt());
                        model.changed(); // here we need to explicitly notify that the model has changed
                    }
                });
            }
        };

        AjaxLink<Void> add = new AjaxLink<Void>("add") {
            @Override
            public void onClick(AjaxRequestTarget target) {
                model.getObject().add(new Person("Person " + new Random().nextInt()));
                // no target.add but auto update
            }
        };

        // gets automatically updated when the underlying model changes
        Label count = new Label("count", new ReferenceModel<Integer, List<Person>>(model) {
            public Integer getObject() {
                return getReferenceModelObject().size();
            }
        });

        personsContainer.add(persons);
        add(personsContainer, add, count);
    }

}
