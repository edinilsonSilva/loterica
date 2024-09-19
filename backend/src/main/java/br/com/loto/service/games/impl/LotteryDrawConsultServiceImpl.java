package br.com.loto.service.games.impl;

import br.com.loto.api.dto.game.queries.ContestQuery;
import br.com.loto.api.dto.game.response.LotteryDrawPublicResponse;
import br.com.loto.api.mappers.LotteryDrawMapper;
import br.com.loto.client.ICaixaFeign;
import br.com.loto.domain.entity.LotteryDraw;
import br.com.loto.domain.enums.TypeGame;
import br.com.loto.domain.repository.ILotteryDrawRepository;
import br.com.loto.domain.specification.LotteryDrawSpecification;
import br.com.loto.service.games.ILotteryDrawConsultService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@AllArgsConstructor(onConstructor_ = @Lazy)
public class LotteryDrawConsultServiceImpl implements ILotteryDrawConsultService {

    private final ILotteryDrawRepository lotteryDrawRepository;

    private final ICaixaFeign caixaFeign;

    private final LotteryDrawMapper lotteryDrawMapper;

    @Override
    public Page<LotteryDraw> findAllByParams(ContestQuery query) {
        Sort.Direction sortDirection = "desc".equalsIgnoreCase(query.getDirection()) ? Sort.Direction.DESC : Sort.Direction.ASC;
        PageRequest pageRequest = PageRequest.of(query.getPage(), query.getLimit(), Sort.by(sortDirection, query.getOrderBy()));
        return lotteryDrawRepository.findAll(LotteryDrawSpecification.search(query), pageRequest);
    }

    @Override
    public Page<LotteryDrawPublicResponse> findAllByParamsPublic(ContestQuery query) {
        return findAllByParams(query).map(l -> lotteryDrawMapper.convertEntityToResponse(l));
    }

    @Override
    public LotteryDraw findByIdWithThrow(Long contestId) {
        return lotteryDrawRepository.findById(contestId)
                .orElseThrow(() -> new RuntimeException("Concurso não encontrado."));
    }

    @Override
    public Optional<LotteryDraw> findByGameTypeAndNumber(TypeGame typeGame, int number) {
        return lotteryDrawRepository.findByGameTypeAndNumber(typeGame, number);
    }
}
