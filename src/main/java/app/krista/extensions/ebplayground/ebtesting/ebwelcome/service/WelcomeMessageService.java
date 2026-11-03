package app.krista.extensions.ebplayground.ebtesting.ebwelcome.service;

import app.krista.extensions.ebplayground.ebtesting.ebwelcome.service.validation.WelcomeMessageValidationService;
import org.jvnet.hk2.annotations.Service;

import javax.inject.Inject;

/**
 * Composes the welcome/greeting message. Pure logic, no I/O — this extension has no external
 * system to call.
 *
 * <p>Behavior (see Documentation tab's "Generate Welcome Message" page for the user-facing
 * description): when {@code customMessage} is blank, the default template is used with the
 * recipient's name substituted in. When {@code customMessage} is supplied and contains the
 * {@value #NAME_PLACEHOLDER} placeholder, the name is substituted into it instead of the
 * default; when it is supplied without the placeholder, it is returned verbatim so a caller can
 * also use this request to simply echo back a fully custom message.
 */
@Service
public class WelcomeMessageService {

    static final String NAME_PLACEHOLDER = "{{name}}";
    private static final String DEFAULT_TEMPLATE =
            "Hello " + NAME_PLACEHOLDER + ", welcome! We're delighted to have you here.";

    private final WelcomeMessageValidationService validationService;

    @Inject
    public WelcomeMessageService(WelcomeMessageValidationService validationService) {
        this.validationService = validationService;
    }

    public String generate(String recipientName, String customMessage) {
        validationService.validateGenerate(recipientName);
        String trimmedName = recipientName.trim();
        String template = (customMessage != null && !customMessage.isBlank()) ? customMessage : DEFAULT_TEMPLATE;
        return template.contains(NAME_PLACEHOLDER) ? template.replace(NAME_PLACEHOLDER, trimmedName) : template;
    }
}
