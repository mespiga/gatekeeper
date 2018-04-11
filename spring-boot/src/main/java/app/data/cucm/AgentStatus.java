package app.data.cucm;

import java.io.Serializable;

public class AgentStatus implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String posto;
	private String nome;
	private String estado;
	private String duracao;
	private String extensaoacd;
	private String equipa;
	private int estadoInt;

	public String getPosto() {
		return posto;
	}
	public void setPosto(String posto) {
		this.posto = posto;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	public String getDuracao() {
		return duracao;
	}
	public void setDuracao(String duracao) {
		this.duracao = duracao;
	}
	public String getExtensaoacd() {
		return extensaoacd;
	}
	public void setExtensaoacd(String extensaoacd) {
		this.extensaoacd = extensaoacd;
	}
	public String getEquipa() {
		return equipa;
	}
	public void setEquipa(String equipa) {
		this.equipa = equipa;
	}
	public int getEstadoInt() {
		return estadoInt;
	}
	public void setEstadoInt(int estadoInt) {
		this.estadoInt = estadoInt;
	}

	public AgentStatus(String posto, String nome, String estado, String duracao, String extensaoacd, String equipa, int estadoInt) {
		super();
		this.posto = posto;
		this.nome = nome;
		this.estado = estado;
		this.duracao = duracao;
		this.extensaoacd = extensaoacd;
		this.equipa = equipa;
		this.estadoInt = estadoInt;
	}
	public AgentStatus() {
		super();
	}
}
