package com.my.project.winscp;

import static org.junit.Assert.*;

import org.junit.Test;

public class LsExecutorTest extends BaseTest {

	private LsExecutor ftp = new LsExecutor(winscp, workingDir, ftpConnection, "password", 120000);
	private LsExecutor sftp = new LsExecutor(winscp, workingDir, sftpConnection, "password", 120000);

	@Test
	public void testFtp1() throws Exception {
		assertEquals(ftp.ls("/").size() > 0, true);
	}

	@Test
	public void testFtp2() throws Exception {
		assertEquals(ftp.ls("/notExists").size(), 0);
	}

	@Test
	public void testFtp3() throws Exception {
		assertEquals(ftp.ls("/emptyDir").size(), 0);
	}

	@Test
	public void testSftp1() throws Exception {
		assertEquals(sftp.ls("/").size() > 0, true);
	}

	@Test
	public void testSftp2() throws Exception {
		assertEquals(sftp.ls("/notExists").size(), 0);
	}

	@Test
	public void testSftp3() throws Exception {
		assertEquals(sftp.ls("/emptyDir").size(), 0);
	}

}
