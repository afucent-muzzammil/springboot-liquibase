package com.example.sample.Controller;

import com.example.sample.DTO.UserRequestDTO;
import com.example.sample.DTO.UserResponseDTO;
import com.example.sample.Exception.ErrorResponse;
import com.example.sample.Service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
@Tag(name = "User APIs", description = "Create, Read, Update, Delete")
public class UserController {

    private final UserService userService;


    @Operation(
            summary = "Create a new User",
            description = "This endpoint is used to create a new user in the system"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "New User Created"),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ErrorResponse.class)
                )
            )
    })
    @PostMapping
    public ResponseEntity<UserResponseDTO> registerUser(@Valid @RequestBody UserRequestDTO requestDTO) {
        UserResponseDTO responseDTO = userService.registerUser(requestDTO);
        return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
    }


    @Operation(
            summary = "Get User by Id",
            description = "Fetches a user by their unique id"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User found"),
            @ApiResponse(responseCode = "404", description = "User not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> getUserById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getById(id));
    }


    @Operation(
            summary = "Get all users",
            description = "Fetched a paginated list of all users"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Users fetched successfully"),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = ErrorResponse.class))
                    )
            )
    })
    @GetMapping
    public ResponseEntity<Page<UserResponseDTO>> getAllUsers(@ParameterObject Pageable pageable) {
        return ResponseEntity.ok(userService.getAllUsers(pageable));
    }


    @Operation(
            summary = "Update User",
            description = "Update User by Id"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User update successfully"),
            @ApiResponse(responseCode = "404", description = "User not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PatchMapping("/{id}")
    public ResponseEntity<UserResponseDTO> updateUser(@PathVariable Long id, @Valid @RequestBody UserRequestDTO requestDTO) {
        return ResponseEntity.ok(userService.update(id , requestDTO));
    }


    @Operation(
            summary = "Delete user",
            description = "Delete a user by their id"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "User deleted successfully"),
            @ApiResponse(responseCode = "404", description = "User not found",
                content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ErrorResponse.class)))
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
