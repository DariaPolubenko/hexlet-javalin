package org.example.hexlet.dto.models;

import org.example.hexlet.model.Course;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CoursePage {
    private Course course;
}
