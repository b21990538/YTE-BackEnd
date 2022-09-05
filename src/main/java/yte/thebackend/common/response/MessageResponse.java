package yte.thebackend.common.response;

public record MessageResponse(
        String message,
        ResultType resultType
) {
}
