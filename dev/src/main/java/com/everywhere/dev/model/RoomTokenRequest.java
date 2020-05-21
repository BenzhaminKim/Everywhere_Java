package com.everywhere.dev.model;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class RoomTokenRequest {
    private EverywhereRooms room;
    private Token token;

    public EverywhereRooms getRoom(){
        return this.room;
    }
    public Token getToken(){
        return this.token;
    }
    public void setRoom(EverywhereRooms room){
        this.room = room;
    }
    public void setToken(Token token){
        this.token = token;
    }
}
