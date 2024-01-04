package med.volt.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.validation.Valid;
import med.volt.api.domain.usuario.DadosAutenticacao;

@Controller
@RequestMapping("/login")
public class AuthenticationController {
	@Autowired
	private AuthenticationManager manager;

	@PostMapping
	public ResponseEntity<Void> efetuarLogin(@RequestBody @Valid DadosAutenticacao dados) {
		var token = new UsernamePasswordAuthenticationToken(dados.login(), dados.senha());
		var authentication = manager.authenticate(token);

		return ResponseEntity.ok().build();
	}
}
