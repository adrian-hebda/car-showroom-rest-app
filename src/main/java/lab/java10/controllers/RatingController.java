package lab.java10.controllers;

import lab.java10.models.Rating;
import lab.java10.services.RatingCrudService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/rating")
public class RatingController {

    RatingCrudService ratingCrudService;

    public RatingController(RatingCrudService ratingCrudService) {
        this.ratingCrudService = ratingCrudService;
    }

    @PostMapping
    public void addRating(@RequestBody Rating rating) {
        ratingCrudService.save(rating);
    }
}
