package org.bootcamp.trashhunter.controllers;

import org.bootcamp.trashhunter.dao.abstraction.UserDao;
import org.bootcamp.trashhunter.dao.abstraction.UserFavoritesDao;
import org.bootcamp.trashhunter.models.User;
import org.bootcamp.trashhunter.models.UserFavorites;
import org.bootcamp.trashhunter.models.dto.UserFavoritesDTO;
import org.bootcamp.trashhunter.services.abstraction.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
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
	private ModelAndView getAllFavorites() {

		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		User userBossFromEmail = userService.findByEmail(email);

		if (userBossFromEmail == null) {
		    return new ModelAndView("index");
        }

		Long numBoss = userBossFromEmail.getId();
		List<UserFavorites> listUsersFav = userFavoritesDao.getAllUserFavById(numBoss);

		if (listUsersFav.size() == 0) {
			ModelAndView modelAndView = new ModelAndView();
			modelAndView.setViewName("favorites");
			modelAndView.addObject("boss", userBossFromEmail.getName());
			modelAndView.addObject("ufDto", null);
			return modelAndView;
		}

		List<Long> listFav = new ArrayList<>();
		for (UserFavorites uf : listUsersFav) {
			listFav.add(uf.getUserSubject());
		}

		List<User> listUsers = userDao.getUsersFriendsListByUsersId(listFav);

		List<UserFavoritesDTO> ufDto = new ArrayList<>();
		for (User lu : listUsers) {
			ufDto.add(new UserFavoritesDTO(userBossFromEmail, lu));
		}

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("favorites");
		modelAndView.addObject("boss", userBossFromEmail.getName());
		modelAndView.addObject("ufDto", ufDto);
		return modelAndView;
	}

}
