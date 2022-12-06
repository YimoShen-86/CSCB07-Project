package com.example.loginpage;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.*;

import java.util.function.Consumer;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(MockitoJUnitRunner.class)
public class AdminLoginUnitTest {

    @Mock
    AdminLoginPg view;

    @Mock
    FireBaseModel model;

    @Captor
    ArgumentCaptor<Consumer<String>> captor;

    @Test
    public void testAdminLoginPresenterCheckEmail1(){
        when(view.getEmail()).thenReturn("");

        AdminLoginPresenter presenter =
                new AdminLoginPresenter(model, view);
        presenter.checkEmail();
        verify(view).displayErrorOnEditTextEmail(
                "Email is required");
    }

    @Test
    public void testAdminLoginPresenterCheckEmail2(){
        when(view.getEmail()).thenReturn("admin@mail");

        AdminLoginPresenter presenter =
                new AdminLoginPresenter(model, view);
        presenter.checkEmail();
        verify(view).displayErrorOnEditTextEmail(
                "Please enter a valid email");
    }

    @Test
    public void testAdminLoginPresenterCheckEmail3(){
        when(view.getEmail()).thenReturn("admin@mail.com");

        AdminLoginPresenter presenter =
                new AdminLoginPresenter(model, view);
        //checkEmail() returning false means there is no problem in this email input
        Assert.assertEquals(presenter.checkEmail(), false);
    }

    @Test
    public void testAdminLoginPresenterCheckPassword1(){
        when(view.getPassword()).thenReturn("");

        AdminLoginPresenter presenter =
                new AdminLoginPresenter(model, view);
        presenter.checkPassword();
        verify(view).displayErrorOnEditTextPassword(
                "Password is required");
    }

    @Test
    public void testAdminLoginPresenterCheckPassword2(){
        when(view.getPassword()).thenReturn("12312");

        AdminLoginPresenter presenter =
                new AdminLoginPresenter(model, view);
        presenter.checkPassword();
        verify(view).displayErrorOnEditTextPassword(
                "The length of password should be at least 8 characters");
    }

    @Test
    public void testAdminLoginPresenterCheckPassword3(){
        when(view.getPassword()).thenReturn("123456789");

        AdminLoginPresenter presenter =
                new AdminLoginPresenter(model, view);
        //checkPassword() returning false means there is no problem in this password input
        Assert.assertEquals(presenter.checkPassword(), false);
    }

    @Test
    public void testStudentLoginSuccessful() {
        String email = "admin@mail.com";
        String password = "12345678";

        AdminLoginPresenter presenter = new AdminLoginPresenter(model, view);
        presenter.login(email, password);

        verify(model).login(eq(email), eq(password), captor.capture());
        Consumer<String> callback = captor.getValue();
        callback.accept("VwrGm5pVYcWMPmvbb4RlPks7ajp1");

        verify(view, times(1)).goToAdminPage(any());
    }

    @Test
    public void testStudentLoginFailed() {
        String email = "admin@mail.com";
        String password = "12345678";

        AdminLoginPresenter presenter = new AdminLoginPresenter(model, view);
        presenter.login(email, password);

        verify(model).login(eq(email), eq(password), captor.capture());
        Consumer<String> callback = captor.getValue();
        callback.accept(null);

        verify(view, times(1)).displayError();
    }
}
