package pet.storage.storage.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import pet.storage.storage.model.FoodItem;
import pet.storage.storage.model.enum_classes.Category;
import pet.storage.storage.model.enum_classes.Metric;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
@ActiveProfiles("test")
public class FoodRepositoryTest {

    @Autowired
    FoodRepository repository;

    @Autowired
    TestEntityManager entityManager;

    @Test
    @DisplayName("Проверка сохранения FoodItem в базу данных")
    void shouldSaveElectricalItem() {
        FoodItem item = new FoodItem(
                "Хлеб",
                "Пекарня №1",
                Category.Food,
                Metric.Piece,
                1,
                45.0,
                LocalDate.of(2025, 6, 1),
                "Свежий ржаной хлеб",
                LocalDate.of(2025, 5, 30),
                LocalDate.of(2025, 6, 5)
        );

        FoodItem savedItem = repository.save(item);

        assertThat(savedItem).isNotNull();
        assertThat(savedItem.getId()).isPositive();
    }
}
