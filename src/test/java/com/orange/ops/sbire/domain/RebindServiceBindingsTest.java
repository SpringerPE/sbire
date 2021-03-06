package com.orange.ops.sbire.domain;

import com.orange.ops.sbire.tags.RebindServiceBindings;
import com.tngtech.jgiven.junit.SimpleScenarioTest;
import org.junit.Test;

/**
 * @author Sebastien Bortolussi
 */
@RebindServiceBindings
public class RebindServiceBindingsTest extends SimpleScenarioTest<ServiceBindingsStage> {

    private static final ImmutableServiceBindingDetail[] ALL_SERVICE_BINDINGS = new ImmutableServiceBindingDetail[]{
            ImmutableServiceBindingDetail.builder()
                    .id("aaa")
                    .serviceInstanceId("my-redis-111-id")
                    .serviceInstanceName("my-redis-111")
                    .servicePlan("shared-vm")
                    .service("p-redis")
                    .space("space11")
                    .organization("org1")
                    .applicationId("my-app-id")
                    .applicationName("my-app")
                    .build(),
            ImmutableServiceBindingDetail.builder()
                    .id("bbb")
                    .serviceInstanceId("my-redis-211-id")
                    .serviceInstanceName("my-redis-211")
                    .servicePlan("dedicated-vm")
                    .service("p-redis")
                    .space("space11")
                    .organization("org1")
                    .applicationId("my-app-id")
                    .applicationName("my-app")
                    .build(),
            ImmutableServiceBindingDetail.builder()
                    .id("ccc")
                    .serviceInstanceId("my-redis-112-id")
                    .serviceInstanceName("my-redis-112")
                    .servicePlan("shared-vm")
                    .service("p-redis")
                    .space("space12")
                    .organization("org2")
                    .applicationId("my-app-id")
                    .applicationName("my-app")
                    .build(),
            ImmutableServiceBindingDetail.builder()
                    .id("ddd")
                    .serviceInstanceId("my-redis-122-id")
                    .serviceInstanceName("my-redis-122")
                    .servicePlan("shared-vm")
                    .service("p-redis")
                    .space("space22")
                    .organization("org2")
                    .applicationId("my-app-id")
                    .applicationName("my-app")
                    .build()
    };

    @Test
    public void paas_ops_rebinds_all_service_broker_service_bindings() throws Exception {
        given().service_bindings(ALL_SERVICE_BINDINGS);
        when().paas_ops_rebinds_service_broker_$_service_bindings("p-redis");
        then().she_should_get_$_new_service_bindings(4,
                ImmutableServiceBindingDetail.builder()
                        .id("eee")
                        .serviceInstanceId("my-redis-111-id")
                        .serviceInstanceName("my-redis-111")
                        .servicePlan("shared-vm")
                        .service("p-redis")
                        .space("space11")
                        .organization("org1")
                        .applicationId("my-app-id")
                        .applicationName("my-app")
                        .build(),
                ImmutableServiceBindingDetail.builder()
                        .id("fff")
                        .serviceInstanceId("my-redis-211-id")
                        .serviceInstanceName("my-redis-211")
                        .servicePlan("dedicated-vm")
                        .service("p-redis")
                        .space("space11")
                        .organization("org1")
                        .applicationId("my-app-id")
                        .applicationName("my-app")
                        .build(),
                ImmutableServiceBindingDetail.builder()
                        .id("ggg")
                        .serviceInstanceId("my-redis-112-id")
                        .serviceInstanceName("my-redis-112")
                        .servicePlan("shared-vm")
                        .service("p-redis")
                        .space("space12")
                        .organization("org2")
                        .applicationId("my-app-id")
                        .applicationName("my-app")
                        .build(),
                ImmutableServiceBindingDetail.builder()
                        .id("hhh")
                        .serviceInstanceId("my-redis-122-id")
                        .serviceInstanceName("my-redis-122")
                        .servicePlan("shared-vm")
                        .service("p-redis")
                        .space("space22")
                        .organization("org2")
                        .applicationId("my-app-id")
                        .applicationName("my-app")
                        .build());
    }

    @Test
    public void paas_ops_rebinds_service_bindings_for_unknown_service_broker() throws Exception {
        given().service_bindings(ALL_SERVICE_BINDINGS);
        when().paas_ops_rebinds_service_broker_$_service_bindings("dummy");
        then().she_should_get_a_rebind_error("Service Broker dummy does not exist");
    }

    @Test
    public void paas_ops_rebinds_service_broker_service_bindings_for_an_org() throws Exception {
        given().service_bindings(ALL_SERVICE_BINDINGS);
        when().paas_ops_rebinds_service_broker_$_service_bindings_for_org("p-redis", "org1");
        then().she_should_get_$_new_service_bindings(2,
                ImmutableServiceBindingDetail.builder()
                        .id("eee")
                        .serviceInstanceId("my-redis-111-id")
                        .serviceInstanceName("my-redis-111")
                        .servicePlan("shared-vm")
                        .service("p-redis")
                        .space("space11")
                        .organization("org1")
                        .applicationId("my-app-id")
                        .applicationName("my-app")
                        .build(),
                ImmutableServiceBindingDetail.builder()
                        .id("fff")
                        .serviceInstanceId("my-redis-211-id")
                        .serviceInstanceName("my-redis-211")
                        .servicePlan("dedicated-vm")
                        .service("p-redis")
                        .space("space11")
                        .organization("org1")
                        .applicationId("my-app-id")
                        .applicationName("my-app")
                        .build()
        );
    }

    @Test
    public void paas_ops_rebinds_service_broker_service_bindings_for_an_unknown_org() throws Exception {
        given().service_bindings(ALL_SERVICE_BINDINGS);
        when().paas_ops_rebinds_service_broker_$_service_bindings_for_org("p-redis", "dummy");
        then().she_should_get_no_service_binding_created();
    }

    @Test
    public void paas_ops_rebinds_service_broker_service_bindings_for_a_space() throws Exception {
        given().service_bindings(ALL_SERVICE_BINDINGS);
        when().paas_ops_rebinds_service_broker_$_service_bindings_for_org_$_and_space("p-redis", "org2", "space12");
        then().she_should_get_$_new_service_bindings(1,
                ImmutableServiceBindingDetail.builder()
                        .id("ggg")
                        .serviceInstanceId("my-redis-112-id")
                        .serviceInstanceName("my-redis-112")
                        .servicePlan("shared-vm")
                        .service("p-redis")
                        .space("space12")
                        .organization("org2")
                        .applicationId("my-app-id")
                        .applicationName("my-app")
                        .build()
        );
    }

    @Test
    public void paas_ops_rebinds_service_broker_service_bindings_for_an_unknown_space() throws Exception {
        given().service_bindings(ALL_SERVICE_BINDINGS);
        when().paas_ops_rebinds_service_broker_$_service_bindings_for_org_$_and_space("p-redis", "org2", "dummy");
        then().she_should_get_no_service_binding_created();
    }

    @Test
    public void paas_ops_rebinds_service_broker_service_bindings_for_a_service_label() throws Exception {
        given().service_bindings(ALL_SERVICE_BINDINGS);
        when().paas_ops_rebinds_service_broker_$_service_bindings_for_service_label("p-redis", "p-redis");
        then().she_should_get_$_new_service_bindings(4,
                ImmutableServiceBindingDetail.builder()
                        .id("eee")
                        .serviceInstanceId("my-redis-111-id")
                        .serviceInstanceName("my-redis-111")
                        .servicePlan("shared-vm")
                        .service("p-redis")
                        .space("space11")
                        .organization("org1")
                        .applicationId("my-app-id")
                        .applicationName("my-app")
                        .build(),
                ImmutableServiceBindingDetail.builder()
                        .id("fff")
                        .serviceInstanceId("my-redis-211-id")
                        .serviceInstanceName("my-redis-211")
                        .servicePlan("dedicated-vm")
                        .service("p-redis")
                        .space("space11")
                        .organization("org1")
                        .applicationId("my-app-id")
                        .applicationName("my-app")
                        .build(),
                ImmutableServiceBindingDetail.builder()
                        .id("ggg")
                        .serviceInstanceId("my-redis-112-id")
                        .serviceInstanceName("my-redis-112")
                        .servicePlan("shared-vm")
                        .service("p-redis")
                        .space("space12")
                        .organization("org2")
                        .applicationId("my-app-id")
                        .applicationName("my-app")
                        .build(),
                ImmutableServiceBindingDetail.builder()
                        .id("hhh")
                        .serviceInstanceId("my-redis-122-id")
                        .serviceInstanceName("my-redis-122")
                        .servicePlan("shared-vm")
                        .service("p-redis")
                        .space("space22")
                        .organization("org2")
                        .applicationId("my-app-id")
                        .applicationName("my-app")
                        .build());
    }

    @Test
    public void paas_ops_rebinds_service_broker_service_bindings_for_an_unknown_service_label() throws Exception {
        given().service_bindings(ALL_SERVICE_BINDINGS);
        when().paas_ops_rebinds_service_broker_$_service_bindings_for_service_label("p-redis", "dummy");
        then().she_should_get_no_service_binding_created();
    }


    @Test
    public void paas_ops_rebinds_service_broker_service_bindings_for_a_service_plan() throws Exception {
        given().service_bindings(ALL_SERVICE_BINDINGS);
        when().paas_ops_rebinds_service_broker_$_service_bindings_for_service_plan_name("p-redis", "dedicated-vm");
        then().she_should_get_$_new_service_bindings(1,
                ImmutableServiceBindingDetail.builder()
                        .id("fff")
                        .serviceInstanceId("my-redis-211-id")
                        .serviceInstanceName("my-redis-211")
                        .servicePlan("dedicated-vm")
                        .service("p-redis")
                        .space("space11")
                        .organization("org1")
                        .applicationId("my-app-id")
                        .applicationName("my-app")
                        .build()
        );
    }

    @Test
    public void paas_ops_rebinds_service_broker_service_bindings_for_an_unknown_service_plan() throws Exception {
        given().service_bindings(ALL_SERVICE_BINDINGS);
        when().paas_ops_rebinds_service_broker_$_service_bindings_for_service_plan_name("p-redis", "dummy");
        then().she_should_get_no_service_binding_created();
    }

    @Test
    public void paas_ops_rebinds_service_broker_service_bindings_for_a_service_instance() throws Exception {
        given().service_bindings(ALL_SERVICE_BINDINGS);
        when().paas_ops_rebinds_service_broker_$_service_bindings_for_service_instance("p-redis", "my-redis-112");
        then().she_should_get_$_new_service_bindings(1,
                ImmutableServiceBindingDetail.builder()
                        .id("ggg")
                        .serviceInstanceId("my-redis-112-id")
                        .serviceInstanceName("my-redis-112")
                        .servicePlan("shared-vm")
                        .service("p-redis")
                        .space("space12")
                        .organization("org2")
                        .applicationId("my-app-id")
                        .applicationName("my-app")
                        .build()
        );
    }

    @Test
    public void paas_ops_rebinds_service_broker_service_bindings_for_an_unknown_service_instance() throws Exception {
        given().service_bindings(ALL_SERVICE_BINDINGS);
        when().paas_ops_rebinds_service_broker_$_service_bindings_for_service_instance("p-redis", "dummy");
        then().she_should_get_no_service_binding_created();
    }

}