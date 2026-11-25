package edu.northwestu.project1p2.forms;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.jspecify.annotations.Nullable;

/**
 * Backing object for the registration form. Kept separate from the {@code User}
 * entity so request binding can never set fields like {@code id} or
 * {@code verifiedAt}, and so the raw password never reaches the entity.
 */
public class RegistrationForm {

    /** 3-20 letters, digits or underscores. Shared with the profile form; V4 enforces the same rule. */
    public static final String GAMER_TAG_PATTERN = "^[A-Za-z0-9_]{3,20}$";

    public static final String GAMER_TAG_MESSAGE = "Gamer tag must be 3-20 letters, numbers or underscores";

    @NotBlank(message = "Username is required")
    @Size(max = 50, message = "Username must be 50 characters or fewer")
    private String username = "";

    @NotBlank(message = "Gamer tag is required")
    @Pattern(regexp = GAMER_TAG_PATTERN, message = GAMER_TAG_MESSAGE)
    private String gamerTag = "";

    @NotBlank(message = "Email is required")
    @Email(message = "Enter a valid email address")
    @Size(max = 254, message = "Email must be 254 characters or fewer")
    private String email = "";

    // BCrypt only uses the first 72 bytes, so cap it there.
    @NotBlank(message = "Password is required")
    @Size(min = 8, max = 72, message = "Password must be between 8 and 72 characters")
    private String password = "";

    @NotBlank(message = "Confirm your password")
    private String confirmPassword = "";

    public String getUsername() {
        return this.username;
    }

    public void setUsername(final String username) {
        this.username = username;
    }

    public String getGamerTag() {
        return this.gamerTag;
    }

    /** Trimmed on bind, like the email address. */
    public void setGamerTag(final @Nullable String gamerTag) {
        this.gamerTag = gamerTag == null ? "" : gamerTag.trim();
    }

    public String getEmail() {
        return this.email;
    }

    /** Trimmed on bind so stray whitespace from copy/paste doesn't fail {@code @Email}. */
    public void setEmail(final @Nullable String email) {
        this.email = email == null ? "" : email.trim();
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(final String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return this.confirmPassword;
    }

    public void setConfirmPassword(final String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public boolean passwordsMatch() {
        return this.password != null && this.password.equals(this.confirmPassword);
    }
}
