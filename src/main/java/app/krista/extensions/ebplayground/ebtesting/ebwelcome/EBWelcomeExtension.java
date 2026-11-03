package app.krista.extensions.ebplayground.ebtesting.ebwelcome;

import app.krista.extension.impl.anno.ChangeLog;
import app.krista.extension.impl.anno.Extension;
import app.krista.extension.impl.anno.InvokerRequest;
import app.krista.extension.impl.anno.Java;
import app.krista.extension.impl.anno.StaticResource;

import java.util.Map;

/**
 * EB Welcome extension entry point.
 *
 * This extension calls no external system and requires no credentials (authentication answer:
 * "none") — it generates a welcome/greeting message entirely locally. There are therefore no
 * connection {@code @Field}s and no Setup-tab validate/test-connection/updated hooks on this
 * class; those concerns live on the business-facing Setup-area catalog requests instead.
 */
@Extension(
        name = "EB Welcome",
        version = "1.0.0",
        description = "Generates a personalized welcome/greeting message. Self-contained — no external system or credentials required.")
@StaticResource(path = "docs", file = "docs")
@ChangeLog(file = "docs/pages/ReleaseNotes.md")
@Java(version = Java.Version.JAVA_21)
public final class EBWelcomeExtension {

    @InvokerRequest(InvokerRequest.Type.CUSTOM_TABS)
    public Map<String, String> customTabs() {
        return Map.of("Documentation", "static/docs");
    }
}
