package com.everywhere.dev.controller;

import com.everywhere.dev.model.EverywhereImages;
import com.everywhere.dev.model.EverywhereRooms;
import com.everywhere.dev.model.Room;
import com.everywhere.dev.model.RoomTokenRequest;
import com.everywhere.dev.service.AmazonService;
import com.everywhere.dev.service.ImageService;
import com.everywhere.dev.service.RoomService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RequestMapping("/room")
@Controller
public class SimpleController {
    @Autowired
    private RoomService service;
    @Autowired
    private ImageService imageService;
    @Autowired
    private AmazonService amazonService;

    @GetMapping("/greeting")
    public String greeting(@RequestParam(name="name", required = false,defaultValue = "World") String name, Model model ){
        model.addAttribute("name", name);
        return "greeting";
    }
    @GetMapping("/list")
    public String getAllRooms(Model model){
        List<EverywhereRooms> listRooms = service.findAll();
        model.addAttribute("listRooms",listRooms);
        return "listRooms";
    }
    @GetMapping("/detail/{id}")
    public String getRoom(@PathVariable(name="id") UUID id, Model model){
        EverywhereRooms room = service.findById(id);
        UUID roomId = room.getRoomId();
        List<EverywhereImages> images = imageService.findImagesByRoomId(roomId);
        model.addAttribute("images",images);
        model.addAttribute("roomId",room.getRoomId());
        model.addAttribute("title",room.getTitle());
        model.addAttribute("address",room.getAddress());
        model.addAttribute("price",room.getPrice());
        model.addAttribute("latitude",room.getLatitude());
        model.addAttribute("longitude",room.getLongitude());
        model.addAttribute("moveInDate",room.getMoveInDate());
        model.addAttribute("createdDate",room.getCreatedDate());
        model.addAttribute("bathRooms",room.getBathRooms());
        model.addAttribute("isParking",room.getIsParking());
        model.addAttribute("isPetFriendly",room.getIsPetFriendly());
        model.addAttribute("isSmoking",room.getIsSmoking());
        model.addAttribute("hasHydro",room.getHasHydro());
        model.addAttribute("hasHeat",room.getHasHeat());
        model.addAttribute("hasAirconditioning",room.getHasAirconditioning());
        model.addAttribute("hasWater",room.getHasWater());
        model.addAttribute("hasWifi",room.getHasWifi());
        model.addAttribute("hasTv",room.getHasTv());
        model.addAttribute("hasLanduary",room.getHasLanduary());
        model.addAttribute("hasDryer",room.getHasDryer());
        model.addAttribute("hasDishwasher",room.getHasDishwasher());
        model.addAttribute("hasFridge",room.getHasFridge());
        model.addAttribute("hasMicrowave",room.getHasMicrowave());
        model.addAttribute("description",room.getDescription());
        model.addAttribute("transportation",room.getTransportation());
        return "detailRoom";
    }
    @PostMapping("/create")
    public String createOrUpdateRoom(@RequestParam("files") MultipartFile[] files, EverywhereRooms room){
        ArrayList<EverywhereImages> images = new ArrayList<EverywhereImages>();
        for(MultipartFile file : files){
            EverywhereImages image = new EverywhereImages();
            image.setImagePath(amazonService.uploadFile(file));
            images.add(image);
        }

        service.saveRoomImages(room,images);
        return "redirect:/room/list";
    }
    @GetMapping("/create")
    public String createRoom(Model model){
        EverywhereRooms room = new EverywhereRooms();
        model.addAttribute("everywhereRooms",room);

        return "add-edit-room";
    }
}
