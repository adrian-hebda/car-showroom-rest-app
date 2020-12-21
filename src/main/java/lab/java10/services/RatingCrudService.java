package lab.java10.services;

import lab.java10.models.Rating;
import lab.java10.repositories.RatingRepository;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RatingCrudService implements CrudService<Rating> {

    private RatingRepository ratingRepository;

    public RatingCrudService(RatingRepository ratingRepository) {
        this.ratingRepository = ratingRepository;
    }

    @Override
    public Optional<Rating> get(long id) {
        return ratingRepository.findById(id);
    }

    @Override
    public List<Rating> getAll() {
        return ratingRepository.findAll();
    }

    @Override
    public void save(Rating rating) {
        ratingRepository.save(rating);
    }

    @Override
    public void update(Rating rating) {
        ratingRepository.save(rating);
    }

    @Override
    public void delete(Rating rating) {
        ratingRepository.delete(rating);
    }
}
