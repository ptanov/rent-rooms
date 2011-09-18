package eu.tanov.rentrooms.client;

import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.HasWidgets;

import eu.tanov.rentrooms.client.common.RoomsColumnDefinitionsFactory;
import eu.tanov.rentrooms.client.presenter.Presenter;
import eu.tanov.rentrooms.client.presenter.RoomsPresenter;
import eu.tanov.rentrooms.client.view.RoomsView;
import eu.tanov.rentrooms.client.view.RoomsViewImpl;
import eu.tanov.rentrooms.shared.model.RoomDTO;

public class AppController implements Presenter, ValueChangeHandler<String> {
	private final HandlerManager eventBus;
	private final RoomsServiceAsync roomsService;
	private HasWidgets container;
	private RoomsViewImpl<RoomDTO> roomsView = null;

	public AppController(RoomsServiceAsync roomsService, HandlerManager eventBus) {
		this.eventBus = eventBus;
		this.roomsService = roomsService;
		bind();
	}

	private void bind() {
		History.addValueChangeHandler(this);
	}

	public void go(final HasWidgets container) {
		this.container = container;

		if ("".equals(History.getToken())) {
			History.newItem("list");
		} else {
			History.fireCurrentHistoryState();
		}
	}

	public void onValueChange(ValueChangeEvent<String> event) {
		String token = event.getValue();

		if (token != null) {
			Presenter presenter = null;

			if (token.equals("list")) {

	            if (roomsView == null) {
	                roomsView = new RoomsViewImpl<RoomDTO>();
	              }

				presenter = new RoomsPresenter(roomsService, eventBus, roomsView, RoomsColumnDefinitionsFactory
		                .getRoomsColumnDefinitions());
			}

			if (presenter != null) {
				presenter.go(container);
			}
		}
	}
}
