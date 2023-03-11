package com.hendisantika;

import java.util.UUID;

/**
 * Created by IntelliJ IDEA.
 * Project : spring-boot-testcontainer-flyway
 * User: hendisantika
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 3/11/23
 * Time: 14:39
 * To change this template use File | Settings | File Templates.
 */
public record AuthorId(UUID id) {

    public AuthorId {
        if (id == null) {
            throw new NullPointerException("Required id is null");
        }
    }

    public static AuthorId from(String uuid) {
        return new AuthorId(UUID.fromString(uuid));
    }
}
