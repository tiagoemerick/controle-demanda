package br.com.bb.controle.arh.infra.filters;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import br.com.bb.controle.arh.model.Funcionario;
import br.com.bb.controle.arh.util.Constants;

public class LoginCheckFilter extends AbstractFilter implements Filter {

	private static List<String> allowedURIs;
	private static List<String> restrictedURIs;

	@Override
	public void init(FilterConfig fConfig) throws ServletException {
		if (allowedURIs == null) {
			allowedURIs = new ArrayList<String>();
			allowedURIs.add(fConfig.getInitParameter("loginActionURI"));
			allowedURIs.add("/controle-arh/javax.faces.resource/main.css.jsf");
			allowedURIs.add("/controle-arh/javax.faces.resource/theme.css.jsf");
			allowedURIs.add("/controle-arh/javax.faces.resource/primefaces.js.jsf");
			allowedURIs.add("/controle-arh/javax.faces.resource/primefaces.css.jsf");
			allowedURIs.add("/controle-arh/javax.faces.resource/jquery/jquery-plugins.js.jsf");
			allowedURIs.add("/controle-arh/javax.faces.resource/jquery/jquery.js.jsf");
			allowedURIs.add("/controle-arh/javax.faces.resource/messages/messages.png.jsf");
			allowedURIs.add("/controle-arh/javax.faces.resource/images/ui-icons_2e83ff_256x240.png.jsf");
			allowedURIs.add("/controle-arh/javax.faces.resource/images/ui-icons_38667f_256x240.png.jsf");
			allowedURIs.add("/controle-arh/javax.faces.resource/bootstrap-responsive.min.css.jsf");
			allowedURIs.add("/controle-arh/javax.faces.resource/bootstrap.min.css.jsf");
			allowedURIs.add("/controle-arh/javax.faces.resource/login.css.jsf");
			allowedURIs.add("/controle-arh/javax.faces.resource/dashboard.main.css.jsf");
			allowedURIs.add("/controle-arh/javax.faces.resource/bootstrap.min.js.jsf");
			allowedURIs.add("/controle-arh/javax.faces.resource/img/bblogin.png.jsf");
			allowedURIs.add("/controle-arh/javax.faces.resource/img/glyphicons-halflings.png.jsf");
		}
		if (restrictedURIs == null) {
			restrictedURIs = new ArrayList<String>();
			restrictedURIs.add("/controle-arh/pages/funcionario/cadastrarFuncionario.jsf");
		}
	}

	@Override
	public void destroy() {

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpSession session = req.getSession();

		if (session.isNew()) {
			doLogin(request, response, req);
			return;
		}

		Funcionario logado = (Funcionario) session.getAttribute(Constants.session.FUNCIONARIO_LOGADO);
		if (logado == null && !allowedURIs.contains(req.getRequestURI())) {
			System.out.println(req.getRequestURI());
			doLogin(request, response, req);
			return;
		} else {
			if (logado != null && (req.getRequestURI().equalsIgnoreCase("/controle-arh/pages/login.jsf"))) {
				goHome(request, response, req);
			}
		}

		if (logado != null && !logado.getIsAdmin() && restrictedURIs.contains(req.getRequestURI())) {
			goHome(request, response, req);
		}
		chain.doFilter(request, response);
	}

}
