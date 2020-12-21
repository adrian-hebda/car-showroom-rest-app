package lab.java10.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@Entity
@JsonIgnoreProperties("vehicle")
public class Rating {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer rating;
    @ManyToOne
    private Vehicle vehicle;
    private LocalDate date = LocalDate.now();
    private String description = "Not provided";
}
