package com.my.project.winscp;

import java.io.ByteArrayInputStream;
import java.io.File;

import org.buildobjects.process.ProcBuilder;

public class KeyGen {

	String winscp;
	String workingDir;
	int timeoutMs;

	public KeyGen(String winscp, String workingDir, int timeoutMs) {
		super();
		this.winscp = winscp;
		this.workingDir = workingDir;
		this.timeoutMs = timeoutMs;
	}

	/**
	 * 将OpenSSH SSH-2 private key (old PEM format)转换成PuTTY format
	 * 因为WinSCP只支持PuTTY format: https://winscp.net/eng/docs/public_key#private
	 * @param oldKeyFilePath OpenSSH SSH-2 private key (old PEM format)
	 * @param passphrase Passphrase
	 */
	public String keygen(String oldKeyFilePath, String passphrase) {
		String newKeyFilePath = oldKeyFilePath + ".ppk";
		buildKeygen(oldKeyFilePath, newKeyFilePath, passphrase).run();
		return newKeyFilePath;
	}

	private ProcBuilder buildKeygen(String oldKeyFilePath, String newKeyFilePath, String passphrase) {
		return new ProcBuilder(winscp)
			.withWorkingDirectory(new File(workingDir))
			.withArgs("/log=winscp.log")
			.withArgs("/loglevel=0")
			.withArgs("/logsize=10M")
			.withArgs("/ini=nul")
		    .withArgs("/console")
		    .withArgs("/command")
		    .withArgs("option confirm off")
		    .withArgs("option batch continue")
		    .withArgs("option transfer binary")
		    .withArgs("/keygen")
		    .withArgs(oldKeyFilePath)
		    .withArgs("/output=" + newKeyFilePath)
		    .withArgs("exit")
		    .withTimeoutMillis(timeoutMs)
		    .withInputStream(new ByteArrayInputStream(passphrase.getBytes()));
	}

}
