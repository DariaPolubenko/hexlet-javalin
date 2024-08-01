package org.example.hexlet.dto.courses;

import lombok.Getter;
import lombok.Setter;
import org.example.hexlet.dto.BasePage;
import org.example.hexlet.model.Course;

import java.util.List;

@Getter
public class CoursesPage extends BasePage {
    private List<Course> courses;
    @Setter
    public String header;
    @Setter
    public String term;

    public CoursesPage(List<Course> courses) {
        this.courses = courses;
    }

}
