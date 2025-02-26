package mate.academy.rickandmorty.service;

import java.util.List;
import mate.academy.rickandmorty.dto.external.CharacterResponseDataDto;

public interface CharacterClient {
    List<CharacterResponseDataDto> initializeDatabase();

    Long getTotalCharactersCount();
}
