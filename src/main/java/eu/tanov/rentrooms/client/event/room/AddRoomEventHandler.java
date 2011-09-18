package eu.tanov.rentrooms.client.event.room;

import com.google.gwt.event.shared.EventHandler;

public interface AddRoomEventHandler extends EventHandler {
  void onAddRoom(AddRoomEvent event);
}
