package labs.secondSemester.commons.network;

import lombok.Getter;

import java.io.*;

@Getter
public class Serializer {


    public <T> byte[] serialize(T object){
        try (ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
             ObjectOutputStream objectStream = new ObjectOutputStream(byteStream)) {
            objectStream.writeObject(object);
            return byteStream.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public <T> T deserialize(byte[] buffer) {
        try (ObjectInputStream inputStream = new ObjectInputStream(new ByteArrayInputStream(buffer))) {
            T object = (T) inputStream.readObject();
            return object;
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}