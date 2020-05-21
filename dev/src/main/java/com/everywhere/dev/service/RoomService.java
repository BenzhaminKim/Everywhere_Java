package com.everywhere.dev.service;

import com.everywhere.dev.Config.JwtUtil;
import com.everywhere.dev.Exceptions.RoomNotFoundException;
import com.everywhere.dev.Repository.RoomRepository;
import com.everywhere.dev.Repository.UserRepository;
import com.everywhere.dev.model.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class RoomService {
    @Autowired
    private RoomRepository repository;
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ImageService imageService;
    @Autowired
    private CustomUserDetailsService userDetailsService;
    public List<EverywhereRooms> findAll(){
        return repository.findAllRooms();
    }

    public List<EverywhereRooms> findRoomsByUserId(UUID user_id){
        return repository.findAllRoomsByUserId(user_id);
    }

    public List<EverywhereRooms> findRoomsByUserToken(Token token){
        String username = jwtUtil.extractUsername(token.getToken());
        UUID userId = userDetailsService.findUserIdByUsername(username);
        return findRoomsByUserId(userId);

    }

    public EverywhereRooms findById(UUID id){
        return repository.findById(id).orElseThrow(()-> new RoomNotFoundException(id));
    }


    public EverywhereRooms createOrUpdateRoom(EverywhereRooms entity){
        UUID userId = UUID.fromString("64390bd2-7152-4811-b0b0-56328438d9b1");
        entity.setUserId(userId);
        if(entity.getRoomId() == null){
            entity = repository.save(entity);
            return entity;
        }else{
            Optional<EverywhereRooms> room = repository.findById(entity.getRoomId());
            if(room.isPresent()){
                EverywhereRooms newEntity = room.get();
                newEntity.setTitle((entity.getTitle()));
                newEntity.setPrice((entity.getPrice()));
                newEntity.setDescription((entity.getDescription()));

                return newEntity;
            }else{
                entity = repository.save(entity);
                return entity;
            }
        }
    }

    @Transactional
    public EverywhereRooms save(RoomTokenRequest roomTokenRequest, List<EverywhereImages> images){
        Token token = roomTokenRequest.getToken();
        EverywhereRooms room = roomTokenRequest.getRoom();

        String username = jwtUtil.extractUsername(token.getToken());
        UUID user_id = userRepository.findUserIdByUsername(username);
        room.setUserId(user_id);
        repository.save(room);
        for(EverywhereImages image : images ){
            image.setRoomId(room.getRoomId());
            imageService.save(image);
        }
        return room;
    }
    @Transactional
    public RoomImage saveRoomImages(EverywhereRooms everywhereRooms, List<EverywhereImages> images){
        RoomImage roomImage = new RoomImage();
        List<EverywhereImages> roomImages = new ArrayList<EverywhereImages>() ;

        UUID userId = UUID.fromString("64390bd2-7152-4811-b0b0-56328438d9b1");
        everywhereRooms.setUserId(userId);
        repository.save(everywhereRooms);
        roomImage.setRoom(everywhereRooms);
        for(EverywhereImages image : images){
            image.setRoomId(everywhereRooms.getRoomId());
            imageService.save(image);
            roomImages.add(image);
        }
        roomImage.setImages(roomImages);
        return roomImage;
    }

    public EverywhereRooms update(RoomTokenRequest roomTokenRequest,UUID roomId){
        Token token = roomTokenRequest.getToken();
        String username = jwtUtil.extractUsername(token.getToken());
        UUID user_id_token = userRepository.findUserIdByUsername(username);
        UUID user_id_room = repository.findUserIdByRoomId(roomId);
        EverywhereRooms newRoom = roomTokenRequest.getRoom();

        if(user_id_room == user_id_token){
            return repository.findById(roomId).map(room -> {
                room.setTitle(newRoom.getTitle());
                room.setDescription(newRoom.getDescription());
                room.setPrice(newRoom.getPrice());
                return repository.save(room);
            }).orElseGet(()->{
                newRoom.setRoomId(roomId);
                return repository.save(newRoom);
            });
        }
        newRoom.setRoomId(roomId);
        return repository.save(newRoom);
    }

    public void deleteByIdAndCheckToken(UUID roomId,Token token) {
        String username = jwtUtil.extractUsername(token.getToken());
        UUID user_id_token = userRepository.findUserIdByUsername(username);
        UUID user_id_room = repository.findUserIdByRoomId(roomId);

        if(user_id_token == user_id_room){
            repository.deleteById(roomId);
        }
    }
}
