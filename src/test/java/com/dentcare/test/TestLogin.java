/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package com.dentcare.test;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import com.microsoft.playwright.*;
import org.junit.jupiter.api.Assertions;

/**
 *
 * @author ADMIN
 */
public class TestLogin {
    
    private static Playwright playwright;
    private static Browser browser;
    private static Page page;

    @BeforeAll
    public static void setUp() {
        playwright = Playwright.create();
        browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false).setSlowMo(50).setChannel("chrome"));
        page = browser.newPage();
    }

    @AfterAll
    public static void tearDown() {
        page.waitForTimeout(7000); // Dừng thực thi trong 7 giây
        browser.close();
        playwright.close();
    }

    @Test
    public void testPageTitle() {
        page.navigate("http://localhost:8080/SWP391-SE1743");
        String pageTitle = page.title();
        Assertions.assertEquals("DentCare", pageTitle);
        
    }
    
}
