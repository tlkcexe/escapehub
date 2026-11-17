package gr.ihu.ict.escapehub;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.info.BuildProperties;
import org.springframework.boot.info.GitProperties;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(properties = {
        "spring.autoconfigure.exclude=" +
                "org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration," +
                "org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration," +
                "org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration",
        "management.endpoints.web.exposure.include=health,info",
        "management.info.git.enabled=true",
        "management.info.git.mode=full",
        "management.info.build.enabled=true"
})
@AutoConfigureMockMvc
class ActuatorInfoTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private BuildProperties buildProperties;

    @Autowired
    private GitProperties gitProperties;

    @Test
    void infoEndpointReturnsCorrectVersionAndCommit()
            throws Exception {

        mockMvc.perform(get("/actuator/info"))
                .andExpect(status().isOk())
                .andExpect(jsonPath(
                        "$.build.version",
                        is(buildProperties.getVersion())
                ))
                .andExpect(jsonPath(
                        "$.git.commit.id.full",
                        is(gitProperties.getCommitId())
                ));
    }
}
