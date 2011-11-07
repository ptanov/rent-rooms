package eu.tanov.rentrooms.client.presenter;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HasWidgets;

import eu.tanov.rentrooms.client.RoomsServiceAsync;
import eu.tanov.rentrooms.client.common.ColumnDefinition;
import eu.tanov.rentrooms.client.common.SelectionModel;
import eu.tanov.rentrooms.client.event.room.AddRoomEvent;
import eu.tanov.rentrooms.client.view.RoomsView;
import eu.tanov.rentrooms.shared.model.RoomDTO;

public class RoomsPresenter implements Presenter, RoomsView.Presenter<RoomDTO> {
	private static final Logger log = Logger.getLogger(RoomsPresenter.class.getName());

	private List<RoomDTO> roomDetails;
	private final RoomsServiceAsync roomsService;
	private final HandlerManager eventBus;
	private final RoomsView<RoomDTO> view;
	private final SelectionModel<RoomDTO> selectionModel;

	public RoomsPresenter(RoomsServiceAsync rpcService, HandlerManager eventBus, RoomsView<RoomDTO> view,
			List<ColumnDefinition<RoomDTO>> columnDefinitions) {
		this.roomsService = rpcService;
		this.eventBus = eventBus;
		this.view = view;
		this.selectionModel = new SelectionModel<RoomDTO>();
		this.view.setPresenter(this);
		this.view.setColumnDefinitions(columnDefinitions);
	}

	public void onAddButtonClicked() {
		eventBus.fireEvent(new AddRoomEvent());
	}

	public void onDeleteButtonClicked() {
		deleteSelectedRooms();
	}

	public void onItemClicked(RoomDTO roomDetails) {
		// eventBus.fireEvent(new EditRoomEvent(roomDetails.getId()));
	}

	public void onItemSelected(RoomDTO roomDetails) {
		if (selectionModel.isSelected(roomDetails)) {
			selectionModel.removeSelection(roomDetails);
		} else {
			selectionModel.addSelection(roomDetails);
		}
	}

	public void go(final HasWidgets container) {
		container.clear();
		container.add(view.asWidget());
		fetchRoomDTO();
	}

	public void sortRoomDTO() {

		// Yes, we could use a more optimized method of sorting, but the
		// point is to create a test case that helps illustrate the higher
		// level concepts used when creating MVP-based applications.
		//
		for (int i = 0; i < roomDetails.size(); ++i) {
			for (int j = 0; j < roomDetails.size() - 1; ++j) {
				if (roomDetails.get(j).getName().compareToIgnoreCase(roomDetails.get(j + 1).getName()) >= 0) {
					RoomDTO tmp = roomDetails.get(j);
					roomDetails.set(j, roomDetails.get(j + 1));
					roomDetails.set(j + 1, tmp);
				}
			}
		}
	}

	public void setRoomDTO(List<RoomDTO> roomDetails) {
		this.roomDetails = roomDetails;
	}

	public List<RoomDTO> getRoomDTO() {
		return roomDetails;
	}

	public RoomDTO getRoomDetail(int index) {
		return roomDetails.get(index);
	}

	private void fetchRoomDTO() {
		roomsService.getRooms(new AsyncCallback<List<RoomDTO>>() {
			public void onSuccess(List<RoomDTO> result) {
				roomDetails = result;
				sortRoomDTO();
				view.setRowData(roomDetails);
			}

			public void onFailure(Throwable caught) {
				log.log(Level.SEVERE, "Error fetching room details", caught);
				Window.alert("Error fetching room details");
			}
		});
	}

	private void deleteSelectedRooms() {
		final List<RoomDTO> selectedRooms = selectionModel.getSelectedItems();
		final ArrayList<String> ids = new ArrayList<String>(selectedRooms.size());

		for (int i = 0; i < selectedRooms.size(); ++i) {
			ids.add(selectedRooms.get(i).getKey());
		}

		roomsService.deleteRooms(ids, new AsyncCallback<List<RoomDTO>>() {
			public void onSuccess(List<RoomDTO> result) {
				roomDetails = result;
				sortRoomDTO();
				view.setRowData(roomDetails);
			}

			public void onFailure(Throwable caught) {
				log.log(Level.SEVERE, "Error deleting selected rooms: " + selectedRooms, caught);
				Window.alert("Error deleting selected rooms");
			}
		});
	}
}
