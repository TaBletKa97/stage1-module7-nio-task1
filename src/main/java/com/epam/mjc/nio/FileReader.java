package com.epam.mjc.nio;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Path;


public class FileReader {

    public Profile getDataFromFile(File file) {
        final int BUFFER_SIZE = 48;
        StringBuilder sb = new StringBuilder();
        try (FileInputStream fileInputStream = new FileInputStream(file)){
            FileChannel channel = fileInputStream.getChannel();
            ByteBuffer buffer = ByteBuffer.allocate(BUFFER_SIZE);
            int read = channel.read(buffer);
            while (read != -1) {
                buffer.flip();
                while (buffer.hasRemaining()) {
                    sb.append((char) buffer.get());
                }
                buffer.clear();
                read = channel.read(buffer);
            }

        } catch(IOException e){
            e.printStackTrace();
        }
        return parseData(sb.toString());
    }

    private Profile parseData(String data) {
        final int NAME_INDEX = 0;
        final int AGE_INDEX = 1;
        final int EMAIL_INDEX = 2;
        final int PHONE_INDEX = 3;
        final int PARAMETER_COUNT = 4;
        final String NEW_LINE = "\n";
        final String SPACE = " ";

        String[] lines = data.split(NEW_LINE);
        for (int i = 0; i < PARAMETER_COUNT; i++) {
            lines[i] = lines[i].trim().split(SPACE)[AGE_INDEX];
        }
        String name = lines[NAME_INDEX];
        int age = Integer.parseInt(lines[AGE_INDEX]);
        String email = lines[EMAIL_INDEX];
        long phone = Long.parseLong(lines[PHONE_INDEX]);

        return new Profile(name, age, email, phone);
    }

    public static void main(String[] args) {
        File file = new File("C:\\Users\\TaBletKa97\\IdeaProjects\\stage1-module7-nio-task1\\src\\main\\resources\\Profile.txt");
        new FileReader().getDataFromFile(file);
    }
}
