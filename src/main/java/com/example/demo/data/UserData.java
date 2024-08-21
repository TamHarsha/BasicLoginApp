package com.example.demo.data;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "UserInfo")
public class UserData {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @NotEmpty(message = "User Name cannot be empty")
    private String userName;

    @NotEmpty(message = "Password cannot be empty")
    private String password;

    @NotEmpty(message = "FirstName cannot be empty")
    private String firstName;
    private String lastName;

    @Email(message = "Email should be valid")
    @NotEmpty(message = "Email cannot be empty")
    private String email;
}
