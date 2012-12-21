package at.cleancode.eventmodel;

import org.apache.wicket.model.IModel;

public interface IReferenceModel<T> {

    public IModel<T> getReferenceModel();

}
