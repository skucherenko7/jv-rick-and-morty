package mate.academy.rickandmorty.mapper;

import mate.academy.rickandmorty.dto.external.CharacterRecordDto;
import mate.academy.rickandmorty.dto.internal.CharacterDto;
import mate.academy.rickandmorty.model.Character;
import org.mapstruct.Mapper;

@Mapper(config = mate.academy.rickandmorty.config.MapperConfig.class)
public interface CharacterMapper {

    Character toModel(CharacterRecordDto recordDto);

    CharacterDto toDto(Character character);
}
