package med.volt.api.domain.paciente;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import med.volt.api.domain.endereco.DadosEndereco;

public record DadosPaciente(
	@NotBlank(message = "Nome é obrigatório")
	String nome,

	@NotBlank(message = "Email é obrigatório")
	@Email(message = "Email inválido")
	String email,

	@NotBlank(message = "Telefone é obrigatório")
	@Pattern(regexp = "\\d{11}")
	String telefone,

	@NotBlank(message = "CPF é obrigatório")
	@Pattern(regexp = "\\d{11}", message = "CPF inválido")
	String cpf,

	@NotNull
	@Valid
	DadosEndereco endereco
) {}
