package models;

import controllers.Auth;
import play.data.validation.Constraints;

/**
<<<<<<< HEAD
 * Данный класс необходим для обработки формы смены пароля.
 * Используется для валидации двух типов.
 *
 * 1. В качестве валидации отдельных полей используются аннотации
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
 * Валидация формы смены пароля проходит тогда, когда старый пароль соответствует текущему пользователю
 *
 * Подсказка: Воспользуйтесь методами Auth.currentUserEmail и Auth.currentUser для получения текущего пользователя и его email
 *
 */
public class ChangePassword{
    //todo обязательное поле с подписью
    public String password;

    //todo обязательное поле с подписью
    public String newPassword;


    /**
     * Производит валидацию формы.
     *
     * Подсказка: Воспользуйтесь методами Auth.currentUserEmail и Auth.currentUser для получения текущего пользователя и его email
     *
     * @return null в случае, если валидация успешна. Строку с ошибкой в противном случае.
     */
    public String validate() {
        //todo
        return "Не реализовано";
=======
 * Форма изменения пароля
 */
public class ChangePassword{
    @Constraints.Required(message = "Введите текущий пароль")
    public String password;

    @Constraints.Required(message = "Введите новый пароль")
    public String newPassword;


    // Валидация
    public String validate() {
        String email = Auth.currentUserEmail();
        if (email == null) return "Сперва необходимо аутентифицироваться";

        return User.authenticate(email, password);
>>>>>>> 2568142012654ea39452598427c429fd8db79ca1
    }
}
