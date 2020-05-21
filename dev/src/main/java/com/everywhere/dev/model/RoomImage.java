package com.everywhere.dev.model;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
public class RoomImage {
    private EverywhereRooms room;
    private List<EverywhereImages> images;

    public EverywhereRooms getRoom(){
        return this.room;
    }
    public List<EverywhereImages> getImages(){
        return this.images;
    }
    public void setRoom(EverywhereRooms room){
        this.room = room;
    }
    public void setImages(List<EverywhereImages> images){
        this.images = images;
    }
}
