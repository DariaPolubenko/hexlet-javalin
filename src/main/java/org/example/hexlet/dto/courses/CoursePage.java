package org.example.hexlet.dto.courses;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.example.hexlet.Course;

@AllArgsConstructor
@Getter
public class CoursePage {
    private Course course;
}