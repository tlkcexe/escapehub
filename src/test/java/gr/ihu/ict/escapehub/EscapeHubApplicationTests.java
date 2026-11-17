package gr.ihu.ict.escapehub;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
class EscapeHubApplicationTests {

    @Test
    void contextLoads() {
        // Επαληθεύει ότι το Spring Boot context εκκινεί χωρίς σφάλματα
    }
}
