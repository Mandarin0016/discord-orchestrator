INSERT INTO discord_orchestrator_svc_test.user (id, create_on, discord_id, email, first_name, is_active,
                                                is_discord_authorized, last_name, password, role, updated_on, username)
VALUES ('30313866-3032-3164-2d36-3261382d3735', '2024-04-21 22:21:14.000000',
        null, 'viktor@gmail.com', 'Viktor', true, false, 'Aleksandrov', '12345', 'USER', '2024-04-21 22:22:00.000000',
        'mandarin');

INSERT INTO discord_orchestrator_svc_test.user_authorities (user_id, authority)
VALUES ('30313866-3032-3164-2d36-3261382d3735', 'DEFAULT_VIEW');