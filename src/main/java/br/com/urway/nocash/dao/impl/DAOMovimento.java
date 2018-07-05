/**
 * Classes de objetos de acesso a dados
 */
package br.com.urway.nocash.dao.impl;

import br.com.urway.nocash.dao.DAOJDBC;
import br.com.urway.nocash.dao.interf.IDAOMovimento;
import br.com.urway.nocash.model.Carteira;
import br.com.urway.nocash.model.Cliente;
import br.com.urway.nocash.model.Movimento;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DAOMovimento extends DAOJDBC implements IDAOMovimento {

    @Override
    public List<Movimento> procurar(Object... criterios) throws Exception{
        
         List<Movimento> mov = new ArrayList<>();
         
         try {
            try (Connection conn = getConnection();
                    PreparedStatement stmt = conn.prepareStatement("SELECT"
                            + " m.id, m.carteiraOrigem, m.carteiraDestino, m.nrDocumento, m.vlBRUTO, m.vlLiquido, m.vlDesc, m.dtMovimento,"
                            + " o.id as 'idOrigem', o.saldo as 'saldoOrigem', o.nome as 'nomeOrigem',"
                            + " d.id as 'idDestino', d.saldo as 'saldoDestino', d.nome as 'nomeDestino'"
                            + " FROM Movimento m"
                            + " LEFT JOIN Carteira o ON o.id = m.carteiraOrigem"
                            + " LEFT JOIN Carteira d on d.id = m.carteiraDestino"
                            + " ORDER BY dtMovimento");
                    ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    
                    
                    Movimento movimento = new Movimento();
                    movimento.setId(rs.getInt("id"));
                    movimento.setCarteiraOrigem(new Carteira());
                        movimento.getCarteiraOrigem().setId(rs.getInt("idOrigem"));
                        movimento.getCarteiraOrigem().setSaldo(rs.getDouble("saldoOrigem"));
                        movimento.getCarteiraOrigem().setNome(rs.getString("nomeOrigem"));
                        
                    movimento.setCarteiraDestino(new Carteira());
                        movimento.getCarteiraOrigem().setId(rs.getInt("idDestino"));
                        movimento.getCarteiraOrigem().setSaldo(rs.getDouble("saldoDestino"));
                        movimento.getCarteiraOrigem().setNome(rs.getString("nomeDestino"));
                   
                    movimento.setNrDocumento(rs.getString("nrDocumento"));
                    movimento.setVlBruto(rs.getDouble("vlBRUTO"));
                    movimento.setVlLiquido(rs.getDouble("vlLIQUIDO"));
                    movimento.setVlDesc(rs.getDouble("vlDESC"));
                    movimento.setDtMovimento(rs.getTimestamp("dtMovimento"));
                    mov.add(movimento);
                }
            }
        } catch (ClassNotFoundException | SQLException ex) {
            throw new Exception(ex);
        }
        
        return mov;
    }
    
    @Override
    public void inserir(Movimento mov) throws Exception {

        try {
            try (Connection conn = getConnection();
                    PreparedStatement stmt = conn.prepareStatement("INSERT INTO"
                            + " Movimento"
                            + " (carteiraOrigem, carteiraDestino, nrDocumento, vlBruto, vlLiquido, vlDesc)"
                            + " VALUES (?, ?, ?, ?, ?, ?) ")) {

                stmt.setInt(1, mov.getCarteiraOrigem().getId());
                stmt.setInt(2, mov.getCarteiraDestino().getId());
                stmt.setString(3, mov.getNrDocumento());
                stmt.setDouble(4, mov.getVlBruto());
                stmt.setDouble(5, mov.getVlLiquido());
                stmt.setDouble(6, mov.getVlDesc());
                
                if (stmt.executeUpdate() == 0) {
                    throw new SQLException("Nenhum registro inserido!");    
                }
            }
        } catch (SQLException ex) {
            throw new Exception(ex);
        }
    }

    @Override
    public void atualizar(Movimento elemento) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void excluir(int id) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Movimento obter(int id) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Movimento> procurarCarteira(String acao, String data) throws Exception {
        List<Movimento> mov = new ArrayList<>();
         
         try {
            try (Connection conn = getConnection();
                    PreparedStatement stmt = conn.prepareStatement("SELECT"
                            + " m.carteiraOrigem, m.carteiraDestino, m.nrDocumento, m.vlBRUTO, m.vlLiquido, m.vlDesc, m.dtMovimento,"
                            + " o.id as 'idOrigem', o.saldo as 'saldoOrigem', o.nome as 'nomeOrigem',"
                            + " d.id as 'idDestino', d.saldo as 'saldoDestino', d.nome as 'nomeDestino'"
                            + " FROM Movimento m"
                            + " LEFT JOIN Carteira o ON o.id = m.carteiraOrigem"
                            + " LEFT JOIN Carteira d on d.id = m.carteiraDestino"
                            + " ORDER BY dtMovimento");
                    ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    
                    
                    Movimento movimento = new Movimento();
                    movimento.setId(rs.getInt("id"));
                    movimento.setCarteiraOrigem(new Carteira());
                        movimento.getCarteiraOrigem().setId(rs.getInt("idOrigem"));
                        movimento.getCarteiraOrigem().setSaldo(rs.getDouble("saldoOrigem"));
                        movimento.getCarteiraOrigem().setNome(rs.getString("nomeOrigem"));
                        
                    movimento.setCarteiraDestino(new Carteira());
                        movimento.getCarteiraOrigem().setId(rs.getInt("idDestino"));
                        movimento.getCarteiraOrigem().setSaldo(rs.getDouble("saldoDestino"));
                        movimento.getCarteiraOrigem().setNome(rs.getString("nomeDestino"));
                   
                    movimento.setNrDocumento(rs.getString("nrDocumento"));
                    movimento.setVlBruto(rs.getDouble("vlBRUTO"));
                    movimento.setVlLiquido(rs.getDouble("vlLIQUIDO"));
                    movimento.setVlDesc(rs.getDouble("vlDESC"));
                    movimento.setDtMovimento(rs.getTimestamp("dtMovimento"));
                    mov.add(movimento);
                }
            }
        } catch (SQLException ex) {
            throw new Exception(ex);
        }
        
        return mov;
    }
    
    @Override
    public void cargaCarteira(Movimento mov) throws Exception {

        try {
            try (Connection conn = getConnection();
                    PreparedStatement stmt = conn.prepareStatement("INSERT INTO"
                            + " Movimento"
                            + " (carteiraOrigem, carteiraDestino, nrDocumento, vlBruto, vlLiquido, vlDesc)"
                            + " VALUES (?, ?, ?, ?, ?, ?) ")) {

                stmt.setInt(1, 0);
                stmt.setInt(2, mov.getCarteiraDestino().getId());
                stmt.setString(3, mov.getNrDocumento());
                stmt.setDouble(4, mov.getVlBruto());
                stmt.setDouble(5, mov.getVlLiquido());
                stmt.setDouble(6, mov.getVlDesc());
                
                if (stmt.executeUpdate() == 0) {
                    throw new SQLException("Nenhum registro inserido!");    
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(DAOCliente.class.getName()).log(Level.SEVERE, null, ex);
            throw new SQLException(ex);
        }
    }
    
    @Override
    public List<Movimento> getDestino(int id) throws Exception{
        
         List<Movimento> mov = new ArrayList<>();
         
         try {
            try (Connection conn = getConnection();
                    PreparedStatement stmt = conn.prepareStatement("SELECT"
                            + " m.id, m.carteiraOrigem, m.carteiraDestino, m.nrDocumento, m.vlBRUTO, m.vlLiquido, m.vlDesc, m.dtMovimento,"
                            + " o.id as 'idOrigem', o.saldo as 'saldoOrigem', o.nome as 'nomeOrigem',"
                            + " d.id as 'idDestino', d.saldo as 'saldoDestino', d.nome as 'nomeDestino'"
                            + " FROM Movimento m"
                            + " LEFT JOIN Carteira o ON o.id = m.carteiraOrigem"
                            + " LEFT JOIN Carteira d on d.id = m.carteiraDestino"
                            + " WHERE m.carteiraDestino = ?"
                            + " ORDER BY dtMovimento")){
                    stmt.setInt(1,id);
                    
            
                    ResultSet rs = stmt.executeQuery();
                while (rs.next()) {
                    
                    
                    Movimento movimento = new Movimento();
                    movimento.setId(rs.getInt("id"));
                    movimento.setCarteiraOrigem(new Carteira());
                        movimento.getCarteiraOrigem().setId(rs.getInt("idOrigem"));
                        movimento.getCarteiraOrigem().setSaldo(rs.getDouble("saldoOrigem"));
                        movimento.getCarteiraOrigem().setNome(rs.getString("nomeOrigem"));
                        
                    movimento.setCarteiraDestino(new Carteira());
                        movimento.getCarteiraOrigem().setId(rs.getInt("idDestino"));
                        movimento.getCarteiraOrigem().setSaldo(rs.getDouble("saldoDestino"));
                        movimento.getCarteiraOrigem().setNome(rs.getString("nomeDestino"));
                   
                    movimento.setNrDocumento(rs.getString("nrDocumento"));
                    movimento.setVlBruto(rs.getDouble("vlBRUTO"));
                    movimento.setVlLiquido(rs.getDouble("vlLIQUIDO"));
                    movimento.setVlDesc(rs.getDouble("vlDESC"));
                    movimento.setDtMovimento(rs.getTimestamp("dtMovimento"));
                    mov.add(movimento);
                }
            }
        } catch (SQLException ex) {
            throw new Exception(ex);
        }
        
        return mov;
    }
    
    @Override
    public List<Movimento> getOrigem(int id) throws Exception{
        
         List<Movimento> mov = new ArrayList<>();
         
         try {
            try (Connection conn = getConnection();
                    PreparedStatement stmt = conn.prepareStatement("SELECT"
                            + " m.id, m.carteiraOrigem, m.carteiraDestino, m.nrDocumento, m.vlBRUTO, m.vlLiquido, m.vlDesc, m.dtMovimento,"
                            + " o.id as 'idOrigem', o.saldo as 'saldoOrigem', o.nome as 'nomeOrigem',"
                            + " d.id as 'idDestino', d.saldo as 'saldoDestino', d.nome as 'nomeDestino'"
                            + " FROM Movimento m"
                            + " LEFT JOIN Carteira o ON o.id = m.carteiraOrigem"
                            + " LEFT JOIN Carteira d on d.id = m.carteiraDestino"
                            + " WHERE m.carteiraOrigem = ?"
                            + " ORDER BY dtMovimento")){
                    stmt.setInt(1,id);
                    
            
                    ResultSet rs = stmt.executeQuery();
                while (rs.next()) {
                    
                    
                    Movimento movimento = new Movimento();
                    movimento.setId(rs.getInt("id"));
                    movimento.setCarteiraOrigem(new Carteira());
                        movimento.getCarteiraOrigem().setId(rs.getInt("idOrigem"));
                        movimento.getCarteiraOrigem().setSaldo(rs.getDouble("saldoOrigem"));
                        movimento.getCarteiraOrigem().setNome(rs.getString("nomeOrigem"));
                        
                    movimento.setCarteiraDestino(new Carteira());
                        movimento.getCarteiraOrigem().setId(rs.getInt("idDestino"));
                        movimento.getCarteiraOrigem().setSaldo(rs.getDouble("saldoDestino"));
                        movimento.getCarteiraOrigem().setNome(rs.getString("nomeDestino"));
                   
                    movimento.setNrDocumento(rs.getString("nrDocumento"));
                    movimento.setVlBruto(rs.getDouble("vlBRUTO"));
                    movimento.setVlLiquido(rs.getDouble("vlLIQUIDO"));
                    movimento.setVlDesc(rs.getDouble("vlDESC"));
                    movimento.setDtMovimento(rs.getTimestamp("dtMovimento"));
                    mov.add(movimento);
                }
            }
        } catch (SQLException ex) {
            throw new Exception(ex);
        }
        
        return mov;
    }
    @Override
    public List<Movimento> getByCliente(int id) throws Exception{
        
         List<Movimento> mov = new ArrayList<>();
         
         try {
            try (Connection conn = getConnection();
                    PreparedStatement stmt = conn.prepareStatement("SELECT"
                            + " m.id, m.carteiraOrigem, m.carteiraDestino, m.nrDocumento, m.vlBRUTO, m.vlLiquido, m.vlDesc, m.dtMovimento,"
                            + " o.id as 'idOrigem', o.saldo as 'saldoOrigem', o.nome as 'nomeOrigem', o.cliente as 'idClienteOrigem',"
                            + " d.id as 'idDestino', d.saldo as 'saldoDestino', d.nome as 'nomeDestino', d.cliente as 'idClienteDestino'"
                            + " FROM Movimento m"
                            + " LEFT JOIN Carteira o ON o.id = m.carteiraOrigem"
                            + " LEFT JOIN Carteira d on d.id = m.carteiraDestino"
                            + " WHERE d.cliente = ? OR o.cliente = ?"
                            + " ORDER BY dtMovimento")){
                    stmt.setInt(1,id);
                    stmt.setInt(2,id);
            
                    ResultSet rs = stmt.executeQuery();
                while (rs.next()) {
                    
                    
                    Movimento movimento = new Movimento();
                    movimento.setId(rs.getInt("id"));
                    Carteira carteiraOrigem = new Carteira();
                        carteiraOrigem.setId(rs.getInt("idOrigem"));
                        carteiraOrigem.setSaldo(rs.getDouble("saldoOrigem"));
                        carteiraOrigem.setNome(rs.getString("nomeOrigem"));
                        Cliente clienteOrigem = new Cliente();
                            clienteOrigem.setId(rs.getInt("idClienteOrigem"));
                        carteiraOrigem.setCliente(clienteOrigem);
                    movimento.setCarteiraOrigem(carteiraOrigem);
                    
                    Carteira carteiraDestino = new Carteira();
                        carteiraDestino.setId(rs.getInt("idDestino"));
                        carteiraDestino.setSaldo(rs.getDouble("saldoDestino"));
                        carteiraDestino.setNome(rs.getString("nomeDestino"));
                        Cliente clienteDestino = new Cliente();
                            clienteDestino.setId(rs.getInt("idClienteDestino"));
                        carteiraDestino.setCliente(clienteDestino);
                    movimento.setCarteiraDestino(carteiraDestino);
                    
                    movimento.setNrDocumento(rs.getString("nrDocumento"));
                    movimento.setVlBruto(rs.getDouble("vlBRUTO"));
                    movimento.setVlLiquido(rs.getDouble("vlLIQUIDO"));
                    movimento.setVlDesc(rs.getDouble("vlDESC"));
                    movimento.setDtMovimento(rs.getTimestamp("dtMovimento"));
                    mov.add(movimento);
                }
            }
        } catch (SQLException ex) {
            throw new Exception(ex);
        }
        
        return mov;
    }
    
}

