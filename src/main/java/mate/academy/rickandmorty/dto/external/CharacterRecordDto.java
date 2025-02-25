package mate.academy.rickandmorty.dto.external;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.OffsetDateTime;

public record CharacterRecordDto(
        @JsonProperty("id")
        Long externalId,
        String name,
        String status,
        String species,
        String type,
        String gender,
        LocationDto origin,
        LocationDto location,
        String image,
        String[] episode,
        String url,
        OffsetDateTime created) {
}
