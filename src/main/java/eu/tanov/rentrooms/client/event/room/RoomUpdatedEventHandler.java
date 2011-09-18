package eu.tanov.rentrooms.client.event.room;

import com.google.gwt.event.shared.EventHandler;

public interface RoomUpdatedEventHandler extends EventHandler{
  void onRoomUpdated(RoomUpdatedEvent event);
}
