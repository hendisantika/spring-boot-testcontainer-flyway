package com.hendisantika;

import org.flywaydb.core.Flyway;
import org.jetbrains.annotations.NotNull;
import org.springframework.core.Ordered;
import org.springframework.test.context.TestContext;
import org.springframework.test.context.TestExecutionListener;

/**
 * Created by IntelliJ IDEA.
 * Project : spring-boot-testcontainer-flyway
 * User: hendisantika
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 3/11/23
 * Time: 14:42
 * To change this template use File | Settings | File Templates.
 */
public class CleanDatabaseTestExecutionListener implements TestExecutionListener, Ordered {
    @Override
    public void beforeTestMethod(@NotNull TestContext testContext) {
        var flyway = testContext.getApplicationContext().getBean(Flyway.class);
        flyway.clean();
        flyway.migrate();
    }

    @Override
    public int getOrder() {
        // Ensures that this TestExecutionListener is run before SqlScriptExecutionTestListener which handles @Sql.
        return Ordered.HIGHEST_PRECEDENCE;
    }
}
