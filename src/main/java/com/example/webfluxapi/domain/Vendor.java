package com.example.webfluxapi.domain;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document
public class Vendor {

    @Id
    private String id;
    private String firstName;
    private String lastName;
}
