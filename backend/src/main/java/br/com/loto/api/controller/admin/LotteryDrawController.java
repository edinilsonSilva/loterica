package br.com.loto.api.controller.admin;

import br.com.loto.api.dto.caixa.CxLotteryDrawResponse;
import br.com.loto.api.dto.game.queries.ContestQuery;
import br.com.loto.api.dto.game.request.CreateContestRequest;
import br.com.loto.domain.entity.Account;
import br.com.loto.domain.entity.LotteryDraw;
import br.com.loto.domain.enums.TypeGame;
import br.com.loto.exceptions.CustomResponse;
import br.com.loto.service.games.ILotteryDrawService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/contests")
@Tag(name = "Rotas para o gerenciamento dos Concursos")
public class LotteryDrawController {

    private final ILotteryDrawService lotteryDrawService;

    @Operation(
            summary = "Lista todos os concursos cadastrados",
            description = "É possível utilizar parametros [name]")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK",
                    content = @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = Account.class)))),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST", content = @Content),
            @ApiResponse(responseCode = "404", description = "NOT FOUND", content = @Content)})
    @GetMapping(value = "/search", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Page<LotteryDraw>> findAllByParams(
            @RequestParam(value = "page", defaultValue = "0", required = false) Integer page,
            @RequestParam(value = "limit", defaultValue = "24", required = false) Integer limit,
            @RequestParam(value = "orderBy", defaultValue = "id", required = false) String orderBy,
            @RequestParam(value = "direction", defaultValue = "DESC", required = false) String direction,
            @RequestParam(value = "number", required = false) Integer contestNumber,
            @RequestParam(value = "game-id", required = false) Long gameId) {

        return new ResponseEntity<>(lotteryDrawService.findAllByParams(ContestQuery.builder()
                .orderBy(orderBy)
                .direction(direction)
                .page(page)
                .limit(limit)
                .contestNumber(contestNumber)
                .gameId(gameId)
                .build()), HttpStatus.OK);
    }

    @Operation(
            summary = "Cadastrar um novo concurso")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CustomResponse.class))),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST", content = @Content),
            @ApiResponse(responseCode = "404", description = "NOT FOUND", content = @Content)})
    @PostMapping(value = "", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<CustomResponse<LotteryDraw>> create(@RequestBody @Valid CreateContestRequest request) {
        return new ResponseEntity<>(lotteryDrawService.createAndUpdate(request), HttpStatus.OK);
    }

    @Operation(
            summary = "Gerar jogo")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CxLotteryDrawResponse.class))),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST", content = @Content),
            @ApiResponse(responseCode = "404", description = "NOT FOUND", content = @Content)})
    @PostMapping(value = "/{typeGame}/type-game", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<CustomResponse<LotteryDraw>> generateGame(@PathVariable TypeGame typeGame) {
        return new ResponseEntity<>(CustomResponse.<LotteryDraw>builder()
                .status(201)
                .message("Atualizado com sucesso.")
                .content(lotteryDrawService.generateGame(typeGame))
                .build(), HttpStatus.OK);
    }
}