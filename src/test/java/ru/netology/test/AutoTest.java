package ru.netology.test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import com.codeborne.selenide.Condition;
import ru.netology.data.DataGenerator;


import static com.codeborne.selenide.Selenide.*;


public class AutoTest {


    @BeforeEach
    void setUp() {
        open("http://localhost:9999/");
    }

    @AfterEach
    void clear() {
        clearBrowserLocalStorage();

    }

    @Test
    void shouldValidDataRegistered() {
        var registeredUser = DataGenerator.Registration.getRegisteredUser("active");
        $("[data-test-id=login] .input__box .input__control").val(registeredUser.getLogin());
        $("[data-test-id=password] .input__box .input__control").val(registeredUser.getPassword());
        $("[data-test-id=action-login]").click();
        $("h2").shouldHave(Condition.exactText("  Личный кабинет"));

    }

    @Test
    void shouldStatusBlockedUser() {
        var blockedUser = DataGenerator.Registration.getRegisteredUser("blocked");
        $("[data-test-id=login] .input__box .input__control").val(blockedUser.getLogin());
        $("[data-test-id=password] .input__box .input__control").val(blockedUser.getPassword());
        $("[data-test-id=action-login]").click();
        $("[data-test-id=error-notification] .notification__content").shouldHave(Condition.exactText("Ошибка! " + "Пользователь заблокирован"));
    }

    @Test
    void shouldInvalidLoginUser() {
        var invalidUser = DataGenerator.Registration.getUser("blocked");
        $("[data-test-id=login] .input__box .input__control").val(DataGenerator.Registration.getRandomLogin());
        $("[data-test-id=password] .input__box .input__control").val(invalidUser.getPassword());
        $("[data-test-id=action-login]").click();
        $("[data-test-id=error-notification] .notification__content").shouldHave(Condition.exactText("Ошибка! " + "Неверно указан логин или пароль"));
    }


    @Test
    void shouldInvalidPassUser() {
        var invalidUser = DataGenerator.Registration.getUser("blocked");
        $("[data-test-id=login] .input__box .input__control").val(invalidUser.getLogin());
        $("[data-test-id=password] .input__box .input__control").val(DataGenerator.Registration.getRandomPassword());
        $("[data-test-id=action-login]").click();
        $("[data-test-id=error-notification] .notification__content").shouldHave(Condition.exactText("Ошибка! " + "Неверно указан логин или пароль"));
    }

    @Test
    void shouldInvalidLogAndPasUser() {
        var validUser = DataGenerator.Registration.getRegisteredUser("blocked");
        $("[data-test-id=login] .input__box .input__control").val(DataGenerator.Registration.getRandomLogin());
        $("[data-test-id=password] .input__box .input__control").val(DataGenerator.Registration.getRandomPassword());
        $("[data-test-id=action-login]").click();
        $("[data-test-id=error-notification] .notification__content").shouldHave(Condition.exactText("Ошибка! " + "Неверно указан логин или пароль"));
    }


    @Test
    void shouldNullLoginUser() {
        var validUser = DataGenerator.Registration.getRegisteredUser("active");
        $("[data-test-id=login] .input__box .input__control").val();
        $("[data-test-id=password] .input__box .input__control").val(validUser.getPassword());
        $("[data-test-id=action-login]").click();
        $("[data-test-id=login].input_invalid .input__sub").shouldHave(Condition.exactText("Поле обязательно для заполнения"));
    }

    @Test
    void shouldNullPassUser() {
        var validUser = DataGenerator.Registration.getRegisteredUser("active");
        $("[data-test-id=login] .input__box .input__control").val(validUser.getLogin());
        $("[data-test-id=password] .input__box .input__control").val();
        $("[data-test-id=action-login]").click();
        $("[data-test-id=password].input_invalid .input__sub").shouldHave(Condition.exactText("Поле обязательно для заполнения"));
    }

    @Test
    void shouldNullInputUser() {
        var validUser = DataGenerator.Registration.getRegisteredUser("active");
        $("[data-test-id=login] .input__box .input__control").val();
        $("[data-test-id=password] .input__box .input__control").val();
        $("[data-test-id=action-login]").click();
        $("[data-test-id=login].input_invalid .input__sub").shouldHave(Condition.exactText("Поле обязательно для заполнения"));
        $("[data-test-id=password].input_invalid .input__sub").shouldHave(Condition.exactText("Поле обязательно для заполнения"));
    }
}
