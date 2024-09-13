package br.com.loto.domain.specification;

import br.com.loto.api.dto.game.queries.ContestQuery;
import br.com.loto.domain.entity.Contest;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class ContestSpecification {

    public static Specification<Contest> search(ContestQuery request) {

        return (root, query, builder) -> {

            List<Predicate> predicates = new ArrayList<>();

            if (request.getContestNumber() != null)
                predicates.add(builder.equal(root.get("contestNumber"), request.getContestNumber()));

            if (request.getGameId() != null)
                predicates.add(builder.equal(root.get("game").get("id"), request.getGameId()));

            query.distinct(true);
            return query.where(predicates.toArray(new Predicate[predicates.size()])).getRestriction();
        };
    }
}
