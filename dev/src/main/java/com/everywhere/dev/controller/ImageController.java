package com.everywhere.dev.controller;

import com.everywhere.dev.model.EverywhereImages;
import com.everywhere.dev.model.Image;
import com.everywhere.dev.model.Room;
import com.everywhere.dev.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequestMapping("/api/")
@RestController
@CrossOrigin
@RequiredArgsConstructor
public class ImageController {
    @Autowired
    private ImageService service;

    @GetMapping("/image/{id}")
    public List<EverywhereImages> getImages(@PathVariable UUID id){
        return service.findImagesByRoomId(id);
    }
}
