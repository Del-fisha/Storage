package pet.storage.storage.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.annotation.DirtiesContext;
import pet.storage.storage.model.ChemicalItem;
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
public class ChemicalRepositoryTest {

    @Autowired
    ChemicalRepository repository;

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
    @DisplayName("Сохранение ChemicalItem в БД")
    void shouldSaveItem() {
        ChemicalItem item = new ChemicalItem(
                "Domestos", "Unilever", Category.Chemicals, Metric.L, 1.0, 150.0,
                LocalDate.of(2025, 6, 1), "Дезинфицирующее средство",
                LocalDate.of(2029, 5, 1)
        );

        ChemicalItem savedItem = repository.save(item);
        ChemicalItem foundItem = entityManager.find(ChemicalItem.class, savedItem.getId());

        assertThat(foundItem).isEqualTo(savedItem);
        assertThat(utilities.baseFieldsComparison(foundItem, savedItem)).isTrue();
    }

    @Test
    @DisplayName("Поиск ChemicalItem по имени")
    void shouldFindItemByName() {
        ChemicalItem item = new ChemicalItem(
                "Domestos", "Unilever", Category.Chemicals, Metric.L, 1.0, 150.0,
                LocalDate.of(2025, 6, 1), "Дезинфицирующее средство",
                LocalDate.of(2029, 5, 1)
        );

        entityManager.persist(item);
        ChemicalItem foundItem = repository.findByName("Domestos");

        assertThat(foundItem).isEqualTo(item);
        assertThat(utilities.baseFieldsComparison(item, foundItem)).isTrue();
    }

    @Test
    @DisplayName("Поиск ChemicalItem по ID")
    void shouldFindItemById() {
        ChemicalItem item = new ChemicalItem(
                "Colgate", "Colgate-Palmolive", Category.Chemicals, Metric.Piece, 1.0, 120.0,
                LocalDate.of(2024, 6, 15), "Зубная паста",
                LocalDate.of(2025, 6, 15)
        );

        entityManager.persist(item);
        ChemicalItem foundItem = repository.findById(item.getId()).orElse(null);

        assertThat(foundItem).isEqualTo(item);
        Assertions.assertNotNull(foundItem);
        assertThat(utilities.baseFieldsComparison(item, foundItem)).isTrue();
    }

    @Test
    @DisplayName("Поиск по несуществующему имени возвращает null")
    void shouldReturnNullWhenItemNotFoundByName() {
        ChemicalItem foundItem = repository.findByName("NonExistent");
        assertThat(foundItem).isNull();
    }

    @Test
    @DisplayName("Поиск по несуществующему ID возвращает null")
    void shouldReturnNullWhenItemNotFoundById() {
        ChemicalItem foundItem = repository.findById(9999).orElse(null);
        assertThat(foundItem).isNull();
    }

    @Test
    @DisplayName("Удаление по ID")
    void shouldDeleteItemById() {
        ChemicalItem item = new ChemicalItem(
                "Vanish", "Reckitt Benckiser", Category.Chemicals, Metric.Kg, 0.75, 200.0,
                LocalDate.of(2024, 3, 10), "Пятновыводитель",
                LocalDate.of(2025, 3, 10)
        );

        entityManager.persist(item);
        List<ChemicalItem> items = repository.findAll();
        assertThat(items.size()).isEqualTo(1);

        repository.deleteById(items.get(0).getId());
        items = repository.findAll();
        assertThat(items.isEmpty()).isTrue();
    }

    @Test
    @DisplayName("Получение всех элементов")
    void shouldGetAllItems() {
        ChemicalItem item1 = new ChemicalItem("Domestos", "Unilever", Category.Chemicals,
                Metric.L, 1.0, 150.0,
                LocalDate.of(2025, 6, 1), "Дезинфицирующее средство",
                LocalDate.of(2029, 5, 1));

        ChemicalItem item2 = new ChemicalItem("Colgate", "Colgate-Palmolive", Category.Chemicals,
                Metric.Piece, 1.0, 120.0,
                LocalDate.of(2024, 6, 15), "Зубная паста",
                LocalDate.of(2025, 6, 15));

        List<ChemicalItem> items = repository.findAll();
        assertThat(items.size()).isEqualTo(0);

        entityManager.persist(item1);
        entityManager.persist(item2);

        items = repository.findAll();
        assertThat(items.size()).isEqualTo(2);
    }

    @Test
    @DisplayName("Получение пустого списка")
    void shouldGetEmptyListItems() {
        List<ChemicalItem> items = repository.findAll();
        assertThat(items.size()).isEqualTo(0);
    }

    @Test
    @DisplayName("Обновление элемента")
    void shouldUpdateItem() {
        ChemicalItem item = new ChemicalItem(
                "Domestos", "Unilever", Category.Chemicals, Metric.L, 1.0, 150.0,
                LocalDate.of(2025, 6, 1), "Дезинфицирующее средство",
                LocalDate.of(2029, 5, 1)
        );

        entityManager.persist(item);
        ChemicalItem foundItem = repository.findById(item.getId()).orElse(null);
        Assertions.assertNotNull(foundItem);

        foundItem.setAmount(3.0);
        ChemicalItem updatedItem = repository.save(foundItem);

        assertThat(updatedItem.getAmount()).isEqualTo(3.0);
        assertThat(utilities.baseFieldsComparison(updatedItem, foundItem)).isTrue();
    }
}
