package org.bootcamp.trashhunter.dao.impl;


import org.bootcamp.trashhunter.dao.abstraction.OfferDao;
import org.bootcamp.trashhunter.models.Offer;
import org.bootcamp.trashhunter.models.Taker;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository("offerDao")
public class OfferDaoImpl extends AbstractDAOImpl<Offer> implements OfferDao {

    @PersistenceContext
    protected EntityManager entityManager;

    /*
      Example:
      map.put("trashType", Arrays.asList(TrashType.METAL, TrashType.GLASS, TrashType.FOOD));
      map.put("weight","15-20");
      map.put("volume","30-45");
      map.put("isSorted", "true");
      map.put("isFree", "false");
  */

    @Override
    public List<Offer> getFilterQuery(Map<String, Object> map) {

        StringBuilder whereQuery = new StringBuilder();
        whereQuery.append("SELECT o FROM Offer o JOIN FETCH o.sender WHERE o.offerStatus<>'COMPLETE'");

        for (Map.Entry<String, Object> entry : map.entrySet()) {
            switch (entry.getKey()) {
                case "trashType":
                    whereQuery.append(" AND (");
                    String collect = ((List<String>) entry.getValue()).stream()
                            .map(value -> "o.trashType='" + value + "'")
                            .collect(Collectors.joining(" OR "));
                    whereQuery.append(collect).append(")");
                    break;
                case "weight":
                    whereQuery.append(" AND ");
                    String[] weightValues = entry.getValue().toString().split("-");
                    whereQuery.append(getBetweenQuery("weight", weightValues));
                    break;
                case "volume":
                    whereQuery.append(" AND ");
                    String[] volumeValues = entry.getValue().toString().split("-");
                    whereQuery.append(getBetweenQuery("volume", volumeValues));
                    break;
                case "isSorted":
                    whereQuery.append(" AND ");
                    whereQuery.append("o.isSorted=").append(entry.getValue());
                    break;
                case "isFree":
                    whereQuery.append(" AND ");
                    if (entry.getValue().equals("true")) {
                        whereQuery.append("o.price=0");
                    } else {
                        whereQuery.append("o.price>0");
                    }
                    break;
            }
        }

        return entityManager.createQuery(whereQuery.toString(), Offer.class).getResultList();
    }


//    entityManager
//            .createQuery("SELECT v FROM Vacancy v JOIN v.tags t WHERE t IN (:param)", Vacancy.class)
//            .setParameter("param", tags)
//                .setMaxResults(limit)
//                .getResultList());
    @Override
    public List<Offer> getOffersByTaker(String email){
        return  entityManager
                .createQuery( "SELECT o FROM Offer o JOIN o.respondingTakers t WHERE"+
                        "(o.offerStatus = 'ACTIVE' and t.email= :email)", Offer.class)
                .setParameter("email", "taker1@mail.ru")
                .getResultList();
    }

    @Override
    public Map<Offer, List<Taker>> getOffersBySenderIdActiveFirst(String email) {
        Map<Offer, List<Taker>> map = new LinkedHashMap<>();
        List<Offer> offers = entityManager
                .createQuery("SELECT o FROM Offer o WHERE o.sender.email = :email ORDER BY FIELD (o.offerStatus,'ACTIVE','TAKEN','OPEN','COMPLETE')", Offer.class)
                .setParameter("email", email)
                .getResultList();
        for (Offer offer : offers) {
            map.put(offer, offer.getRespondingTakers());
        }
        return map;
    }

    private String getBetweenQuery(String name, String[] array) {
        StringBuilder query = new StringBuilder();
        return query.append("o.").append(name).append(" BETWEEN ")
                .append(array[0])
                .append(" AND ")
                .append(array[1])
                .toString();
    }


}
