package eu.tanov.rentrooms.client.event.room;

import com.google.gwt.event.shared.EventHandler;

public interface EditRoomCancelledEventHandler extends EventHandler {
  void onEditRoomCancelled(EditRoomCancelledEvent event);
}
