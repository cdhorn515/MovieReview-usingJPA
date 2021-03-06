package com.cdhorn.Interfaces;

import com.cdhorn.Models.Review;
import com.cdhorn.Models.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends CrudRepository<Review, Long> {

    List<Review> findAllByUser(User user);
}
