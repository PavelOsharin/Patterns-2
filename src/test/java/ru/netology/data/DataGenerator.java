package ru.netology.data;

import com.github.javafaker.Faker;

import static io.restassured.RestAssured.given;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

import java.util.Locale;

import lombok.Value;

public class DataGenerator {

    private static RequestSpecification requestSpec = new RequestSpecBuilder()
            .setBaseUri("http://localhost")
            .setPort(9999)
            .setAccept(ContentType.JSON)
            .setContentType(ContentType.JSON)
            .log(LogDetail.ALL)
            .build();

    private static Faker faker = new Faker(new Locale("en"));


    private DataGenerator() {
    }

    public static class Registration {
        private Registration() {
        }


        public static void sendRequest(RegistrationUser user) {
            given() // "дано"
                    .spec(requestSpec) // указываем, какую спецификацию используем
                    .body(user) // передаём в теле объект, который будет преобразован в JSON
                    .when() // "когда"
                    .post("/api/system/users") // на какой путь, относительно BaseUri отправляем запрос
                    .then() // "тогда ожидаем"
                    .statusCode(200); // код 200 OK
        }

        public static String getRandomLogin() {
            String login = faker.name().username();
            return login;
        }

        public static String getRandomPassword() {
            String password = faker.internet().password();
            return password;
        }

        public static RegistrationUser getUser(String status) {
            RegistrationUser user = new RegistrationUser(getRandomLogin(), getRandomPassword(), status);
            return user;
        }

        public static RegistrationUser getRegisteredUser(String status) {
            RegistrationUser registeredUser = getUser(status);
            sendRequest(registeredUser);
            return registeredUser;
        }
    }

    @Value
    public static class RegistrationUser {
        private String login;
        private String password;
        private String status;
    }


}
