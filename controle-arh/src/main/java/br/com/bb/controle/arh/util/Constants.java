package br.com.bb.controle.arh.util;

public final class Constants {

	private Constants() {
	}

	public static final class database {
		public static final String SCHEMA = "arh";
		public static final String PERSISTENCE_UNIT_NAME = "controleArhPu";
	}

	public static final class pages {
		public static final String LOGIN = "/pages/login.jsf";
		public static final String ACCESS_DENIED = "/pages/accessDenied.jsf";
		public static final String HOME = "/pages/home.jsf";
	}

	public static final class funcionarioPages {
		public static final String PESQUISAR_FUNCIONARIO = "/pages/funcionario/pesquisarFuncionario?faces-redirect=true";
		public static final String CADASTRAR_FUNCIONARIO = "/pages/funcionario/cadastrarFuncionario?faces-redirect=true";
	}
	
	public static final class demandaPages {
		public static final String PESQUISAR_DEMANDA = "/pages/demanda/pesquisarDemanda?faces-redirect=true";
		public static final String CADASTRAR_DEMANDA = "/pages/demanda/cadastrarDemanda?faces-redirect=true";
	}

	public static final class session {
		public static final String FUNCIONARIO_LOGADO = "funcionarioLogado";
	}

}
