package org.bootcamp.trashhunter.dao.abstraction;

import org.bootcamp.trashhunter.models.UserFavorites;
import java.util.List;

public interface UserFavoritesDao {

    public List<UserFavorites> getAllUserFavById(Long id);

}
