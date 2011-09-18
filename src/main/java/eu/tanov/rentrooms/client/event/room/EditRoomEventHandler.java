package eu.tanov.rentrooms.client.event.room;

import com.google.gwt.event.shared.EventHandler;

public interface EditRoomEventHandler extends EventHandler {
  void onEditRoom(EditRoomEvent event);
}
