package travel.letstrip.enums;

import java.util.Map;

public enum Mode {
    TEST(Map.of(
            Language.EN, "Test mode",
            Language.RU, "Тестовый режим",
            Language.UZ, "Test rejimi"
    )),
    PROD(Map.of(
            Language.EN, "Production mode",
            Language.RU, "Режим продакшн",
            Language.UZ, "Ishlab chiqarish rejimi"
    ));

    private final Map<Language, String> descriptions;

    Mode(Map<Language, String> descriptions) {
        this.descriptions = descriptions;
    }

    public String getDescription(Language language) {
        return descriptions.get(language);
    }
}
