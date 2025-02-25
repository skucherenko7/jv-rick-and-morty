package mate.academy.rickandmorty.service;

import mate.academy.rickandmorty.dto.internal.CharacterDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CharacterService {
    CharacterDto getRandom();

    Page<CharacterDto> findByName(String name, Pageable pageable);

    void initializeDatabase();
}
