package eu.tanov.rentrooms.server;

import javax.persistence.EntityManager;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import eu.tanov.rentrooms.client.GreetingService;
import eu.tanov.rentrooms.server.model.Lessor;
import eu.tanov.rentrooms.server.model.Room;
import eu.tanov.rentrooms.shared.FieldVerifier;

/**
 * The server side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class GreetingServiceImpl extends RemoteServiceServlet implements GreetingService {

	public String greetServer(String input) throws IllegalArgumentException {
		testJPA();

		// Verify that the input is valid.
		if (!FieldVerifier.isValidName(input)) {
			// If the input is not valid, throw an IllegalArgumentException back to
			// the client.
			throw new IllegalArgumentException("Name must be at least 4 characters long");
		}

		String serverInfo = getServletContext().getServerInfo();
		String userAgent = getThreadLocalRequest().getHeader("User-Agent");

		// Escape data from the client to avoid cross-site script vulnerabilities.
		input = escapeHtml(input);
		userAgent = escapeHtml(userAgent);

		return "Hello, " + input + "!<br><br>I am running " + serverInfo + ".<br><br>It looks like you are using:<br>"
				+ userAgent;
	}

	private void testJPA() {
		//XXX test for persistence:
		
		final Key roomKey;
		{//persist
			final EntityManager em = EMF.get().createEntityManager();
			final Lessor lessor = new Lessor();
			lessor.setGoogleId(KeyFactory.createKey(Lessor.class.getSimpleName(), "googleID1"));
			lessor.setMail("mail1");
			em.persist(lessor);
			em.close();
		}
		{
			final EntityManager em = EMF.get().createEntityManager();
			final Lessor lessor = em.getReference(Lessor.class, KeyFactory.createKey(Lessor.class.getSimpleName(), "googleID1"));

			final Room room = new Room();
			room.setName("room1");
			room.setOwner(lessor);
//			room.setKey(KeyFactory.createKey(lessor.getGoogleId(), Room.class.getSimpleName(), 11));
			em.persist(room);
			System.out.println("roomid: "+room.getKey());
			em.close();
			roomKey = room.getKey();
			System.out.println("roomid: "+roomKey);
		}
		
		{//load
			final EntityManager em = EMF.get().createEntityManager();
			final Lessor lessor = em.getReference(Lessor.class, KeyFactory.createKey(Lessor.class.getSimpleName(), "googleID1"));
			System.out.println("mail:"+lessor.getMail());
			System.out.println("rooms:"+lessor.getRooms());
			for (Room room : lessor.getRooms()) {
				System.out.println("room:"+room.getKey()+": "+room.getName()+", "+room.getOwner().getMail());
			}

			final Room room = em.getReference(Room.class, roomKey);
			System.out.println("room:"+room.getKey()+": "+room.getName()+", "+room.getOwner().getMail());
			em.close();
		}
	}
	/**
	 * Escape an html string. Escaping data received from the client helps to
	 * prevent cross-site script vulnerabilities.
	 * 
	 * @param html
	 *            the html string to escape
	 * @return the escaped string
	 */
	private String escapeHtml(String html) {
		if (html == null) {
			return null;
		}
		return html.replaceAll("&", "&amp;").replaceAll("<", "&lt;").replaceAll(">", "&gt;");
	}
}
