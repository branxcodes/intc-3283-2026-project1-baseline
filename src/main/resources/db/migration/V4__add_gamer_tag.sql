-- A player's public handle. Nullable: rows that already exist, and accounts created
-- through Google sign-in, have no gamer tag until the player sets one on /profile.
ALTER TABLE users
    ADD COLUMN gamer_tag TEXT NULL;

-- The database enforces the same rule as the form, so bad data can't get in
-- through any other path (a script, psql, a future API).
ALTER TABLE users
    ADD CONSTRAINT users_gamer_tag_format CHECK (gamer_tag ~ '^[A-Za-z0-9_]{3,20}$');
