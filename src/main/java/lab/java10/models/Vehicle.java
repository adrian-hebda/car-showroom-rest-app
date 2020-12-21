package lab.java10.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lab.java10.annotations.ToExport;
import lab.java10.enums.ItemCondition;
import lombok.Data;

import javax.persistence.*;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@JsonIgnoreProperties("carShowroom")
public class Vehicle implements Comparable<Vehicle>, Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ToExport
    public Integer inStore;

    @ToExport
    private String model;

    @ToExport(toSerialise = false)
    private ItemCondition itemCondition;

    @ToExport
    private Double price;

    @ToExport
    private Integer productionYear;

    @ToExport
    private Double mileage;

    @ToExport
    private Double engineCapacity;

    @ManyToOne
    private CarShowroom carShowroom;

    @OneToMany(mappedBy = "vehicle", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<Rating> ratings = new ArrayList<>();

    public Vehicle() {
        this.model = "undefined";
        this.itemCondition = ItemCondition.DAMAGED;
        this.price = 0.0;
        this.productionYear = 0;
        this.mileage = 0.0;
        this.engineCapacity = 0.0;
        inStore = 0;
    }

    public Vehicle(String model, ItemCondition itemCondition, Double price, Integer productionYear, Double mileage, Double engineCapacity, Integer inStore, CarShowroom carShowroom) {
        this.model = model;
        this.itemCondition = itemCondition;
        this.price = price;
        this.productionYear = productionYear;
        this.mileage = mileage;
        this.engineCapacity = engineCapacity;
        this.inStore = inStore;
        this.carShowroom = carShowroom;
    }

    public Vehicle(String model, String s, double price, int productionYear, double mileage, double engineCapacity, int inStore, CarShowroom carShowroom) {
        this.model = model;
        this.price = price;
        this.productionYear = productionYear;
        this.mileage = mileage;
        this.engineCapacity = engineCapacity;
        this.inStore = inStore;
        this.carShowroom = carShowroom;

        if(s.equals("NEW")){
            this.itemCondition = ItemCondition.NEW;
        } else if(s.equals("DAMAGED")){
            this.itemCondition = ItemCondition.DAMAGED;
        } else if(s.equals("USED")){
            this.itemCondition = ItemCondition.USED;
        } else {
            this.itemCondition = ItemCondition.DAMAGED;
        }
    }

    public String print() {
        return "model='" + model +
                ", condition=" + itemCondition +
                ", price=" + price +
                ", productionYear=" + productionYear +
                ", mileage=" + mileage +
                ", engineCapacity=" + engineCapacity +
                ", in store=" + inStore;
    }

    @Override
    public int compareTo(Vehicle o) {
        return o.model.compareTo(this.getModel());
    }

    // Serialisation

    private void writeObject(ObjectOutputStream oos) throws Exception {
        oos.writeUTF(model);
        oos.writeDouble(price);
        oos.writeInt(productionYear);
        oos.writeDouble(mileage);
        oos.writeDouble(engineCapacity);
        oos.writeInt(inStore);
    }

    // this method is executed by jvm when readObject() on
    // Account object reference in main method is executed by jvm.
    private void readObject(ObjectInputStream ois) throws Exception {
        this.model = ois.readUTF();
        this.price = ois.readDouble();
        this.productionYear = ois.readInt();
        this.mileage = ois.readDouble();
        this.engineCapacity = ois.readDouble();
        this.inStore = ois.readInt();
        this.itemCondition = ItemCondition.NEW;
    }


}
