package labs.secondSemester.server;

import labs.secondSemester.commons.commands.Command;
import labs.secondSemester.commons.exceptions.IllegalValueException;
import labs.secondSemester.commons.network.Header;
import labs.secondSemester.commons.network.Packet;
import labs.secondSemester.commons.network.Response;
import labs.secondSemester.commons.network.Serializer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketAddress;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Arrays;

public class Server {
    private final int PORT = 2224;
    private final DatagramSocket datagramSocket;
    private final Serializer serializer;
    private final RuntimeManager runtimeManager;
    private final int BUFFER_LENGTH = 1000;

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
        byte[] buffer = new byte[BUFFER_LENGTH];
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
        try {
            Header header = new Header(0, 0);
            int headerLength = serializer.serialize(header).length + 200;

            byte[] buffer = serializer.serialize(response);
            int bufferLength = buffer.length;
            int countOfPieces = bufferLength/(BUFFER_LENGTH-headerLength);
            if (countOfPieces*(BUFFER_LENGTH-headerLength) < bufferLength){
                countOfPieces += 1;
            }
            for (int i=0; i<countOfPieces; i++){
                header = new Header(countOfPieces, i);
                headerLength = serializer.serialize(header).length + 200;
                Packet packet = new Packet(header, Arrays.copyOfRange(buffer, i*(BUFFER_LENGTH-headerLength), Math.min(bufferLength, (i+1)*(BUFFER_LENGTH-headerLength)) ));

                byte[] array = serializer.serialize(packet);
                DatagramPacket datagramPacket2 = new DatagramPacket(array, array.length, address);
                datagramSocket.send(datagramPacket2);
                Thread.sleep(100);
            }

        }
        catch (IOException | InterruptedException e){
            System.out.println(e.getMessage());
        }
    }


    public <T> T readRequest(DatagramPacket datagramPacket, byte[] buffer) throws IOException {
        datagramSocket.receive(datagramPacket);
        Packet packet = serializer.deserialize(buffer);
        Header header = packet.getHeader();
        int countOfPieces = header.getCount();
        ArrayList<Packet> list = new ArrayList<>(countOfPieces);
        list.add(header.getNumber(), packet);
        int k = 1;

        while (k<countOfPieces){
            datagramSocket.receive(datagramPacket);
            Packet newPacket = serializer.deserialize(buffer);
            Header newHeader = newPacket.getHeader();
            list.add(newHeader.getNumber(), newPacket);
            k += 1;
        }

        int buffLength = 0;
        for (int i = 0; i < countOfPieces; i++) {
            buffLength += list.get(i).getPieceOfBuffer().length;
        }
        try (ByteArrayOutputStream byteStream = new ByteArrayOutputStream(buffLength)) {
            for (int i = 0; i < countOfPieces; i++) {
                byteStream.write(list.get(i).getPieceOfBuffer());
            }
            return serializer.deserialize(byteStream.toByteArray());
        } catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }
}
