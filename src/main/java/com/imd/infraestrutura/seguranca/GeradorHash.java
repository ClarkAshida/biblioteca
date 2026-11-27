package com.imd.infraestrutura.seguranca;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

public final class GeradorHash {

    private GeradorHash() {
    }

    /** Devolve "salt:hash", os dois em Base64. */
    public static String gerar(String senha) {
        byte[] salt = new byte[16];
        new SecureRandom().nextBytes(salt);
        return Base64.getEncoder().encodeToString(salt) + ":" + calcular(senha, salt);
    }

    public static boolean verificar(String senha, String armazenado) {
        String[] partes = armazenado.split(":");
        if (partes.length != 2) {
            return false;
        }
        byte[] salt = Base64.getDecoder().decode(partes[0]);
        return calcular(senha, salt).equals(partes[1]);
    }

    private static String calcular(String senha, byte[] salt) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(salt);
            byte[] hash = md.digest(senha.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(hash);
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("SHA-256 indisponível.", e);
        }
    }
}
