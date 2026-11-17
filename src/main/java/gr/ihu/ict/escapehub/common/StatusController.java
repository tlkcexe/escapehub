package gr.ihu.ict.escapehub.common;

import java.util.Map;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Βασικό endpoint ελέγχου λειτουργίας του skeleton (US-03).
 */
@RestController
@RequestMapping("/api/v1")
public class StatusController {

    @GetMapping("/status")
    public Map<String, String> getStatus() {
        return Map.of(
                "application", "EscapeHub",
                "status", "UP"
        );
    }
}
