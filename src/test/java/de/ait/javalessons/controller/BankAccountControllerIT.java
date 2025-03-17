package de.ait.javalessons.controller;

import de.ait.javalessons.model.BankAccount;
import de.ait.repositories.BankAccountRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BankAccountControllerIT {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private BankAccountRepository bankAccountRepository;

    private static final String BASE_URL = "/accounts";

    private BankAccount accountOne;
    private BankAccount accountTwo;
    private BankAccount accountThree;
    private BankAccount accountFour;
    private BankAccount accountFive;
    private BankAccount accountSix;
    private BankAccount accountSeven;
    private BankAccount accountEight;
    private BankAccount accountNine;
    private BankAccount accountTen;

    @BeforeEach
    public void setUp() {
        accountOne = new BankAccount("1001", "Alice Johnson", 1500.0);
        accountTwo = new BankAccount("1002", "Bob Smith", 2500.0);
        accountThree = new BankAccount("1003", "Charlie Brown", 3500.0);
        accountFour = new BankAccount("1004", "David White", 4500.0);
        accountFive = new BankAccount("1005", "Emma Green", 5500.0);
        accountSix = new BankAccount("1006", "Frank Black", 6500.0);
        accountSeven = new BankAccount("1007", "Grace Adams", 7500.0);
        accountEight = new BankAccount("1008", "Henry Scott", 8500.0);
        accountNine = new BankAccount("1009", "Isabella Lee", 0.0);
        accountTen = new BankAccount("1010", "Jack Wilson", 10500.0);

        bankAccountRepository.deleteAll();
        bankAccountRepository.saveAll(
                List.of(
                        accountOne,
                        accountTwo,
                        accountThree,
                        accountFour,
                        accountFive,
                        accountSix,
                        accountSeven,
                        accountEight,
                        accountNine,
                        accountTen
                )
        );
    }


    @Test
    void testGetAllBankAccountsSuccess() {
        ResponseEntity<BankAccount[]> response = testRestTemplate.getForEntity(BASE_URL, BankAccount[].class);
        assertNotNull(response.getBody());
        assertEquals(10, response.getBody().length);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("1005", response.getBody()[4].getAccountNumber());
    }

    @Test
    void testGetAllBankAccountsNoContent() {
        bankAccountRepository.deleteAll();
        ResponseEntity<BankAccount[]> response = testRestTemplate.getForEntity(BASE_URL, BankAccount[].class);
        assertNull(response.getBody());
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    void testCreateBankAccountSuccess() {
        BankAccount bankAccount = null;
        String url = BASE_URL + "?accountNumber=1011&ownerName=Badaev Aleksandr";
        ResponseEntity<BankAccount> response = testRestTemplate.postForEntity(url, null, BankAccount.class);
        ResponseEntity<BankAccount[]> responseEntity = testRestTemplate.getForEntity(url, BankAccount[].class);
        List<BankAccount> bankAccounts = Arrays.asList(responseEntity.getBody());
        for (BankAccount bankAccount1 : bankAccounts) {
            if (bankAccount1.getAccountNumber().equals("1011")) {
                bankAccount = bankAccount1;
                break;
            }
        }
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("1011", response.getBody().getAccountNumber());
        assertEquals("Badaev Aleksandr", response.getBody().getOwnerName());
        assertEquals(0.0, response.getBody().getBalance(), 0);
        assertEquals(bankAccount.getId(), response.getBody().getId());
        assertEquals(11, bankAccountRepository.count());
    }

    @Test
    void testCreateBankAccountBadRequest() {
        String url = BASE_URL + "?accountNumber=&ownerName=";
        ResponseEntity<BankAccount> response = testRestTemplate.postForEntity(url, null, BankAccount.class);
        assertNull(response.getBody());
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(10, bankAccountRepository.count());
    }

    @Test
    @DisplayName("Проверка теста только с прописанием в методе saveNewBankAccount сервисе принудительно возврат null")
    @Disabled
    void testCreateBankAccountInternalServerError() {
        String url = BASE_URL + "?accountNumber=1011&ownerName=Badaev Aleksandr";
        ResponseEntity<BankAccount> response = testRestTemplate.postForEntity(url, null, BankAccount.class);
        assertNull(response.getBody());
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals(10, bankAccountRepository.count());
    }

    @Test
    void testGetBankAccountByIdWasFound() {
        String url = BASE_URL + "/" + accountOne.getId();
        ResponseEntity<BankAccount> response = testRestTemplate.getForEntity(url, BankAccount.class);
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("1001", response.getBody().getAccountNumber());
        assertEquals("Alice Johnson", response.getBody().getOwnerName());
        assertEquals(1500.0, response.getBody().getBalance(), 0);
        assertEquals(accountOne.getId(), response.getBody().getId());
    }

    @Test
    void testGetBankAccountByIdNotFound() {
        ResponseEntity<BankAccount> response = testRestTemplate.getForEntity(BASE_URL + "/1111", BankAccount.class);
        assertNull(response.getBody());
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void testDepositPositive() {
        String url = BASE_URL + "/" + accountFive.getId() + "/deposit?amount=1500.0";
        ResponseEntity<Double> response = testRestTemplate.postForEntity(url, null, Double.class);
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(7000, response.getBody());

        BankAccount updateAccount = testRestTemplate.getForEntity(BASE_URL + "/" + accountFive.getId(), BankAccount.class).getBody();
        assertNotNull(updateAccount);
        assertEquals(7000, updateAccount.getBalance());
    }

    @Test
    void testDepositNegativeAccountNotFound() {
        String url = BASE_URL + "/155/deposit?amount=1500.0";
        ResponseEntity<Double> response = testRestTemplate.postForEntity(url, null, Double.class);
        assertNull(response.getBody());
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void testDepositNegativeAmount() {
        String url = BASE_URL + "/" + accountFive.getId() + "/deposit?amount=-1500.0";
        ResponseEntity<Double> response = testRestTemplate.postForEntity(url, null, Double.class);
        assertNull(response.getBody());
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void testWithdrawPositive() {
        String url = BASE_URL + "/" + accountTen.getId() + "/withdraw?amount=1000.0";
        ResponseEntity<Double> response = testRestTemplate.postForEntity(url, null, Double.class);
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(9500, response.getBody());

        BankAccount bankAccount = testRestTemplate.getForEntity(BASE_URL + "/" + accountTen.getId(), BankAccount.class).getBody();
        assertEquals(9500, bankAccount.getBalance());
    }

    @Test
    void testWithdrawNegativeAmount() {
        String url = BASE_URL + "/" + accountTen.getId() + "/withdraw?amount=-1000.0";
        ResponseEntity<Double> response = testRestTemplate.postForEntity(url, null, Double.class);
        assertNull(response.getBody());
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

        BankAccount bankAccount = testRestTemplate.getForEntity(BASE_URL + "/" + accountTen.getId(), BankAccount.class).getBody();
        assertEquals(10500, bankAccount.getBalance());
    }

    @Test
    void testWithdrawExceedingLimitReturnsBadRequest() {
        String url = BASE_URL + "/" + accountTwo.getId() + "/withdraw?amount=5000.0";
        ResponseEntity<Double> response = testRestTemplate.postForEntity(url, null, Double.class);
        assertNull(response.getBody());
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

        BankAccount bankAccount = testRestTemplate.getForEntity(BASE_URL + "/" + accountTwo.getId(), BankAccount.class).getBody();
        assertEquals(2500, bankAccount.getBalance());
    }

    @Test
    void testWithdrawAccountNotFound() {
        String url = BASE_URL + "/255/withdraw?amount=1000.0";
        ResponseEntity<Double> response = testRestTemplate.postForEntity(url, null, Double.class);
        assertNull(response.getBody());
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void testTransferPositive() {
        String url = BASE_URL + "/" + accountFive.getId() + "/" + accountTwo.getId() + "/transfer-money?amount=1000.0";
        ResponseEntity<Double> response = testRestTemplate.postForEntity(url, null, Double.class);
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        BankAccount bankAccountFive = testRestTemplate.getForEntity(BASE_URL + "/" + accountFive.getId(), BankAccount.class).getBody();
        BankAccount bankAccountTwo = testRestTemplate.getForEntity(BASE_URL + "/" + accountTwo.getId(), BankAccount.class).getBody();
        assertEquals(4500, bankAccountFive.getBalance());
        assertEquals(3500, bankAccountTwo.getBalance());
    }

    @Test
    void testTransferNegativeAccountNotFoundOne() {
        String url = BASE_URL + "/555/" + accountTwo.getId() + "/transfer-money?amount=1000.0";
        ResponseEntity<Double> response = testRestTemplate.postForEntity(url, null, Double.class);
        assertNull(response.getBody());
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void testTransferNegativeAccountNotFoundTwo() {
        String url = BASE_URL + "/" + accountFive.getId() + "/654/transfer-money?amount=1000.0";
        ResponseEntity<Double> response = testRestTemplate.postForEntity(url, null, Double.class);
        assertNull(response.getBody());
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void testTransferNegativeLimitReturnsBadRequest() {
        String url = BASE_URL + "/" + accountFive.getId() + "/" + accountTwo.getId() + "/transfer-money?amount=6000.0";
        ResponseEntity<Double> response = testRestTemplate.postForEntity(url, null, Double.class);
        assertNull(response.getBody());
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void testUpdateOwnerNamePositive() {
        String url = BASE_URL + "/" + accountFive.getId() + "/update-owner-name?ownerName=Badaev Aleksandr";
        ResponseEntity<BankAccount> response = testRestTemplate.exchange(url, HttpMethod.PUT, null, BankAccount.class);
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Badaev Aleksandr", response.getBody().getOwnerName());
    }

    @Test
    void testUpdateOwnerNameNegativeAccountNotFound() {
        String url = BASE_URL + "/55/update-owner-name?ownerName=Badaev Aleksandr";
        ResponseEntity<BankAccount> response = testRestTemplate.exchange(url,HttpMethod.PUT, null, BankAccount.class);
        assertNull(response.getBody());
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void testDeleteBankAccountPositive() {
        String url = BASE_URL + "/" + accountNine.getId();
        ResponseEntity<Void> response = testRestTemplate.exchange(url, HttpMethod.DELETE, null, Void.class);
        assertNull(response.getBody());
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        BankAccount[] bankAccounts = testRestTemplate.getForObject(BASE_URL, BankAccount[].class);
        assertNotNull(bankAccounts);
        assertEquals(9, bankAccounts.length);
    }

    @Test
    void testDeleteBankAccountNegativeWithBalancePositive() {
        String url = BASE_URL + "/" + accountFour.getId();
        ResponseEntity<Void> response = testRestTemplate.exchange(url, HttpMethod.DELETE, null, Void.class);
        assertNull(response.getBody());
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void testDeleteBankAccountWasNotFoundNegative() {
        String url = BASE_URL + "/477";
        ResponseEntity<Void> response = testRestTemplate.exchange(url, HttpMethod.DELETE, null, Void.class);
        assertNull(response.getBody());
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}

