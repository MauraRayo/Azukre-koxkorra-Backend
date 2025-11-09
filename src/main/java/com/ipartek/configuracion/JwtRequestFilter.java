package com.ipartek.configuracion;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtRequestFilter {
	@Autowired
	private JwtUtil jwtUtil;

	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		String path = request.getRequestURI();

		if (path.startsWith("/api/login") || path.equals("/") || path.startsWith("/swagger-ui")
				|| path.startsWith("/v3/api-docs")) {
			filterChain.doFilter(request, response);
			return;
		}

		String header = request.getHeader("Authorization");

		if (header != null && header.startsWith("Bearer ")) {
			String token = header.substring(7);

			try {
				if (jwtUtil.isTokenValid(token)) {
					Claims claims = jwtUtil.extractClaims(token);
					String username = claims.getSubject();
					String role = claims.get("role", String.class);

				
				} else {
					System.out.println("❌ Token inválido: " + token);
					response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token inválido");
					return;
				}
			} catch (Exception e) {
				System.out.println("❌ Error al validar token: " + e.getMessage());
				response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token inválido");
				return;
			}

		} else {
			System.out.println("⚠️ Falta el header Authorization");
			response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Falta el token");
			return;
		}
		filterChain.doFilter(request, response);
	}

}
