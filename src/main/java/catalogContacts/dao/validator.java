package catalogContacts.dao;

/**
 *
 */
public interface Validator {
    /**
     * Проверяет xml файлы хранилища на соответсвие схемы xsd
     * @return
     */
    boolean isXmlCorrect();
}
