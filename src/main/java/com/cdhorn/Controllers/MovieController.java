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

import java.text.SimpleDateFormat;
import java.util.Date;

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

    @RequestMapping(value = "/movie/{movieId}", method = RequestMethod.GET)
    public String movieDetail(@PathVariable("movieId") long movieId, Model model ) {
        Movie movie = movieRepo.findOne(movieId);
        model.addAttribute("movie", movie);
        return "movieDetail";
    }

    @RequestMapping(value = "/movie/{movieId}/reviews", method = RequestMethod.GET)
    public String getReviews(@PathVariable("movieId") long movieId, Model model) {
        Movie movie = movieRepo.findOne(movieId);
        System.out.println(movie.getReleasedate());
//        1977-05-25 00:00:00.0 is returned, yr, mth, date stored in db
        model.addAttribute("movie", movie);
        return "reviews";
    }

    @RequestMapping(value = "/movie/{movieId}/reviews", method = RequestMethod.POST)
    public String addReview(@PathVariable("movieId") long movieId,
                            @RequestParam("reviewername") String reviewername,
                            @RequestParam("rating") String rating,
                            @RequestParam("age") int age,
                            @RequestParam("gender") char gender,
                            @RequestParam("occupation") String occupation) {
        Movie movie = movieRepo.findOne(movieId);
        Review newReview = new Review(reviewername, rating, age, gender, occupation, movie);
        reviewRepo.save(newReview);
        return "redirect:/movie/" + movieId + "/reviews";
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

        Date formattedReleaseDate = new SimpleDateFormat("yyyy-MM-dd").parse(releasedate);
        Movie movie = new Movie(title, genre, imdblink, formattedReleaseDate);
        System.out.println("FORMATTED" + formattedReleaseDate);
//        returns FORMATTEDWed Jun 07 00:00:00 EDT 2017
            movieRepo.save(movie);

        return "redirect:/";
    }

    @RequestMapping(value = "/edit/{movieId}", method = RequestMethod.GET)
    public String editLandingPage(@PathVariable("movieId") long movieId, Model model) {
        Movie movie = movieRepo.findOne(movieId);
        model.addAttribute("movie", movie);
        return "edit";
    }


    @RequestMapping(value = "/edit/{movieId}", method = RequestMethod.POST)
    public String edit(@PathVariable("movieId") long movieId,
                            @RequestParam("title") String title,
                            @RequestParam("genre") String genre,
                            @RequestParam("imdblink") String imdblink,
                            @RequestParam("releasedate") String releasedate,
                            Model model) throws Exception{
        Movie movie = movieRepo.findOne(movieId);
        movie.setTitle(title);
        movie.setGenre(genre);
        movie.setImdblink(imdblink);
        Date formattedReleaseDate = new SimpleDateFormat("yyyy-MM-dd").parse(releasedate);
        movie.setReleasedate(formattedReleaseDate);
        movieRepo.save(movie);
        model.addAttribute(movie);
        return "edit";
    }

}

