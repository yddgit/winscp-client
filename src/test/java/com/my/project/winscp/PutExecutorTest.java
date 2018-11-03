package com.my.project.winscp;

import static org.junit.Assert.*;

import java.io.File;

import org.junit.Test;

public class PutExecutorTest extends BaseTest {

	private PutExecutor ftp = new PutExecutor(winscp, workingDir, ftpConnection, "password", 120000);
	private PutExecutor sftp = new PutExecutor(winscp, workingDir, sftpConnection, "password", 120000);

	@Test
	public void testFtp1() throws Exception {
		String remoteFile = "/normal.txt";
		String localFile = workingDir + File.separator + "upload" + File.separator + "normal.txt";
		ftp.put(localFile, remoteFile);
		assertTrue(new File(localFile).exists());
	}

	@Test
	public void testFtp2() throws Exception {
		String remoteFile = "/notExists";
		String localFile = workingDir + File.separator + "upload" + File.separator + "notExists";
		assertEquals(ftp.put(localFile, remoteFile).size(), 0);
	}

	@Test
	public void testFtp3() throws Exception {
		String remoteFile = "/emptyDir";
		String localFile = workingDir + File.separator + "upload" + File.separator + "emptyDir";
		ftp.put(localFile, remoteFile);
		File local = new File(localFile);
		assertTrue(local.exists() && local.isDirectory() && local.list().length == 0);
	}

	@Test
	public void testFtp4() throws Exception {
		String remoteFile = "/directory";
		String localFile = workingDir + File.separator + "upload" + File.separator + "directory";
		ftp.put(localFile, remoteFile);
		assertTrue(new File(localFile).exists() && new File(localFile).isDirectory());
	}

	@Test
	public void testFtp5() throws Exception {
		String remoteFile = "/empty.txt";
		String localFile = workingDir + File.separator + "upload" + File.separator + "empty.txt";
		ftp.put(localFile, remoteFile);
		assertTrue(new File(localFile).exists());
	}

	@Test
	public void testSftp1() throws Exception {
		String remoteFile = "/normal.txt";
		String localFile = workingDir + File.separator + "upload" + File.separator + "normal.txt";
		sftp.put(localFile, remoteFile);
		assertTrue(new File(localFile).exists());
	}

	@Test
	public void testSftp2() throws Exception {
		String remoteFile = "/notExists";
		String localFile = workingDir + File.separator + "upload" + File.separator + "notExists";
		assertEquals(sftp.put(localFile, remoteFile).size(), 0);
	}

	@Test
	public void testSftp3() throws Exception {
		String remoteFile = "/emptyDir";
		String localFile = workingDir + File.separator + "upload" + File.separator + "emptyDir";
		sftp.put(localFile, remoteFile);
		File local = new File(localFile);
		assertTrue(local.exists() && local.isDirectory() && local.list().length == 0);
	}
	
	@Test
	public void testSftp4() throws Exception {
		String remoteFile = "/directory";
		String localFile = workingDir + File.separator + "upload" + File.separator + "directory";
		sftp.put(localFile, remoteFile);
		assertTrue(new File(localFile).exists() && new File(localFile).isDirectory());
	}

}
