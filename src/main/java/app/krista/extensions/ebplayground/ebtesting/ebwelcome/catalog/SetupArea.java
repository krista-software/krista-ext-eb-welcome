package app.krista.extensions.ebplayground.ebtesting.ebwelcome.catalog;

import app.krista.extension.executor.ExtensionResponse;
import app.krista.extension.executor.ExtensionResponseBuilder;
import app.krista.extension.executor.Invoker;
import app.krista.extension.impl.anno.CatalogRequest;
import app.krista.extension.impl.anno.Domain;
import app.krista.extension.impl.anno.Field;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Setup area — the standard Test Connection + Health Check requests.
 *
 * {@code area = "Setup"} matches the area name already registered in Krista Studio for this
 * domain exactly (capital S, lowercase u). This extension has no external system or credentials
 * (authentication: "none"), so these checks confirm the extension itself is loaded and running —
 * there is nothing external to connect to.
 */
@Domain(id = "catEntryDomain_738dcd68-fbd8-4207-a7b2-bac849a4362b",
        name = "EB Testing",
        ecosystemId = "catEntryEcosystem_e1557d10-6587-409e-aa1c-b1bbb4c42b17",
        ecosystemName = "EB Playground",
        ecosystemVersion = "86e76048-8d73-4636-a70a-bb2d0e5dcf2c")
public class SetupArea {

    private static final Logger LOG = LoggerFactory.getLogger(SetupArea.class);

    private final String invokerId;

    @Inject
    public SetupArea(Invoker invoker) {
        this.invokerId = invoker.getInvokerId();
    }

    @CatalogRequest(
            id = "localDomainRequest_b2c3d4e5-6f70-48b9-8c2d-3e4f5a6b7c8d",
            name = "Test Connection",
            description = "Verify the extension is loaded and operating normally. This extension has no external system to connect to.",
            area = "Setup",
            type = CatalogRequest.Type.QUERY_SYSTEM)
    @Field.Boolean(name = "Success", required = false)
    @Field.Text(name = "Message", required = false)
    public ExtensionResponse testConnection() {
        Map<String, Object> out = new LinkedHashMap<>();
        out.put("Success", true);
        out.put("Message", "EB Welcome is operating normally. No external system is configured — "
                + "this extension generates welcome messages locally.");
        LOG.info("Test Connection ok for invoker {}", invokerId);
        return new ExtensionResponseBuilder().success(out).build();
    }

    @CatalogRequest(
            id = "localDomainRequest_c3d4e5f6-7081-49ca-9d3e-4f5a6b7c8d9e",
            name = "Health Check",
            description = "Report extension health (JVM heap usage).",
            area = "Setup",
            type = CatalogRequest.Type.CHANGE_SYSTEM)
    @Field.Boolean(name = "Is Healthy", required = false)
    @Field(name = "Heap Used MB", type = "Number", required = false)
    @Field.Text(name = "Details", required = false)
    public ExtensionResponse healthCheck() {
        Map<String, Object> out = new LinkedHashMap<>();
        MemoryMXBean memBean = ManagementFactory.getMemoryMXBean();
        long heapUsedMb = memBean.getHeapMemoryUsage().getUsed() / (1024 * 1024);
        out.put("Is Healthy", true);
        out.put("Heap Used MB", (double) heapUsedMb);
        out.put("Details", "OK");
        LOG.info("Health Check ok for invoker {} ({} MB heap used)", invokerId, heapUsedMb);
        return new ExtensionResponseBuilder().success(out).build();
    }
}
