package eu.tanov.rentrooms.client.event.room;

import com.google.gwt.event.shared.EventHandler;

public interface RoomDeletedEventHandler extends EventHandler {
  void onRoomDeleted(RoomDeletedEvent event);
}
