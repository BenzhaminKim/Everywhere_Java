package com.everywhere.dev.service;

import com.everywhere.dev.Repository.ImageRepository;
import com.everywhere.dev.model.EverywhereImages;
import com.everywhere.dev.model.Image;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ImageService {
    @Autowired
    private ImageRepository imageRepository;

    public EverywhereImages save(EverywhereImages image){
        return imageRepository.save(image);
    }

    public Optional<EverywhereImages> finaById(UUID id){
        return imageRepository.findById(id);
    }
    public List<EverywhereImages> findImagesByRoomId(UUID id){
        return imageRepository.findImagesByRoomId(id);
    }
}
