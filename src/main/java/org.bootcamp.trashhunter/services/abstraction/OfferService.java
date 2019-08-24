package org.bootcamp.trashhunter.services.abstraction;

import org.bootcamp.trashhunter.models.Offer;
import org.bootcamp.trashhunter.models.Taker;

import java.util.List;
import java.util.Map;

public interface OfferService extends AbstractService<Offer>{
    List<Offer> getFilterQuery(Map<String, Object> map);

    Map<Offer, List<Taker>> getOffersBySenderIdActiveFirst(Long id);

    void confirmOffer(Long takerId, Long offerId);

    public void cancelOffer(Long offerId);

    public void makeCompleteOffer(Long offerId);

    public void restoreOffer(Long offerId);
}
