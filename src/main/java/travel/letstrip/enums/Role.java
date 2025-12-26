package travel.letstrip.enums;

import java.util.Map;

public enum Role {
    USER(Map.of(
        Language.EN, "User",
        Language.RU, "Пользователь",
        Language.UZ, "Foydalanuvchi"
    )),
    DEVELOPER(Map.of(
        Language.EN, "Developer",
        Language.RU, "Разработчик",
        Language.UZ, "Developer foydalanuvchi"
    ));

    private final Map<Language, String> descriptions;

    Role(Map<Language, String> descriptions) {
        this.descriptions = descriptions;
    }

    public String getDescription(Language language) {
        return descriptions.get(language);
    }
}
