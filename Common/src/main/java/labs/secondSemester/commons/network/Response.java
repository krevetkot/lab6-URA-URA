package labs.secondSemester.commons.network;


import lombok.Getter;

import java.io.Serializable;
import java.util.ArrayList;

@Getter
public class Response implements Serializable {
    private final ArrayList<String> response;

    public Response(){
        response = new ArrayList<>();
    }

    public Response(String resp){
        response = new ArrayList<>(1);
        response.add(resp);
    }

    public void add(String response){
        this.response.add(response);
    }
}
