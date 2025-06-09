package pet.storage.storage.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import pet.storage.storage.model.ElectricalItem;
import pet.storage.storage.model.enum_classes.Category;
import pet.storage.storage.model.enum_classes.Metric;
import pet.storage.storage.repository.ElectricalRepository;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat; // Используем AssertJ для более читаемых ассертов

@DataJpaTest // 1. Это ключевая аннотация для тестирования JPA репозиториев
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY) // 2. Настраивает тестовую БД (по умолчанию H2)
@ActiveProfiles("dev") // 3. Активируем тестовый профиль (если есть application-test.properties)
class ElectricalRepositoryTest {

    @Autowired // 4. Инжектируем наш репозиторий, который хотим тестировать
    private ElectricalRepository electricalRepository;

    @Autowired // 5. Инжектируем TestEntityManager для работы с БД вне репозитория (подготовка данных, проверка)
    private TestEntityManager entityManager;

    @Test
    @DisplayName("Проверка сохранения ElectricalItem в базу данных")
    void shouldSaveElectricalItem() {

        ElectricalItem item = new ElectricalItem(
                "Электрочайник",
                "Bosch",
                Category.Electrical,
                Metric.Piece,
                1,
                2100.0,
                LocalDate.of(2025, 2, 15),
                "Чайник с защитой от перегрева",
                LocalDate.of(2029, 2, 15),
                48
        );

        ElectricalItem savedItem = electricalRepository.save(item);

        assertThat(savedItem).isNotNull();
        assertThat(savedItem.getId()).isPositive();


        ElectricalItem foundItem = entityManager.find(ElectricalItem.class, savedItem.getId());
        assertThat(foundItem).isEqualTo(savedItem);
        assertThat(foundItem.getName()).isEqualTo("Электрочайник");
        assertThat(foundItem.getFabricator()).isEqualTo("Bosch");
    }

    @Test
    @DisplayName("Проверка поиска ElectricalItem по имени")
    void shouldFindItemByName() {
        // Подготовка данных: сохраняем элемент напрямую через entityManager
        // Это важно, чтобы убедиться, что репозиторий находит то, что *уже* есть в БД
        ElectricalItem item = new ElectricalItem(
                "Электрочайник",
                "Bosch",
                Category.Electrical,
                Metric.Piece,
                1,
                2100.0,
                LocalDate.of(2025, 2, 15),
                "Чайник с защитой от перегрева",
                LocalDate.of(2029, 2, 15),
                48
        );

        entityManager.persist(item); // Сохраняем напрямую
        entityManager.flush(); // Принудительно сбрасываем изменения в базу

        // Выполнение действия: ищем элемент через репозиторий
        ElectricalItem foundByNameItem = electricalRepository.findByName("Электрочайник");

        assertThat(foundByNameItem).isNotNull();
        assertThat(foundByNameItem.getName()).isEqualTo("Электрочайник");
        assertThat(foundByNameItem.getFabricator()).isEqualTo("Bosch");
    }

    @Test
    @DisplayName("Проверка, что findByName возвращает Optional.empty() для несуществующего имени")
    void shouldReturnEmptyOptionalWhenItemNotFoundByName() {
        // Действие
        ElectricalItem foundByNameItem = electricalRepository.findByName("NonExistent Item");

        // Проверка
        assertThat(foundByNameItem).isNull(); // Убеждаемся, что Optional пустой
    }

    @Test
    @DisplayName("Проверка удаления ElectricalItem по ID")
    void shouldDeleteElectricalItemById() {
        // Подготовка данных: сохраняем элемент
        ElectricalItem item = new ElectricalItem(
                "Электрочайник",
                "Bosch",
                Category.Electrical,
                Metric.Piece,
                1,
                2100.0,
                LocalDate.of(2025, 2, 15),
                "Чайник с защитой от перегрева",
                LocalDate.of(2029, 2, 15),
                48
        );

        entityManager.persist(item);
        entityManager.flush();

        int itemId = item.getId(); // Получаем ID сохраненного элемента

        // Выполнение действия: удаляем элемент
        electricalRepository.deleteById(itemId);
        entityManager.flush(); // Принудительно сбрасываем изменения

        // Проверка: пытаемся найти удаленный элемент
        ElectricalItem deletedItem = entityManager.find(ElectricalItem.class, itemId);
        assertThat(deletedItem).isNull(); // Убеждаемся, что элемент удален
    }
}