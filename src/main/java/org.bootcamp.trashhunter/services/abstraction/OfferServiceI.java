package org.bootcamp.trashhunter.services.abstraction;

import org.bootcamp.trashhunter.models.Offer;

import java.util.List;
import java.util.Map;

public interface OfferServiceI extends AbstractServiceI<Offer> {
    List<Offer> getFilterQuery(Map<String, Object> map);

    List<Offer> getOffersBySenderId(Long id);

    void confirmOffer(Long id);
}
