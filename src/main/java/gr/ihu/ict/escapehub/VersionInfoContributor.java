
package gr.ihu.ict.escapehub;

import org.springframework.boot.actuate.info.Info;
import org.springframework.boot.actuate.info.InfoContributor;
import org.springframework.boot.info.BuildProperties;
import org.springframework.boot.info.GitProperties;
import org.springframework.stereotype.Component;

@Component
public class VersionInfoContributor implements InfoContributor {

    private final BuildProperties buildProperties;
    private final GitProperties gitProperties;

    public VersionInfoContributor(
            BuildProperties buildProperties,
            GitProperties gitProperties) {
        this.buildProperties = buildProperties;
        this.gitProperties = gitProperties;
    }

    @Override
    public void contribute(Info.Builder builder) {
        builder.withDetail("version", buildProperties.getVersion());
        builder.withDetail("commit", gitProperties.getCommitId());
    }
}
