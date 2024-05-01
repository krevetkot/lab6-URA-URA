package labs.secondSemester.commons.network;

import lombok.Getter;

import java.io.Serializable;
@Getter
public class ClientIdentification implements Serializable {
    private final String login;
    private final String password;

    public ClientIdentification(String login, String password){
        this.login = login;
        this.password = password;
    }
}
