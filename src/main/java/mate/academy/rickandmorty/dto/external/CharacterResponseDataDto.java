package mate.academy.rickandmorty.dto.external;

import java.util.List;

public record CharacterResponseDataDto(
        CharacterMetadataDto info,
        List<CharacterRecordDto> results) {
}
