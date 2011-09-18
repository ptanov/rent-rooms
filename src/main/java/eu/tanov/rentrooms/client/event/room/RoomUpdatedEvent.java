package eu.tanov.rentrooms.client.event.room;

import com.google.gwt.event.shared.GwtEvent;

import eu.tanov.rentrooms.shared.model.RoomDTO;

public class RoomUpdatedEvent extends GwtEvent<RoomUpdatedEventHandler>{
  public static Type<RoomUpdatedEventHandler> TYPE = new Type<RoomUpdatedEventHandler>();
  private final RoomDTO updatedRoom;
  
  public RoomUpdatedEvent(RoomDTO updatedRoom) {
    this.updatedRoom = updatedRoom;
  }
  
  public RoomDTO getUpdatedRoom() { return updatedRoom; }
  

  @Override
  public Type<RoomUpdatedEventHandler> getAssociatedType() {
    return TYPE;
  }

  @Override
  protected void dispatch(RoomUpdatedEventHandler handler) {
    handler.onRoomUpdated(this);
  }
}
