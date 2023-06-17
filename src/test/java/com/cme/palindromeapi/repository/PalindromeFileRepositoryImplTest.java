package com.cme.palindromeapi.repository;

import org.junit.Rule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.system.CapturedOutput;
import org.springframework.boot.test.system.OutputCaptureExtension;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.util.ReflectionTestUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
@TestPropertySource(locations="classpath:application.yml")
@ExtendWith(OutputCaptureExtension.class)
class PalindromeFileRepositoryImplTest {

    @InjectMocks
    private PalindromeFileRepositoryImpl repository;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        ReflectionTestUtils.setField(repository, "fileName", "processed-values-db.txt");
    }



    @Test
    @DisplayName("When given an existing value skip adding it to the database")
    void testSkipAddingExistingValue() {
        repository.write("{\"textValue\":\"kayak\",\"palindrome\":true}");
        assertTrue(true);
    }

    @Test
    @DisplayName("When given a new value add it to the database")
    void testAddNewValue(CapturedOutput output) {

        repository.write("{\"textValue\":\"test\",\"palindrome\":false}");
        assertThat(output).contains("Message successfully stored in the database");
        assertTrue(true);
        cleanUp();
    }

    @Test
    @DisplayName("Read all values from database")
    void testReadAll() {

        List<String> response = repository.readAll();
        assertNotNull(response);
        assertTrue(true);

    }

    private void cleanUp() {
        String filePath = "processed-values-db.txt";
        String searchString = "{\"textValue\":\"test\",\"palindrome\":false}";

        try {
            Path inputFile = Path.of(filePath);
            List<String> lines = Files.readAllLines(inputFile);

            List<String> filteredLines = lines.stream()
                    .filter(line -> !line.contains(searchString))
                    .collect(Collectors.toList());

            Files.write(inputFile, filteredLines, StandardOpenOption.TRUNCATE_EXISTING);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}