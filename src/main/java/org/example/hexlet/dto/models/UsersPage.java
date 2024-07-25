package org.example.hexlet.dto.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.example.hexlet.model.User;

import java.util.List;

@Getter
@AllArgsConstructor
public class UsersPage {
    List<User> users;
}
