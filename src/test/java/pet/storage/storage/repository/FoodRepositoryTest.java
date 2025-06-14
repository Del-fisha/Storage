package pet.storage.storage.repository;

import org.junit.jupiter.api.BeforeEach;
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
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
@ActiveProfiles("test")
public class FoodRepositoryTest {

    private static int expectedId = 1;

    @Autowired
    FoodRepository repository;

    @Autowired
    TestEntityManager entityManager;

    @Autowired
    TestUtilities utilities;

    @BeforeEach
    void tearDown() {
        repository.deleteAll();
        entityManager.flush();
        entityManager.clear();
    }

    @Test
    @DisplayName("Проверка сохранения FoodItem в базу данных")
    void shouldSaveItem() {

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

        try {
            assertThat(savedItem.getId()).isNotNull();
            assertThat(savedItem.getId()).isEqualTo(expectedId);
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
        expectedId++;

        FoodItem savedItem = repository.findByName(expectedName);

        assertThat(savedItem).isEqualTo(item);
        assertThat(utilities.baseFieldsComparison(item, savedItem)).isTrue();
    }

    @Test
    @DisplayName("Проверка поиска FoodItem по id")
    void shouldFindItemById() {

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
        expectedId++;
        System.out.println(expectedId);
        FoodItem savedItem = repository.findById(expectedId).orElse(null); // ToDo разобраться с логикой expectedID

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

    @Test
    @DisplayName("Проверка, что findByName возвращает null для несуществующего ID")
    void shouldReturnEmptyOptionalWhenItemNotFoundById() {

        FoodItem foundByIdItem = repository.findById(9999).orElse(null);

        assertThat(foundByIdItem).isNull();
    }

    @Test
    @DisplayName("Проверка удаления Item по ID")
    void shouldDeleteItemById() {

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
        expectedId++;

        List<FoodItem> listOfItems = repository.findAll();

        assertThat(listOfItems.size()).isEqualTo(1);

        repository.deleteById(listOfItems.get(0).getId());

        listOfItems = repository.findAll();

        assertThat(listOfItems.isEmpty()).isTrue();

    }

    @Test
    @DisplayName("Проверка findAll")
    void shouldGetAllItems() {

        FoodItem item1 = new FoodItem(
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

        FoodItem item2 = new FoodItem(
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

        FoodItem item3 = new FoodItem(
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

        List<FoodItem> items = repository.findAll();

        assertThat(items.size()).isEqualTo(0);

        entityManager.persist(item1);
        entityManager.persist(item2);
        entityManager.persist(item3);
        entityManager.flush();
        expectedId += 3;

        items = repository.findAll();
        assertThat(items.size()).isEqualTo(3);

    }

    @Test
    @DisplayName("Проверка findAll с пустой базой")
    void shouldGetEmptyListItems() {

        List<FoodItem> items = repository.findAll();

        assertThat(items.size()).isEqualTo(0);

    }

    @Test
    @DisplayName("Проверка изменения Item")
    void shouldUpdateItem() {

    }
}
