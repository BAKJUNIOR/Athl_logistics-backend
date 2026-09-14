package athl.logistics.athl_logistics.security;

public interface JwtConfig {
    String BASE64_SECRET = System.getenv("JWT_SECRET") != null ? System.getenv("JWT_SECRET")
            : "ZG1wa01TWjgrT3NXT3hCd3J0b0plNEp4WGVUOHZBclQvY3h0WHZJam45ek13L2JQM08vQ2Yxc2xEeWhwcFZnRg==";

    int TOKEN_VALIDITY_SECONDS = System.getenv("JWT_VALIDITY") != null
            ? Integer.parseInt(System.getenv("JWT_VALIDITY"))
            : 86400;
    int TOKEN_VALIDITY_SECONDS_REMEMBER_ME = System.getenv("JWT_VALIDITY_REMEMBER") != null
            ? Integer.parseInt(System.getenv("JWT_VALIDITY_REMEMBER"))
            : 123456;
}
