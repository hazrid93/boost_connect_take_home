package com.axiata.boostDemo.dto;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class UserDTO {
    private String id;
    private String title;
    private String firstName;
    private String lastName;
    private String picture;
}