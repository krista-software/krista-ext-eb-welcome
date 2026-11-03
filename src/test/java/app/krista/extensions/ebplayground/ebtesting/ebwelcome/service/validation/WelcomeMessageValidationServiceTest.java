package app.krista.extensions.ebplayground.ebtesting.ebwelcome.service.validation;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class WelcomeMessageValidationServiceTest {

    private final WelcomeMessageValidationService validationService = new WelcomeMessageValidationService();

    @Test
    void acceptsNonBlankRecipientName() {
        assertDoesNotThrow(() -> validationService.validateGenerate("Jane Doe"));
    }

    @Test
    void rejectsNullRecipientName() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> validationService.validateGenerate(null));
        assertTrue(ex.getMessage().contains("Recipient Name"));
    }

    @Test
    void rejectsBlankRecipientName() {
        assertThrows(IllegalArgumentException.class, () -> validationService.validateGenerate("   "));
    }

    @Test
    void rejectsEmptyRecipientName() {
        assertThrows(IllegalArgumentException.class, () -> validationService.validateGenerate(""));
    }
}
