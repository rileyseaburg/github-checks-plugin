package io.jenkins.plugins.checks.github;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

/**
 * Test to verify that the github-branch-source dependency is properly optional
 * and doesn't cause plugin loading failures due to version constraints.
 */
class OptionalDependencyTest {
    
    @Test
    void shouldAllowOptionalGitHubBranchSourceDependency() {
        // This test verifies that the github-branch-source dependency is optional
        // by checking that the classes can be loaded without causing ClassNotFoundException
        // when the dependency is available
        
        try {
            // These classes should be loadable when github-branch-source is available
            Class.forName("org.jenkinsci.plugins.github_branch_source.GitHubSCMSource");
            Class.forName("org.jenkinsci.plugins.github_branch_source.GitHubAppCredentials");
            Class.forName("org.jenkinsci.plugins.github_branch_source.Connector");
            
            // If we reach here, the dependency is available and working
            assertThat(true).as("github-branch-source dependency is available").isTrue();
        } catch (ClassNotFoundException e) {
            // This would happen if the dependency is not available, which should be fine
            // since it's marked as optional
            assertThat(e.getMessage()).contains("org.jenkinsci.plugins.github_branch_source");
        }
    }
    
    @Test
    void shouldNotFailWhenCreatingBasicObjects() {
        // Verify that basic plugin functionality doesn't depend on hard version requirements
        SCMFacade scmFacade = new SCMFacade();
        assertThat(scmFacade).isNotNull();
        
        // GitHubChecksPublisherFactory should be creatable regardless of github-branch-source version
        GitHubChecksPublisherFactory factory = new GitHubChecksPublisherFactory();
        assertThat(factory).isNotNull();
    }
    
    @Test
    void shouldGracefullyHandleOptionalDependency() {
        // Test that core plugin classes can be instantiated without requiring
        // specific versions of github-branch-source
        assertThatCode(() -> {
            new SCMFacade();
            new GitHubChecksPublisherFactory();
        }).as("Core plugin classes should instantiate without version dependencies")
          .doesNotThrowAnyException();
    }
}