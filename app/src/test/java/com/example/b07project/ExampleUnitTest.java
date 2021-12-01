package com.example.b07project;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(MockitoJUnitRunner.class)
public class ExampleUnitTest {


    @Mock
    OwnerLogin view;


    @Mock
    MyModel model;


    @Test
    public void testCheck_Account1() {
        when(view.getUsername()).thenReturn("");
        when(view.getPassword()).thenReturn("");

        MyPresenter presenter = new MyPresenter(model, view);

        presenter.Check_Account("Cutomers");

        verify(view).Alert("Invalid", "Username or Password Left Blank");

    }

    @Test
    public void testCheck_Account2() {
        when(view.getUsername()).thenReturn("Test123");
        when(view.getPassword()).thenReturn("");

        MyPresenter presenter = new MyPresenter(model, view);

        presenter.Check_Account("Cutomers");

        verify(view).Alert("Invalid", "Username or Password Left Blank");


    }

    @Test
    public void testCheck_Account3() {
        when(view.getUsername()).thenReturn("");
        when(view.getPassword()).thenReturn("Test!@12345");

        MyPresenter presenter = new MyPresenter(model, view);

        presenter.Check_Account("Cutomers");

        verify(view).Alert("Invalid", "Username or Password Left Blank");

    }

    @Test
    public void testCheck_Account4() {
        when(view.getUsername()).thenReturn("Test123");
        when(view.getPassword()).thenReturn("Test!@12345");

        Customer customer = new Customer("Test123", "Test!@12345");

        MyPresenter presenter = new MyPresenter(model, view);

        presenter.Check_Account("Customers");

        verify(model).Check_Info("Test123","Test!@12345", "Customers", presenter);
    }


    @Test
    public void Authenticate1() {
        MyPresenter presenter = new MyPresenter(model, view);

        assertEquals(presenter.Authenticate("Test123","Test!@12345"), true);

    }

    @Test
    public void Authenticate2() {
        MyPresenter presenter = new MyPresenter(model, view);

        boolean value = presenter.Authenticate("Test123","T");

        verify(view).Alert("Invalid Password", "Must be: " +
                "at least 7 characters, " +
                "include a lowercase, " +
                "an uppercase, a number, " +
                "a special character, " +
                "and not other character types.");

        assertEquals(value, false);

    }

    @Test
    public void Authenticate3() {
        MyPresenter presenter = new MyPresenter(model, view);

        boolean value = presenter.Authenticate("Te","Test!@12345");

        verify(view).Alert("Invalid Username", "Usernames must be at least 7 characters long");

        assertEquals(value, false);

    }

    @Test
    public void voidVaildlogin() {
        MyPresenter presenter = new MyPresenter(model, view);

        Owner owner = new Owner("Test123","Test!@12345", "Food");
        presenter.Vaildlogin(owner, "Owners");

        verify(view).NextPage(owner, "Owners");


    }

    @Test
    public void Invaildlogin() {
        MyPresenter presenter = new MyPresenter(model, view);

        presenter.Invaildlogin();

        verify(view).Alert("Invalid", "Username or Password is Incorrect");

    }

    @Test
    public void Create_Failed() {
        MyPresenter presenter = new MyPresenter(model, view);

        presenter.Create_Failed();

        verify(view).Alert("Username Invalid", "Username already exists.");

    }

    @Test
    public void Create_Owner() {
        when(view.getUsername()).thenReturn("Test123");
        when(view.getPassword()).thenReturn("Test!@12345");

        MyPresenter presenter = new MyPresenter(model, view);



        presenter.Create_Owner("Food");

        Owner owner = new Owner("Test123", "Test!@12345", "Food");

        verify(model).Add_User(owner, "Owners", presenter);
    }

    @Test
    public void Create_Customer() {

        when(view.getUsername()).thenReturn("Test123");
        when(view.getPassword()).thenReturn("Test!@12345");

        MyPresenter presenter = new MyPresenter(model, view);


        Customer customer = new Customer("Test123", "Test!@12345");

        presenter.Create_Customer();

        verify(model).Add_User(customer, "Customers", presenter);

    }
}
