package com.soroush.security;

import com.amazonaws.services.kms.AWSKMSClientBuilder;
import com.amazonaws.services.kms.model.GetPublicKeyRequest;
import com.amazonaws.services.kms.model.SignRequest;
import com.amazonaws.services.kms.model.SignResult;
import com.amazonaws.services.kms.model.SigningAlgorithmSpec;
import java.nio.ByteBuffer;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import org.apache.commons.codec.digest.DigestUtils;

public class AwsKmsSigner implements Signer {

  private static AwsKmsSigner instance;
  private static final String KEY_ID = "YOUR_KEY_ID";

  @Override
  public byte[] sign(final byte[] message) {
    final SignResult signResult = AWSKMSClientBuilder
        .standard()
        .build()
        .sign(getSignRequest(message));
    return signResult.getSignature().array();
  }

  private SignRequest getSignRequest(final byte[] signingInput) {
    final byte[] hashedInput = DigestUtils.sha256(signingInput);
    final ByteBuffer message = ByteBuffer.wrap(hashedInput);
    return new SignRequest()
        .withMessage(message)
        .withMessageType("DIGEST")
        .withKeyId(KEY_ID)
        .withSigningAlgorithm(SigningAlgorithmSpec.ECDSA_SHA_256);
  }

  private static byte[] getPublicKeyBytes() {
    return AWSKMSClientBuilder
        .standard()
        .build()
        .getPublicKey(new GetPublicKeyRequest().withKeyId(KEY_ID))
        .getPublicKey()
        .array();
  }


  public static PublicKey getPublicKey() throws NoSuchAlgorithmException, InvalidKeySpecException {
    X509EncodedKeySpec pubKeySpec = new X509EncodedKeySpec(getPublicKeyBytes());
    return KeyFactory.getInstance("EC").generatePublic(pubKeySpec);
  }

  public static AwsKmsSigner getInstance() {
    if (instance == null) {
      instance = new AwsKmsSigner();
    }
    return instance;
  }

}
