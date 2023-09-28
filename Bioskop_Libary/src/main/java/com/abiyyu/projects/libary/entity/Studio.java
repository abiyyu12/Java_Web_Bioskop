package com.abiyyu.projects.libary.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "studio",uniqueConstraints = @UniqueConstraint(columnNames = {"name"}))
public class Studio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 50,unique = true)
    private String name;

    @OneToMany(mappedBy = "studio",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<Film> film = new ArrayList<>();
}
