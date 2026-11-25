package edu.northwestu.project1p2.forms;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import org.jspecify.annotations.Nullable;

/**
 * Backing object for the "My profile" form: only the fields a player may change
 * themselves. It has no id, so a request can't choose which user it updates.
 */
public class ProfileForm {

    @NotBlank(message = "Gamer tag is required")
    @Pattern(regexp = RegistrationForm.GAMER_TAG_PATTERN, message = RegistrationForm.GAMER_TAG_MESSAGE)
    private String gamerTag = "";

    public String getGamerTag() {
        return this.gamerTag;
    }

    /** Trimmed on bind, like the registration form. */
    public void setGamerTag(final @Nullable String gamerTag) {
        this.gamerTag = gamerTag == null ? "" : gamerTag.trim();
    }
}
