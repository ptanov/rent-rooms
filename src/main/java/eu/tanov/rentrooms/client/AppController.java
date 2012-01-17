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
import eu.tanov.rentrooms.client.i18n.Constants;
import eu.tanov.rentrooms.client.presenter.EditRoomPresenter;
import eu.tanov.rentrooms.client.presenter.Presenter;
import eu.tanov.rentrooms.client.presenter.RoomsPresenter;
import eu.tanov.rentrooms.client.view.EditRoomView;
import eu.tanov.rentrooms.client.view.EditRoomViewImpl;
import eu.tanov.rentrooms.client.view.RoomsViewImpl;
import eu.tanov.rentrooms.shared.model.RoomDTO;

public class AppController implements Presenter, ValueChangeHandler<String> {
	private static final String HISTORY_ITEM_ADD_ROOM = "addRoom";
	private static final String HISTORY_ITEM_ROOMS_LIST = "list";
	private final HandlerManager eventBus;
	private final RoomsServiceAsync roomsService;
	private HasWidgets container;
	private RoomsViewImpl<RoomDTO> roomsView = null;
	private EditRoomView editRoomView = null;
	private Constants constants;

	public AppController(Constants constants, HandlerManager eventBus, RoomsServiceAsync roomsService) {
		this.constants = constants;
		this.roomsService = roomsService;
		this.eventBus = eventBus;

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
			History.newItem(HISTORY_ITEM_ROOMS_LIST);
		} else {
			History.fireCurrentHistoryState();
		}
	}

	public void onValueChange(ValueChangeEvent<String> event) {
		String token = event.getValue();

		if (token != null) {
			Presenter presenter = null;

			if (token.equals(HISTORY_ITEM_ROOMS_LIST)) {

				if (roomsView == null) {
					roomsView = new RoomsViewImpl<RoomDTO>();
				}

				presenter = new RoomsPresenter(constants, eventBus, roomsService, roomsView,
						RoomsColumnDefinitionsFactory.getRoomsColumnDefinitions());
			} else if (token.equals(HISTORY_ITEM_ADD_ROOM)) {
				GWT.runAsync(new RunAsyncCallback() {
					public void onFailure(Throwable caught) {
						Window.alert(constants.applicationErrorInit());
					}

					public void onSuccess() {
						if (editRoomView == null) {
							editRoomView = new EditRoomViewImpl();

						}
						new EditRoomPresenter(constants, eventBus, roomsService, editRoomView).go(container);
					}
				});
			}

			if (presenter != null) {
				presenter.go(container);
			}
		}
	}

	private void doRoomUpdated() {
		History.newItem(HISTORY_ITEM_ROOMS_LIST);
	}

	private void doAddNewRoom() {
		History.newItem(HISTORY_ITEM_ADD_ROOM);
	}

	private void doEditRoomCancelled() {
		History.newItem(HISTORY_ITEM_ROOMS_LIST);
	}
}
