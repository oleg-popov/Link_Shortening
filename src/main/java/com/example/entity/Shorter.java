package com.example.entity;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
//@RequiredArgsConstructor
@Entity
public class Shorter {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String hash;
    @Column(name = "original_url")
    private String originalUrl;


}
