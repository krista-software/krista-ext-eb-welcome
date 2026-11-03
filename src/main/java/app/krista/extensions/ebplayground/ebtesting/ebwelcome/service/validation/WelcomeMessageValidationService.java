package app.krista.extensions.ebplayground.ebtesting.ebwelcome.service.validation;

import org.jvnet.hk2.annotations.Service;

/**
 * Input validation for the Welcome area. Every failure throws {@link IllegalArgumentException}
 * with the field name, why it failed, and an example — the signal the Area's audited wrapper
 * catches to classify the response as an input error.
 */
@Service
public class WelcomeMessageValidationService {

    public void validateGenerate(String recipientName) {
        ValidationUtil.requireNonEmpty(recipientName, "Recipient Name");
    }
}

/** Static, non-instantiable primitive toolkit — package-private, shared by validation services. */
final class ValidationUtil {
    private ValidationUtil() {
    }

    static boolean isNullOrEmpty(String value) {
        return value == null || value.trim().isEmpty();
    }

    static void requireNonEmpty(String value, String field) {
        if (isNullOrEmpty(value)) {
            throw new IllegalArgumentException(
                    field + " is required. Please provide a value for " + field + ", e.g. \"Jane Doe\".");
        }
    }
}
