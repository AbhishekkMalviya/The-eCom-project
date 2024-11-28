package com.abhishek.malviya.eComProject.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;

import java.math.*;
import java.util.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String description;
    private String brand;
    private BigDecimal price;
    private String category;

//    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")  // Now this part is handled in ui itself
    private Date releaseDate;
    private boolean available;
    private int quantity;

    //Image variables
    private String imageName;
    private String imageType;
    @Lob  // to handle large data in this case we  use image:)
    private byte[] imageData;

}
