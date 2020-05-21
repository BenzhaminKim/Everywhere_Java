package com.everywhere.dev.model;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.UUID;

public class LocationVM {
    private UUID roomId;

    private UUID userId;

    private String title;

    private String address;

    private Double price;

    private Double latitude;

    private Double longitude;

    private String imagePath;

}
