package br.com.urway.nocash.dao.impl;

import br.com.urway.nocash.dao.DAOJDBC;
import br.com.urway.nocash.dao.interf.IDAOCarteira;
import br.com.urway.nocash.model.Carteira;
import br.com.urway.nocash.model.Cliente;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;

public class DAOCarteira extends DAOJDBC implements IDAOCarteira {

    @Override
    public List<Carteira> procurar(Object... criterios) throws Exception {
        
        List<Carteira> carteiras = new ArrayList<>();
        
        try {
            try (Connection conn = getConnection();
                    PreparedStatement stmt = conn.prepareStatement("SELECT"
                            + " t.id carteira, c.id as cliente, c.nome as nomeCliente, "
                            + " c.email, c.cpf, c.rg, t.saldo, t.nome as nomeCarteira"
                            + " FROM Carteira t"
                            + " LEFT JOIN Cliente c on t.cliente = c.id ");
                    ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    
                    // Carteira
                    Carteira carteira = new Carteira();
                    carteira.setId(rs.getInt("carteira"));                    
                    carteira.setSaldo(rs.getDouble("saldo"));
                    carteira.setNome(rs.getString("nomeCarteira"));
                    
                    // Cliente
                    Cliente cliente = new Cliente();
                    cliente.setId(rs.getInt("cliente"));
                    cliente.setNome(rs.getString("nomeCliente"));
                    cliente.setEmail(rs.getString("email"));
                    cliente.setCpf(rs.getString("cpf"));
                    cliente.setRg(rs.getString("rg"));
                    
                    carteira.setCliente(cliente);
                    carteiras.add(carteira);
                }
            }
        } catch (SQLException ex) {
            throw new Exception(ex);
        }
        
        return carteiras;     
    }

    @Override
    public void inserir(Carteira carteira) throws Exception { 
        
        try {
            
            Cliente cliente = new Cliente();
            cliente.setId(carteira.getCliente().getId());
            
            if(cliente.getId() > 0){
                try (Connection conn = getConnection();
                        PreparedStatement stmt = conn.prepareStatement("INSERT INTO"
                                + " Carteira"
                                + " (cliente, saldo, nome,"
                                + " senha, senhaOpcional)"
                                + " VALUES (?, ?, ?, ?, ?) ")) {
                    stmt.setLong(1, cliente.getId());
                    stmt.setDouble(2, carteira.getSaldo());
                    stmt.setString(3, carteira.getNome());
                    stmt.setString(4, carteira.getSenha());
                    stmt.setShort(5, carteira.getSenhaOpcional());
                    
                    if(stmt.executeUpdate() <= 0){
                        throw new SQLException("O registro não foi inserido!");
                    }
                }
            }
            
        } catch (SQLException ex) {
            throw new Exception(ex);
        }
        
    }

    @Override
    public void atualizar(Carteira carteira) throws Exception {
        
        try {
            
            Cliente cliente = new Cliente();
            cliente.setId(carteira.getCliente().getId());
            
            if(carteira.getId() > 0 && cliente.getId() > 0){
                try (Connection conn = getConnection();
                        PreparedStatement stmt = conn.prepareStatement("UPDATE "
                                + " Carteira SET"
                                + " cliente=?, nome=?, "
                                + " senha=?, senhaOpcional=?"
                                + " where id=?")) {
                    stmt.setLong(1, cliente.getId());
                    stmt.setString(2, carteira.getNome());
                    stmt.setString(3, carteira.getSenha());
                    stmt.setShort(4, carteira.getSenhaOpcional());
                    stmt.setLong(5, carteira.getId());
                    
                    if(stmt.executeUpdate() <= 0){
                        throw new SQLException("O registro não pode ser atualizado!");
                    }
                }
            }
            
        } catch (SQLException ex) {
            throw new Exception(ex);
        }
        
    }

    @Override
    public void excluir(int id) throws Exception {
        
        try {
            if(id > 0){
                try (Connection conn = getConnection();
                    PreparedStatement stmt = conn.prepareStatement(""
                            + "DELETE FROM Carteira where id = ? ")) {
                    stmt.setLong(1, id);
                    
                    if(stmt.executeUpdate() <= 0){
                        throw new SQLException("O registro não pode ser deletado!");
                    }
                }
            }
            
        } catch (SQLException ex) {
            throw new Exception(ex);
        }
        
    }

    @Override
    public Carteira obter(int id) throws Exception {
        
        Carteira carteira = new Carteira();
        
        try {
            Cliente cliente = new Cliente();
            
            try (Connection conn = getConnection()){
                    PreparedStatement stmt = conn.prepareStatement("SELECT"
                            + " t.id, c.id as cliente, c.nome as nomeCliente, "
                            + " c.email, c.cpf, c.rg, t.saldo, t.nome"
                            + " FROM Carteira t"
                            + " LEFT JOIN Cliente c on t.cliente = c.id "
                            + " Where t.cliente = ?");
                    stmt.setInt(1, id);
                    ResultSet rs = stmt.executeQuery();
                    
                while (rs.next()) {
                    
                    // Carteira
                    carteira.setId(rs.getInt("id"));                    
                    carteira.setSaldo(rs.getDouble("saldo"));
                    carteira.setNome(rs.getString("nome"));
                    
                    // Cliente
                    cliente.setId(rs.getInt("cliente"));
                    cliente.setNome(rs.getString("nomeCliente"));
                    cliente.setEmail(rs.getString("email"));
                    cliente.setCpf(rs.getString("cpf"));
                    cliente.setRg(rs.getString("rg"));
                    
                    carteira.setCliente(cliente);
                }
            }
        } catch (ClassNotFoundException | SQLException ex) {
            throw new Exception(ex);
        }
        
        return carteira;
    }
     
}
