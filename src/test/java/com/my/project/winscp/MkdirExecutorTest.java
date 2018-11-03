package com.my.project.winscp;

import org.junit.Test;

public class MkdirExecutorTest extends BaseTest {

	private MkdirExecutor ftp = new MkdirExecutor(winscp, workingDir, ftpConnection, "password", 120000);
	private MkdirExecutor sftp = new MkdirExecutor(winscp, workingDir, sftpConnection, "password", 120000);

	@Test
	public void testFtp() throws Exception {
		ftp.mkdir("/a/b/c/d");
	}

	@Test
	public void testSftp() throws Exception {
		String base = "";
		for(String s : new String[]{"a", "b", "c", "d"}) {
			base += "/" + s;
			sftp.mkdir(base);
		}
	}

}
