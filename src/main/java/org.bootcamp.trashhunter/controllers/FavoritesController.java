package org.bootcamp.trashhunter.controller;


import org.bootcamp.trashhunter.dao.abstraction.UserDao;
import org.bootcamp.trashhunter.dao.abstraction.UserFavoritesDao;
import org.bootcamp.trashhunter.models.User;
import org.bootcamp.trashhunter.models.UserFavorites;
import org.bootcamp.trashhunter.models.dto.UserFavoritesDTO;
import org.bootcamp.trashhunter.services.impl.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;
import java.util.ArrayList;
import java.util.List;

@Controller
public class FavoritesController {

	@Autowired
    UserService userService;

	@Autowired
    UserFavoritesDao userFavoritesDao;

	@Autowired
    UserDao userDao;

	@GetMapping(value = "/favorites")
	private ModelAndView getPageFavorites() {

// I get the list of friends of this user (1L):
// Сюда передать id Boss'a
		Long num = 1L;
		List<UserFavorites> listUf = userFavoritesDao.getAllUserFavById(num);

// Got 1 userBoss object by id (1L):
		User userBoss = userService.getById(num);

// Create a listFav collection of friends id:
		List<Long> listFav = new ArrayList<>();
		for (UserFavorites uf : listUf) {
			listFav.add(uf.getUserSubject());
		}

// Get the Users list by id from the listFav list:
		List<User> listUsers = userDao.getUsersFriendsListByUsersId(listFav);
// Create list of Users using UserFavoritesDTO:
		List<UserFavoritesDTO> ufDto = new ArrayList<>();
		for (User lu : listUsers) {
			ufDto.add(new UserFavoritesDTO(userBoss, lu));
		}

// Print ModelAndView for favorites.html:
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("favorites");
		modelAndView.addObject("boss", ufDto.get(0).getUserboss());
		modelAndView.addObject("ufDto", ufDto);
		return modelAndView;
	}

}
