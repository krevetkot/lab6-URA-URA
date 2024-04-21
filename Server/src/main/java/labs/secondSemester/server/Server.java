package labs.secondSemester.server;

import labs.secondSemester.commons.commands.Command;
import labs.secondSemester.commons.exceptions.IllegalValueException;
import labs.secondSemester.commons.network.Header;
import labs.secondSemester.commons.network.Packet;
import labs.secondSemester.commons.network.Response;
import labs.secondSemester.commons.network.Serializer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.*;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketAddress;
import java.net.SocketException;
import java.util.ArrayList;

public class Server {
    private final int PORT = 2224;
    private final DatagramSocket datagramSocket;
    private final Serializer serializer;
    private final RuntimeManager runtimeManager;
    private final int BUFFER_LENGTH = 10240;

    private static final Logger logger = LogManager.getLogger(Server.class);

    {
        serializer = new Serializer();
        runtimeManager = new RuntimeManager();
    }

    public Server() throws SocketException {
        datagramSocket = new DatagramSocket(PORT);
    }

    public void start() throws IOException {
        logger.info("Запуск сервера.");
        byte[] buffer = new byte[10240];
        logger.info("Создание DatagramPacket.");
        DatagramPacket datagramPacket = new DatagramPacket(buffer, buffer.length, datagramSocket.getInetAddress(), PORT);
        while (true) {
            Response response = null;
            try {
                logger.info("Чтение запроса.");
                Command command = readRequest(datagramPacket, buffer);

                logger.info("Обработка команды и выполнение.");
                response = runtimeManager.commandProcessing(command, false, null);
            } catch (IllegalValueException | ArrayIndexOutOfBoundsException | NumberFormatException e) {
                response = new Response(e.getMessage());
                logger.error(e.getMessage());
            } finally {
                logger.info("Отправка ответа.");
                sendResponse(response, datagramPacket.getSocketAddress());
            }
        }
    }

    public void sendResponse(Response response, SocketAddress address) throws IOException {
        byte[] array = serializer.serialize(response);

        DatagramPacket datagramPacket2 = new DatagramPacket(array, array.length, address);
        datagramSocket.send(datagramPacket2);
    }


    public <T> T readRequest(DatagramPacket datagramPacket, byte[] buffer) throws IOException {
        datagramSocket.receive(datagramPacket);
        Packet packet = serializer.deserialize(buffer);
        Header header = packet.getHeader();
        int countOfPieces = header.getCount();
        ArrayList<Packet> list = new ArrayList<>(countOfPieces);
        list.add(header.getNumber(), packet);
        int lengthOfBytes = packet.getPieceOfBuffer().length;
        int k = 1;

        while (k<countOfPieces){
            datagramSocket.receive(datagramPacket);
            Packet newPacket = serializer.deserialize(buffer);
            Header newHeader = packet.getHeader();
            list.add(newHeader.getNumber(), newPacket);
            k += 1;
            //как-то нужно сделать так, что если мы слишком долго ждем, лавочка сама прикрывалась
        }

        try (ByteArrayOutputStream byteStream = new ByteArrayOutputStream()) {
            for (int i = 0; i < countOfPieces; i++) {
                byteStream.write(list.get(i).getPieceOfBuffer(), i * lengthOfBytes, lengthOfBytes);
            }
            return serializer.deserialize(byteStream.toByteArray());
        } catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }

//    public <T> T readRequest(DatagramPacket datagramPacket, byte[] buffer) throws IOException {
//        datagramSocket.receive(datagramPacket);
//        return serializer.deserialize(buffer);
//    }


}
