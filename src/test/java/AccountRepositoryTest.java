import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class AccountRepositoryTest {
    private AccountRepository accountRepository;


    @BeforeAll
    public static void setup() {
        Connection connection = MyConnection.connection;
    }

    @BeforeEach
    public void beforeEach() throws SQLException {
        accountRepository = new AccountRepository();
    }

    //    @AfterAll
//    public void afterEach() throws SQLException {
//        // deletes all records in table.
//        String delete = "DELETE FROM customer ";
//        PreparedStatement preparedStatement = MyConnection.connection.prepareStatement(delete);
//        preparedStatement.execute();
//        preparedStatement.close();
//    }

    @Test
    void insert() throws SQLException {
        //Arrange
        Customer customer = new Customer("Benyamin", "Omidali", 123456789);
        Account account = new Account(customer, 10000, null, Branch.KISH,
                AccountStatus.ALLOW, null);

        //Act
        Integer id = accountRepository.insert(account);
        account.setId(id);

        //Assert
        assertNotNull(accountRepository.findById(id));
        accountRepository.delete(id);
    }

    @Test
    void update() throws SQLException {
        //Arrange
        Customer customer = new Customer("Benyamin", "Omidali", 123456789);
        Account account = new Account(customer, 10000, null, Branch.KISH,
                AccountStatus.ALLOW, null);

        //Act
        Integer id = accountRepository.insert(account);
        account.setId(id);
        account.setAmount(500);
        accountRepository.update(account);

        //Assert
        assertEquals(500, accountRepository.findById(id).getAmount());
        accountRepository.delete(id);

    }

    @Test
    void delete() throws SQLException {
        //Arrange
        Customer customer = new Customer("Benyamin", "Omidali", 123456789);
        Account account = new Account(customer, 10000, null, Branch.KISH,
                AccountStatus.ALLOW, null);
        //Act
        account.setId(accountRepository.insert(account));
        accountRepository.delete(account.getId());

        //Assert
        assertNull(accountRepository.findById(account.getId()));

    }

    @Test
    void findByNationalCode() throws SQLException {
        //Arrange
        Customer customer = new Customer("Benyamin", "Omidali", 123456789);
        Account account = new Account(customer, 10000, null, Branch.KISH,
                AccountStatus.ALLOW, null);

        //Act
        account.setId(accountRepository.insert(account));

        //Assert
        assertNotNull(accountRepository.findByNationalCode(account.getCustomer().getNationalCode()));


        accountRepository.delete(account.getId());
    }

    @Test
    void getAmount() throws SQLException {
        //Arrange
        Customer customer = new Customer("Benyamin", "Omidali", 123456789);
        Account account = new Account(customer, 10000, null, Branch.KISH,
                AccountStatus.ALLOW, null);

        //Act
        account.setId(accountRepository.insert(account));

        //Assert
        assertEquals(10000,accountRepository.getAmount(account.getId()));

        accountRepository.delete(account.getId());


    }
}