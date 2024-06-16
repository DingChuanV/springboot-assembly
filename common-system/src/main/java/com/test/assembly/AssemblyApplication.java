package com.test.assembly;

import com.test.a1.FiSystemApplication;
import com.test.a2.PmSystemApplication;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.context.scope.refresh.RefreshScope;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * 新增启动参数 program arguments
 * 实盘 --system --pm-system
 * 仿真 --system-simulate --pm-system-simulate
 * @author dingchuan
 */
@Slf4j
@SpringBootApplication(scanBasePackages = "com.test.assembly")
@EnableDiscoveryClient
public class AssemblyApplication {

  public static final String SYSTEM = "system";
  public static final String SYSTEM_SIMULATE = "system-simulate";
  public static final String PM_SYSTEM = "pm-system";
  public static final String PM_SYSTEM_SIMULATE = "pm-system-simulate";

  public static void main(String[] args) {
    final ConfigurableApplicationContext commonContext =
        new SpringApplicationBuilder(AssemblyApplication.class)
            .properties("spring.cloud.service-registry.auto-registration.enabled=false")
            .web(WebApplicationType.NONE)
            .run(args);
    log.info(commonContext.getId() + " isActive: " + commonContext.isActive());
    log.info(commonContext.getId() + " env: " + commonContext.getEnvironment());

    // system or system-simulate
    if (commonContext.getEnvironment().containsProperty(SYSTEM)) {
      System.setProperty("spring.cloud.service-registry.auto-registration.enabled", "true");

      String[] a1Args = new String[]{"--spring.application.name=system"};
      System.setProperty("spring.application.admin.enabled", "false");
      final ConfigurableApplicationContext context =
          new SpringApplicationBuilder(FiSystemApplication.class)
//              .profiles("real")
//              .properties("spring.application.name=system")
              .properties("server.port=9060")
              .properties("spring.config.name=bootstrap-real")
              .properties("spring.jmx.default-domain=system-jmx")
              .sources(RefreshScope.class)
              .run(a1Args);
      log.info("system port: " + context.getEnvironment().getProperty("server.port"));
      log.info("system:{}", a1Args);
      log.info("system:{}", context.getEnvironment().getProperty("spring.application.name"));
      log.info(context.getId() + " isActive: " + context.isActive());
      log.info(commonContext.getId() + " env: " + context.getEnvironment());
    } else if (commonContext.getEnvironment().containsProperty(SYSTEM_SIMULATE)) {
      System.setProperty("spring.cloud.service-registry.auto-registration.enabled", "true");

      String[] a1Args = new String[]{"--spring.application.name=system"};
      System.setProperty("spring.application.admin.enabled", "false");
      final ConfigurableApplicationContext context =
          new SpringApplicationBuilder(FiSystemApplication.class)
//              .profiles("simulate")
//              .properties("spring.application.name=system")
              .properties("server.port=9060")
              .properties("spring.config.name=bootstrap-simulate")
              .sources(RefreshScope.class)
              .properties("spring.jmx.default-domain=system-simulate-jmx")
              .run(a1Args);

      log.info("system-simulate port: " + context.getEnvironment().getProperty("server.port"));
      log.info("system-simulate:{}", a1Args);
      log.info("system-simulate:{}",
          context.getEnvironment().getProperty("spring.application.name"));
      log.info(context.getId() + " isActive: " + context.isActive());
      log.info(commonContext.getId() + " env: " + context.getEnvironment());
    }

    // pm-system or pm-system-simulate
    if (commonContext.getEnvironment().containsProperty(PM_SYSTEM)) {
      System.setProperty("spring.cloud.service-registry.auto-registration.enabled", "true");

      System.setProperty("spring.application.admin.enabled", "false");
      String[] a1Args = new String[]{"--spring.application.name=pm-system"};
      final ConfigurableApplicationContext context =
          new SpringApplicationBuilder(PmSystemApplication.class)
//              .profiles("real")
//              .properties("spring.application.name=pm-system")
              .properties("server.port=9070")
              .properties("spring.config.name=bootstrap-real")
              .properties("spring.jmx.default-domain=pm-system-jmx")
              .sources(RefreshScope.class)
              .run(a1Args);
      log.info("pm-system port: " + context.getEnvironment().getProperty("server.port"));
      log.info("pm-system:{}", a1Args);
      log.info("pm-system:{}", context.getEnvironment().getProperty("spring.application.name"));
      log.info(context.getId() + " isActive: " + context.isActive());
      log.info(commonContext.getId() + " env: " + context.getEnvironment());
    } else if (commonContext.getEnvironment().containsProperty(PM_SYSTEM_SIMULATE)) {
      System.setProperty("spring.cloud.service-registry.auto-registration.enabled", "true");

      System.setProperty("spring.application.admin.enabled", "false");
      String[] a1Args = new String[]{"--spring.application.name=pm-system"};
      final ConfigurableApplicationContext context =
          new SpringApplicationBuilder(PmSystemApplication.class)
//              .profiles("simulate")
//              .properties("spring.application.name=pm-system")
              .properties("server.port=9070")
              .properties("spring.config.name=bootstrap-simulate")
              .properties("spring.jmx.default-domain=pm-system-simulate-jmx")
              .sources(RefreshScope.class)
              .run(a1Args);
      log.info("pm-system-simulate:{}", a1Args);
      log.info("pm-system-simulate:{}",
          context.getEnvironment().getProperty("spring.application.name"));
      log.info(context.getId() + " isActive: " + context.isActive());
      log.info(commonContext.getId() + " env: " + context.getEnvironment());
    }
  }
}
