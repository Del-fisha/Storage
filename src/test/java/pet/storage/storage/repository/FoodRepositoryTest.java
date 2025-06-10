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
import pet.storage.storage.utility.test.TestUtilities;

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

    @Autowired
    TestUtilities utilities;

    @Test
    @DisplayName("Проверка сохранения FoodItem в базу данных")
    void shouldSaveElectricalItem() {

        String expectedName = "Хлеб";
        int expectedId = 1;
        int nullId = 0;

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

        assertThat(item.getId()).isEqualTo(nullId);

        FoodItem savedItem = repository.save(item);

        assertThat(savedItem).isNotNull();
        assertThat(savedItem.getId()).isPositive();
        try {
            assertThat(savedItem.getId()).isNotNull();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        FoodItem foundItem = entityManager.find(FoodItem.class, savedItem.getId());

        assertThat(foundItem).isEqualTo(savedItem);
        assertThat(utilities.baseFieldsComparison(foundItem, savedItem)).isTrue();
    }

    @Test
    @DisplayName("Проверка поиска FoodItem по имени")
    void shouldFindItemByName() {
        String expectedName = "Молоко";

        FoodItem item = new FoodItem(
                "Молоко",
                "Молочный завод",
                Category.Food,
                Metric.L,
                1,
                80.0,
                LocalDate.of(2025, 6, 2),
                "Пастеризованное молоко",
                LocalDate.of(2025, 5, 31),
                LocalDate.of(2025, 6, 7)
        );

        entityManager.persist(item);
        entityManager.flush();

        FoodItem savedItem = repository.findByName(expectedName);

        assertThat(savedItem).isEqualTo(item);
        assertThat(utilities.baseFieldsComparison(item, savedItem)).isTrue();
    }

    @Test
    @DisplayName("Проверка поиска FoodItem по id")
    void shouldFindItemById() {

        int expectedId = 1;

        FoodItem item = new FoodItem(
                "Яблоки",
                "Фермер Иванов",
                Category.Food,
                Metric.Kg,
                2,
                120.0,
                LocalDate.of(2025, 5, 28),
                "Сочные яблоки сорта Гала",
                LocalDate.of(2025, 5, 25),
                LocalDate.of(2025, 6, 10)
        );

        entityManager.persist(item);
        entityManager.flush();

        FoodItem savedItem = repository.findById(expectedId).orElseThrow();

        assertThat(savedItem).isEqualTo(item);
        assertThat(utilities.baseFieldsComparison(item, savedItem)).isTrue();
    }

    @Test
    @DisplayName("Проверка, что findByName возвращает null для несуществующего имени")
    void shouldReturnEmptyOptionalWhenItemNotFoundByName() {
        // Действие
        FoodItem foundByNameItem = repository.findByName("NonExistent Item");

        // Проверка
        assertThat(foundByNameItem).isNull();
    }
}
