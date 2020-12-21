package lab.java10.controllers;

import lab.java10.models.Vehicle;
import lab.java10.services.VehicleCrudService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class vehicleControllerTest {
    @Mock
    VehicleCrudService vehicleCrudService;

    @InjectMocks
    VehicleController vehicleController;

    @Test
    void addNewVehicle() {
        Vehicle vehicle = new Vehicle();

        when(vehicleCrudService.saveAndReturn(vehicle)).thenReturn(vehicle);

        Vehicle returned = vehicleController.addNewVehicle(vehicle);

        assertEquals(vehicle, returned);
    }

    @Test
    void deleteVehicle() {
        Vehicle vehicle = new Vehicle();
        vehicle.setId(1l);

        when(vehicleCrudService.deleteById(1l)).thenReturn(vehicle);

        Vehicle returned = vehicleController.deleteVehicle(1l);

        //Id comparison
        assertEquals(vehicle.getId(), returned.getId());
    }

    @Test
    void getRating() {
        when(vehicleController.getRating(1l)).thenReturn(5.0);
        Double returnedRating = vehicleController.getRating(1l);
        assertEquals(5.0, returnedRating);
    }
}