package lab.java10.services;


import lab.java10.models.CarShowroom;
import lab.java10.repositories.CarShowroomRepository;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

@Service
public class CarShowroomCrudService implements CrudService<CarShowroom> {

    private CarShowroomRepository carShowroomRepository;
    @PersistenceContext
    private EntityManager em;

    public CarShowroomCrudService(CarShowroomRepository carShowroomRepository) {
        this.carShowroomRepository = carShowroomRepository;
    }

    @Override
    public Optional<CarShowroom> get(long id) {
        return carShowroomRepository.findById(id);
    }

    @Override
    public List<CarShowroom> getAll() {
        return carShowroomRepository.findAll();
    }

    @Override
    public void save(CarShowroom carShowroom) {
        carShowroomRepository.save(carShowroom);
    }

    public CarShowroom saveAndGet(CarShowroom carShowroom) {
        return carShowroomRepository.save(carShowroom);
    }

    @Override
    public void update(CarShowroom carShowroom) {
        carShowroomRepository.save(carShowroom);
    }

    @Override
    public void delete(CarShowroom carShowroom) {
        carShowroomRepository.delete(carShowroom);
    }

    public CarShowroom delete(Long id) {
        CarShowroom showroom;
        try {
            showroom = carShowroomRepository.getOne(id);
            carShowroomRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            System.out.println(e.getMessage());
            return null;
        }

        return showroom;
    }

    public Double getFill(Long id) {
        Object[] maxActual;

        String queryText = "SELECT c.maxCarAmount as max, c.vehicles.size as actual FROM carshowroom c where c.id = :id";
        TypedQuery<Object[]> query = em.createQuery(queryText, Object[].class).setParameter("id", id);
        try {
            maxActual = query.getSingleResult();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }


        return Double.valueOf(maxActual[1].toString()) / Double.valueOf(maxActual[0].toString());
    }
}
