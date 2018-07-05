package br.com.urway.nocash.validacoes;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import br.com.urway.nocash.dao.DAOJDBC;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

public class valid extends DAOJDBC {
    

    private static final String EMAIL_PATTERN = 
        "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
        + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    private static final Pattern pattern = Pattern.compile(EMAIL_PATTERN, Pattern.CASE_INSENSITIVE);
        
    public static boolean isEmail(String email){
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
    
    public static boolean isValidEmailAddress(String email) {
        boolean result = true;
        try {
           InternetAddress emailAddr = new InternetAddress(email);
           emailAddr.validate();
        } catch (AddressException ex) {
           result = false;
        }
        return result;
     }
    
    public static boolean isUniqueEmail(String email){
        /*String sql = "SELECT emailCliente FROM cliente WHERE emailCliente = " + email;
        
        try {
            try (Connection conn = getConnection();
                    PreparedStatement stmt = conn.prepareStatement(sql);
                    ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return true;
                }
            }
        } catch (ClassNotFoundException | SQLException ex) {
        
        }*/
        
        return true;
    }

}
