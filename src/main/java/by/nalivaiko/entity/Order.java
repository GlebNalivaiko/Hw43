package by.nalivaiko.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "orders_by_gleb")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @Column(name = "category_of_order")
    private String categoryOfOrder;
    @Column(name = "name_of_order")
    private String nameOfOrder;
    @Column(name = "price")
    private String price;
    @Column(name = "country")
    private String country;
    @Column(name = "data_of_order")
    private String dataOfOrder;
    @Column(name = "data_of_end_order")
    private String dataOfEndOrder;
}
