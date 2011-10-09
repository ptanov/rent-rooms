package eu.tanov.rentrooms.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.RunAsyncCallback;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.HasWidgets;

import eu.tanov.rentrooms.client.common.RoomsColumnDefinitionsFactory;
import eu.tanov.rentrooms.client.event.room.AddRoomEvent;
import eu.tanov.rentrooms.client.event.room.AddRoomEventHandler;
import eu.tanov.rentrooms.client.event.room.EditRoomCancelledEvent;
import eu.tanov.rentrooms.client.event.room.EditRoomCancelledEventHandler;
import eu.tanov.rentrooms.client.event.room.RoomUpdatedEvent;
import eu.tanov.rentrooms.client.event.room.RoomUpdatedEventHandler;
import eu.tanov.rentrooms.client.presenter.EditRoomPresenter;
import eu.tanov.rentrooms.client.presenter.Presenter;
import eu.tanov.rentrooms.client.presenter.RoomsPresenter;
import eu.tanov.rentrooms.client.view.EditRoomView;
import eu.tanov.rentrooms.client.view.EditRoomViewImpl;
import eu.tanov.rentrooms.client.view.RoomsViewImpl;
import eu.tanov.rentrooms.shared.model.RoomDTO;

public class AppController implements Presenter, ValueChangeHandler<String> {
	private final HandlerManager eventBus;
	private final RoomsServiceAsync roomsService;
	private HasWidgets container;
	private RoomsViewImpl<RoomDTO> roomsView = null;
	private EditRoomView editRoomView = null;

	public AppController(RoomsServiceAsync roomsService, HandlerManager eventBus) {
		this.eventBus = eventBus;
		this.roomsService = roomsService;
		bind();
	}

	private void bind() {
		History.addValueChangeHandler(this);

		eventBus.addHandler(AddRoomEvent.TYPE, new AddRoomEventHandler() {
			public void onAddRoom(AddRoomEvent event) {
				doAddNewRoom();
			}
		});
		eventBus.addHandler(RoomUpdatedEvent.TYPE, new RoomUpdatedEventHandler() {
			public void onRoomUpdated(RoomUpdatedEvent event) {
				doRoomUpdated();
			}
		});
		eventBus.addHandler(EditRoomCancelledEvent.TYPE, new EditRoomCancelledEventHandler() {
			@Override
			public void onEditRoomCancelled(EditRoomCancelledEvent event) {
				doEditRoomCancelled();
			}
		});
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

				presenter = new RoomsPresenter(roomsService, eventBus, roomsView,
						RoomsColumnDefinitionsFactory.getRoomsColumnDefinitions());
			} else if (token.equals("addRoom")) {
				GWT.runAsync(new RunAsyncCallback() {
					public void onFailure(Throwable caught) {
						Window.alert("Could not init application");
					}

					public void onSuccess() {
						if (editRoomView == null) {
							editRoomView = new EditRoomViewImpl();

						}
						new EditRoomPresenter(roomsService, eventBus, editRoomView).go(container);
	 				}
				});
			}

			if (presenter != null) {
				presenter.go(container);
			}
		}
	}

	private void doRoomUpdated() {
		History.newItem("list");
	}

	private void doAddNewRoom() {
		History.newItem("addRoom");
	}
	private void doEditRoomCancelled() {
		History.newItem("list");
	}
}
