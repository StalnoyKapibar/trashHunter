package org.bootcamp.trashhunter.dao.abstraction;

import org.bootcamp.trashhunter.models.Offer;
import org.bootcamp.trashhunter.models.Taker;

import java.util.List;
import java.util.Map;

public interface OfferDao extends AbstractDao<Offer> {
    /*
          Example:
          map.put("trashType", Arrays.asList(TrashType.METAL, TrashType.GLASS, TrashType.FOOD));
          map.put("weight","15-20");
          map.put("volume","30-45");
          map.put("isSorted", "true");
          map.put("isFree", "false");
      */
    List<Offer> getFilterQuery(Map<String, Object> map);

    Map<Offer,List<Taker>> getOffersBySenderIdActiveFirst(String name);

    List<Offer> getOffersByTaker(String email);

    List<Offer> getFilterOffersForTaker(Map<String , Object> map, String email);
}
