package pet.storage.storage.model.abstract_classes;


import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pet.storage.storage.model.enum_classes.Category;
import pet.storage.storage.model.enum_classes.Metric;

import java.time.LocalDate;

@Getter
@Setter
@MappedSuperclass
@NoArgsConstructor
@EqualsAndHashCode
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

    public Item(String name, String fabricator, Category category, Metric metric, double amount,
                double price, LocalDate dateOfPurchase, String description) {
        this.name = name;
        this.fabricator = fabricator;
        this.category = category;
        this.metric = metric;
        this.amount = amount;
        this.price = price;
        this.dateOfPurchase = dateOfPurchase;
        this.description = description;
    }
}
