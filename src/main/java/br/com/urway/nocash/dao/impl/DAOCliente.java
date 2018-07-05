package br.com.urway.nocash.dao.impl;

import br.com.urway.nocash.dao.DAOJDBC;
import br.com.urway.nocash.dao.interf.IDAOCliente;
import br.com.urway.nocash.model.Cliente;
import br.com.urway.nocash.validacoes.valid;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import java.security.Key;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import org.codehaus.jettison.json.JSONObject;

public class DAOCliente extends DAOJDBC implements IDAOCliente {
    
    private static final String ALGO = "AES";
    private final byte[] KeyValue;
    private final String KEY = "lv39eptlvuhaqqsr";
    
    public DAOCliente(String Key){
        KeyValue = Key.getBytes();
    }
    
    public String encrypt(String Data) throws Exception {
        Key key = generateKey();
        Cipher c = Cipher.getInstance(ALGO);
        c.init(Cipher.ENCRYPT_MODE, key);
        byte[] encVal = c.doFinal(Data.getBytes());
        String encryptedValue = new BASE64Encoder().encode(encVal);
        return encryptedValue;
    }
    
    public String decrypt(String encryptedData) throws Exception {
        Key key = generateKey();
        Cipher c = Cipher.getInstance(ALGO);
        c.init(Cipher.DECRYPT_MODE, key);
        byte[] decordedValue = new BASE64Decoder().decodeBuffer(encryptedData);
        byte[] decValue = c.doFinal(decordedValue);
        String decryptedValue = new String(decValue);
        return decryptedValue;
    }
    
    private Key generateKey() throws Exception {
        Key key = new SecretKeySpec(KeyValue, ALGO);
        return key;
    }

    @Override
    public List<Cliente> procurar(Object... criterios) throws Exception {
        
        List<Cliente> clientes = new ArrayList<>();
        
        try {
            try (Connection conn = getConnection();
                    PreparedStatement stmt = conn.prepareStatement("SELECT"
                            + " c.id, c.nome, c.sobrenome, c.email, c.cep,"
                            + " c.cpf, c.rg, c.dtNasc, c.sexo, c.tel, c.cel,"
                            + " c.dtRegistro, c.senha "
                            + " FROM Cliente c"
                            + " ORDER BY nome");
                    ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    
                    Cliente cliente = new Cliente();
                    cliente.setId(rs.getInt("id"));
                    cliente.setTel(rs.getString("tel"));
                    cliente.setCel(rs.getString("cel"));
                    cliente.setNome(rs.getString("nome"));
                    cliente.setSobrenome(rs.getString("sobrenome"));
                    cliente.setEmail(rs.getString("email"));
                    cliente.setCep(rs.getString("cep"));
                    cliente.setCpf(rs.getString("cpf"));
                    cliente.setRg(rs.getString("rg"));
                    cliente.setSexo(rs.getString("sexo"));
                    cliente.setSenha(rs.getString("senha"));
                    
                    clientes.add(cliente);
                }
            }
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(DAOCliente.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return clientes;
    }

    @Override
    public void inserir(Cliente cliente) throws Exception {

        try {
            
            try (Connection conn = getConnection();
                    PreparedStatement stmt = conn.prepareStatement("INSERT INTO"
                            + " Cliente"
                            + " (nome, sobrenome, email, cep,"
                            + " cpf, rg, sexo, tel, cel, senha) "
                            + " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ")) {

                stmt.setString(1, cliente.getNome());
                stmt.setString(2, cliente.getSobrenome());
                
                String email = cliente.getEmail();
                boolean isEmail = valid.isEmail(email);
                    
                if(!isEmail){
                    Logger.getLogger(DAOCliente.class.getName())
                            .log(Level.SEVERE, null, "E-mail inválido");
                    throw new SQLException("E-mail inválido");
                }
                
                if(verificaEmail(email)) {
                    throw new SQLException("E-mail já cadastrado!");
                }
                
                stmt.setString(3, email);
                stmt.setString(4, cliente.getCep());
                stmt.setString(5, cliente.getCpf());
                stmt.setString(6, cliente.getRg());
                stmt.setString(7, cliente.getSexo());
                stmt.setString(8, cliente.getTel());
                stmt.setString(9, cliente.getCel());
                
                // Encrypt AES
                DAOCliente aes = new DAOCliente(KEY);
                String encrypt = aes.encrypt(cliente.getSenha());
                stmt.setString(10, encrypt);
                
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
    public void atualizar(Cliente cliente) throws Exception {
        
        try {
            try (Connection conn = getConnection();
                    PreparedStatement stmt = conn.prepareStatement("UPDATE cliente SET"
                        + " nome =  ?,"
                        + " sobrenome = ?, "
                        + " email = ?, "
                        + " cep = ?, "
                        + " cpf = ?, "
                        + " rg = ?, "
                        + " sexo = ?, "
                        + " tel = ?, "
                        + " cel = ? "
                        + " WHERE id = ?")) {

                stmt.setString(1, cliente.getNome());
                stmt.setString(2, cliente.getSobrenome());
                stmt.setString(3, cliente.getEmail()); 
                stmt.setString(4, cliente.getCep());
                stmt.setString(5, cliente.getCpf());
                stmt.setString(6, cliente.getRg());
                stmt.setString(7, cliente.getSexo());
                stmt.setString(8, cliente.getTel());
                stmt.setString(9, cliente.getCel());
                stmt.setInt(10, cliente.getId());
                
                if(stmt.executeUpdate() <= 0){
                    throw new SQLException("O registro não pode ser atualizado!");
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(DAOCliente.class.getName()).log(Level.SEVERE, null, ex);
            throw new SQLException(ex);
        }
    }

    @Override
    public void excluir(int id) throws Exception {
        try {
            try (Connection conn = getConnection();
                    PreparedStatement stmt = conn.prepareStatement("DELETE FROM Cliente c"
                            + " WHERE c.id = ?")) {
                
                stmt.setInt(1, id);
                
                if (stmt.executeUpdate() == 0) {
                    throw new SQLException("Nenhum registro inserido!");
                } else {
                    try (ResultSet rs = stmt.getGeneratedKeys()) {
                        if (!rs.next()) {
                            throw new SQLException("Cliente não foi excluído!");
                        }
                    }
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(DAOCliente.class.getName()).log(Level.SEVERE, null, ex);
            throw new SQLException(ex);
        }
    }

    @Override
    public Cliente obter(int id) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public Cliente Login(Cliente cliente) throws Exception {
        
        Cliente retornoCliente = new Cliente();
        
        try {
            
            try (Connection conn = getConnection()) {
                    PreparedStatement stmt = 
                            conn.prepareStatement("select top(1) * from Cliente where email = ? AND senha = ?");
                    
                    
                    String email = cliente.getEmail();
                    boolean isEmail = valid.isEmail(email);
                    
                    if(!isEmail){
                        Logger.getLogger(DAOCliente.class.getName())
                                .log(Level.SEVERE, null, "E-mail inválido");
                        throw new SQLException("E-mail inválido");
                    }
                    
                    stmt.setString(1, email);
                    
                    // Verifica senha
                    DAOCliente aes = new DAOCliente(KEY);
                    String encrypt = aes.encrypt(cliente.getSenha());
                    System.out.println(aes.decrypt("bD4D6XmDKtiKoGYr3qPW2A=="));
                    
                    stmt.setString(2, encrypt);
                    ResultSet rs = stmt.executeQuery();

                    while (rs.next()) {
                        retornoCliente.setId(rs.getInt("id"));
                        retornoCliente.setNome(rs.getString("nome"));
                        retornoCliente.setEmail(rs.getString("email"));
                        retornoCliente.setSobrenome(rs.getString("sobrenome"));
                        retornoCliente.setCep(rs.getString("cep"));
                        retornoCliente.setCpf(rs.getString("cpf"));
                        retornoCliente.setRg(rs.getString("rg"));
                        retornoCliente.setSexo(rs.getString("sexo"));
                        retornoCliente.setTel(rs.getString("tel"));
                        retornoCliente.setCel(rs.getString("cel"));
                    }
            }
        } catch(SQLException ex) {
            Logger.getLogger(DAOCliente.class.getName()).log(Level.SEVERE, null, ex);
            throw new SQLException(ex);
        }
        
        return retornoCliente;
    }
    
    
    @Override
    public boolean verificaEmail(String email) throws Exception {
        boolean retorno = false;
        
        try {
            
            try (Connection conn = getConnection()) {
                   PreparedStatement stmt = 
                           conn.prepareStatement("select top(1) email from Cliente where email = ?");
                   
                   boolean isEmail = valid.isEmail(email);
                   
                   if(!isEmail){
                        Logger.getLogger(DAOCliente.class.getName())
                                .log(Level.SEVERE, null, "E-mail inválido");
                        throw new SQLException("E-mail inválido");
                    }
                   
                   stmt.setString(1, email);
                   ResultSet rs = stmt.executeQuery();

                   retorno = rs.next();
           }
        } catch(SQLException ex) {
            Logger.getLogger(DAOCliente.class.getName()).log(Level.SEVERE, null, ex);
            throw new SQLException(ex);
        }
         
        return retorno;
    }
}
