package org.bootcamp.trashhunter.services.abstraction;

import org.bootcamp.trashhunter.models.Offer;
import org.bootcamp.trashhunter.models.Sender;
import org.bootcamp.trashhunter.models.Taker;

import java.util.List;
import java.util.Map;

public interface OfferService extends AbstractService<Offer>{

    List<Offer> getFilterQuery(Map<String, Object> map);

    Map<Offer, List<Taker>> getOffersBySenderIdActiveFirst(String email);

    List<Offer> getOffersByTaker(String email);

    List<Offer> getTakenOffersByTaker(Taker taker);

    List<Offer> getTakenOffersBySender(Sender sender);

    void confirmOffer(Long takerId, Long offerId);

    void cancelOffer(Long offerId);

    void makeCompleteOffer(Long offerId);

    void rateOfferBySender(Long takerId, Long offerId, Integer rating);

    void restoreOffer(Long offerId);

    List<Offer> getFilterOffersForTaker(Map<String, Object> map,String email);
}
