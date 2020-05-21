package com.everywhere.dev.controller;

import com.everywhere.dev.Exceptions.RoomNotFoundException;
import com.everywhere.dev.Repository.RoomRepository;
import com.everywhere.dev.model.*;
import com.everywhere.dev.service.AmazonService;
import com.everywhere.dev.service.ImageService;
import com.everywhere.dev.service.RoomService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RequestMapping("/api/")
@RestController
@CrossOrigin
@RequiredArgsConstructor
public class RoomController {
    @Autowired
    private RoomService service;
    @Autowired
    private ImageService imageService;

    @Autowired
    private AmazonService amazonServiceservice;

    @GetMapping("/rooms")
    public List<RoomImage> rooms(){
        List<EverywhereRooms> rooms = service.findAll();
        List<RoomImage> roomImages = new ArrayList<RoomImage>();

        for(EverywhereRooms room : rooms){
            RoomImage roomImage = new RoomImage();
            UUID roomId = room.getRoomId();
            List<EverywhereImages> images = imageService.findImagesByRoomId(roomId);
            roomImage.setRoom(room);
            roomImage.setImages(images);
            roomImages.add(roomImage);
        }
        return roomImages;
    }

    @PostMapping("/rooms/myrooms")
    public List<EverywhereRooms> myRooms(@RequestBody Token token){return service.findRoomsByUserToken(token);}

    @GetMapping("/roomdetail/{id}")
    public EverywhereRooms getRoom(@PathVariable UUID id){
        return service.findById(id);
    }

    @PostMapping("/rooms")
    public EverywhereRooms createRoom(@RequestParam(value="files") MultipartFile[] files, @RequestParam(value="RoomTokenRequest") String jsonString) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        RoomTokenRequest roomTokenRequest = objectMapper.readValue(jsonString,RoomTokenRequest.class);
        ArrayList<EverywhereImages> images = new ArrayList<EverywhereImages>();
        for(MultipartFile file : files){
            System.out.println(file.getOriginalFilename());
            EverywhereImages image = new EverywhereImages();
            image.setImagePath(amazonServiceservice.uploadFile(file));
            images.add(image);
        }

        return service.save(roomTokenRequest,images);
    }
    
    @PutMapping("/rooms/{id}")
    public EverywhereRooms updateRoom(@Valid @RequestBody RoomTokenRequest roomTokenRequest,@PathVariable UUID id){
        return service.update(roomTokenRequest, id);
    }
    @DeleteMapping("/rooms/{id}")
    public void deleteRoom(@PathVariable UUID id,@RequestBody Token token){
        service.deleteByIdAndCheckToken(id,token);
    }
    /*
    *  @DeleteMapping("/deleteFile")
    public String deleteFile(@RequestPart(value="url")String fileUrl){
        return amazonServiceservice.deleteFileFromS3Bucket(fileUrl);
    }
    * */
}
