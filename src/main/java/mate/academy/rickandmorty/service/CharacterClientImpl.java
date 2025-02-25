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
import mate.academy.rickandmorty.dto.external.CharacterResponseDataDto;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CharacterClientImpl implements CharacterClient {
    private static final String GET_ALL_CHARS_URL = "https://rickandmortyapi.com/api/character";

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
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
