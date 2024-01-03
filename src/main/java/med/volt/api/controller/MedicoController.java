package med.volt.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import med.volt.api.domain.medico.DadosAtualizarMedico;
import med.volt.api.domain.medico.DadosCadastroMedico;
import med.volt.api.domain.medico.DadosDetalhamentoMedico;
import med.volt.api.domain.medico.DadosListagemMedico;
import med.volt.api.domain.medico.Medico;
import med.volt.api.domain.medico.MedicoRepository;

@RestController
@RequestMapping("medicos")
public class MedicoController {
	@Autowired
	private MedicoRepository repository;
	
	@PostMapping
	@Transactional
	public ResponseEntity<DadosDetalhamentoMedico> cadastrar(@RequestBody @Valid DadosCadastroMedico dados, UriComponentsBuilder uriBuilder) {
		var medico = new Medico(dados);
		repository.save(medico);

		var uri = uriBuilder.path("/medicos/{id}").buildAndExpand(medico.getId()).toUri();

		return ResponseEntity.created(uri).body(new DadosDetalhamentoMedico(medico));
	}

	@GetMapping
	public ResponseEntity<Page<DadosListagemMedico>> listar(@PageableDefault(size = 10, sort = {"nome"}) Pageable paginacao) {
		var page = repository.findAllByAtivoTrue(paginacao).map(DadosListagemMedico::new);
		return ResponseEntity.ok(page);
	}

	@PutMapping
	@Transactional
	public ResponseEntity<DadosDetalhamentoMedico> atualizar(@RequestBody @Valid DadosAtualizarMedico dados) {
		Medico medico = repository.getReferenceById(dados.id());
		medico.atualizarInformacoes(dados);

		return ResponseEntity.ok(new DadosDetalhamentoMedico(medico));
	}

	@DeleteMapping("/{id}")
	@Transactional
	public ResponseEntity<Void> excluir(@PathVariable Long id) {
		Medico medico = repository.getReferenceById(id);
		medico.excluir();

		return ResponseEntity.noContent().build();
	}

	@GetMapping("/{id}")
	public ResponseEntity<DadosDetalhamentoMedico> detalhar(@PathVariable Long id) {
		Medico medico = repository.getReferenceById(id);

		return ResponseEntity.ok(new DadosDetalhamentoMedico(medico));
	}
}