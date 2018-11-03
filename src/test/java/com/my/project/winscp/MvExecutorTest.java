package com.my.project.winscp;

import static org.junit.Assert.*;

import org.junit.Test;

public class MvExecutorTest extends BaseTest {

	private MvExecutor ftp = new MvExecutor(winscp, workingDir, ftpConnection, "password", 120000);
	private MvExecutor sftp = new MvExecutor(winscp, workingDir, sftpConnection, "password", 120000);

	@Test
	public void testFtp1() throws Exception {
		String fromPath = "/normal.txt";
		String toPath = "/normal.txt.bak";
		assertEquals(ftp.mv(fromPath, toPath).size(), 1);
	}

	@Test(expected = WinSCPException.class)
	public void testFtp2() throws Exception {
		String fromPath = "/notExists";
		String toPath = "/notExists.bak";
		ftp.mv(fromPath, toPath);
		fail("Can't get attributes of file '/notExists'.");
	}

	@Test
	public void testFtp3() throws Exception {
		String fromPath = "/emptyDir";
		String toPath = "/emptyDirBak";
		assertEquals(ftp.mv(fromPath, toPath).size(), 1);
	}

	@Test
	public void testFtp4() throws Exception {
		String fromPath = "/directory";
		String toPath = "/directoryBak";
		assertEquals(ftp.mv(fromPath, toPath).size(), 2);
	}

	@Test
	public void testSftp1() throws Exception {
		String fromPath = "/normal.txt";
		String toPath = "/normal.txt.bak";
		assertEquals(sftp.mv(fromPath, toPath).size(), 1);
	}

	@Test(expected = WinSCPException.class)
	public void testSftp2() throws Exception {
		String fromPath = "/notExists";
		String toPath = "/notExists.bak";
		sftp.mv(fromPath, toPath);
		fail("Can't get attributes of file '/notExists'.");
	}

	@Test
	public void testSftp3() throws Exception {
		String fromPath = "/emptyDir";
		String toPath = "/emptyDirBak";
		assertEquals(sftp.mv(fromPath, toPath).size(), 1);
	}

	@Test
	public void testSftp4() throws Exception {
		String fromPath = "/directory";
		String toPath = "/directoryBak";
		assertEquals(sftp.mv(fromPath, toPath).size(), 2);
	}

}
