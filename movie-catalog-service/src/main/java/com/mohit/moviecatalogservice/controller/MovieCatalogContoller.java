package com.mohit.moviecatalogservice.controller;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.reactive.function.client.WebClientAutoConfiguration;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.mohit.moviecatalogservice.model.CatalogItem;
import com.mohit.moviecatalogservice.model.Movie;
import com.mohit.moviecatalogservice.model.Rating;
import com.mohit.moviecatalogservice.model.UserRating;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;


@RestController
@RequestMapping("/catalog")
public class MovieCatalogContoller {

	@Autowired
	private RestTemplate restTemplate;
	
	@RequestMapping("/{userId}")
	@HystrixCommand(fallbackMethod ="getFallbackCatalog",
	commandProperties = {
	 @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value="2000"),
	 @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "5"), 
     @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "50"), 
     @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "5000") })
	public List<CatalogItem> getList(@PathVariable("userId") String userId){
		
		//Movie movieObject=restTemplate.getForObject("http://localhost:8081/movies/foo",  Movie.class);
		
	
		
		/*UserRating ratings= restTemplate
				.getForObject("http://localhost:8083/ratingdata/users/"+userId, UserRating.class);*/
		
		//@LoadBalanced --after adding this in classpath you can directly call
		//service name instead of localhost
		UserRating userRating = restTemplate.getForObject("http://ratings-data-service/ratingsdata/user/" + userId, UserRating.class);

        return userRating.getRatings().stream()
                .map(rating -> {
                    Movie movie = restTemplate.getForObject("http://movie-info-service/movies/" + rating.getMovieId(), Movie.class);
                    return new CatalogItem(movie.getName(), movie.getDescription(), rating.getRating());
                })
                .collect(Collectors.toList());
		
		
		
		/*
		 * return Collections.singletonList( new CatalogItem("Transformers","Test",3) );
		 */
	}
	
	public List<CatalogItem> getFallbackCatalog(@PathVariable("userId") String userId){
		return Arrays.asList(new CatalogItem("No Movie Bro", "", 0));
		
	}
	
}

/*
Alternative WebClient way
Movie movie = webClientBuilder.build().get().uri("http://localhost:8082/movies/"+ rating.getMovieId())
.retrieve().bodyToMono(Movie.class).block();
*/