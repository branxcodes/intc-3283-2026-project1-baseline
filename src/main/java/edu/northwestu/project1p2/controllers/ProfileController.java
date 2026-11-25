package edu.northwestu.project1p2.controllers;

import edu.northwestu.project1p2.forms.ProfileForm;
import edu.northwestu.project1p2.security.AppUserDetails;
import edu.northwestu.project1p2.services.ProfileService;
import jakarta.validation.Valid;
import java.util.Objects;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * "My profile": the signed-in player edits their own details. Not in
 * {@code SecurityConfig.PUBLIC_PATHS}, so only signed-in users reach it. The user
 * id always comes from the session, never from the request.
 */
@Controller
@RequestMapping("/profile")
public class ProfileController {

    /** Template name, and the active nav key for this page. */
    private static final String VIEW = "profile";

    private static final String SECTION = "section";

    private final ProfileService profileService;

    public ProfileController(final ProfileService profileService) {
        this.profileService = profileService;
    }

    @GetMapping
    public String showProfile(@AuthenticationPrincipal final AppUserDetails currentUser, final Model model) {
        model.addAttribute("form", this.profileService.load(userId(currentUser)));
        model.addAttribute(SECTION, VIEW);
        return VIEW;
    }

    @PostMapping
    public String updateProfile(@AuthenticationPrincipal final AppUserDetails currentUser,
                                @Valid @ModelAttribute("form") final ProfileForm form,
                                final BindingResult bindingResult,
                                final Model model,
                                final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            model.addAttribute(SECTION, VIEW);
            return VIEW;
        }
        this.profileService.update(userId(currentUser), form);
        // Post/Redirect/Get. A flash attribute survives exactly one redirect.
        redirectAttributes.addFlashAttribute("saved", true);
        return "redirect:/profile";
    }

    /** A signed-in user's row always has an id. */
    private static long userId(final AppUserDetails currentUser) {
        return Objects.requireNonNull(currentUser.id(), "signed-in user has no id");
    }
}
