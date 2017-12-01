package models;

import java.security.*;
import java.util.Random;

import javax.persistence.*;

import io.ebean.Finder;
import org.apache.commons.codec.binary.Base64;
import play.api.libs.Crypto;
import play.data.validation.Constraints.Email;
import io.ebean.*;

@Entity
public class User extends Model {

    /**
     * Email - электронная почта пользователя
     */
    @Id
    @Email
    private String email;

    /**
     * Хэш пароля
     */
    private String passwordHash;

    /**
     * Соль. случайная последовательность символов, используется для хэширования пароля ,
     * хранится для каждого пользователя своя.
     */
    private String salt;

    public User(String email, String password) {
        this.email = email;
        setPassword(password);
    }

    /**
     * Объект для связи с СУБД. Для поиска объектов в базе данных
     */
    public static Finder<String, User> find = new Finder<String, User>(User.class);

    /**
     * Генерирует хэш от строки.
     * Определяет текущую версию выбранного алгоритма конкретной криптосистемы
     *
     * @param s строка
     * @return хэш от пароля в соответствии с применяемым алгоритмом
     */
    private String getHash(String s) {
        return SHA256(s);
    }

    /**
     * Устанавливаем новый пароль.
     * В зависимости от алгоритма может использовать хэширование пароля. Возможна генерация соли.
     *
     * @param password (новый) пароль
     */
    public void setPassword(String password) {
        //todo Сгенерировать соль
        //todo установить хэш от пароля (с солью)
        this.salt = genSalt();
        this.passwordHash = getHash(password+salt);
    }

    /**
     * Проверяем подошел ли пароль
     *
     * @param password пароль
     * @return в случае совпадения пароля, возвращет true, иначе возвращает false
     */
    private boolean checkPassword(String password) {
        return getHash(password+salt).equals(passwordHash);
    }


    /**
     * @param email почтовый адрес
     * @param password пароль
     * @return возвращает null в случае успешной аутентификации.
     * В случае если пользователь не зарегистрирован возвращает сообщение об ошибке
     * "Пользователь с данным email не зарегистрирован"
     * В случае если пароль не подошел, возвращает сообщение об ошибке "Не верный пароль". для этого использовать метод checkPassword(password)
     */
    public static String authenticate(String email, String password) {
        User user = find.byId(email);
        if (user == null || !user.checkPassword(password))
            return "Пользователь с данным email не зарегистрирован или не верный пароль";
        else
            return null;
    }



    /**
     * Существует ли пользователь с данным почтовым адресом
     * @param email адрес электронной почты
     * @return false в случае, если пользователь с данным почтовым адресом уже зарегистрирован в системе.
     * Иначе возвращает true
     */
    public static boolean emailAvailable(String email) {
        return (find.byId(email) == null);
    }


    /**
     * Возвращает хэш-функцию SHA-256
     *
     * @see <a href="https://ru.wikipedia.org/wiki/SHA-2">SHA-2 в Википедии</a>
     *
     * @param str строка для хэширования
     * @return хэш от строки
     */
    public static String SHA256(String str) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            digest.update(str.getBytes());
            return Base64.encodeBase64String(digest.digest());
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Подписывает строку секретом приложения с помощью встроенной в Play библиотекой Crypto
     * Используется алгоритм SHA-1
     *
     * @link play.api.libs.Crypto.sign
     * @see play.api.libs.Crypto.cookieSigner().sign(String)
     *
     * @param str Строка для подписи (хэширования)
     * @return подпись, хэш для заданной строки
     */
    public static String SHA1(String str){
        //Используется класс Crypto из пакета, встроенного в Play
        return Crypto.cookieSigner().sign(str);
    }


    /**
     * Генерирует случайную строку для использования в качестве соли
     * @return случайная последовательность символов
     */
    public static String genSalt(){
        final Random r = new SecureRandom();
        byte[] salt = new byte[32];
        r.nextBytes(salt);
        return Base64.encodeBase64String(salt);
    }
}
