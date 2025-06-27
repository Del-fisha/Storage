package pet.storage.storage.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import pet.storage.storage.domain.events.ProductEvent;
import pet.storage.storage.model.FurnitureItem;
import pet.storage.storage.model.enum_classes.Category;
import pet.storage.storage.model.enum_classes.Metric;
import pet.storage.storage.utility.test.TestUtilities;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class FurnitureRepositoryTest {

    @Autowired
    FurnitureRepository repository;

    @Autowired
    TestEntityManager entityManager;

    @Autowired
    TestUtilities utilities;

    @MockitoBean
    private KafkaTemplate<Long, ProductEvent> kafkaTemplate;

    @BeforeEach
    void tearDown() {
        repository.deleteAll();
        entityManager.flush();
        entityManager.clear();
    }

    @Test
    @DisplayName("Сохранение FurnitureItem в БД")
    void shouldSaveItem() {
        FurnitureItem item = new FurnitureItem(
                "Диван", "IKEA", Category.Furniture, Metric.Piece, 1, 25000.0,
                LocalDate.of(2024, 11, 10), "Удобный диван"
        );

        FurnitureItem savedItem = repository.save(item);
        FurnitureItem foundItem = entityManager.find(FurnitureItem.class, savedItem.getId());

        assertThat(foundItem).isEqualTo(savedItem);
        assertThat(utilities.baseFieldsComparison(foundItem, savedItem)).isTrue();
    }

    @Test
    @DisplayName("Поиск FurnitureItem по имени")
    void shouldFindItemByName() {
        FurnitureItem item = new FurnitureItem(
                "Диван", "IKEA", Category.Furniture, Metric.Piece, 1, 25000.0,
                LocalDate.of(2024, 11, 10), "Удобный диван"
        );

        entityManager.persist(item);
        FurnitureItem foundItem = repository.findByName("Диван");

        assertThat(foundItem).isEqualTo(item);
        assertThat(utilities.baseFieldsComparison(item, foundItem)).isTrue();
    }

    @Test
    @DisplayName("Поиск FurnitureItem по ID")
    void shouldFindItemById() {
        FurnitureItem item = new FurnitureItem(
                "Стол", "LoftDesign", Category.Furniture, Metric.Piece, 1, 15000.0,
                LocalDate.of(2023, 5, 5), "Обеденный стол"
        );

        entityManager.persist(item);
        FurnitureItem foundItem = repository.findById(item.getId()).orElse(null);

        assertThat(foundItem).isEqualTo(item);
        Assertions.assertNotNull(foundItem);
        assertThat(utilities.baseFieldsComparison(item, foundItem)).isTrue();
    }

    @Test
    @DisplayName("Поиск по несуществующему имени возвращает null")
    void shouldReturnNullWhenItemNotFoundByName() {
        FurnitureItem foundItem = repository.findByName("NonExistent");
        assertThat(foundItem).isNull();
    }

    @Test
    @DisplayName("Поиск по несуществующему ID возвращает null")
    void shouldReturnNullWhenItemNotFoundById() {
        FurnitureItem foundItem = repository.findById(9999).orElse(null);
        assertThat(foundItem).isNull();
    }

    @Test
    @DisplayName("Удаление по ID")
    void shouldDeleteItemById() {
        FurnitureItem item = new FurnitureItem(
                "Кресло", "ComfortPlus", Category.Furniture, Metric.Piece, 1, 12000.0,
                LocalDate.of(2025, 1, 20), "Эргономичное кресло"
        );

        entityManager.persist(item);
        List<FurnitureItem> items = repository.findAll();
        assertThat(items.size()).isEqualTo(1);

        repository.deleteById(items.get(0).getId());
        items = repository.findAll();
        assertThat(items.isEmpty()).isTrue();
    }

    @Test
    @DisplayName("Получение всех элементов")
    void shouldGetAllItems() {
        FurnitureItem item1 = new FurnitureItem("Диван", "IKEA", Category.Furniture,
                Metric.Piece, 1, 25000.0,
                LocalDate.of(2024, 11, 10), "Удобный диван");

        FurnitureItem item2 = new FurnitureItem("Стол", "LoftDesign", Category.Furniture,
                Metric.Piece, 1, 15000.0,
                LocalDate.of(2023, 5, 5), "Обеденный стол");

        List<FurnitureItem> items = repository.findAll();
        assertThat(items.size()).isEqualTo(0);

        entityManager.persist(item1);
        entityManager.persist(item2);

        items = repository.findAll();
        assertThat(items.size()).isEqualTo(2);
    }

    @Test
    @DisplayName("Получение пустого списка")
    void shouldGetEmptyListItems() {
        List<FurnitureItem> items = repository.findAll();
        assertThat(items.size()).isEqualTo(0);
    }

    @Test
    @DisplayName("Обновление элемента")
    void shouldUpdateItem() {
        FurnitureItem item = new FurnitureItem(
                "Диван", "IKEA", Category.Furniture, Metric.Piece, 1, 25000.0,
                LocalDate.of(2024, 11, 10), "Удобный диван"
        );

        entityManager.persist(item);
        FurnitureItem foundItem = repository.findById(item.getId()).orElse(null);
        Assertions.assertNotNull(foundItem);

        foundItem.setPrice(27000.0);
        FurnitureItem updatedItem = repository.save(foundItem);

        assertThat(updatedItem.getPrice()).isEqualTo(27000.0);
        assertThat(utilities.baseFieldsComparison(updatedItem, foundItem)).isTrue();
    }
}
