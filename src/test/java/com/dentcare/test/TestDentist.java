/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package com.dentcare.test;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions.*;
import com.microsoft.playwright.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

/**
 *
 * @author ADMIN
 */
public class TestDentist {

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
        page.waitForTimeout(5000); // Dừng thực thi trong 7 giây
        browser.close();
        playwright.close();
    }

    @Test
    public void testPageTitle() {
        page.navigate("http://localhost:8084/SWP391-SE1743/index.jsp");
        String pageTitle = page.title();
        Assertions.assertEquals("DentCare", pageTitle);

        // ghi lai xem bac si
        page.hover("//a[normalize-space()='Xem Thêm']");
        page.waitForSelector("//a[contains(text(),'Nha sĩ của chúng tôi')]");
        page.click("//a[contains(text(),'Nha sĩ của chúng tôi')]");
        page.click("//div[2]//div[1]//div[1]//div[1]//a[1]");
        String currentContent = page.textContent(".display-5.mb-0");
        Assertions.assertEquals("Trần Thị Mỹ Nga", currentContent);
        page.waitForTimeout(1000); 
        page.goBack();
        page.click("//div[3]//div[1]//div[1]//div[1]//a[1]");
        page.waitForTimeout(1000); 
        String currentDentist = page.textContent(".display-5.mb-0");
        Assertions.assertEquals("Nguyễn Hiếu Tùng", currentDentist);
    }

    //DDT
    public static Object[][] initData() {
        return new Object[][]{
            {"Khám tổng quát"},
            {"Cạo vôi răng"},
            {"Trám răng sữa"}
        };
    }

    @ParameterizedTest
    @MethodSource(value = "initData")
    public void testView_DDT(String search) throws InterruptedException {
        page.navigate("http://localhost:8084/SWP391-SE1743/index.jsp");
        String pageTitle = page.title();
        Assertions.assertEquals("DentCare", pageTitle);
        // Ghi lại các hành động search
        page.click("//a[contains(text(),'Dịch Vụ')]");
        page.waitForTimeout(3000);
        String currentUrl = page.url();  
        Assertions.assertEquals("http://localhost:8084/SWP391-SE1743/ServiceUserController", currentUrl); 
        page.fill(".form-control.floating", search);
        page.waitForTimeout(3000);
        String currentSearch = page.textContent("tbody tr td:nth-child(1)"); 
        Assertions.assertEquals(search, currentSearch);
    }
}

