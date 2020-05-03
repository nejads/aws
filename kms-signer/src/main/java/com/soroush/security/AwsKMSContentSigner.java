package com.soroush.security;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import org.bouncycastle.asn1.x509.AlgorithmIdentifier;
import org.bouncycastle.operator.ContentSigner;
import org.bouncycastle.operator.DefaultSignatureAlgorithmIdentifierFinder;

public class AwsKMSContentSigner implements ContentSigner {

  private final ByteArrayOutputStream outputStream;
  private final AlgorithmIdentifier sigAlgId;
  private final AwsKmsSigner signer;

  public AwsKMSContentSigner() {
    this.sigAlgId = new DefaultSignatureAlgorithmIdentifierFinder().find("SHA256WITHECDSA");
    this.outputStream = new ByteArrayOutputStream();
    this.signer = AwsKmsSigner.getInstance();
  }

  @Override
  public AlgorithmIdentifier getAlgorithmIdentifier() {
    return this.sigAlgId;
  }

  @Override
  public OutputStream getOutputStream() {
    return this.outputStream;
  }

  @Override
  public byte[] getSignature() {
    byte[] signedAttributeSet = outputStream.toByteArray();
    return signer.sign(signedAttributeSet);
  }

}
