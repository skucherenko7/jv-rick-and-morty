package mate.academy.rickandmorty.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import mate.academy.rickandmorty.dto.internal.CharacterDto;
import mate.academy.rickandmorty.service.CharacterService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Rick&Morty universe management",
        description = "Endpoints for the universe management")
@RestController
@RequestMapping("/rmuniverse")
@RequiredArgsConstructor

public class CharactersController {
    private final CharacterService service;

    @Operation(summary = "Get a random character",
            description = "Retrieves a random character from the universe")
    @GetMapping("char/random")
    public CharacterDto getRandomChar() {
        return service.getRandomChar();
    }

    @Operation(summary = "Get character by name",
            description = "Retrieves a character by name from the universe"
                    + " with sorting and pagination")
    @GetMapping("char/by-name")
    public Page<CharacterDto> findCharByName(@RequestParam String name, Pageable pageable) {
        return service.findCharByName(name, pageable);
    }
}
