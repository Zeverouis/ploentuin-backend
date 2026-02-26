INSERT INTO planner_item_catalog (id, name, colour, image_url, type)
VALUES (1, 'Tomato', 'red', null, 'VEGETABLES');

INSERT INTO ploentuin_user (id, username, email, password, email_verified, role, email_verification_token, reset_token, banned)
VALUES (7, 'testuser', 'test@example.com', 'encodedpassword', true, 'USER', NULL, NULL, false);

INSERT INTO user_profile (user_id, avatar_url, about)
VALUES (7, 'https://img.icons8.com/?size=100&id=14736&format=png&color=000000', 'Ik ben een test subject, neger me maar weer lekker hoor! Whaaaaaaaaaaaaaaa!(luigi)');