package eu.tanov.rentrooms.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.ui.RootPanel;

public class RentRooms implements EntryPoint {

	public void onModuleLoad() {
		final RoomsServiceAsync greetingService = GWT.create(RoomsService.class);

		final HandlerManager eventBus = new HandlerManager(null);
		final AppController appViewer = new AppController(greetingService, eventBus);
		appViewer.go(RootPanel.get());
	}

}
