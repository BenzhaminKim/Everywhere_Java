package com.everywhere.dev.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name="everywhere_images")
public class EverywhereImages {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="imageid")
    private UUID imageId;

    @Column( name="roomid")
    private UUID roomId;

    @Column(name="imagepath", nullable = false, unique = true)
    private String imagePath;

    @Column( name="createddate")
    private Date createdDate;
}
