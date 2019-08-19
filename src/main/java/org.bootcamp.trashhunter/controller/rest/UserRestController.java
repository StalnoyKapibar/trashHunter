package org.bootcamp.trashhunter.controller.rest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/")
public class UserRestController {

	@PostMapping(value = "/login", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity getUser(@RequestParam String loginUser,
								  @RequestParam String passUser) {

		if (loginUser == null || passUser == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

//		if (userAuth == null) {
//			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//		}

		return new ResponseEntity(loginUser, HttpStatus.OK);
	}
}
