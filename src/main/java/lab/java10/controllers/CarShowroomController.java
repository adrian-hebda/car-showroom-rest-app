package lab.java10.controllers;

import lab.java10.exceptions.NotFoundException;
import lab.java10.models.CarShowroom;
import lab.java10.models.Vehicle;
import lab.java10.services.CarShowroomCrudService;
import org.apache.coyote.Response;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/api/fulfillment")
public class CarShowroomController {

    CarShowroomCrudService carShowroomCrudService;

    public CarShowroomController(CarShowroomCrudService carShowroomCrudService) {
        this.carShowroomCrudService = carShowroomCrudService;
    }

    @GetMapping(path = {"", "/"}, produces = "application/json")
    public List<CarShowroom> getAll(){
        return carShowroomCrudService.getAll();
    }

    @GetMapping(path = "/{id}/products", produces = "application/json")
    public List<Vehicle> getVehiclesFromCarShowroom(@PathVariable("id") Long id) throws NotFoundException {
        Optional<CarShowroom> optionalCarShowroom = carShowroomCrudService.get(id);
         if (optionalCarShowroom.isEmpty()){
             throw new NotFoundException();
         }
         return optionalCarShowroom.get().getVehicles();
    }
    @GetMapping("/{id}/fill")
    public Double getFillOfCarShowroom(@PathVariable("id") Long id){
        Double fill = carShowroomCrudService.getFill(id);
        if(fill == null){
            throw new NotFoundException();
        }
        return fill;
    }

    @PostMapping(path = {"","/"}, produces = "application/json")
    public CarShowroom addNewCarShowroom(@RequestBody CarShowroom carShowroom){
       return carShowroomCrudService.saveAndGet(carShowroom);
    }

    @DeleteMapping(path = {"/{id}","/{id}/"}, produces = "application/json")
    public CarShowroom deleteCarShowroom(@PathVariable("id") Long id){
        CarShowroom carShowroom = carShowroomCrudService.delete(id);
        if(carShowroom == null){
            throw new NotFoundException();
        }
        return carShowroom;
    }

}
