package br.com.bb.controle.arh.model;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQuery;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * The persistent class for the tarefa database table.
 * 
 */
@Entity
@NamedQuery(name = "Tarefa.findAll", query = "SELECT d FROM Tarefa d")
public class Tarefa implements IEntity {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	private Long numero;

	private Integer acao;

	private String descricao;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "dt_fim_plan")
	private Date dtFimPlan;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "dt_ini_plan")
	private Date dtIniPlan;

	@Temporal(TemporalType.DATE)
	@Column(name = "dt_fim_realizado")
	private Date dtFimRealizado;

	@Temporal(TemporalType.DATE)
	@Column(name = "dt_ini_realizado")
	private Date dtIniRealizado;

	private Integer esforco;

	// bi-directional many-to-many association to Funcionario
	@ManyToMany(mappedBy = "tarefas")
	private List<Funcionario> funcionarios;

	// bi-directional many-to-many association to Impacto
	@ManyToMany(mappedBy = "tarefas")
	private List<Impacto> impactos;

	public Tarefa() {
	}

	public Long getNumero() {
		return this.numero;
	}

	public void setNumero(Long numero) {
		this.numero = numero;
	}

	public Integer getAcao() {
		return this.acao;
	}

	public void setAcao(Integer acao) {
		this.acao = acao;
	}

	public String getDescricao() {
		return this.descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getDtFimPlanFormatada() {
		if (getDtFimPlan() != null) {
			SimpleDateFormat sf = new SimpleDateFormat("dd/MM/yyyy");
			return sf.format(getDtFimPlan());
		}
		return "";
	}

	public Date getDtFimPlan() {
		return this.dtFimPlan;
	}

	public void setDtFimPlan(Date dtFim) {
		this.dtFimPlan = dtFim;
	}

	public Date getDtIniPlan() {
		return this.dtIniPlan;
	}

	public String getDtIniPlanFormatada() {
		if (getDtIniPlan() != null) {
			SimpleDateFormat sf = new SimpleDateFormat("dd/MM/yyyy");
			return sf.format(getDtIniPlan());
		}
		return "";
	}

	public void setDtIniPlan(Date dtIni) {
		this.dtIniPlan = dtIni;
	}

	public Integer getEsforco() {
		return this.esforco;
	}

	public void setEsforco(Integer esforco) {
		this.esforco = esforco;
	}

	public List<Funcionario> getFuncionarios() {
		if (this.funcionarios == null) {
			this.funcionarios = new ArrayList<Funcionario>();
		}
		return this.funcionarios;
	}

	public void setFuncionarios(List<Funcionario> funcionarios) {
		this.funcionarios = funcionarios;
	}

	public List<Impacto> getImpactos() {
		if (this.impactos == null) {
			this.impactos = new ArrayList<Impacto>();
		}
		return this.impactos;
	}

	public void setImpactos(List<Impacto> impactos) {
		this.impactos = impactos;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		Long result = 1l;
		result = prime * result + id;
		return result.intValue();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Tarefa other = (Tarefa) obj;
		if (id != other.id)
			return false;
		return true;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Override
	public void setId(Object id) {
		setId(Integer.valueOf(String.valueOf(id)));
	}

	public Date getDtFimRealizado() {
		return dtFimRealizado;
	}

	public void setDtFimRealizado(Date dtFimRelizado) {
		this.dtFimRealizado = dtFimRelizado;
	}

	public Date getDtIniRealizado() {
		return dtIniRealizado;
	}

	public void setDtIniRealizado(Date dtIniRealizado) {
		this.dtIniRealizado = dtIniRealizado;
	}
	
	public String getDtFimRealizadoFormatada() {
		if (getDtFimRealizado() != null) {
			SimpleDateFormat sf = new SimpleDateFormat("dd/MM/yyyy");
			return sf.format(getDtFimRealizado());
		}
		return "";
	}
	
	public String getDtIniRealizadoFormatada() {
		if (getDtIniRealizado() != null) {
			SimpleDateFormat sf = new SimpleDateFormat("dd/MM/yyyy");
			return sf.format(getDtIniRealizado());
		}
		return "";
	}

}