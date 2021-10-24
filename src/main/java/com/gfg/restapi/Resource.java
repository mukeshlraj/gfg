package com.gfg.restapi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
public class Resource {

    @Autowired
    private List<Movie> movieList;

    // post, get, patch, put, delete

    @PostMapping("/v1/movie") // to tell spring we need a post api
    void create(@RequestBody Movie movie){
        this.movieList.add(movie);
    }

    @GetMapping("/v1/movie") // to tell spring we need a get api
    Movie get(@RequestParam("name") String name){
        for (Movie movie : movieList){
            if (movie.getName().equals(name)) {
                return movie;
            }
        }
        return null;
    }

    @PatchMapping("/v1/movie/{name}")
    String patch(@PathVariable("name") String name, @RequestBody Movie movie){
        for(Movie movie1: movieList){
            if (movie1.getName().equals(name)) {
                movie1.setRating(movie.getRating());
                return "Rating got updated";
            }
        }
        return "no movie found";
    }

    @PutMapping("/v1/movie/{name}")
    String put(@PathVariable("name") String name, @RequestBody Movie movie) {
        for(Movie movie1: movieList){
            if (movie1.getName().equals(name)) {
                movie1 = movie;
                return "Replaced movie object";
            }
        }
        return "no movie found";
    }

//    @DeleteMapping("/v1/movie")
//    String del(@RequestParam("name") String name) {
//        for(Movie movie : movieList) {
//            if (movie.getName().equals(name)){
//                movieList.remove(movie);
//                return "Movie got deleted";
//            }
//        }
//        return "No movie found";
//    }

    @DeleteMapping("/v1/movie")
    Object dele(@RequestParam("name") String name) {
        RestTemplate restTemplate = new RestTemplate();
        Movie movieResponse = restTemplate.getForObject("http://localhost:8080/v1/movie?name=avengers", Movie.class);
        movieList.remove(movieResponse);
        return "deleted movie";
    }
}
