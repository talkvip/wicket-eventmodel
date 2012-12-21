package at.cleancode.eventmodel.example;

import java.util.ArrayList;
import java.util.List;

import at.cleancode.eventmodel.EventModel;

public class PersonModel extends EventModel<List<Person>> {

    private List<Person> persons = new ArrayList<Person>();

    public PersonModel() {
        for (int i = 0; i < 3; i++) {
            persons.add(new Person("Person " + i));
        }
    }

    @Override
    public List<Person> get() {
        return persons;
    }

    @Override
    public void set(List<Person> object) {
        persons = object;
    }

}
