package at.cleancode.eventmodel.example;

import org.apache.wicket.protocol.http.WebApplication;

import at.cleancode.eventmodel.EventModelInstantiationListener;

public class WicketApplication extends WebApplication {

    @Override
    public Class<HomePage> getHomePage() {
        return HomePage.class;
    }

    @Override
    public void init() {
        super.init();
        getComponentInitializationListeners().add(new EventModelInstantiationListener());
    }

}
