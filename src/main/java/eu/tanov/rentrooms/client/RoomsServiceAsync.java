package eu.tanov.rentrooms.client;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;

import eu.tanov.rentrooms.shared.model.RoomDTO;

public interface RoomsServiceAsync {
	public void addRoom(RoomDTO room, AsyncCallback<RoomDTO> callback);
//
//	public void deleteRoom(String id, AsyncCallback<Boolean> callback);

	public void deleteRooms(List<String> encodedKeys, AsyncCallback<List<RoomDTO>> callback);

	public void getRooms(AsyncCallback<List<RoomDTO>> callback);
//
//	public void getRoom(String id, AsyncCallback<RoomDTO> callback);
//
//	public void updateRoom(RoomDTO room, AsyncCallback<RoomDTO> callback);
}
