package org.example.runner;

import org.apache.commons.lang3.SerializationUtils;
import org.example.Response;

import java.io.DataOutputStream;
import java.io.IOException;
import java.nio.channels.SocketChannel;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ResponseCommand {

    private static final ReadWriteLock lock = new ReentrantReadWriteLock();
    public static void response(String msg, SocketChannel socketChannel) throws IOException {

        lock.readLock().lock();
        //Отправка ответа
        Response response = new Response(msg);
        byte[] byteRead = SerializationUtils.serialize(response);
        DataOutputStream objectOutputStream = new DataOutputStream(socketChannel.socket().getOutputStream());
        objectOutputStream.write(byteRead);
        lock.readLock().unlock();
    }
}
