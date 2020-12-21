package lab.java10.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import lab.java10.models.CarShowroom;
import lab.java10.models.Vehicle;
import lab.java10.services.CarShowroomCrudService;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.awt.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.ignoreStubs;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@ExtendWith(MockitoExtension.class)
class carShowroomControllerTest {

    @InjectMocks
    CarShowroomController controller;

    @Mock
    CarShowroomCrudService crudService;

    @Test
    void getAll() {
        CarShowroom carShowroom = new CarShowroom("Test1", 20);
        CarShowroom carShowroom1 = new CarShowroom("Test2", 20);
        CarShowroom carShowroom2 = new CarShowroom("Test3", 20);
        List<CarShowroom> carShowroomList = List.of( carShowroom, carShowroom1, carShowroom2);

        when(crudService.getAll()).thenReturn(carShowroomList);

        List<CarShowroom> result = controller.getAll();

        assertEquals(3, result.size());
        assertEquals("Test1", result.get(0).getName());
    }

    @Test
    void getVehiclesFromCarShowroom() {
        CarShowroom carShowroom = new CarShowroom("Test1", 20);
        carShowroom.setId(1l);
        carShowroom.addProduct(new Vehicle());

        when(crudService.get(1l)).thenReturn(java.util.Optional.of(carShowroom));

        List<Vehicle> vehicles = controller.getVehiclesFromCarShowroom(1l);

        assertIterableEquals(carShowroom.getVehicles(), vehicles);
    }

    @Test
    void getFillOfCarShowroom() {

        CarShowroom carShowroom = new CarShowroom();
        carShowroom.setFav(true);
        carShowroom.setName("Test");
        carShowroom.setMaxCarAmount(20);
        carShowroom.setId(1l);

        when(crudService.getFill(1l)).thenReturn((double) (carShowroom.getVehicles().size()/carShowroom.getMaxCarAmount()));

        Double responseTest = controller.getFillOfCarShowroom(1l);

        assertEquals(0.0, responseTest);
    }

    @Test
    void addNewCarShowroomTest() {

        CarShowroom requestCarShowroom = new CarShowroom();
        requestCarShowroom.setFav(true);
        requestCarShowroom.setName("Test");
        requestCarShowroom.setMaxCarAmount(20);

        CarShowroom responseCarShowroom = new CarShowroom();
        responseCarShowroom.setFav(true);
        responseCarShowroom.setName("Test");
        responseCarShowroom.setMaxCarAmount(20);
        responseCarShowroom.setId(1l);

        when(crudService.saveAndGet(requestCarShowroom)).thenReturn(responseCarShowroom);

        CarShowroom responseTest = controller.addNewCarShowroom(requestCarShowroom);

        assertEquals(responseCarShowroom, responseTest);
    }

    @Test
    void deleteCarShowroom() {
        CarShowroom carShowroom = new CarShowroom();
        carShowroom.setFav(true);
        carShowroom.setName("Test");
        carShowroom.setMaxCarAmount(20);
        carShowroom.setId(1l);

        when(crudService.delete(1l)).thenReturn(carShowroom);

        CarShowroom responseTest = controller.deleteCarShowroom(1l);

        assertEquals(carShowroom, responseTest);
    }
}