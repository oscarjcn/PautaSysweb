package br.mpeg.drosofila.util.criptografia;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author Victor Coutinho
 * @see essa classe contem metodos estáticos e retorna um valor criptografado
 */
public class GeraHash {

	public static String criptografar(String senha) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(senha.getBytes());
			BigInteger hash = new BigInteger(1, md.digest(senha.getBytes()));
			String retornaSenha = hash.toString(16);
			return retornaSenha;
		} catch (NoSuchAlgorithmException e) {
			return null;
		}

	}

}
