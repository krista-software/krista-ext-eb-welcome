package app.krista.extensions.ebplayground.ebtesting.ebwelcome.catalog;

import app.krista.extension.executor.ExtensionResponse;
import app.krista.extension.executor.ExtensionResponse.Error.ExceptionType;
import app.krista.extension.executor.Invoker;
import app.krista.extension.impl.anno.Attribute;
import app.krista.extension.impl.anno.CatalogRequest;
import app.krista.extension.impl.anno.Domain;
import app.krista.extension.impl.anno.Field;
import app.krista.extensions.ebplayground.ebtesting.ebwelcome.catalog.extresp.ExtensionResponseFactory;
import app.krista.extensions.ebplayground.ebtesting.ebwelcome.catalog.extresp.TelemetryHelper;
import app.krista.extensions.ebplayground.ebtesting.ebwelcome.service.WelcomeMessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Welcome-message catalog requests.
 *
 * {@code area = "Welcome"} matches the area already registered in Krista Studio for the
 * EB Playground / EB Testing domain (see the domain's registered areas) rather than inventing a
 * new, near-duplicate area name for the same business concept.
 */
@Domain(id = "catEntryDomain_738dcd68-fbd8-4207-a7b2-bac849a4362b",
        name = "EB Testing",
        ecosystemId = "catEntryEcosystem_e1557d10-6587-409e-aa1c-b1bbb4c42b17",
        ecosystemName = "EB Playground",
        ecosystemVersion = "86e76048-8d73-4636-a70a-bb2d0e5dcf2c")
public class WelcomeArea {

    private static final Logger LOG = LoggerFactory.getLogger(WelcomeArea.class);
    private static final String PREFIX = "ebwelcome";

    private final WelcomeMessageService welcomeMessageService;
    private final TelemetryHelper telemetry;
    private final String invokerId;

    @Inject
    public WelcomeArea(WelcomeMessageService welcomeMessageService, TelemetryHelper telemetry, Invoker invoker) {
        this.welcomeMessageService = welcomeMessageService;
        this.telemetry = telemetry;
        this.invokerId = invoker.getInvokerId();
    }

    @CatalogRequest(
            id = "localDomainRequest_a1b2c3d4-5e6f-47a8-9b1c-2d3e4f5a6b7c",
            name = "Generate Welcome Message",
            description = "Compose a personalized welcome/greeting message for a named recipient. Self-contained — no external system is called.",
            area = "Welcome",
            type = CatalogRequest.Type.QUERY_SYSTEM)
    @Field(name = "Welcome Message", type = "Paragraph", required = false)
    public ExtensionResponse generateWelcomeMessage(
            @Field.Text(name = "Recipient Name", required = true,
                    attributes = {@Attribute(name = "visualWidth", value = "M")}, options = {}) String recipientName,
            @Field(name = "Custom Message", type = "Paragraph", required = false,
                    attributes = {@Attribute(name = "visualWidth", value = "L"),
                            @Attribute(name = "toolTip", value = "'Optional template overriding the default greeting. Include {{name}} to have the recipient name inserted; without it, this text is returned verbatim.'")},
                    options = {}) String customMessage) {
        return audited("GenerateWelcomeMessage", () -> {
            String message = welcomeMessageService.generate(recipientName, customMessage);
            Map<String, Object> out = new LinkedHashMap<>();
            out.put("Welcome Message", message);
            return ExtensionResponseFactory.ok(out);
        });
    }

    @FunctionalInterface
    private interface CheckedSupplier<T> {
        T get() throws Exception;
    }

    /** Uniform try/catch → classify → ExtensionResponse + telemetry + timing boundary. */
    private ExtensionResponse audited(String name, CheckedSupplier<ExtensionResponse> action) {
        long t0 = telemetry.start(PREFIX + "." + name);
        LOG.info("Catalog '{}' started for invoker {}", name, invokerId);
        try {
            ExtensionResponse r = action.get();
            LOG.info("Catalog '{}' ok in {}ms for invoker {}", name, System.currentTimeMillis() - t0, invokerId);
            telemetry.recordSuccess(PREFIX + "." + name, t0, Map.of());
            return r;
        } catch (IllegalArgumentException e) {
            LOG.warn("Catalog '{}' INPUT error for invoker {}: {}", name, invokerId, e.getMessage());
            telemetry.recordError(PREFIX + "." + name, t0, e, Map.of());
            return ExtensionResponseFactory.failure(e.getMessage(), ExceptionType.INPUT_ERROR);
        } catch (Exception e) {
            LOG.error("Catalog '{}' SYSTEM error for invoker {}: {}", name, invokerId, e.getMessage(), e);
            telemetry.recordError(PREFIX + "." + name, t0, e, Map.of());
            return ExtensionResponseFactory.failure(e.getMessage(), ExceptionType.SYSTEM_ERROR);
        }
    }
}
