package com.cme.palindromeapi.repository;

import com.cme.palindromeapi.exception.DataStorageException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Repository
@Slf4j
public class PalindromeFileRepositoryImpl implements PalindromeRepository {


    @Value("${file.name}")
    private String fileName;

    @Override
    public void write(String message) {
        log.info("Storing message in the database");
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true));
            if(!containsMessage(message)) {
                writer.write(String.format("%s%n",message));
                log.info("Message successfully stored in the database");
            }
            writer.close();
        } catch (IOException ex) {
            throw new DataStorageException(ex.getMessage());
        }
    }

    @Override
    public List<String> readAll() {
        List<String> fileData = new ArrayList<>();
        try {
            File file = new File(fileName);
            if(!file.exists()) {
                file.createNewFile();
                return fileData;
            }
            BufferedReader reader = new BufferedReader(new FileReader(fileName));
            String line;
            while((line = reader.readLine())!=null) {
                fileData.add(line);
            }
            reader.close();
        } catch (IOException ex) {
            throw new DataStorageException(ex.getMessage());
        }
        return fileData;
    }

    private boolean containsMessage(String message) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(fileName));
            String line;
            while((line = reader.readLine())!=null) {
                if(line.trim().equals(message)) {
                    log.info("Message already stored in the database");
                    reader.close();
                    return true;
                }
            }
            reader.close();
            } catch (IOException ex) {
                throw new DataStorageException(ex.getMessage());
            }
        return false;
    }

}
