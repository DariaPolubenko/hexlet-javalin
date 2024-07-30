package org.example.hexlet;

public class NamedRoutes {

    public static String mainPath() {
        return "/";
    }

    public static String helloPath() {
        return "/hello";
    }

    public static String coursesPath() {
        return "/courses";
    }

    public static String coursesPath(Long id) {
        return coursesPath(String.valueOf(id));
    }

    public static String coursesPath(String id) {
        return "/courses/" + id;
    }

    public static String buildCoursesPath() {
        return "/courses/build";
    }



    public static String usersPath() {
        return "/users";
    }

    public static String usersPath(Long id) {
        return usersPath(String.valueOf(id));
    }

    public static String usersPath(String id) {
        return "/users/" + id;
    }

    public static String usersPath(Long id, Long postId) {
        return usersPath(String.valueOf(id), String.valueOf(postId));
    }

    public static String usersPath(String id, String postId) {
        return "/users/" + id + "/post/" + postId;
    }

    public static String buildUsersPath() {
        return "/users/build";
    }

}
