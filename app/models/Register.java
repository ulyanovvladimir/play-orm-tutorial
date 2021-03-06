package models;

import play.data.validation.Constraints.Email;
import play.data.validation.Constraints.Required;

/**
 * Данный класс необходим для обработки формы регистрации.
 * Используется для валидации двух типов.
 *
 * 1. В качестве валидации отдельных полей используются аннотации
 * @Email - строка соответствует адресу эл. почты
 * @Required - обязательное поле.
 *
 * Для задания осмысленного сообщения при наарушении данного ограничения,
 * используется параметр message.
 * @Email(message = "Некорректный адрес электронной почты")
 *
 *
 * 2. Валидация на уровне формы с помощью метода String validate()
 * Когда нет возможности ограничиться валидацией полей по отдельности,
 * например когда условие валидации зависит от соответствия значений нескольких полей,
 *
 * Валидация форма регистрации проходит тогда, когда пользователь с данным email еще не зарегистрирован
 *
 */

public class Register{

    @Email(message= "Некорректный адрес электронной почты")
    @Required(message = "Обязательное поле")
    public String email;

    @Required(message = "Обязательное поле")
    public String password;

    // Валидация
    public String validate() {
        boolean available= User.emailAvailable(email);
        return (available==true) ? null : "Пользователь с данным почтовым адресом уже зарегистрирован";
    }
}
