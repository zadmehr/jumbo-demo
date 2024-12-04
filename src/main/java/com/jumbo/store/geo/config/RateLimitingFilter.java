package com.jumbo.store.geo.config;
import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Refill;
import org.springframework.stereotype.Component;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.Duration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * RateLimitingFilter is a servlet filter that applies rate limiting to incoming HTTP requests based on the client's IP address.
 * It uses a token bucket algorithm to limit the number of requests a client can make within a specified time window.
 * 
 * <p>This filter checks the client's IP address and maintains a bucket for each IP. If the bucket has available tokens,
 * the request is allowed to proceed; otherwise, a 429 Too Many Requests response is returned.</p>
 * 
 * <p>Configuration:</p>
 * <ul>
 *   <li>Rate limit: 5 requests per second</li>
 * </ul>
 * 
 * <p>Dependencies:</p>
 * <ul>
 *   <li>Bucket4j library for token bucket implementation</li>
 * </ul>
 * 
 * <p>Example usage:</p>
 * <pre>
 * {@code
 * @Component
 * public class RateLimitingFilter implements Filter {
 *     // implementation details
 * }
 * }
 * </pre>
 * 
 * @see jakarta.servlet.Filter
 * @see jakarta.servlet.FilterChain
 * @see jakarta.servlet.ServletRequest
 * @see jakarta.servlet.ServletResponse
 * @see jakarta.servlet.http.HttpServletRequest
 * @see jakarta.servlet.http.HttpServletResponse
 */
@Component
public class RateLimitingFilter implements Filter {

    private final Map<String, Bucket> cache = new ConcurrentHashMap<>();

    @Override
    public void doFilter(jakarta.servlet.ServletRequest request, jakarta.servlet.ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        String clientIp = getClientIp(httpRequest);
        Bucket bucket = cache.computeIfAbsent(clientIp, this::createNewBucket);

        if (bucket.tryConsume(1)) {
            chain.doFilter(request, response);
        } else {
            // Throw 429 Too Many Requests status code
            httpResponse.setStatus(429);
            httpResponse.getWriter().write("Rate limit exceeded. Please try again later.");
        }
    }

    private Bucket createNewBucket(String key) {
        Bandwidth limit = Bandwidth.classic(5, Refill.greedy(5, Duration.ofSeconds(1))); 
        return Bucket.builder()
                .addLimit(limit)
                .build();
    }

    private String getClientIp(HttpServletRequest request) {
        String xfHeader = request.getHeader("X-Forwarded-For");
        if (xfHeader == null) {
            return request.getRemoteAddr();
        }
        return xfHeader.split(",")[0];
    }
}