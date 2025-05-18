package pet.storage.storage.model.abstract_classes;


import jakarta.persistence.*;
import pet.storage.storage.model.enum_classes.Category;
import pet.storage.storage.model.enum_classes.Metric;

import java.time.LocalDate;

@MappedSuperclass
public abstract class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected int id;

    @Column(nullable = false, name = "name")
    protected String name;

    @Column(nullable = false, name = "fabricator")
    protected String fabricator;

    @Enumerated(EnumType.STRING)
    @Column(name = "category")
    protected Category category;

    @Enumerated(EnumType.STRING)
    @Column(name = "metric")
    protected Metric metric;

    @Column(nullable = false, name = "amount")
    protected double amount;

    @Column(nullable = false, name = "price")
    protected double price;

    @Column(nullable = false, name = "date_of_purchase")
    protected LocalDate dateOfPurchase;

    @Column(nullable = false, name = "description")
    protected String description;
}
