package com.mohit.movieinfoservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.mohit.movieinfoservice.model.Movie;
import com.mohit.movieinfoservice.model.MovieSummary;

@RestController
@RequestMapping("/movies")
public class MovieController {
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Value("${api.key}")
    private String apiKey;

	@RequestMapping("/{movieId}")
    public Movie getMovieInfo(@PathVariable("movieId") String movieId) {
       // MovieSummary movieSummary = restTemplate.getForObject("https://api.themoviedb.org/3/movie/" + movieId + "?api_key=" +  apiKey, MovieSummary.class);
       // return new Movie(movieId, movieSummary.getTitle(), movieSummary.getOverview());
        
       // return new Movie(movieId, "Hello", "Mast");

		 MovieSummary movieSummary = restTemplate.getForObject("https://api.themoviedb.org/3/movie/100?api_key=2357705fe49e72fe55f0a52dc29c3983", MovieSummary.class);
	       return new Movie(movieId, movieSummary.getTitle(), movieSummary.getOverview());
	        
    }
	
}
