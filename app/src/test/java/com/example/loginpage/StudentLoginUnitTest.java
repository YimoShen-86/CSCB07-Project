package com.example.loginpage;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.times;
import static org.mockito.Matchers.any;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.*;

import java.util.function.Consumer;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(MockitoJUnitRunner.class)
public class StudentLoginUnitTest {

    @Mock
    StudentLoginPg view1;

    @Mock
    FireBaseModel model;

    @Captor
    ArgumentCaptor<Consumer<String>> captor;

    @Test
    public void testStudentLoginPresenterCheckEmail1(){
        when(view1.getEmail()).thenReturn("");

        StudentLoginPresenter presenter =
                new StudentLoginPresenter(model, view1);
        presenter.checkEmail();
        verify(view1).displayErrorOnEditTextEmail(
                "Email is required");
    }

    @Test
    public void testStudentLoginPresenterCheckEmail2(){
        when(view1.getEmail()).thenReturn("bob@mail");

        StudentLoginPresenter presenter =
                new StudentLoginPresenter(model, view1);
        presenter.checkEmail();
        verify(view1).displayErrorOnEditTextEmail(
                "Please enter a valid email");
    }

    @Test
    public void testStudentLoginPresenterCheckEmail3(){
        when(view1.getEmail()).thenReturn("bob@mail.com");

        StudentLoginPresenter presenter =
                new StudentLoginPresenter(model, view1);
        //checkEmail() returning false means there is no problem in this email input
        Assert.assertEquals(presenter.checkEmail(), false);
    }

    @Test
    public void testStudentLoginPresenterCheckPassword1(){
        when(view1.getPassword()).thenReturn("");

        StudentLoginPresenter presenter =
                new StudentLoginPresenter(model, view1);
        presenter.checkPassword();
        verify(view1).displayErrorOnEditTextPassword(
                "Password is required");
    }

    @Test
    public void testStudentLoginPresenterCheckPassword2(){
        when(view1.getPassword()).thenReturn("12312");

        StudentLoginPresenter presenter =
                new StudentLoginPresenter(model, view1);
        presenter.checkPassword();
        verify(view1).displayErrorOnEditTextPassword(
                "The length of password should be at least 8 characters");
    }

    @Test
    public void testStudentLoginPresenterCheckPassword3(){
        when(view1.getPassword()).thenReturn("123456789");

        StudentLoginPresenter presenter =
                new StudentLoginPresenter(model, view1);
        //checkPassword() returning false means there is no problem in this password input
        Assert.assertEquals(presenter.checkPassword(), false);
    }

    @Test
    public void testStudentLoginSuccessful() {
        String email = "bob@gmail.com";
        String password = "000000000";

        StudentLoginPresenter presenter = new StudentLoginPresenter(model, view1);
        presenter.login(email, password);

        verify(model).login(eq(email), eq(password), captor.capture());
        Consumer<String> callback = captor.getValue();
        callback.accept("1vVBfteNZIe01ChXYgBrcZcK3HC2");

        verify(view1, times(1)).goToStudentPage(any());
    }

    @Test
    public void testStudentLoginFailed() {
        String email = "bob@gmail.com";
        String password = "000000000";

        StudentLoginPresenter presenter = new StudentLoginPresenter(model, view1);
        presenter.login(email, password);

        verify(model).login(eq(email), eq(password), captor.capture());
        Consumer<String> callback = captor.getValue();
        callback.accept(null);

        verify(view1, times(1)).displayError();
    }
}