package org.bootcamp.trashhunter.services.abstraction;

import org.bootcamp.trashhunter.models.Offer;
import org.bootcamp.trashhunter.models.Taker;

import java.util.List;
import java.util.Map;

public interface OfferService extends AbstractService<Offer>{

    List<Offer> getFilterQuery(Map<String, Object> map);

    Map<Offer, List<Taker>> getOffersBySenderIdActiveFirst(String email);

    List<Offer> getOffersByTaker(String email);

    void confirmOffer(Long takerId, Long offerId);

    void cancelOffer(Long offerId);

    void makeCompleteOffer(Long offerId);

    void restoreOffer(Long offerId);

    List<Offer> getFilterOffersForTaker(Map<String, Object> map,String email);

    void takingOfferByTaker(Long takerId, Long offerId);
}
