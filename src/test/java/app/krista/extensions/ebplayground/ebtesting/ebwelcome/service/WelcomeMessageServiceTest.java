package app.krista.extensions.ebplayground.ebtesting.ebwelcome.service;

import app.krista.extensions.ebplayground.ebtesting.ebwelcome.service.validation.WelcomeMessageValidationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class WelcomeMessageServiceTest {

    private WelcomeMessageService service;

    @BeforeEach
    void setUp() {
        service = new WelcomeMessageService(new WelcomeMessageValidationService());
    }

    @Test
    void defaultTemplateIncludesRecipientName() {
        String message = service.generate("Jane Doe", null);

        assertTrue(message.contains("Jane Doe"), "default message should contain the recipient name");
        assertTrue(message.toLowerCase().contains("welcome"), "default message should be a welcome message");
    }

    @Test
    void blankCustomMessageFallsBackToDefaultTemplate() {
        String message = service.generate("Jane Doe", "   ");

        assertTrue(message.contains("Jane Doe"));
        assertTrue(message.toLowerCase().contains("welcome"));
    }

    @Test
    void customMessageWithPlaceholderSubstitutesName() {
        String message = service.generate("Sam", "Hi {{name}}, great to have you on the team!");

        assertEquals("Hi Sam, great to have you on the team!", message);
    }

    @Test
    void customMessageWithoutPlaceholderIsReturnedVerbatim() {
        String custom = "A fully custom announcement with no name placeholder.";

        String message = service.generate("Sam", custom);

        assertEquals(custom, message);
    }

    @Test
    void recipientNameIsTrimmedBeforeSubstitution() {
        String message = service.generate("  Pat  ", "Hello {{name}}!");

        assertEquals("Hello Pat!", message);
    }

    @Test
    void blankRecipientNameIsRejected() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> service.generate("   ", null));

        assertTrue(ex.getMessage().contains("Recipient Name"));
    }

    @Test
    void nullRecipientNameIsRejected() {
        assertThrows(IllegalArgumentException.class, () -> service.generate(null, null));
    }
}
