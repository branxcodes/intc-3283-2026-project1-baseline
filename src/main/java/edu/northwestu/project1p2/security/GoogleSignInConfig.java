package edu.northwestu.project1p2.security;

import org.jspecify.annotations.Nullable;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotatedTypeMetadata;
import org.springframework.security.config.oauth2.client.CommonOAuth2Provider;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;

/**
 * "Sign in with Google" is opt-in: it is only switched on when both
 * {@code app.google.client-id} and {@code app.google.client-secret} are set
 * (normally through the {@code GOOGLE_CLIENT_ID} / {@code GOOGLE_CLIENT_SECRET}
 * environment variables). Without them there is no {@link ClientRegistrationRepository},
 * so {@link SecurityConfig} skips OAuth login and the login page hides the button.
 */
@Configuration
@Conditional(GoogleSignInConfig.CredentialsPresent.class)
public class GoogleSignInConfig {

    static final String CLIENT_ID = "app.google.client-id";

    static final String CLIENT_SECRET = "app.google.client-secret";

    /** Google's well-known endpoints, the openid/profile/email scopes, and our credentials. */
    @Bean
    public ClientRegistrationRepository clientRegistrationRepository(final Environment env) {
        return new InMemoryClientRegistrationRepository(CommonOAuth2Provider.GOOGLE.getBuilder("google")
                .clientId(env.getRequiredProperty(CLIENT_ID))
                .clientSecret(env.getRequiredProperty(CLIENT_SECRET))
                .build());
    }

    /** Matches only when both credentials are present and non-blank. */
    static final class CredentialsPresent implements Condition {

        @Override
        public boolean matches(final ConditionContext context, final AnnotatedTypeMetadata metadata) {
            final Environment env = context.getEnvironment();
            return hasText(env.getProperty(CLIENT_ID)) && hasText(env.getProperty(CLIENT_SECRET));
        }

        private static boolean hasText(final @Nullable String value) {
            return value != null && !value.isBlank();
        }
    }
}
