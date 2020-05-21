package com.everywhere.dev.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Data
@Table(name="room")
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="room_id")
    private Long id;
    @Column(name="title")
    private String title;
    @Column(name="content")
    private String content;
    @Column(name="price")
    private Double price;
    @CreationTimestamp
    @Column(name="createdDate")
    private Date createdDate;
    @Column(name="user_id")
    private Long user_id;

    /*@JsonBackReference
    @ManyToOne
    @JoinColumn(name="user_id_FK", nullable = false)
    private User user_id_FK;*/
}
