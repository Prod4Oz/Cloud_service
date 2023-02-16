package ru.prod.Cloud_service.dto;

import com.fasterxml.jackson.annotation.JsonGetter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TokenDTO {
    private String authToken;

    @JsonGetter("auth-token")
    public String getAuthToken() {
        return authToken;
    }
}
