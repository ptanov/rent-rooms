package eu.tanov.rentrooms.client;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.RootPanel;

import eu.tanov.rentrooms.client.i18n.Constants;

public class RentRooms implements EntryPoint {
	private static final Logger log = Logger.getLogger(RentRooms.class.getName());

	public void onModuleLoad() {
		final RoomsServiceAsync greetingService = GWT.create(RoomsService.class);
		final Constants constants = GWT.<Constants> create(Constants.class);
		GWT.setUncaughtExceptionHandler(new GWT.UncaughtExceptionHandler() {
			public void onUncaughtException(Throwable e) {
				log.log(Level.SEVERE, "Uncaught Exception: " + e.getMessage(), e);
				Window.alert(constants.applicationErrorUncaught());
			}
		});
		final HandlerManager eventBus = new HandlerManager(null);
		final AppController appViewer = new AppController(constants, eventBus, greetingService);
		appViewer.go(RootPanel.get());
	}

}
