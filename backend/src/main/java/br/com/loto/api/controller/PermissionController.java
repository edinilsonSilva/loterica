package br.com.loto.api.controller;

import br.com.loto.api.dto.requests.CreatePermissionRequest;
import br.com.loto.domain.entity.Permission;
import br.com.loto.exceptions.CustomResponse;
import br.com.loto.service.IPermissionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/permissions")
@Tag(name = "Rotas para o gerenciamento das permissões")
public class PermissionController {
    
    private final IPermissionService permissionService;

    @Operation(
            summary = "Cadastrar uma nova permissão")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CustomResponse.class))),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST", content = @Content),
            @ApiResponse(responseCode = "404", description = "NOT FOUND", content = @Content)})
    @PostMapping
    public ResponseEntity<CustomResponse<Permission>> create(@RequestBody @Valid CreatePermissionRequest request) {
        return new ResponseEntity<>(permissionService.create(request), HttpStatus.OK);
    }
}