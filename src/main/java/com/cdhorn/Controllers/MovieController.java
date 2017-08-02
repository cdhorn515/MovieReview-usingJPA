package com.cdhorn.Controllers;

import com.cdhorn.Interfaces.MovieRepository;
import com.cdhorn.Interfaces.ReviewRepository;
import com.cdhorn.Models.Movie;
import com.cdhorn.Models.Review;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MovieController {

    @Autowired
    MovieRepository movieRepo;

    @Autowired
    ReviewRepository reviewRepo;

    @RequestMapping("/")
    public String index(Model model) {
        Iterable<Movie> movies = movieRepo.findAll();
        model.addAttribute("movies", movies);
        return "index";
    }

    @RequestMapping("/movie/{movieId}")
    public String movieDetail(@PathVariable("movieId") long movieId, Model model ) {
        Movie movie = movieRepo.findOne(movieId);
        model.addAttribute("movie", movie);
        return "movieDetail";
    }

    @RequestMapping(value = "/movie/{movieId}/addReview", method = RequestMethod.POST)
    public String addReview(@PathVariable("movieId") long movieId,
                            @RequestParam("reviewername") String reviewername,
                            @RequestParam("rating") String rating,
                            @RequestParam("age") int age,
                            @RequestParam("gender") char gender,
                            @RequestParam("occupation") String occupation) {
        Movie movie = movieRepo.findOne(movieId);
        Review newReview = new Review(reviewername, rating, age, gender, occupation, movie);
        reviewRepo.save(newReview);
        return "redirect:/movie/" + movieId;
    }

    @RequestMapping(value = "/addMovie", method = RequestMethod.GET)
    public String addMovieLandingPage() {
        return "addMovie";
    }

    @RequestMapping(value = "/addMovie", method = RequestMethod.POST)
    public String addMovie(@RequestParam("title") String title,
                           @RequestParam("genre") String genre,
                           @RequestParam("imdblink") String imdblink,
                           @RequestParam("releasedate") String releasedate) throws Exception {

//        Date formattedReleaseDate = new SimpleDateFormat("yyyy-MM-dd").parse(releasedate);
        Movie movie = new Movie(title, genre, imdblink, releasedate);
            movieRepo.save(movie);

        return "redirect:/";
    }


}

