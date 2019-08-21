package org.bootcamp.trashhunter.dao.impl;


import org.bootcamp.trashhunter.dao.AbstractDAO;
import org.bootcamp.trashhunter.models.Offer;
import org.bootcamp.trashhunter.models.TrashType;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository("offerDao")
public class OfferDao extends AbstractDAO<Offer> {

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
    public List<Offer> getFilterQuery(Map<String, Object> map) {

        StringBuilder whereQuery = new StringBuilder();
        whereQuery.append("SELECT o FROM Offer o JOIN FETCH o.sender WHERE o.isClosed=false");

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

    public List<Offer> getOffersBySenderId(Long senderId) {
        return
                entityManager
                        .createQuery("SELECT t FROM Offer t WHERE t.sender.id = :id", Offer.class)
                        .setParameter("id", senderId)
                        .getResultList();
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
