package org.bootcamp.trashhunter.controllers;

import org.bootcamp.trashhunter.dao.abstraction.UserDao;
import org.bootcamp.trashhunter.dao.abstraction.UserFavoritesDao;
import org.bootcamp.trashhunter.models.User;
import org.bootcamp.trashhunter.models.UserFavorites;
import org.bootcamp.trashhunter.models.dto.UserFavoritesDTO;
import org.bootcamp.trashhunter.services.abstraction.UserService;
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
	private ModelAndView initTest() {

		Long num = 1L;
		List<UserFavorites> listUf = userFavoritesDao.getAllUserFavById(num);

		User userBoss = userService.getById(num);

		List<Long> listFav = new ArrayList<>();
		for (UserFavorites uf : listUf) {
			listFav.add(uf.getUserSubject());
		}

		List<User> listUsers = userDao.getUsersFriendsListByUsersId(listFav);

		List<UserFavoritesDTO> ufDto = new ArrayList<>();
		for (User lu : listUsers) {
			ufDto.add(new UserFavoritesDTO(userBoss, lu));
		}

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("favorites");
		modelAndView.addObject("boss", ufDto.get(0).getUserboss());
		modelAndView.addObject("ufDto", ufDto);
		return modelAndView;
	}

}
