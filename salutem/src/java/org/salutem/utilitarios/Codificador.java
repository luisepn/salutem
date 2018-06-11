package org.salutem.utilitarios;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Codificador {

    public static String getEncoded(String texto, String algoritmo) {
        String output = "";
        try {
            byte[] textBytes = texto.getBytes();
            MessageDigest md = MessageDigest.getInstance(algoritmo);
            md.update(textBytes);
            byte[] codigo = md.digest();
            StringBuilder hexString = new StringBuilder();
            for (int i = 0; i < codigo.length; i++) {
                String hex = Integer.toHexString(0xFF & codigo[i]);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            output = hexString.toString();
        } catch (NoSuchAlgorithmException ex) {
        }
        return output;
    }
}
