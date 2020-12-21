package lab.java10.controllers;

import lab.java10.exceptions.NotFoundException;
import lab.java10.models.Vehicle;
import lab.java10.services.VehicleCrudService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/product")
public class VehicleController {
    private VehicleCrudService vehicleCrudService;

    public VehicleController(VehicleCrudService vehicleCrudService) {
        this.vehicleCrudService = vehicleCrudService;
    }

    @DeleteMapping(value = "/{id}")
    public Vehicle deleteVehicle(@PathVariable Long id) {
        return vehicleCrudService.deleteById(id);
    }

    @GetMapping(value = "/{id}/rating")
    public Double getRating(@PathVariable Long id) throws NotFoundException {
        return vehicleCrudService.getVehicleRating(id);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public Vehicle addNewVehicle(@RequestBody Vehicle vehicle) {
        return vehicleCrudService.saveAndReturn(vehicle);
    }

    @GetMapping(value = "/csv")
    public String getCsvFile() {
        return vehicleCrudService.getAllVehiclesCsv();
    }
}
