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

@Controller
public class ReviewController {

    @Autowired
    MovieRepository movieRepo;

    @Autowired
    ReviewRepository reviewRepo;

    @Autowired
    UserRepository userRepo;

    @RequestMapping(value = "/movie/{movieId}/reviews", method = RequestMethod.GET)
    public String getReviews(@PathVariable("movieId") long movieId, Model model,
                             Principal principal) {
        Movie movie = movieRepo.findOne(movieId);
        String username = principal.getName();
        model.addAttribute("username", username);
        model.addAttribute("movie", movie);
        return "reviews";
    }

    @RequestMapping(value = "/movie/{movieId}/reviews", method = RequestMethod.POST)
    public String addReview(@PathVariable("movieId") long movieId,
                            @RequestParam("rating") String rating,
                            Principal principal) {
        Movie movie = movieRepo.findOne(movieId);
        String username = principal.getName();
        User user = userRepo.findByUsername(username);
        Review newReview = new Review();
        newReview.setMovie(movie);
        newReview.setRating(rating);
        newReview.setUser(user);
        reviewRepo.save(newReview);
        return "redirect:/movie/" + movieId + "/reviews";
    }

    @RequestMapping(value = "/movie/myReviews")
    public String myReviews(Model model, Principal principal) {
        User user = userRepo.findByUsername(principal.getName());
        Iterable<Review> reviews = reviewRepo.findAllByUser(user);
        model.addAttribute("reviews", reviews);
        model.addAttribute("user", user);
        return "myReviews";
    }

}
