package br.com.bb.controle.arh.model;

public enum StatusEnum {

	ATIVO(1, "Ativo", true), INATIVO(0, "Inativo", false);

	private StatusEnum(Integer id, String desc, Boolean status) {
		this.id = id;
		this.desc = desc;
		this.status = status;
	}

	private Integer id;
	private String desc;
	private Boolean status;

	public Integer getId() {
		return id;
	}

	public String getDesc() {
		return desc;
	}

	public Boolean getStatus() {
		return status;
	}

}
