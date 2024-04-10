package labs.secondSemester.client;

import labs.secondSemester.commons.commands.*;
import labs.secondSemester.commons.commands.Exit;
import labs.secondSemester.commons.exceptions.FailedBuildingException;
import labs.secondSemester.commons.exceptions.IllegalValueException;
import labs.secondSemester.commons.managers.Console;
import labs.secondSemester.commons.network.CommandFactory;
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
import java.util.NoSuchElementException;
import java.util.Scanner;


public class Client {
    private DatagramChannel datagramChannel;
    private InetSocketAddress serverAddress;
    private Selector selector;
    private Serializer serializer;
    private FileManager fileManager;

    {
        selector = Selector.open();
        datagramChannel = DatagramChannel.open();
        serializer = new Serializer();
        selector = Selector.open();
    }

    public Client (String ip) throws IOException {
        serverAddress = new InetSocketAddress(ip, 2224);
        fileManager = new FileManager(this);
    }


    public void start() throws IOException {

        ByteBuffer buffer = ByteBuffer.allocate(10240);
        try {
            datagramChannel.configureBlocking(false);
            datagramChannel.register(selector, SelectionKey.OP_READ);
            System.out.println("Подключение к серверу налажено.");
        } catch (IOException e){
            System.out.println("Кажется, барахлит подключение к серверу. Попробуйте позже.");
            System.exit(0);
        }
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
                Command command = commandFactory.buildCommand(request, false);
                if (command.getClass().equals(Add.class) || command.getClass().equals(InsertAt.class) || command.getClass().equals(Update.class)){
                    DragonForm newDragon = new DragonForm();
                    try {
                        Dragon buildedDragon = newDragon.build(scanner, false);
                        command.setObjectArgument(buildedDragon);
                    } catch (FailedBuildingException | IllegalValueException e) {
                        Console.print(e.getMessage(), false);
                    }
                }

                if (command.getClass().equals(Exit.class)){
                    command.execute(null, false, null);

                }
                if (command.getClass().equals(ExecuteFile.class)){
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
            datagramChannel.send(ByteBuffer.wrap(serializer.serialize(command)), serverAddress);
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
            while (!serverAddress.equals(address)) {
                buffer.clear();
                selector.select();
                address = datagramChannel.receive(buffer);
            }

            Response response = serializer.deserialize(buffer.array());
            return response;
        } catch (Exception e){
            System.out.println(e.getMessage());
            return  null;
        }

    }


}
