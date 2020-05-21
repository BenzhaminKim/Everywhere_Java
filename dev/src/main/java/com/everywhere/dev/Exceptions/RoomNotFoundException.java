package com.everywhere.dev.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.UUID;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class RoomNotFoundException extends RuntimeException {

    public RoomNotFoundException(UUID id){
        super("Could not find room "+ id);
    }
}
