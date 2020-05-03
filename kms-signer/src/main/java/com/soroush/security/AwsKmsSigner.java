package com.soroush.security;

import com.amazonaws.services.kms.AWSKMSClientBuilder;
import com.amazonaws.services.kms.model.SignRequest;
import com.amazonaws.services.kms.model.SignResult;
import com.amazonaws.services.kms.model.SigningAlgorithmSpec;
import java.nio.ByteBuffer;
import org.apache.commons.codec.digest.DigestUtils;

public class AwsKmsSigner implements Signer {

  private static AwsKmsSigner instance;

  @Override
  public byte[] sign(final byte[] message) {
    final SignResult signResult = AWSKMSClientBuilder
        .standard()
        .build()
        .sign(getSignRequest(message));
    return signResult.getSignature().array();
  }

  private static SignRequest getSignRequest(final byte[] signingInput) {
    final byte[] hashedInput = DigestUtils.sha256(signingInput);
    final ByteBuffer message = ByteBuffer.wrap(hashedInput);
    return new SignRequest()
        .withMessage(message)
        .withMessageType("DIGEST")
        .withKeyId("<KEY-ID>")
        .withSigningAlgorithm(SigningAlgorithmSpec.ECDSA_SHA_256);
  }

  public static AwsKmsSigner getInstance() {
    if (instance == null) {
      instance = new AwsKmsSigner();
    }
    return instance;
  }

}
