package nl.ploentuin.ploentuin.dto.api;

import org.springframework.http.ResponseEntity;

public class ResponseHelper {

    public static <T> ResponseEntity<ApiResponse<T>> ok(T data, String message) {
        return ResponseEntity.ok(new ApiResponse<>(data, message));
    }

    public static <T> ResponseEntity<ApiResponse<T>> notFound(String message) {
        return ResponseEntity.status(404).body(new ApiResponse<>(null, message));
    }

    public static <T> ResponseEntity<ApiResponse<T>> badRequest(String message) {
        return ResponseEntity.status(400).body(new ApiResponse<>(null, message));
    }
}
