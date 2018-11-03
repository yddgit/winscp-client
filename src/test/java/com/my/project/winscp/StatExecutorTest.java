package com.my.project.winscp;

import static org.junit.Assert.*;

import org.junit.Test;

public class StatExecutorTest extends BaseTest {

	private StatExecutor ftp = new StatExecutor(winscp, workingDir, ftpConnection, "password", 120000);
	private StatExecutor sftp = new StatExecutor(winscp, workingDir, sftpConnection, "password", 120000);

	@Test
	public void testFtp1() throws Exception {
		String remotePath = "/normal.txt";
		assertTrue(ftp.exists(remotePath));
	}

	@Test
	public void testFtp2() throws Exception {
		String remotePath = "/notExists";
		assertFalse(ftp.exists(remotePath));
	}

	@Test
	public void testFtp3() throws Exception {
		String remotePath = "/emptyDir";
		assertTrue(ftp.exists(remotePath));
	}

	@Test
	public void testFtp4() throws Exception {
		String remotePath = "/directory";
		assertTrue(ftp.exists(remotePath));
	}

	@Test
	public void testSftp1() throws Exception {
		String remotePath = "/normal.txt";
		assertTrue(sftp.exists(remotePath));
	}

	@Test
	public void testSftp2() throws Exception {
		String remotePath = "/notExists";
		assertFalse(sftp.exists(remotePath));
	}

	@Test
	public void testSftp3() throws Exception {
		String remotePath = "/emptyDir";
		assertTrue(sftp.exists(remotePath));
	}

	@Test
	public void testSftp4() throws Exception {
		String remotePath = "/directory";
		assertTrue(sftp.exists(remotePath));
	}

}
