package travel.letstrip.enums;

/**
 * Supported application languages.
 * <p>
 * This enum provides standard ISO 639-1 codes and the native display name
 * for a wide range of popular languages, facilitating Internationalization (i18n)
 * in the application.
 */
public enum Language {

    /** English (Code: en) */
    EN("en", "English"),

    /** Russian (Code: ru) */
    RU("ru", "Русский"),

    /** Uzbek (Code: uz) */
    UZ("uz", "O'zbekcha"),


    /** Chinese (Mandarin) (Code: zh) */
    ZH("zh", "中文"),

    /** Spanish (Code: es) */
    ES("es", "Español"),

    /** French (Code: fr) */
    FR("fr", "Français"),

    /** German (Code: de) */
    DE("de", "Deutsch"),

    /** Turkish (Code: tr) */
    TR("tr", "Türkçe"),

    /** Japanese (Code: ja) */
    JA("ja", "日本語"),

    /** Korean (Code: ko) */
    KO("ko", "한국어"),

    /** Portuguese (Code: pt) */
    PT("pt", "Português"),

    /** Italian (Code: it) */
    IT("it", "Italiano"),

    /** Arabic (Code: ar) */
    AR("ar", "العربية"),

    /** Hindi (Code: hi) */
    HI("hi", "हिन्दी");


    /** * The ISO 639-1 code for the language (e.g., "uz", "en").
     * This is typically used for API requests, file localization, or database keys.
     */
    private final String code;

    /** * The native display name of the language, intended for presentation 
     * in the User Interface (UI), such as a language selection menu (e.g., "O'zbekcha"). 
     */
    private final String displayName;


    /**
     * Constructs a Language enum element with its ISO code and display name.
     * * @param code The two-letter ISO 639-1 code (e.g., "en").
     * @param displayName The localized name of the language (e.g., "English").
     */
    Language(String code, String displayName) {
        this.code = code;
        this.displayName = displayName;
    }


    /**
     * Retrieves the ISO 639-1 code for the language.
     * * @return The two-letter language code (e.g., "en").
     */
    public String getCode() {
        return code;
    }

    /**
     * Retrieves the display name of the language in its native form.
     * * @return The UI-friendly, native name (e.g., "Deutsch").
     */
    public String getDisplayName() {
        return displayName;
    }

    /**
     * Attempts to find a Language enum constant based on its ISO code (case-insensitive).
     * * @param code The ISO 639-1 language code string to search for.
     * @return The corresponding Language enum object.
     * @throws IllegalArgumentException if no matching language code is found.
     */
    public static Language fromCode(String code) {
        for (Language lang : values()) {
            if (lang.code.equalsIgnoreCase(code)) {
                return lang;
            }
        }
        throw new IllegalArgumentException("Unknown language code: " + code);
    }
}