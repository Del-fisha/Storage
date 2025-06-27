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
import pet.storage.storage.model.ElectricalItem;
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
public class ElectricalRepositoryTest {

    @Autowired
    ElectricalRepository repository;

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
    @DisplayName("Сохранение ElectricalItem в БД")
    void shouldSaveItem() {
        ElectricalItem item = new ElectricalItem(
                "Холодильник", "Bosch", Category.Electrical, Metric.Piece, 1, 64990.0,
                LocalDate.of(2023, 1, 15), "Встраиваемый холодильник",
                LocalDate.of(2029, 1, 15), 72
        );

        ElectricalItem savedItem = repository.save(item);
        ElectricalItem foundItem = entityManager.find(ElectricalItem.class, savedItem.getId());

        assertThat(foundItem).isEqualTo(savedItem);
        assertThat(utilities.baseFieldsComparison(foundItem, savedItem)).isTrue();
    }

    @Test
    @DisplayName("Поиск ElectricalItem по имени")
    void shouldFindItemByName() {
        ElectricalItem item = new ElectricalItem(
                "Холодильник", "Bosch", Category.Electrical, Metric.Piece, 1, 64990.0,
                LocalDate.of(2023, 1, 15), "Встраиваемый холодильник",
                LocalDate.of(2029, 1, 15), 72
        );

        entityManager.persist(item);
        ElectricalItem foundItem = repository.findByName("Холодильник");

        assertThat(foundItem).isEqualTo(item);
        assertThat(utilities.baseFieldsComparison(item, foundItem)).isTrue();
    }

    @Test
    @DisplayName("Поиск ElectricalItem по ID")
    void shouldFindItemById() {
        ElectricalItem item = new ElectricalItem(
                "Пылесос", "Dyson", Category.Electrical, Metric.Piece, 1, 59990.0,
                LocalDate.of(2024, 5, 20), "Беспроводной пылесос",
                LocalDate.of(2027, 5, 20), 36
        );

        entityManager.persist(item);
        ElectricalItem foundItem = repository.findById(item.getId()).orElse(null);

        assertThat(foundItem).isEqualTo(item);
        Assertions.assertNotNull(foundItem);
        assertThat(utilities.baseFieldsComparison(item, foundItem)).isTrue();
    }

    @Test
    @DisplayName("Поиск по несуществующему имени возвращает null")
    void shouldReturnNullWhenItemNotFoundByName() {
        ElectricalItem foundItem = repository.findByName("NonExistent");
        assertThat(foundItem).isNull();
    }

    @Test
    @DisplayName("Поиск по несуществующему ID возвращает null")
    void shouldReturnNullWhenItemNotFoundById() {
        ElectricalItem foundItem = repository.findById(9999).orElse(null);
        assertThat(foundItem).isNull();
    }

    @Test
    @DisplayName("Удаление по ID")
    void shouldDeleteItemById() {
        ElectricalItem item = new ElectricalItem(
                "Микроволновка", "Samsung", Category.Electrical, Metric.Piece, 1, 8990.0,
                LocalDate.of(2024, 3, 10), "С грилем",
                LocalDate.of(2026, 3, 10), 24
        );

        entityManager.persist(item);
        List<ElectricalItem> items = repository.findAll();
        assertThat(items.size()).isEqualTo(1);

        repository.deleteById(items.get(0).getId());
        items = repository.findAll();
        assertThat(items.isEmpty()).isTrue();
    }

    @Test
    @DisplayName("Получение всех элементов")
    void shouldGetAllItems() {
        ElectricalItem item1 = new ElectricalItem("Холодильник", "Bosch", Category.Electrical,
                Metric.Piece, 1, 64990.0,
                LocalDate.of(2023, 1, 15), "Встраиваемый холодильник",
                LocalDate.of(2029, 1, 15), 72);

        ElectricalItem item2 = new ElectricalItem("Пылесос", "Dyson", Category.Electrical,
                Metric.Piece, 1, 59990.0,
                LocalDate.of(2024, 5, 20), "Беспроводной пылесос",
                LocalDate.of(2027, 5, 20), 36);

        List<ElectricalItem> items = repository.findAll();
        assertThat(items.size()).isEqualTo(0);

        entityManager.persist(item1);
        entityManager.persist(item2);

        items = repository.findAll();
        assertThat(items.size()).isEqualTo(2);
    }

    @Test
    @DisplayName("Получение пустого списка")
    void shouldGetEmptyListItems() {
        List<ElectricalItem> items = repository.findAll();
        assertThat(items.size()).isEqualTo(0);
    }

    @Test
    @DisplayName("Обновление элемента")
    void shouldUpdateItem() {
        ElectricalItem item = new ElectricalItem(
                "Холодильник", "Bosch", Category.Electrical, Metric.Piece, 1, 64990.0,
                LocalDate.of(2023, 1, 15), "Встраиваемый холодильник",
                LocalDate.of(2029, 1, 15), 72
        );

        entityManager.persist(item);
        ElectricalItem foundItem = repository.findById(item.getId()).orElse(null);
        Assertions.assertNotNull(foundItem);

        foundItem.setWarrantyMonths(84);
        ElectricalItem updatedItem = repository.save(foundItem);

        assertThat(updatedItem.getWarrantyMonths()).isEqualTo(84);
        assertThat(utilities.baseFieldsComparison(updatedItem, foundItem)).isTrue();
    }
}
