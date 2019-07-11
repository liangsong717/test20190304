package com.fineway.scaffolding;


import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.*;
import com.github.springtestdbunit.assertion.DatabaseAssertionMode;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest()
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class,
        DirtiesContextTestExecutionListener.class,
        DbUnitTestExecutionListener.class })
@DbUnitConfiguration(databaseConnection = "dbUnitDatabaseConnection")
public class DBUnitTest {


    @Test
    @DatabaseSetup(value = "/META-INF/children.xml", type = DatabaseOperation.CLEAN_INSERT)
    public void query() {
    }

    @Test
    @DatabaseSetup(value = "/META-INF/children.xml", type = DatabaseOperation.REFRESH)
    @ExpectedDatabase(value = "/META-INF/expectedchildren.xml",table = "children",
            assertionMode = DatabaseAssertionMode.NON_STRICT)
    public void update() {
    }

    @Test
    @DatabaseSetups({
            @DatabaseSetup(value = "/META-INF/children.xml", type = DatabaseOperation.CLEAN_INSERT),
            @DatabaseSetup(value = "/META-INF/sampleData.xml", type = DatabaseOperation.CLEAN_INSERT)})
    @ExpectedDatabases({
            @ExpectedDatabase(value = "/META-INF/expectedchildren.xml",table = "children",
                    assertionMode = DatabaseAssertionMode.NON_STRICT),
            @ExpectedDatabase(value = "/META-INF/expectedsampleData.xml",table = "static_resource", assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED)
    })
    public void demoQuery() {
    }
}
