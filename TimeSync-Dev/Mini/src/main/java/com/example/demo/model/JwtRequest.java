package com.example.demo.model;

import lombok.*;

@Data
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor

public class JwtRequest {
    private String email;
    private String password;
}
