package lab.java10.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Entity(name = "carshowroom")
public class CarShowroom implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @OneToMany(mappedBy = "carShowroom", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<Vehicle> vehicles;
    private Integer maxCarAmount;
    private Boolean fav = false;

    public CarShowroom() {
        this.name = "empty";
        this.vehicles = new ArrayList<>();
        this.maxCarAmount = -1;
    }

    public CarShowroom(String name, Integer maxCarAmount) {
        this.name = name;
        this.vehicles = new ArrayList<>();
        this.maxCarAmount = maxCarAmount;
    }

    public List<Vehicle> getVehicles() {
        return vehicles;
    }

    public int addProduct(Vehicle vehicle) {
        int carAmount = vehicles.stream().mapToInt(v -> v.inStore).sum();

        if (carAmount + vehicle.inStore <= maxCarAmount) {
            int index = vehicles.indexOf(vehicle);
            if (index != -1) {
                vehicles.get(index).inStore++;
            } else {
                vehicles.add(vehicle);
            }
        } else {
            System.err.println("Storage capacity exceeded");
        }
        return 0;
    }

    public boolean getProduct(Vehicle vehicle) {
        for (Vehicle v : vehicles) {
            if (v.getModel().equals(vehicle.getModel()) && v.inStore != 0) {
                v.inStore--;
                if (v.inStore == 0) {
                    vehicles.remove(v);
                    return true;
                }
                return true;
            }
        }
        return false;
    }

    public boolean getProduct(String vehicle) {
        for (Vehicle v : vehicles) {
            if (v.getModel().equals(vehicle) && v.inStore != 0) {
                v.inStore--;
                if (v.inStore == 0) {
                    vehicles.remove(v);
                    return true;
                }
                return true;
            }
        }
        return false;
    }

    public boolean removeProduct(String vehicle) {
        return vehicles.removeIf(v -> v.getModel().equals(vehicle));
    }

    public ArrayList<Vehicle> searchPartial(String s) {
        final ArrayList<Vehicle> collect = (ArrayList<Vehicle>) vehicles.stream()
                .filter(vehicle -> vehicle.print().contains(s.toLowerCase()))
                .collect(Collectors.toList());
        return collect;
    }

    public void summary() {
        vehicles.stream().forEach(vehicle -> System.out.println(vehicle.print()));
    }

    public ArrayList<Vehicle> sortByAmount() {

        Comparator<Vehicle> comparator = new Comparator<Vehicle>() {
            @Override
            public int compare(Vehicle o1, Vehicle o2) {
                Integer amount1 = o1.inStore;
                Integer amount2 = o2.inStore;
                return amount2.compareTo(amount1);
            }
        };

        ArrayList<Vehicle> collect = (ArrayList<Vehicle>) vehicles.stream().sorted(comparator).collect(Collectors.toList());
        vehicles = collect;
        return collect;
    }

    public List<Vehicle> sortByName() {
        Collections.sort(vehicles);
        return List.copyOf(vehicles);
    }

    public boolean isEmpty() {
        return vehicles.isEmpty();
    }

    public Integer numberOfCars() {
        return vehicles.stream().mapToInt(v -> v.inStore).sum();
    }
}
