package lab.java10.controllers;

import lab.java10.models.Rating;
import lab.java10.services.RatingCrudService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ratingControllerTest {

    @Mock
    RatingCrudService ratingCrudService;

    @InjectMocks
    RatingController ratingController;

    @Test
    void addRating() {

    }
}