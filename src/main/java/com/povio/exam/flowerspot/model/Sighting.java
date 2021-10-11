package com.povio.exam.flowerspot.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name = "sighting")
public class Sighting {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sighting_id")
    private Long id;

    @Column(name = "sighting_long")
    private BigDecimal longitude;

    @Column(name = "sighting_lat")
    private BigDecimal latitude;

    @Column(name = "sighting_image")
    private String image;

    @Column(name = "sighting_quote")
    private String quote;

    @ManyToOne
    @JoinColumn(name = "sighting_user_id", referencedColumnName = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "sighting_flower_id", referencedColumnName = "flower_id")
    private Flower flower;

    @ManyToMany
    @JoinTable(
            name = "user_sighting_likes",
            joinColumns = @JoinColumn(name = "like_sighting_id"),
            inverseJoinColumns = @JoinColumn(name = "like_user_id"))
    private List<User> likedByUsers;
}