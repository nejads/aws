package com.soroush.security;

@FunctionalInterface
public interface Signer {

  byte[] sign(byte[] message);
}
