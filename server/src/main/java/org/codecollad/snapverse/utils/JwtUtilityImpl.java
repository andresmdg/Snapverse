package org.codecollad.snapverse.utils;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.RSASSASigner;
import com.nimbusds.jose.crypto.RSASSAVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.text.ParseException;
import java.util.Base64;
import java.util.Date;

@Service
public class JwtUtilityImpl implements JwtUtility {

    @Value("${jwtKeys.privateKeyPath}")
    private Resource privateKeyResource;

    @Value("${jwtKeys.publicKeyPath}")
    private Resource publicKeyResource;

    @PostConstruct
    public void init() {
        try {
            System.out.println("Private key path: " + privateKeyResource.getURI());
            System.out.println("Public key path: " + publicKeyResource.getURI());
        } catch (IOException e) {
            System.out.println("No se pueden encontrar las keys");
            throw new RuntimeException(e);
        }
    }


    @Override
    public String generateJWT(Long userId)
            throws IOException, NoSuchAlgorithmException, InvalidKeySpecException, JOSEException {
        System.out.println("Generating token for user ID: " + userId);
        System.out.println("Loading private key from: " + privateKeyResource.getURI());
        PrivateKey privateKey = loadPrivateKey(privateKeyResource);
        JWSSigner signer = new RSASSASigner(privateKey);
        Date now = new Date();
        JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
                .subject(userId.toString())
                .issueTime(now)
                .expirationTime(new Date(now.getTime() + 14400000))
                .build();

        SignedJWT signedJWT = new SignedJWT(new JWSHeader(JWSAlgorithm.RS256), claimsSet);
        signedJWT.sign(signer);
        return signedJWT.serialize();
    }

    @Override
    public JWTClaimsSet parseJWT(String jwtToken)
            throws IOException, NoSuchAlgorithmException, InvalidKeySpecException, ParseException, JOSEException {
        PublicKey publicKey = loadPublicKey(publicKeyResource);
        SignedJWT signedJWT = SignedJWT.parse(jwtToken);
        JWSVerifier verifier = new RSASSAVerifier((RSAPublicKey) publicKey);

        if (!signedJWT.verify(verifier)) {
            throw new JOSEException("Invalid signature");
        }

        JWTClaimsSet claimsSet = signedJWT.getJWTClaimsSet();
        if (claimsSet.getExpirationTime().before(new Date())) {
            throw new JOSEException("Expired token");
        }
        return claimsSet;
    }

    private PrivateKey loadPrivateKey(Resource resource)
            throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
        byte[] keyBytes = Files.readAllBytes(Paths.get(resource.getURI()));
        String privateKeyPEM = new String(keyBytes, StandardCharsets.UTF_8)
                .replace("-----BEGIN PRIVATE KEY-----", "")
                .replace("-----END PRIVATE KEY-----", "")
                .replaceAll("\\s+", "");

        byte[] decodeKey = Base64.getDecoder().decode(privateKeyPEM);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return keyFactory.generatePrivate(new PKCS8EncodedKeySpec(decodeKey));
    }

    private PublicKey loadPublicKey(Resource resource)
            throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
        byte[] keyBytes = Files.readAllBytes(Paths.get(resource.getURI()));
        String publicKeyPEM = new String(keyBytes, StandardCharsets.UTF_8)
                .replace("-----BEGIN PUBLIC KEY-----", "")
                .replace("-----END PUBLIC KEY-----", "")
                .replaceAll("\\s+", "");

        byte[] decodeKey = Base64.getDecoder().decode(publicKeyPEM);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return keyFactory.generatePublic(new X509EncodedKeySpec(decodeKey));
    }

    public Long extractUserId(String token)
            throws ParseException, JOSEException, IOException, NoSuchAlgorithmException, InvalidKeySpecException {
        SignedJWT signedJWT = SignedJWT.parse(token.replace("Bearer ", ""));

        PublicKey publicKey = loadPublicKey(publicKeyResource);
        JWSVerifier verifier = new RSASSAVerifier((RSAPublicKey) publicKey);

        if (!signedJWT.verify(verifier)) {
            throw new JOSEException("Invalid signature");
        }

        JWTClaimsSet claimsSet = signedJWT.getJWTClaimsSet();

        return Long.valueOf(claimsSet.getSubject());
    }

}
