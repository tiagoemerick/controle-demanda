package br.com.bb.controle.arh.util;

public final class Constants {

	private Constants() {
	}

	public static final class database {
		public static final String PERSISTENCE_UNIT_NAME = "controleArhPu";
	}

	public static final class pages {
		public static final String LOGIN = "/pages/public/login.jsf";
		public static final String ACCESS_DENIED = "/pages/public/accessDenied.jsf";
		public static final String HOME = "/pages/public/home.jsf";
	}

	public static final class session {
		public static final String FUNCIONARIO_LOGADO = "funcionarioLogado";
	}

}
