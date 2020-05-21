package com.everywhere.dev.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name="image")
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="image_id")
    private Long id;
    @Column(name="path", nullable = false, unique = true)
    private String path;
    @Column( name="room_id")
    private Long room_id;
    @CreationTimestamp
    @Column( name="createdDate")
    private Date createdDate;
}
