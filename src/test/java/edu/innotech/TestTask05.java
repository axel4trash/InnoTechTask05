package edu.innotech;

import edu.innotech.entity.*;
import edu.innotech.repository.*;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;

@SpringBootTest(classes = {Starter.class}, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class TestTask05 {
    @Autowired
    AccountPoolRepository accountPoolRepository;
    @Autowired
    AccountRepository accountRepository;
    @Autowired
    AgreementRepository agreementRepository;
    @Autowired
    TppProductRegisterRepository tppProductRegisterRepository;
    @Autowired
    TppProductRepository tppProductRepository;
    @Autowired
    TppRefAccountTypeRepository tppRefAccountTypeRepository;
    @Autowired
    TppRefProductClassRepository tppRefProductClassRepository;
    @Autowired
    TppRefProductRegisterTypeRepository tppRefProductRegisterTypeRepository;

    public static PostgreSQLContainer<?> sqlContainer = new PostgreSQLContainer<>("postgres:15-alpine");

    @BeforeAll
    static void beforeAll() {
        sqlContainer.start();
        RestAssured.baseURI = "http://localhost:8081";
    }

    @AfterAll
    static void afterAll() {
        sqlContainer.stop();
    }

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", sqlContainer::getJdbcUrl);
        registry.add("spring.datasource.username", sqlContainer::getUsername);
        registry.add("spring.datasource.password", sqlContainer::getPassword);
    }

    @Test
    @DisplayName("Test 1: Тест для проверки основных репозиториев")
    public void test01(){
        AccountPool accountPool = new AccountPool(null,null,"001","643","666","01","600");
        Account account = new Account(null, accountPool, "40817810411234567890");

        Agreement agreement = new Agreement();
        TppProductRegister tppProductRegister = new TppProductRegister();
        TppProduct tppProduct = new TppProduct();
        TppRefAccountType tppRefAccountType = new TppRefAccountType();
        TppRefProductClass tppRefProductClass = new TppRefProductClass();
        TppRefProductRegisterType tppRefProductRegisterType = new TppRefProductRegisterType();

        accountPoolRepository.save(accountPool);
        accountRepository.save(account);
        agreementRepository.save(agreement);
        tppProductRegisterRepository.save(tppProductRegister);
        tppProductRepository.save(tppProduct);
        tppRefAccountTypeRepository.save(tppRefAccountType);
        tppRefProductClassRepository.save(tppRefProductClass);
        tppRefProductRegisterTypeRepository.save(tppRefProductRegisterType);

        Assertions.assertEquals(accountPool.getId(),1);
        Assertions.assertEquals(account.getId(),1);
        Assertions.assertEquals(agreement.getId(),1);
        Assertions.assertEquals(tppProductRegister.getId(),1);
        Assertions.assertEquals(tppProduct.getId(),1);
        Assertions.assertEquals(tppRefAccountType.getInternal_id(),1);
        Assertions.assertEquals(tppRefProductClass.getInternal_id(),1);
        Assertions.assertEquals(tppRefProductRegisterType.getInternal_id(),1);
    }

    @Test
    @DisplayName("Test 2: Тест для проверки исключений")
    public void test02(){
        String requestBody = "{\n" +
                "\t\"instanceId\":1,\n" +
                "\t\"registryTypeCode\":\"01.015.003_47555_ComSoLd\",\n" +
                "\t\"accountType\":\"Клиентский\",\n" +
                "\t\"currencyCode\":\"643\",\n" +
                "\t\"branchCode\":\"0011\",\n" +
                "\t\"priorityCode\":\"01\",\n" +
                "\t\"mdmCode\":\"666\"\n" +
                "}";

        Response response = RestAssured.given()
                .header("Content-type", "application/json")
                .and()
                .body(requestBody)
                .when()
                .post("/account/create")
                .then()
                .extract().response();

        Assertions.assertEquals(404, response.statusCode());
        Assertions.assertEquals("404", response.jsonPath().getString("errorCode"));

        String requestBody2 = "{\n" +
                "\t\"instanceId\":null,\n" +
                "\t\"productType\":\"НСО\",\n" +
                "\t\"productCode\":\"01.013.002\",\n" +
                "\t\"registerType\":\"01.015.003_47555_ComSoLd\",\n" +
                "\t\"mdmCode\":\"666\",\n" +
                "\t\"contractNumber\":\"001/2024_NSO\",\n" +
                "\t\"contractDate\":\"2024-11-10\",\n" +
                "\t\"priority\":1,\n" +
                "\t\"interestRatePenalty\":14.7,\n" +
                "\t\"minimalBalance\":12.3,\n" +
                "\t\"thresholdAmount\":11.2,\n" +
                "\t\"accountingDetails\":\"р/с 40702810344444555555\",\n" +
                "\t\"rateType\":\"прогрессивная\",\n" +
                "\t\"taxPercentageRate\":2.1,\n" +
                "\t\"technicalOverdraftLimitAmount\":2.1,\n" +
                "\t\"contractId\":0,\n" +
                "\t\"branchCode\":\"0011\",\n" +
                "\t\"isoCurrencyCode\":\"643\",\n" +
                "\t\"urgencyCode\":\"00\",\n" +
                "\t\"referenceCode\":12,\n" +
                "\t\"additionalPropertiesVIP\":{\n" +
                "\t\t\t\"data\":[\n" +
                "\t\t\t\t\t{\"key\":\"RailwayRegionOwn\",\"value\":\"WER\"},\n" +
                "\t\t\t\t\t{\"key\":\"counter\",\"value\":\"555\"}\n" +
                "\t\t\t]\n" +
                "\t},\n" +
                "\t\"instanceAgreements\":[\n" +
                "\t\t{\"generalAgreementId\":\"234\",\n" +
                "\t\t \"supplementaryAgreementId\":\"12\",\n" +
                "\t\t \"arrangementType\":\"СМО\",\n" +
                "\t\t \"number\":\"430\",\n" +
                "\t\t \"shedulerJobId\":51,\n" +
                "\t\t \"openingDate\":\"2024-11-10\",\n" +
                "\t\t \"closingDate\":null,\n" +
                "\t\t \"cancelDate\":null,\n" +
                "\t\t \"validityDuration\":36,\n" +
                "\t\t \"cancellationReason\":null,\n" +
                "\t\t \"status\":\"открыт\",\n" +
                "\t\t \"interestCalculationDate\":\"2026-11-10\",\n" +
                "\t\t \"interestRate\":2.2,\n" +
                "\t\t \"coefficient\":2.1,\n" +
                "\t\t \"coefficientAction\":\"+\",\n" +
                "\t\t \"minimumInterestRate\":3.4,\n" +
                "\t\t \"minimumInterestRateCoefficient\":3.3,\n" +
                "\t\t \"minimumInterestRateCoefficientAction\":\"-\",\n" +
                "\t\t \"maximalInterestRate\":11,\n" +
                "\t\t \"maximalInterestRateCoefficient\":0.34,\n" +
                "\t\t \"maximalInterestRateCoefficientAction\":\"-\"\n" +
                "\t\t}\n" +
                "\t]\n" +
                "}";

        Response response2 = RestAssured.given()
                .header("Content-type", "application/json")
                .and()
                .body(requestBody2)
                .when()
                .post("/instance/create")
                .then()
                .extract().response();

        Assertions.assertEquals(500, response2.statusCode());
        Assertions.assertEquals("500", response2.jsonPath().getString("сode"));
    }
}
