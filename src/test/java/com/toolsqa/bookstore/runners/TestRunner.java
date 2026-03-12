package com.toolsqa.bookstore.runners;

import com.toolsqa.bookstore.tests.AccountDeletionTest;
import com.toolsqa.bookstore.tests.BookManagementTest;
import com.toolsqa.bookstore.tests.CreateUserTest;
import com.toolsqa.bookstore.tests.LoginUserTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.platform.suite.api.IncludeTags;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.SelectPackages;
import org.junit.platform.suite.api.Suite;

@Suite
@IncludeTags("regression")
@DisplayName("Automação - Bookstore")
@SelectPackages("com.toolsqa.bookstore.tests.tests")
@SelectClasses({
        AccountDeletionTest.class,
        BookManagementTest.class,
        CreateUserTest.class,
        LoginUserTest.class
})

public class TestRunner { }
