package eu.tanov.rentrooms.client;

import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import eu.tanov.rentrooms.shared.model.RoomDTO;

@RemoteServiceRelativePath("roomsService")
public interface RoomsService extends RemoteService {
	public RoomDTO addRoom(RoomDTO room);
//
//	public Boolean deleteRoom(String id);
//
//	public List<RoomDTO> deleteRooms(List<String> ids);

	public List<RoomDTO> getRooms();

//	public RoomDTO getRoom(String id);
//
//	public RoomDTO updateRoom(RoomDTO room);
}
