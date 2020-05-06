package com.soroush;

import static com.soroush.security.AwsKmsSigner.getPublicKey;

import com.soroush.security.AwsKMSContentSigner;
import java.io.IOException;
import java.io.StringWriter;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import javax.security.auth.x500.X500Principal;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bouncycastle.openssl.jcajce.JcaPEMWriter;
import org.bouncycastle.operator.ContentSigner;
import org.bouncycastle.pkcs.PKCS10CertificationRequest;
import org.bouncycastle.pkcs.PKCS10CertificationRequestBuilder;
import org.bouncycastle.pkcs.jcajce.JcaPKCS10CertificationRequestBuilder;
import org.bouncycastle.util.io.pem.PemObject;

public class CsrCreator {

  private static final Logger LOG = LogManager.getLogger(CsrCreator.class);

  public CsrCreator() {
    //Constractor
  }

  public String create() {
    try {
      PKCS10CertificationRequestBuilder p10Builder = new JcaPKCS10CertificationRequestBuilder(
          new X500Principal("CN=Signer, O=MyOrganization, OU=MyOrganizationUnit, C=Gothenburg, L=Sweden"),
          getPublicKey()
      );
      ContentSigner contentSigner = new AwsKMSContentSigner();
      PKCS10CertificationRequest csr = p10Builder.build(contentSigner);

      PemObject pemObject = new PemObject("CERTIFICATE REQUEST", csr.getEncoded());
      StringWriter csrString = new StringWriter();
      JcaPEMWriter pemWriter = new JcaPEMWriter(csrString);
      pemWriter.writeObject(pemObject);
      pemWriter.close();
      csrString.close();
      LOG.info("CSR: {}", csrString);
      return csrString.toString();

    } catch (IOException | NoSuchAlgorithmException | InvalidKeySpecException e) {
      LOG.error("Error in csr creation process: ", e);
      return "";
    }
  }

}
