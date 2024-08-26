package org.example;

import org.example.models.HumanBeing;


import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

import static org.apache.commons.lang3.SerializationUtils.serialize;


public class SetCommand implements Serializable {
    private static SocketChannel clientSocket;

    private final String command;
    private final String args;
    private final HumanBeing humanBeing;
//    private final int scriptStack;

    private static final long serialVersionUID = 9212236352251172958L;

    public SetCommand(String command, String args, HumanBeing humanBeing, SocketChannel clientSocket) {
        this.command = command;
        this.args = args;
        this.humanBeing = humanBeing;
        this.clientSocket = clientSocket;
//        this.scriptStack = scriptStack;
    }

    public static void sendCommand(SetCommand setCommand, ByteBuffer responseBuffer) throws IOException {
        responseBuffer.clear();
        byte[] byteCommand = serialize(setCommand);
        ByteBuffer buffer = ByteBuffer.wrap(byteCommand);
        clientSocket.write(buffer);
    }

    public static void responseServer(ByteBuffer responseBuffer){
        //Получение ответа от сервера
        responseBuffer = responseBuffer.clear();
        try {
            clientSocket.read(responseBuffer);
        } catch (IOException e) {
            System.out.println("Произошли шоколадки с IO (скорее всего офнулся сервер)");
        }

    }

    public static SetCommnadBuilder builder() {
        return new SetCommnadBuilder();
    }

    public static class SetCommnadBuilder {
        private String command;
        private String args;
        private HumanBeing humanBeing;
        private static SocketChannel clientSocket;
        private  int scriptStack;

        public SetCommnadBuilder command(String command) {
            this.command = command;
            return this;
        }

        public SetCommnadBuilder args(String args) {
            this.args = args;
            return this;
        }

        public SetCommnadBuilder humanBeing(HumanBeing humanBeing) {
            this.humanBeing = humanBeing;
            return this;
        }

        public SetCommnadBuilder clientSocket(SocketChannel socketChannel) {
            this.clientSocket = socketChannel;
            return this;
        }

        public void scriptStack(int scriptStack) {
            this.scriptStack = scriptStack;
        }

        public SetCommand build() {
            return new SetCommand(command, args, humanBeing, clientSocket);
        }
    }

    public SocketChannel getClientSocket() {
        return clientSocket;
    }

    public String getCommand() {
        return command;
    }

    public String getArgs() {
        return args;
    }

    public HumanBeing getHumanBeing() {
        return humanBeing;
    }

//    public int getScriptStack() {return scriptStack;}
}

