package at.cleancode.eventmodel;

import org.apache.wicket.model.IModel;

public abstract class ReferenceModel<T, U> implements IModel<T>, IReferenceModel<U> {

    private final IModel<U> reference;

    public ReferenceModel(IModel<U> reference) {
        this.reference = reference;
    }

    @Override
    public IModel<U> getReferenceModel() {
        return reference;
    }

    public U getReferenceModelObject() {
        return getReferenceModel().getObject();
    }

    @Override
    public void setObject(T object) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void detach() {
    }

}
