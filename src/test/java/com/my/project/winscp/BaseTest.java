package com.my.project.winscp;

public class BaseTest {
	String winscp = "/path/to/winscp.com";
	String workingDir = "/path/to";
	String ftpConnection = WinSCPExecutor.ftp("ftp.example.com", "username");
	String sftpConnection = WinSCPExecutor.sftp("sftp.example.com", "username", "hostKey");
	int timeout = 120000;
}
