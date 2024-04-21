package labs.secondSemester.commons.network;

import java.io.*;

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

    @SuppressWarnings("unchecked")
    public <T> T deserialize(byte[] buffer) {
        try (ObjectInputStream inputStream = new ObjectInputStream(new ByteArrayInputStream(buffer))) {
            return (T) inputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
