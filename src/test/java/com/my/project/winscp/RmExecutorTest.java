package com.my.project.winscp;

import static org.junit.Assert.*;

import org.junit.Test;

public class RmExecutorTest extends BaseTest {

	private RmExecutor ftp = new RmExecutor(winscp, workingDir, ftpConnection, "password", 120000);
	private RmExecutor sftp = new RmExecutor(winscp, workingDir, sftpConnection, "password", 120000);

	@Test
	public void testFtp1() throws Exception {
		String remotePath = "/normal.txt";
		assertEquals(ftp.rm(remotePath).size(), 1);
	}

	@Test(expected = WinSCPException.class)
	public void testFtp2() throws Exception {
		String remotePath = "/notExists";
		ftp.rm(remotePath);
		fail("Can't get attributes of file '/notExists'.");
	}

	@Test
	public void testFtp3() throws Exception {
		String remotePath = "/emptyDir";
		assertEquals(ftp.rm(remotePath).size(), 1);
	}

	@Test
	public void testFtp4() throws Exception {
		String remotePath = "/directory";
		assertEquals(ftp.rm(remotePath).size(), 2);
	}

	@Test
	public void testSftp1() throws Exception {
		String remotePath = "/normal.txt";
		assertEquals(sftp.rm(remotePath).size(), 1);
	}

	@Test(expected = WinSCPException.class)
	public void testSftp2() throws Exception {
		String remotePath = "/notExists";
		sftp.rm(remotePath);
		fail("Can't get attributes of file '/notExists'.");
	}

	@Test
	public void testSftp3() throws Exception {
		String remotePath = "/emptyDir";
		assertEquals(sftp.rm(remotePath).size(), 1);
	}

	@Test
	public void testSftp4() throws Exception {
		String remotePath = "/directory";
		assertEquals(sftp.rm(remotePath).size(), 2);
	}

}
