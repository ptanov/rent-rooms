package eu.tanov.rentrooms.server;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import eu.tanov.rentrooms.client.RoomsService;
import eu.tanov.rentrooms.server.model.Lessor;
import eu.tanov.rentrooms.server.model.Room;
import eu.tanov.rentrooms.shared.model.RoomDTO;

/**
 * The server side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class RoomsServiceImpl extends RemoteServiceServlet implements RoomsService {
	private static final Logger log = Logger.getLogger(RoomsServiceImpl.class.getName());

	@Override
	public List<RoomDTO> getRooms() {
		final EntityManager em = EMF.get().createEntityManager();
		try {

			final Query query = em.createNamedQuery("roomsByLessor");

			query.setParameter("owner", currentUserKey());

			final List<?> userRooms = query.getResultList();

			final List<RoomDTO> result = new ArrayList<RoomDTO>(userRooms.size());
			for (Object object : userRooms) {
				if (!(object instanceof Room)) {
					throw new IllegalStateException("Not-a-room: " + object);
				}
				final Room room = (Room) object;

				final RoomDTO dto = new RoomDTO();
				dto.setName(room.getName());
				dto.setKey(KeyFactory.keyToString(room.getKey()));

				result.add(dto);
			}
			return result;
		} finally {
			em.close();
		}
	}

	private Key currentUserKey() {
		final User currentUser = UserServiceFactory.getUserService().getCurrentUser();
		return KeyFactory.createKey(Lessor.class.getSimpleName(), currentUser.getUserId());
	}

	@Override
	public RoomDTO addRoom(RoomDTO roomDTO) {
		final EntityManager em = EMF.get().createEntityManager();

		try {
			final Lessor owner = getOrCreateOwner(em);
			final Room room = new Room();
			room.setName(roomDTO.getName());
			room.setOwner(owner);
			em.persist(room);
			log.info(String.format("Room created: %s from %s", room.getName(), owner.getMail()));
		} finally {
			em.close();
		}

		return roomDTO;
	}

	@Override
	public List<RoomDTO> deleteRooms(List<String> encodedKeys) {
		final EntityManager em = EMF.get().createEntityManager();
		try {
			for (String encodedKey : encodedKeys) {
				final Key key = KeyFactory.stringToKey(encodedKey);
				final Room room = em.find(Room.class, key);
				if (room == null) {
					throw new IllegalStateException("Could not find room with key: " + key);
				}
				//FIXME check if user have rights to delete this room
				em.remove(room);
				log.info(String.format("Room removed: %s from %s", room.getName(), getOrCreateOwner(em).getMail()));
			}
		} finally {
			em.close();
		}
		return getRooms();
	}

	private Lessor getOrCreateOwner(EntityManager em) {
		final User currentUser = UserServiceFactory.getUserService().getCurrentUser();
		Lessor result = em.find(Lessor.class,
				KeyFactory.createKey(Lessor.class.getSimpleName(), currentUser.getUserId()));
		if (result == null) {
			// create
			result = new Lessor();
			result.setGoogleId(KeyFactory.createKey(Lessor.class.getSimpleName(), currentUser.getUserId()));
			result.setMail(currentUser.getEmail());
			em.persist(result);
		}
		return result;
	}

}
