package com.abiyyu.projects.libary.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "film",uniqueConstraints = @UniqueConstraint(columnNames = {"name"}))
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Film {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 50,unique = true)
    private String name;

    private Integer duration;

    private Integer rating;

    @Column(name = "release_date")
    private String releaseDate;

    @ManyToOne
    @JoinColumn(name = "id_studio",referencedColumnName = "id")
    private Studio studio;

    @Lob
    @Column(columnDefinition = "MEDIUMBLOB")
    private String posters;
    @Lob
    @Column(columnDefinition = "MEDIUMBLOB")
    private String hero;
    private Double regularPrice;
    private Double premiumPrice;

    @Column(columnDefinition = "TEXT")
    private String description;

    private String genre;

    @OneToMany(mappedBy = "film",fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private List<Schedule> schedule;
}
