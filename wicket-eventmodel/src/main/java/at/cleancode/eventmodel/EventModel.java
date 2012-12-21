package at.cleancode.eventmodel;

import java.util.List;

import org.apache.wicket.Component;
import org.apache.wicket.Page;
import org.apache.wicket.core.request.handler.IPageRequestHandler;
import org.apache.wicket.event.Broadcast;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.IRequestHandler;
import org.apache.wicket.request.cycle.RequestCycle;

import com.google.common.collect.ForwardingList;

public abstract class EventModel<T> implements IModel<T> {

    public abstract T get();

    public abstract void set(T object);

    @Override
    public final T getObject() {
        return wrap(get());
    }

    @Override
    public final void setObject(T object) {
        set(object);
    }

    private T wrap(T t) {
        if (t instanceof List<?>) {
            return wrapList((List<?>) t);
        }
        // TODO
        return wrapGeneric(t);
    }

    private T wrapGeneric(T t) {
        return t; // create fancy proxy here?
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    private T wrapList(final List<?> list) {
        return (T) new ForwardingList() {
            @Override
            protected List delegate() {
                return list;
            }

            @Override
            public boolean add(Object element) {
                return changed(super.add(element));
            }

            @Override
            public boolean remove(Object object) {
                return changed(super.remove(object));
            }

            // TODO
        };
    }

    public boolean changed(boolean notify) {
        if (notify) {
            changed();
        }
        return notify;
    }

    public void changed() {
        send(Broadcast.BREADTH, new ModelChangedEvent(EventModel.this));
    }

    protected void send(Broadcast event, Object payload) {
        getPage().send(getPage(), event, payload);
    }

    protected Page getPage() {
        IRequestHandler handler = RequestCycle.get().getActiveRequestHandler();
        if (handler instanceof IPageRequestHandler) {
            return (Page) ((IPageRequestHandler) handler).getPage();
        }
        throw new IllegalStateException("Can't access page from " + handler);
    }

    public void bind(Component... components) {
        for (Component c : components) {
            c.setOutputMarkupPlaceholderTag(true);
            c.add(new EventModelBehavior(getClass()));
        }
    }

    @Override
    public void detach() {
    }

}
