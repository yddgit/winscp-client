package com.my.project.winscp;

import java.io.File;

import org.junit.Test;

public class KeyGenTest extends BaseTest {

	private KeyGen keygen = new KeyGen(winscp, workingDir, 120000);

	@Test
	public void testKeygen() {
		keygen.keygen(workingDir + File.separator + "privateKeyFile", "passphrase");
	}

}
