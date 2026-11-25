package edu.northwestu.project1p2.services;

import edu.northwestu.project1p2.entities.User;
import edu.northwestu.project1p2.forms.ProfileForm;
import edu.northwestu.project1p2.repositories.UserRepository;
import org.springframework.stereotype.Service;

/** Reads and updates the fields a player can edit on their own profile. */
@Service
public class ProfileService {

    private final UserRepository userRepository;

    public ProfileService(final UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /** The user's current values, ready to fill in the form. */
    public ProfileForm load(final long userId) {
        final User user = this.userRepository.findById(userId).orElseThrow();
        final ProfileForm form = new ProfileForm();
        form.setGamerTag(user.getGamerTag());
        return form;
    }

    /** Copies the form onto the user and saves it. Spring Data JDBC has no dirty checking: no save, no UPDATE. */
    public void update(final long userId, final ProfileForm form) {
        final User user = this.userRepository.findById(userId).orElseThrow();
        user.setGamerTag(form.getGamerTag());
        this.userRepository.save(user);
    }
}
