package edu.ithaca.dturnbull.bank;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


class BankAccountTest {

    @Test
    void getBalanceTest() {
        BankAccount bankAccount1 = new BankAccount("a@b.com", 200); // creates account with money 
        assertEquals(200, bankAccount1.getBalance());

        assertThrows(IllegalArgumentException.class, () -> new BankAccount("a@b.com", -50)); // creates account with negative money

        BankAccount bankAccount3 = new BankAccount("a@b.com", 0); // creates account with 0 money
        assertEquals(0, bankAccount3.getBalance());
    }

    @Test
    void withdrawTest() throws InsufficientFundsException{
        BankAccount bankAccount = new BankAccount("a@b.com", 200); // creates account
        
        bankAccount.withdraw(100); // correct withdraw
        assertEquals(100, bankAccount.getBalance());
        
        bankAccount.withdraw(0); // withdraw nothing
        assertEquals(100, bankAccount.getBalance());

        assertThrows(IllegalArgumentException.class, () -> bankAccount.withdraw(-50)); // withdraw negative
        assertThrows(InsufficientFundsException.class, () -> bankAccount.withdraw(101)); // withdraw too many 

        BankAccount bankAccount1 = new BankAccount("a@b.com", 200);
        bankAccount1.withdraw(0.01); // withdraw cents
        assertEquals(199.99, bankAccount1.getBalance());
    }

    @Test
    void isEmailValidTest(){
        assertTrue(BankAccount.isEmailValid( "a@b.com"));                   // valid email address
        assertFalse(BankAccount.isEmailValid("abc.com"));                   // no @ sign
        assertFalse(BankAccount.isEmailValid("abc@@mail.com"));             // 2 @ signs
        assertFalse(BankAccount.isEmailValid(""));                          // empty string
        assertTrue( BankAccount.isEmailValid("abc-d@mail.com"));            // good prefix
        assertTrue( BankAccount.isEmailValid("abc.def@mail.com"));          // good prefix
        assertTrue( BankAccount.isEmailValid("abc@mail.com"));              // good prefix
        assertTrue( BankAccount.isEmailValid("abc_def@mail.com"));          // good prefix
        assertFalse( BankAccount.isEmailValid("abc..def@mail.com"));        // bad prefix - too many periods
        assertFalse( BankAccount.isEmailValid(".abc@mail.com"));            // bad prefix - cant start w/ period
        assertFalse( BankAccount.isEmailValid("abc#def@mail.com	"));        // bad prefix - invalid characters
        assertTrue( BankAccount.isEmailValid("abc.def@mail.cc"));           // good domain
        assertTrue( BankAccount.isEmailValid("abc.def@mail-archive.com"));  // good domain
        assertTrue( BankAccount.isEmailValid("abc.def@mail.org"));          // good domain
        assertTrue( BankAccount.isEmailValid("abc.def@mail.com"));          // good domain
        assertFalse( BankAccount.isEmailValid("abc.def@mail.c"));           // bad domain - too short
        assertFalse( BankAccount.isEmailValid("abc.def@mail#archive.com")); // bad domain - invalid characters
        assertFalse( BankAccount.isEmailValid("abc.def@mail"));             // bad domain - missing domain portion
        assertFalse( BankAccount.isEmailValid("abc.def@mail..com"));        // bad domain - too many periods
    }

    @Test
    void constructorTest() {
        BankAccount bankAccount = new BankAccount("a@b.com", 200);

        assertEquals("a@b.com", bankAccount.getEmail());
        assertEquals(200, bankAccount.getBalance(), 0.001);
        //check for exception thrown correctly
        assertThrows(IllegalArgumentException.class, ()-> new BankAccount("", 100));
    }

}