package com.everywhere.dev.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Data
@Table(name="everywhere_rooms")
public class EverywhereRooms {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="roomid")
    private UUID roomId;

    @Column(name="userid")
    private UUID userId;

    @Column(name="title")
    private String title;

    @Column(name="address")
    private String address;

    @Column(name="price")
    private Double price;

    @Column(name="latitude")
    private Double latitude;

    @Column(name="longitude")
    private Double longitude;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name="moveindate")
    private Date moveInDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name="createddate")
    private Date createdDate;

    @Column(name="bathrooms")
    private Double bathRooms;

    @Column(name="isparking")
    private Boolean isParking;

    @Column(name="ispetfriendly")
    private Boolean isPetFriendly;

    @Column(name="issmoking")
    private Boolean isSmoking;

    @Column(name="hashydro")
    private Boolean hasHydro;

    @Column(name="hasheat")
    private Boolean hasHeat;

    @Column(name="hasairconditioning")
    private Boolean hasAirconditioning;

    @Column(name="haswater")
    private Boolean hasWater;

    @Column(name="haswifi")
    private Boolean hasWifi;

    @Column(name="hastv")
    private Boolean hasTv;

    @Column(name="haslanduary")
    private Boolean hasLanduary;

    @Column(name="hasdryer")
    private Boolean hasDryer;
    @Column(name="hasdishwasher")
    private Boolean hasDishwasher;

    @Column(name="hasfridge")
    private Boolean hasFridge;

    @Column(name="hasmicrowave")
    private Boolean hasMicrowave;

    @Column(name="description")
    private String description;

    @Column(name="transportation")
    private String transportation;

}
