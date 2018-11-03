package com.my.project.winscp;

import static org.junit.Assert.*;

import java.io.File;

import org.junit.Test;

public class GetExecutorTest extends BaseTest {

	private GetExecutor ftp = new GetExecutor(winscp, workingDir, ftpConnection, "password", 120000);
	private GetExecutor sftp = new GetExecutor(winscp, workingDir, sftpConnection, "password", 120000);

	@Test
	public void testFtp1() throws Exception {
		String remoteFile = "/normal.txt";
		String localFile = workingDir + File.separator + "normal.txt";
		ftp.get(localFile, remoteFile);
		assertTrue(new File(localFile).exists());
	}

	@Test(expected = WinSCPException.class)
	public void testFtp2() throws Exception {
		String remoteFile = "/notExists";
		String localFile = workingDir + File.separator + "notExists";
		ftp.get(localFile, remoteFile);
		fail("Can't get attributes of file '/notExists'.");
	}

	@Test
	public void testFtp3() throws Exception {
		String remoteFile = "/emptyDir";
		String localFile = workingDir + File.separator + "emptyDir";
		ftp.get(localFile, remoteFile);
		File local = new File(localFile);
		assertTrue(local.exists() && local.isDirectory() && local.list().length == 0);
	}

	@Test
	public void testFtp4() throws Exception {
		String remoteFile = "/directory";
		String localFile = workingDir + File.separator + "directory";
		ftp.get(localFile, remoteFile);
		assertTrue(new File(localFile).exists() && new File(localFile).isDirectory());
	}

	@Test
	public void testSftp1() throws Exception {
		String remoteFile = "/normal.txt";
		String localFile = workingDir + File.separator + "normal.txt";
		sftp.get(localFile, remoteFile);
		assertTrue(new File(localFile).exists());
	}

	@Test(expected = WinSCPException.class)
	public void testSftp2() throws Exception {
		String remoteFile = "/notExists";
		String localFile = workingDir + File.separator + "notExists";
		sftp.get(localFile, remoteFile);
		fail("Can't get attributes of file '/notExists'.");
	}

	@Test
	public void testSftp3() throws Exception {
		String remoteFile = "/emptyDir";
		String localFile = workingDir + File.separator + "emptyDir";
		sftp.get(localFile, remoteFile);
		File local = new File(localFile);
		assertTrue(local.exists() && local.isDirectory() && local.list().length == 0);
	}
	
	@Test
	public void testSftp4() throws Exception {
		String remoteFile = "/directory";
		String localFile = workingDir + File.separator + "directory";
		sftp.get(localFile, remoteFile);
		assertTrue(new File(localFile).exists() && new File(localFile).isDirectory());
	}

}
