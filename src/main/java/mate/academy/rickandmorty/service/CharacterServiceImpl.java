package mate.academy.rickandmorty.service;

import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Random;
import lombok.RequiredArgsConstructor;
import mate.academy.rickandmorty.dto.external.CharacterResponseDataDto;
import mate.academy.rickandmorty.dto.internal.CharacterDto;
import mate.academy.rickandmorty.mapper.CharacterMapper;
import mate.academy.rickandmorty.model.Character;
import mate.academy.rickandmorty.repository.CharacterRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CharacterServiceImpl implements CharacterService {
    private static final long TOTAL_CHARS = 826;

    private final CharacterClient client;

    private final CharacterMapper mapper;

    private final CharacterRepository repository;

    private final Random random = new Random();

    @Override
    public Page<CharacterDto> findByName(String name, Pageable pageable) {
        return repository.findByNameContainingIgnoreCase(name, pageable).map(mapper::toDto);
    }

    @Override
    public void initializeDatabase() {
        client.initializeDatabase().stream()
                .map(CharacterResponseDataDto::results)
                .flatMap(List::stream)
                .map(mapper::toModel)
                .forEach(repository::save);
    }

    @Override
    public CharacterDto getRandom() {
        Long id = random.nextLong(TOTAL_CHARS) + 1;
        Character byId = repository
                .findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Character not found by id: " + id)
                );
        return mapper.toDto(byId);
    }
}
