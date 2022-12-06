package com.example.loginpage;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;

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
public class ExampleUnitTest {

    @Mock
    StudentLoginPg view1;
    AdminLoginPg view;

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

    /**
     @Test
     public void testStudentLoginPresenter2(){
     when(stuLoginView.getEmail()).thenReturn("bob@mail.com");
     when(stuLoginView.getPassword()).thenReturn("000000000");

     StudentLoginPresenter presenter =
     new StudentLoginPresenter(model, stuLoginView);

     verify(model).login("bob@mail.com", "000000000",
     captor.capture());

     }**/
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }
}