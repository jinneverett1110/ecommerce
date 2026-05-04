//package quant.ecommerce.security;
//
//import jakarta.servlet.FilterChain;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import lombok.RequiredArgsConstructor;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.stereotype.Component;
//import org.springframework.web.filter.OncePerRequestFilter;
//import quant.ecommerce.repository.PermissionRepository;
//
//import java.io.IOException;
//import java.util.List;
//import java.util.Set;
//import java.util.stream.Collectors;
//
//@Component
//@RequiredArgsConstructor
//public class DynamicAuthFilter extends OncePerRequestFilter {
//    private final PermissionRepository permissionRepository;
//
//    // Các route không cần check permission
//    private static final List<String> PUBLIC_PATHS = List.of(
//            "/api/auth/register",
//            "/api/auth/login"
//    );
//
//    @Override
//    protected void doFilterInternal(HttpServletRequest request,
//                                    HttpServletResponse response,
//                                    FilterChain chain) throws ServletException, IOException {
//
//        String path = request.getRequestURI();
//        String method = request.getMethod();
//
//        // Bỏ qua public routes
//        if (PUBLIC_PATHS.contains(path)) {
//            chain.doFilter(request, response);
//            return;
//        }
//
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//
//        // Chưa đăng nhập
//        if (authentication == null || !authentication.isAuthenticated()) {
//            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//            return;
//        }
//
//        // Lấy roles của user
//        Set<String> roles = authentication.getAuthorities().stream()
//                .map(GrantedAuthority::getAuthority)
//                .collect(Collectors.toSet());
//
//        // Check trong DB xem role có permission với path + method không
//        boolean hasPermission = permissionRepository
//                .existsByPathAndMethodAndRoles(path, method, roles);
//
//        if (!hasPermission) {
//            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
//            return;
//        }
//
//        chain.doFilter(request, response);
//    }
//}
