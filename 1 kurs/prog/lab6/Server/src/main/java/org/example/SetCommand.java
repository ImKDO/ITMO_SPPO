package org.example;

import org.example.commands.Command;
import org.example.models.HumanBeing;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class SetCommand implements Serializable {
    private static  SocketChannel clientSocket;

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

    public static void sendCommand(SetCommand setCommand) {
        try {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(clientSocket.socket().getOutputStream());
            objectOutputStream.writeObject(setCommand);

        } catch (IOException e) {
            throw new RuntimeException(e);
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
//        private  int scriptStack;;

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

        public SetCommand build() {
            return new SetCommand(command, args, humanBeing, clientSocket);
        }
    }

    public static void setClientSocket(SocketChannel clientSocket) {
        SetCommand.clientSocket = clientSocket;
    }


    public static SocketChannel getClientSocket() {
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

//    public int getScriptStack() {
//        return scriptStack;
//    }
}
