// component/EndpointLogger.java
package quant.ecommerce;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;
import quant.ecommerce.entity.auth.Permission;
import quant.ecommerce.enums.HttpMethod;
import quant.ecommerce.repository.PermissionRepository;

@Slf4j
@Component
@RequiredArgsConstructor
public class EndpointLogger implements ApplicationRunner {

    private final RequestMappingHandlerMapping requestMappingHandlerMapping;
    private final PermissionRepository permissionRepository;

    @Override
    public void run(ApplicationArguments args) {
        log.info("=".repeat(60));
        log.info("ALL ENDPOINTS:");
        log.info("=".repeat(60));

        requestMappingHandlerMapping.getHandlerMethods()
                .entrySet()
                .stream()
                .flatMap(entry -> {
                    var patterns = entry.getKey().getPatternValues();
                    var methods = entry.getKey().getMethodsCondition().getMethods();
                    return patterns.stream().flatMap(path ->
                            methods.stream().map(method -> new String[]{method.name(), path})
                    );
                })
                .filter(e -> e[1].startsWith("/api/"))
                .sorted((a, b) -> a[1].compareTo(b[1]))
                .forEach(e -> {
                    log.info("%-10s %s".formatted(e[0], e[1]));
                    syncToDb(e[0], e[1]);
                });

        log.info("=".repeat(60));
    }

    private void syncToDb(String methodStr, String path) {
        try {
            HttpMethod method = HttpMethod.valueOf(methodStr);
            if (!permissionRepository.existsByPathAndMethod(path, method)) {
                permissionRepository.save(Permission.builder()
                        .name(methodStr + " " + path)
                        .path(path)
                        .method(method)
                        .build());
                log.info("Added permission: {} {}", methodStr, path);
            }
        } catch (IllegalArgumentException e) {
            // bỏ qua method không có trong enum (vd: HEAD, OPTIONS)
        }
    }
}