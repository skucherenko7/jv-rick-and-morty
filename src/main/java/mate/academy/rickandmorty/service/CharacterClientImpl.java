package mate.academy.rickandmorty.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import mate.academy.rickandmorty.dto.external.CharacterMetadataDto;
import mate.academy.rickandmorty.dto.external.CharacterResponseDataDto;
import mate.academy.rickandmorty.exception.CharacterCountFetchException;
import mate.academy.rickandmorty.exception.DatabaseInitializationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CharacterClientImpl implements CharacterClient {

    private static final String GET_ALL_CHARS_URL = "https://rickandmortyapi.com/api/character";
    private static final Logger logger = LoggerFactory.getLogger(CharacterClientImpl.class);

    private final ObjectMapper mapper;

    @Override
    public List<CharacterResponseDataDto> initializeDatabase() {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request;
        HttpResponse<String> response;

        try {
            List<CharacterResponseDataDto> responseDataDtoTotal = new ArrayList<>();
            CharacterResponseDataDto characterResponseDataDto;
            String nextPage = GET_ALL_CHARS_URL;
            do {
                request = HttpRequest.newBuilder()
                        .GET()
                        .uri(URI.create(nextPage))
                        .build();
                response = client.send(request, HttpResponse.BodyHandlers.ofString());
                characterResponseDataDto = mapper.readValue(response.body(),
                        CharacterResponseDataDto.class);
                responseDataDtoTotal.add(characterResponseDataDto);
            } while ((nextPage = characterResponseDataDto.info().next()) != null);

            return responseDataDtoTotal;
        } catch (IOException e) {
            logger.error("IOException occurred while initializing database: {}", e.getMessage(), e);
            throw new DatabaseInitializationException("Failed to initialize database"
                    + " due to I/O error.", e);
        } catch (InterruptedException e) {
            logger.error("Request interrupted while initializing database: {}", e.getMessage(), e);
            Thread.currentThread().interrupt();
            throw new DatabaseInitializationException("Database initialization "
                    + "was interrupted.", e);
        } catch (Exception e) {
            logger.error("Unexpected error occurred while "
                    + "initializing database: {}", e.getMessage(), e);
            throw new DatabaseInitializationException("An unexpected error occurred "
                    + "while initializing the database.", e);
        }
    }

    @Override
    public Long getTotalCharactersCount() {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request;
        HttpResponse<String> response;

        try {
            request = HttpRequest.newBuilder()
                    .GET()
                    .uri(URI.create(GET_ALL_CHARS_URL))
                    .build();
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
            CharacterResponseDataDto responseDto = mapper.readValue(response.body(),
                    CharacterResponseDataDto.class);
            CharacterMetadataDto metadata = responseDto.info();
            return (long) metadata.count();
        } catch (IOException e) {

            logger.error("IOException occurred while fetching "
                    + "total characters count: {}", e.getMessage(), e);
            throw new CharacterCountFetchException("Failed to fetch total"
                    + " character count due to I/O error.", e);
        } catch (InterruptedException e) {
            logger.error("Request interrupted while fetching "
                    + "total characters count: {}", e.getMessage(), e);
            Thread.currentThread().interrupt();
            throw new CharacterCountFetchException("Character count fetch was interrupted.", e);
        } catch (Exception e) {
            logger.error("Unexpected error occurred while fetching"
                    + " total character count: {}", e.getMessage(), e);
            throw new CharacterCountFetchException("An unexpected error occurred"
                    + " while fetching the total character count.", e);
        }
    }
}
