package yte.thebackend.pojo;

import java.util.List;

public record LoginResponse(
        Long id,
        String username,
        List<String> authorities
) {
}
