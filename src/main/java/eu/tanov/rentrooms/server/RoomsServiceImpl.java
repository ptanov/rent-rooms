package eu.tanov.rentrooms.server;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import eu.tanov.rentrooms.client.RoomsService;
import eu.tanov.rentrooms.server.model.Room;
import eu.tanov.rentrooms.shared.model.RoomDTO;

/**
 * The server side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class RoomsServiceImpl extends RemoteServiceServlet implements RoomsService {

	@Override
	public List<RoomDTO> getRooms() {
		final EntityManager em = EMF.get().createEntityManager();
		final List<?> allRooms = em.createNamedQuery("allRooms").getResultList();
		
		
		final List<RoomDTO> result = new ArrayList<RoomDTO>(allRooms.size());
		for (Object object : allRooms) {
			if (!(object instanceof Room)) {
				throw new IllegalStateException("Not-a-room: "+object);
			}
			final Room room = (Room) object;
			
			final RoomDTO dto = new RoomDTO();
			dto.setName(room.getName());
			
			result.add(dto);
		}
		return result;
	}
}
