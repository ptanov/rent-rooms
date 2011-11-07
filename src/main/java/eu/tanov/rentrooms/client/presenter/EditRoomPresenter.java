package eu.tanov.rentrooms.client.presenter;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HasWidgets;

import eu.tanov.rentrooms.client.RoomsServiceAsync;
import eu.tanov.rentrooms.client.event.room.EditRoomCancelledEvent;
import eu.tanov.rentrooms.client.event.room.RoomUpdatedEvent;
import eu.tanov.rentrooms.client.view.EditRoomView;
import eu.tanov.rentrooms.shared.model.RoomDTO;

public class EditRoomPresenter implements Presenter, EditRoomView.Presenter {
	private static final Logger log = Logger.getLogger(EditRoomPresenter.class.getName());

	private final RoomsServiceAsync roomsService;
	private final HandlerManager eventBus;
	private final EditRoomView view;

	public EditRoomPresenter(RoomsServiceAsync rpcService, HandlerManager eventBus, EditRoomView view) {
		this.roomsService = rpcService;
		this.eventBus = eventBus;
		this.view = view;
		this.view.setPresenter(this);
	}

	public void onSaveButtonClicked() {
		final RoomDTO room = new RoomDTO();
		room.setName(view.getName().getValue());
		roomsService.addRoom(room, new AsyncCallback<RoomDTO>() {
			@Override
			public void onSuccess(RoomDTO result) {
				eventBus.fireEvent(new RoomUpdatedEvent(result));
			}

			@Override
			public void onFailure(Throwable caught) {
				log.log(Level.SEVERE, "Error updating room: " + room, caught);
				Window.alert("Error updating room");
			}
		});
	}

	public void onCancelButtonClicked() {
		eventBus.fireEvent(new EditRoomCancelledEvent());
	}

	public void go(final HasWidgets container) {
		container.clear();
		container.add(view.asWidget());
		view.getName().setValue("");
	}

}
