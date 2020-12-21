package lab.java10.services;

import lab.java10.annotations.ToExport;
import lab.java10.models.CarShowroom;
import lab.java10.models.Vehicle;
import lab.java10.repositories.VehicleRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Optional;

@Service
public class VehicleCrudService implements CrudService<Vehicle> {

    VehicleRepository vehicleRepository;

    @PersistenceContext
    private EntityManager em;

    public VehicleCrudService(VehicleRepository vehicleRepository) {
        this.vehicleRepository = vehicleRepository;
    }

    @Override
    public Optional<Vehicle> get(long id) {
        return vehicleRepository.findById(id);
    }

    @Override
    public List<Vehicle> getAll() {
        return vehicleRepository.findAll();
    }

    @Override
    public void save(Vehicle vehicle) {
        vehicleRepository.save(vehicle);
    }

    public Vehicle saveAndReturn(Vehicle vehicle){
        return vehicleRepository.save(vehicle);
    }

    @Override
    public void update(Vehicle vehicle) {
        vehicleRepository.save(vehicle);
    }

    @Override
    public void delete(Vehicle vehicle) {
        vehicleRepository.delete(vehicle);
    }

    public Vehicle deleteById(Long id){
        Vehicle v = vehicleRepository.getOne(id);
        vehicleRepository.deleteById(id);
        return v;
    }

    public Double getVehicleRating(Long id) {
        String query = "SELECT avg(Rating.rating) FROM Rating WHERE Rating.vehicle.id = :id";
        Double rating = (Double) em.createQuery(query).setParameter("id", id).getSingleResult();
        return rating;
    }

    public String getAllVehiclesCsv(){

        List<Vehicle> allVehicles = vehicleRepository.findAll();
        StringBuilder csvString = new StringBuilder();

        for (Vehicle vehicle : allVehicles) {
            try {
                Class<?> Vehicle = vehicle.getClass();

                for (Field f : Vehicle.getDeclaredFields()) {
                    if (f.isAnnotationPresent(ToExport.class)) {
                        f.setAccessible(true);
                        String s = f.get(vehicle).toString();
                        csvString.append(s + ";");
                    }
                }

                csvString.append("\n");
            } catch (IllegalAccessException e) {
                e.printStackTrace();
                System.out.println("Error during saving to csv file");
            }
        }

        return csvString.toString();
    }
}
