package dbmanager;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class StringUtil {

	public static String sha512(String password) {
		try { 
            MessageDigest md = MessageDigest.getInstance("SHA-512"); 

            byte[] messageDigest = md.digest(password.getBytes()); 
  
            BigInteger no = new BigInteger(1, messageDigest); 

            String hashtext = no.toString(16); 

            while (hashtext.length() < 32) { 
                hashtext = "0" + hashtext; 
            } 

            return hashtext; 
        } 

        catch (NoSuchAlgorithmException e) { 
            throw new RuntimeException(e); 
        } 
	}

}
