/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package com.dentcare.test;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import com.microsoft.playwright.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

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
        page.waitForTimeout(3000); // Dừng thực thi trong 3 giây
        browser.close();
        playwright.close();
    }

    @Test
    public void testPageTitle() {
        page.navigate("http://localhost:8080/SWP391-SE1743/login.jsp");
        String pageTitle = page.title();
        Assertions.assertEquals("DentCare", pageTitle);
        // Ghi lại các hành động đăng nhập
        page.fill("#user", "usertestdcbs@gmail.com");
        page.fill("#pass", "User123456");
        page.click("//button[contains(text(),'Đăng Nhập')]");
        page.waitForTimeout(3000);
        String currentUrl = page.url();  
        Assertions.assertEquals("http://localhost:8080/SWP391-SE1743/MainController", currentUrl);    
        page.hover("//a[@class='dropdown-toggle nav-link user-link']");
        page.waitForSelector("//button[contains(text(),'Tài Khoản')]");
        page.click("//button[contains(text(),'Tài Khoản')]");
        String currentContent = page.textContent("li:nth-child(2) span:nth-child(2) a:nth-child(1)"); 
        Assertions.assertEquals("usertestdcbs@gmail.com", currentContent);
    }
    
    //DDT 
    public static Object[][] initData(){    
        return new Object[][]{
                                {"ductri3122002@gmail.com", "User123456"},
                                {"levannhat574602@gmail.com", "User123456"},
                                {"phansongthao203@gmail.com", "User123456"}
                             };
    }
    @ParameterizedTest
    @MethodSource(value = "initData")
    public void testLogin_DDT(String email, String password) throws InterruptedException{
        page.navigate("http://localhost:8080/SWP391-SE1743/login.jsp");
        String pageTitle = page.title();
        Assertions.assertEquals("DentCare", pageTitle);
        // Ghi lại các hành động đăng nhập
        page.fill("#user", email);
        page.fill("#pass", password);
        page.click("//button[contains(text(),'Đăng Nhập')]");
        page.waitForTimeout(3000);
        page.hover("//a[@class='dropdown-toggle nav-link user-link']");
        page.waitForSelector("//button[contains(text(),'Tài Khoản')]");
        page.click("//button[contains(text(),'Tài Khoản')]");
        page.waitForTimeout(3000);
        String currentContent = page.textContent("li:nth-child(2) span:nth-child(2) a:nth-child(1)"); 
        Assertions.assertEquals(email, currentContent);
    }
    
}