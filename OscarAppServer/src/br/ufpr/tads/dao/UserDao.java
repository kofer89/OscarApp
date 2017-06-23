package br.ufpr.tads.dao;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;


import com.mysql.jdbc.PreparedStatement;
import java.sql.Connection;
import br.ufpr.tads.bd.ConnectionFactory;
import br.ufpr.tads.bean.Login;

public class UserDao {
	
	private final String stmtGetLogin = "SELECT * FROM usuario WHERE nome=?";
	
	public Login getLogin(Login login) throws SQLException, UnsupportedEncodingException, NoSuchAlgorithmException{
        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Login feito = new Login();
        try {
            con = new ConnectionFactory().getConnection();
            stmt = (PreparedStatement) con.prepareStatement(stmtGetLogin);
            stmt.setString(1, login.getNome());
            rs = stmt.executeQuery();
            rs.next();
            if ((login.getSenha().equals(rs.getString("senha"))) && (login.getNome().equals(rs.getString("nome")))){
                feito.setNome(rs.getString("nome"));
                feito.setSenha(rs.getString("senha"));
            }
            return feito;
        } catch (SQLException ex) {
            throw new RuntimeException("Erro ao consultar cadastro de usuario. Origem="+ex.getMessage());
        }finally{
            try{rs.close();}catch(Exception ex){System.out.println("Erro ao fechar result set. Ex="+ex.getMessage());};
            try{stmt.close();}catch(Exception ex){System.out.println("Erro ao fechar stmt. Ex="+ex.getMessage());};
            try{con.close();;}catch(Exception ex){System.out.println("Erro ao fechar conexão. Ex="+ex.getMessage());};               
        }
    }
	
}
