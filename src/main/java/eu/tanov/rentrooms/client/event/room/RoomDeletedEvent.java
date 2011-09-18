package eu.tanov.rentrooms.client.event.room;

import com.google.gwt.event.shared.GwtEvent;

public class RoomDeletedEvent extends GwtEvent<RoomDeletedEventHandler>{
  public static Type<RoomDeletedEventHandler> TYPE = new Type<RoomDeletedEventHandler>();
  
  @Override
  public Type<RoomDeletedEventHandler> getAssociatedType() {
    return TYPE;
  }

  @Override
  protected void dispatch(RoomDeletedEventHandler handler) {
    handler.onRoomDeleted(this);
  }
}
