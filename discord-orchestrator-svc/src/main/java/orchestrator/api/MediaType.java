package orchestrator.api;

import lombok.experimental.UtilityClass;

@UtilityClass
public class MediaType {

    public static final String DISCORD_SETUP_REQUEST = "application/discord.server.setup.request+json;version=1";
    public static final String DISCORD_SETUP_RESPONSE = "application/discord.server.setup.response+json;version=1";

    public static final String USER_REGISTRATION_REQUEST = "application/user.registration.request+json;version=1";
    public static final String USER_REGISTRATION_RESPONSE = "application/user.registration.response+json;version=1";
    public static final String USER_LOGIN_REQUEST = "application/user.login.request+json;version=1";
    public static final String USER_LOGIN_RESPONSE = "application/user.login.response+json;version=1";

}
