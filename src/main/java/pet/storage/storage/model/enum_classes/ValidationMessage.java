package pet.storage.storage.model.enum_classes;

public enum ValidationMessage {

    NOT_BLANK_NAME("Поле не может быть пустым"),
    POSITIVE_AMOUNT("Количество должно быть больше 0"),
    POSITIVE_OR_ZERO_PRICE("Цена не может быть отрицательной"),
    NOT_NULL_CATEGORY("Категория обязательна"),
    NOT_NULL_METRIC("Единица измерения обязательна"),
    NOT_NULL_DATE_OF_PURCHASE("Дата покупки обязательна"),

    FUTURE_OR_PRESENT_EXPIRATION_DATE("Срок годности не может быть в прошлом"),
    PAST_OR_PRESENT_DATE_OF_PURCHASE("Дата покупки не может быть в будущем");


    private final String message;

    ValidationMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}