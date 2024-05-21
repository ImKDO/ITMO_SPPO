package org.example;

import com.fasterxml.jackson.databind.JsonSerializable;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import org.apache.commons.lang3.SerializationUtils;
import org.example.commands.abstractCommandClass.Command;
import org.example.models.HumanBeing;


import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.List;

import static org.apache.commons.lang3.SerializationUtils.serialize;


public class SetCommand implements Serializable {
    private static SocketChannel clientSocket;
    private final String command;
    private final String args;
    private final HumanBeing humanBeing;
    private static final long serialVersionUID = 9212236352251172958L;

    public SetCommand(String command, String args, HumanBeing humanBeing, SocketChannel clientSocket) {
        this.command = command;
        this.args = args;
        this.humanBeing = humanBeing;
        this.clientSocket = clientSocket;
    }

    public static void sendCommand(SetCommand setCommand) throws IOException {
        byte[] byteCommand = serialize(setCommand);
        ByteBuffer buffer = ByteBuffer.wrap(byteCommand);
        clientSocket.write(buffer);

    }

    public static SetCommnadBuilder builder() {
        return new SetCommnadBuilder();
    }

    public static class SetCommnadBuilder {
        private String command;
        private String args;
        private HumanBeing humanBeing;
        private static SocketChannel clientSocket;

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
}

