package med.volt.api.domain.medico;

import jakarta.validation.constraints.NotNull;
import med.volt.api.domain.endereco.DadosEndereco;

public record DadosAtualizarMedico(
	@NotNull
	Long id,
	String nome,
	String telefone,
	DadosEndereco endereco
) {}
