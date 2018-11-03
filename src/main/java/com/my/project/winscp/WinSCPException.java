package com.my.project.winscp;

/**
 * 使用WinSCP连接FTP/SFTP出现的异常
 */
public class WinSCPException extends Exception {

	private static final long serialVersionUID = 751783356930377701L;

	public WinSCPException(String message, Throwable cause) {
		super(message, cause);
	}

}
