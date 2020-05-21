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
@Table(name="everywhere_users")
public class EverywhereUsers {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="userid")
    private UUID id;
    @Column(name="username", nullable = false, unique = true)
    private String username;
    @Column( name="password")
    private String password;
    @Column( name="email")
    private String email;
    @Column(name="createddate")
    private Date createdDate;
}
