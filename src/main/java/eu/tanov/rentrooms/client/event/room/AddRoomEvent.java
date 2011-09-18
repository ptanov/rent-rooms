package eu.tanov.rentrooms.client.event.room;

import com.google.gwt.event.shared.GwtEvent;

public class AddRoomEvent extends GwtEvent<AddRoomEventHandler> {
  public static Type<AddRoomEventHandler> TYPE = new Type<AddRoomEventHandler>();
  
  @Override
  public Type<AddRoomEventHandler> getAssociatedType() {
    return TYPE;
  }

  @Override
  protected void dispatch(AddRoomEventHandler handler) {
    handler.onAddRoom(this);
  }
}
