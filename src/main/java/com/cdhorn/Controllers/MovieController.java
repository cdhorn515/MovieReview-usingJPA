package com.cdhorn.Controllers;

import com.cdhorn.Interfaces.MovieRepository;
import com.cdhorn.Interfaces.ReviewRepository;
import com.cdhorn.Interfaces.UserRepository;
import com.cdhorn.Models.Movie;
import com.cdhorn.Models.Review;
import com.cdhorn.Models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
public class MovieController {

    @Autowired
    MovieRepository movieRepo;

    @Autowired
    UserRepository userRepo;

    @Autowired
    ReviewRepository reviewRepo;

    @RequestMapping("/")
    public String index(Model model, Principal principal) {
        String username = principal.getName();
        User user = userRepo.findByUsername(username);
        Iterable<Review> reviews = reviewRepo.findAllByUser(user);
        Iterable<Movie> movies = movieRepo.findAll();
        model.addAttribute("movies", movies);
        model.addAttribute("reviews", reviews);
        return "index";
    }

    @RequestMapping(value = "/movie/{movieId}", method = RequestMethod.GET)
    public String movieDetail(@PathVariable("movieId") long movieId, Model model ) {
        Movie movie = movieRepo.findOne(movieId);
        model.addAttribute("movie", movie);
        return "movieDetail";
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

    /*
    use for endpoints for my reviews
            User user = userRepo.findByUsername(principal.getName());
        Iterable<Secret> secrets = secretRepo.findAllByUser(user);
        model.addAttribute("secrets", secrets);
        model.addAttribute("user", user);

     */

}

