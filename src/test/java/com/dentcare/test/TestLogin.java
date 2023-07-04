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
        page.navigate("http://localhost:3030/SWP391-SE1743/login.jsp");
        String pageTitle = page.title();
        Assertions.assertEquals("DentCare", pageTitle);
        // Ghi lại các hành động đăng nhập
        page.fill("#user", "usertestdcbs@gmail.com");
        page.fill("#pass", "User123456");
        page.click("//button[contains(text(),'Đăng Nhập')]");
        String currentUrl = page.url();  
        Assertions.assertEquals("http://localhost:3030/SWP391-SE1743/MainController", currentUrl);    
        page.hover("//a[@class='dropdown-toggle nav-link user-link']");
        page.waitForSelector("//button[contains(text(),'Tài Khoản')]");
        page.click("//button[contains(text(),'Tài Khoản')]");
        String currentContent = page.textContent(".user-name.m-t-0.mb-0"); 
        Assertions.assertEquals("User Test", currentContent);
    }
    
}
