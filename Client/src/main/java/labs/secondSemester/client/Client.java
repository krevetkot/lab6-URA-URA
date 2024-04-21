package labs.secondSemester.client;

import labs.secondSemester.commons.commands.*;
import labs.secondSemester.commons.commands.Exit;
import labs.secondSemester.commons.exceptions.FailedBuildingException;
import labs.secondSemester.commons.exceptions.IllegalValueException;
import labs.secondSemester.commons.managers.Console;
import labs.secondSemester.commons.network.Header;
import labs.secondSemester.commons.network.Packet;
import labs.secondSemester.commons.network.Response;
import labs.secondSemester.commons.network.Serializer;
import labs.secondSemester.commons.objects.Dragon;
import labs.secondSemester.commons.objects.forms.DragonForm;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.util.*;


public class Client {
    private final DatagramChannel datagramChannel;
    private InetSocketAddress serverAddress;
    private Selector selector;
    private final Serializer serializer;
    private final FileManager fileManager;
    private final String ip;
    private final int BUFFER_LENGTH = 1000;

    {
        selector = Selector.open();
        datagramChannel = DatagramChannel.open();
        serializer = new Serializer();
        selector = Selector.open();
    }

    public Client (String ip) throws IOException {

        fileManager = new FileManager(this);
        this.ip = ip;
    }




    public void start() {

        connectServer(0);
        ByteBuffer buffer = ByteBuffer.allocate(BUFFER_LENGTH);
        System.out.println("Приветствуем Вас в приложении по управлению коллекцией! Введите 'help' для вывода доступных команд.");

        CommandFactory commandFactory = new CommandFactory();
        while (true){

            String request = null;
            Scanner scanner = null;

            try {
                scanner = new Scanner(System.in);
                request = scanner.nextLine();
            } catch (NoSuchElementException e1) {
                System.out.println("До свидания! Приходите еще :)");
                System.exit(0);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }

            try {
                Command command = commandFactory.buildCommand(request);
                if (command instanceof Add || command instanceof InsertAt || command instanceof Update){
                    DragonForm newDragon = new DragonForm();
                    try {
                        Dragon buildedDragon = newDragon.build(scanner, false);
                        command.setObjectArgument(buildedDragon);
                    } catch (FailedBuildingException | IllegalValueException e) {
                        Console.print(e.getMessage(), false);
                    }
                }

                if (command instanceof Exit){
                    command.execute(null, false, null);

                }
                if (command instanceof ExecuteFile){
                    assert request != null;
                    fileManager.executeFile(request.trim().split(" ")[1]);
                    continue;
                } else {
                    send(command);
                }

            } catch (IllegalValueException | ArrayIndexOutOfBoundsException | NumberFormatException e){
                System.out.println(e.getMessage());
                continue;
            }

            Response response = receive(buffer);
            for (String element: response.getResponse()){
                System.out.println(element);
            }
        }

    }


    public void send(Command command){
        try {
            byte[] buffer = serializer.serialize(command);
            int bufferLength = buffer.length;
            int countOfPieces = bufferLength/BUFFER_LENGTH;
            if (countOfPieces*BUFFER_LENGTH<bufferLength){
                countOfPieces += 1;
            }
            for (int i=0; i<countOfPieces; i++){
                Header header = new Header(countOfPieces, i);
                int headerLength = serializer.serialize(header).length + 200;
                Packet packet = new Packet(header, Arrays.copyOfRange(buffer, i*(BUFFER_LENGTH-headerLength), Math.min(bufferLength, (i+1)*(BUFFER_LENGTH-headerLength)) ));
                datagramChannel.send(ByteBuffer.wrap(serializer.serialize(packet)), serverAddress);

            }

        }
        catch (IOException e){
            System.out.println(e.getMessage());
        }
    }



    public Response receive(ByteBuffer buffer){
        buffer.clear();
        buffer.flip();
        try {
            SocketAddress address = null;
            int time = 0;
            while (!serverAddress.equals(address)) {
                if (time==100000){
                    System.out.println("Кажется, сервер недоступен, приходите позже.");
                    System.exit(0);
                }
                buffer.clear();
                address = datagramChannel.receive(buffer);
                time += 1;
            }

            return serializer.deserialize(buffer.array());
        } catch (Exception e){
            System.out.println(e.getMessage());
            return  null;
        }

    }

    public void connectServer(int connectionTries){
        try {
            serverAddress = new InetSocketAddress(ip, 2224);
            datagramChannel.configureBlocking(false);
            datagramChannel.register(selector, SelectionKey.OP_READ);
            System.out.println("Подключение к серверу налажено.");
        } catch (IOException e){
            connectionTries += 1;
            if (connectionTries<3){
                System.out.println("Переподключаемся...");
                connectServer(connectionTries);
            } else {
                System.out.println("Кажется, барахлит подключение к серверу. Попробуйте позже.");
                System.exit(0);
            }
        }
    }


}
