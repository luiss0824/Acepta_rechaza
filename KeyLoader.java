/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aceptacion_rechazo;

/**
 *
 * @author Administrator
 */
import com.google.common.io.ByteStreams;
import java.io.InputStream;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.security.spec.PKCS8EncodedKeySpec;
import org.apache.commons.ssl.PKCS8Key;

/**
 *
 * @author Administrator
 */
public final class KeyLoader {

    public static PrivateKey loadPKCS8PrivateKey(InputStream in, String passwd) 
      throws Exception {
      byte[] decrypted = (passwd != null) 
        ? getBytes(in, passwd.toCharArray())
        : getBytes(in);
      PKCS8EncodedKeySpec keysp = new PKCS8EncodedKeySpec(decrypted);
      KeyFactory kf = KeyFactory.getInstance("RSA");
      return kf.generatePrivate(keysp);
    }

    public static X509Certificate loadX509Certificate(InputStream in) 
      throws Exception {
      CertificateFactory factory = CertificateFactory.getInstance("X.509");
      return (X509Certificate) factory.generateCertificate(in);
    }

    private static byte[] getBytes(InputStream in) throws Exception {
      try {
        return ByteStreams.toByteArray(in);
      } finally {
        in.close();
      }
    }

    private static byte[] getBytes(InputStream in, char[] passwd) 
      throws Exception {
      try {
        PKCS8Key pkcs8 = new PKCS8Key(in, passwd);
        return pkcs8.getDecryptedBytes();
      } finally {
        in.close();
      }
    }
    

}