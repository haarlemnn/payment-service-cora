package br.med.nextmed.cora.infrastructure.configs;

import br.med.nextmed.cora.domain.models.CoraConfig;
import org.apache.http.ssl.SSLContextBuilder;
import org.bouncycastle.asn1.ASN1InputStream;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.DERNull;
import org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers;
import org.bouncycastle.asn1.pkcs.PrivateKeyInfo;
import org.bouncycastle.asn1.pkcs.RSAPrivateKey;
import org.bouncycastle.asn1.x509.AlgorithmIdentifier;

import javax.net.ssl.SSLContext;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.security.KeyFactory;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.UnrecoverableKeyException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;

public class CoraAuthConfig {

    private static final String CORA_ALIAS = "cora";
    private static final String CORA_PASSWORD = "cora-password";

    public static SSLContext getSSLContext(CoraConfig coraConfig) throws CertificateException, IOException, NoSuchAlgorithmException, KeyStoreException, InvalidKeySpecException, UnrecoverableKeyException, KeyManagementException {
        var certificate = decodeCertificate(coraConfig.getCertificate());
        var privateKey = decodePrivateKey(coraConfig.getPrivateKey());

        var keyStore = KeyStore.getInstance("PKCS12");
        keyStore.load(null, null);

        keyStore.setKeyEntry(CORA_ALIAS, privateKey, CORA_PASSWORD.toCharArray(), new Certificate[]{certificate});

        return SSLContextBuilder.create()
            .loadKeyMaterial(keyStore, CORA_PASSWORD.toCharArray())
            .build();
    }

    private static X509Certificate decodeCertificate(String certificate) throws CertificateException {
        var pemCertificate = new String(Base64.getDecoder().decode(certificate));
        var cleanedCertificate = pemCertificate
            .replace("-----BEGIN CERTIFICATE-----", "")
            .replace("-----END CERTIFICATE-----", "")
            .replaceAll("\\s+", "");

        byte[] decodedCert = Base64.getDecoder().decode(cleanedCertificate);
        var certFactory = CertificateFactory.getInstance("X.509");
        return (X509Certificate) certFactory.generateCertificate(new ByteArrayInputStream(decodedCert));
    }

    private static PrivateKey decodePrivateKey(String privateKey) throws InvalidKeySpecException, NoSuchAlgorithmException, IOException {
        var pemPrivateKey = new String(Base64.getDecoder().decode(privateKey));
        var cleanedKey = pemPrivateKey
            .replace("-----BEGIN RSA PRIVATE KEY-----", "")
            .replace("-----END RSA PRIVATE KEY-----", "")
            .replaceAll("\\s+", "");

        byte[] decodedKey = convertPKCS1ToPKCS8(Base64.getDecoder().decode(cleanedKey));
        var keySpec = new PKCS8EncodedKeySpec(decodedKey);
        var keyFactory = KeyFactory.getInstance("RSA");
        return keyFactory.generatePrivate(keySpec);
    }

    private static byte[] convertPKCS1ToPKCS8(byte[] pkcs1Key) throws IOException {
        try (ByteArrayInputStream bais = new ByteArrayInputStream(pkcs1Key); ASN1InputStream asn1InputStream = new ASN1InputStream(bais)) {
            ASN1Sequence seq = (ASN1Sequence) asn1InputStream.readObject();
            RSAPrivateKey privateKey = RSAPrivateKey.getInstance(seq);
            return new PrivateKeyInfo(
                new AlgorithmIdentifier(PKCSObjectIdentifiers.rsaEncryption, DERNull.INSTANCE),
                privateKey
            ).getEncoded();
        }
    }

}
