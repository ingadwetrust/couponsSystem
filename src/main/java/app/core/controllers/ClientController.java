package app.core.controllers;

import app.core.util.JwtUtil.UserDetails;

public abstract class ClientController {

	abstract UserDetails login(String email, String password);

}
