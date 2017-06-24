package br.ufpr.tads.dao;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import java.sql.SQLException;


import java.sql.PreparedStatement;
import java.sql.Connection;
import br.ufpr.tads.bd.ConnectionFactory;
import br.ufpr.tads.bean.Login;

public class UserDao {
	
	private final String stmtGetLogin = "SELECT * FROM usuario WHERE nome=?";
	private final String stmtConfirmaVotoBD = "UPDATE usuario SET filme=?, diretor=?, votou=? WHERE nome=?";
	private final String stmtCheckVoto = "SELECT votou FROM usuario WHERE nome=?";
	
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
	
	public Login confirmaVoto(Login confirmaVoto) throws SQLException, NoSuchAlgorithmException, UnsupportedEncodingException {
		Connection con = null;
        PreparedStatement stmt = null;
        
        try {
            con = new ConnectionFactory().getConnection();
            con.setAutoCommit(false);
            stmt = con.prepareStatement(stmtConfirmaVotoBD, PreparedStatement.RETURN_GENERATED_KEYS);
            stmt.setString(1, confirmaVoto.getFilme());
            stmt.setString(2, confirmaVoto.getDiretor());
            stmt.setInt(3, 1);
            stmt.setString(4, confirmaVoto.getNome());
            
            stmt.executeUpdate();//grava no banco
            con.commit();
            
            confirmaVoto.setVotou(1);
            return confirmaVoto;
            
        }catch (SQLException ex) {
            try{con.rollback();}catch(SQLException ex1){ex1.printStackTrace();  System.out.println("Erro ao tentar rollback. Ex="+ex1.getMessage());};
            throw new RuntimeException("Erro ao confirmar voto no banco de dados. Origem="+ex.getMessage());
        } finally{
            try{stmt.close();}catch(Exception ex){ex.printStackTrace();  System.out.println("Erro ao fechar stmt. Ex="+ex.getMessage());};
            try{con.close();;}catch(Exception ex){ex.printStackTrace();  System.out.println("Erro ao fechar conexão. Ex="+ex.getMessage());};
        }
        
        
	}

	public int checkVoto(Login login) throws SQLException, NoSuchAlgorithmException, UnsupportedEncodingException {
		Connection con = null;
        PreparedStatement stmt = null;
        int checkVoto;
        ResultSet rs = null;
        
        try {
            con = new ConnectionFactory().getConnection();
            con.setAutoCommit(false);
            stmt = con.prepareStatement(stmtCheckVoto, PreparedStatement.RETURN_GENERATED_KEYS);
            stmt.setString(1, login.getNome());
            rs = stmt.executeQuery();
            rs.next();
            checkVoto = rs.getInt("votou");
            return checkVoto;
            
        }catch (SQLException ex) {
            try{con.rollback();}catch(SQLException ex1){ex1.printStackTrace();  System.out.println("Erro ao tentar rollback. Ex="+ex1.getMessage());};
            throw new RuntimeException("Erro ao confirmar voto no banco de dados. Origem="+ex.getMessage());
        } finally{
            try{stmt.close();}catch(Exception ex){ex.printStackTrace();  System.out.println("Erro ao fechar stmt. Ex="+ex.getMessage());};
            try{con.close();;}catch(Exception ex){ex.printStackTrace();  System.out.println("Erro ao fechar conexão. Ex="+ex.getMessage());};
        }
	}
	
}
