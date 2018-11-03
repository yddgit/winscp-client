package com.my.project.winscp;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.my.project.winscp.GetExecutor.GetEntry;
import com.my.project.winscp.LsExecutor.LsEntry;
import com.my.project.winscp.PutExecutor.PutEntry;
import com.my.project.winscp.RmExecutor.RmEntry;

public class WinSCPClient {

	public static final Boolean OS_IS_WINDOWS = System.getProperty("os.name").toLowerCase().startsWith("windows");

	private static String WINSCP;
	private static String WORKING_DIR;

	static {
		try {
			URL winscpUrl = WinSCPClient.class.getClassLoader().getResource("winscp/winscp.com");
			if(winscpUrl == null) {
				throw new FileNotFoundException("Can't found winscp/winscp.com command in classpath!");
			}
			File winscp = new File(winscpUrl.toURI().getSchemeSpecificPart());
			WINSCP = winscp.getAbsolutePath();
			WORKING_DIR = winscp.getParent();
		} catch (URISyntaxException | FileNotFoundException e) {
			e.printStackTrace();
			System.exit(-1);
		}
	}

	private String separator = "/";

	private String host;
	private int port;
	private String user;
	private String password;
	private int timeout;
	private String remotePath;

	// for SFTP
	private String hostKey;
	private String privateKey;
	private String passphrase;

	private String connection;

	// WinSCP command executor
	private GetExecutor get;
	private LsExecutor ls;
	private MvExecutor mv;
	private PutExecutor put;
	private RmExecutor rm;
	private StatExecutor stat;
	private MkdirExecutor mkdir;

    /**
     * 创建FTP连接
     * @param type 连接类型
     * @param host 主机名/IP
     * @param port 端口
     * @param user 用户名
     * @param password 密码
     * @param timeout 超时时间ms
     * @param remotePath 远程路径
     * @throws WinSCPException 
     */
    public WinSCPClient(String host, int port, String user, String password, int timeout,
			String remotePath) throws WinSCPException {
		super();
		this.host = host;
		this.port = port;
		this.user = user;
		this.password = password;
		this.timeout = timeout;
		this.remotePath = remotePath;
		this.connection = WinSCPExecutor.ftp(this.host, this.port, this.user);
		initExecutor();
		createRemoteBase();
	}

	/**
     * 创建SFTP连接(基于用户名密码的认证)
     * @param type 连接类型
     * @param host 主机名/IP
     * @param port 端口
     * @param user 用户名
     * @param password 密码
     * @param timeout 超时时间ms
     * @param remotePath 远程路径
     * @param hostKey 远程主机的hostKey
	 * @throws WinSCPException 
     */
	public WinSCPClient(String host, int port, String user, String password, int timeout,
			String remotePath, String hostKey) throws WinSCPException {
		super();
		this.host = host;
		this.port = port;
		this.user = user;
		this.password = password;
		this.timeout = timeout;
		this.remotePath = remotePath;
		this.hostKey = hostKey;
		this.connection = WinSCPExecutor.sftp(this.host, this.port, this.user, this.hostKey);
		initExecutor();
		createRemoteBase();
	}

	/**
	 * 创建SFTP连接(基于用户名私钥的认证)
     * @param type 连接类型
     * @param host 主机名/IP
     * @param port 端口
     * @param user 用户名
     * @param timeout 超时时间ms
     * @param remotePath 远程路径
	 * @param hostKey 远程主机的hostKey
	 * @param privateKey 私钥文件
	 * @param passphrase 私钥密码
	 * @throws WinSCPException 
	 */
	public WinSCPClient(String host, int port, String user, int timeout, String remotePath,
			String hostKey, String privateKey, String passphrase) throws WinSCPException {
		super();
		this.host = host;
		this.port = port;
		this.user = user;
		this.timeout = timeout;
		this.remotePath = remotePath;
		this.hostKey = hostKey;
		this.privateKey = privateKey;
		this.passphrase = passphrase;
		KeyGen keygen = new KeyGen(WINSCP, WORKING_DIR, timeout);
		String puttyFormatKeyFilePath = keygen.keygen(this.privateKey, this.passphrase);
		this.connection = WinSCPExecutor.sftp(this.host, this.port, this.user, this.hostKey, puttyFormatKeyFilePath, this.passphrase);
		initExecutor();
		createRemoteBase();
	}

    /** 初始化executor */
	private void initExecutor() {
		this.get = new GetExecutor(WINSCP, WORKING_DIR, connection, password, timeout);
		this.ls = new LsExecutor(WINSCP, WORKING_DIR, connection, password, timeout);
		this.mv = new MvExecutor(WINSCP, WORKING_DIR, connection, password, timeout);
		this.put = new PutExecutor(WINSCP, WORKING_DIR, connection, password, timeout);
		this.rm = new RmExecutor(WINSCP, WORKING_DIR, connection, password, timeout);
		this.stat = new StatExecutor(WINSCP, WORKING_DIR, connection, password, timeout);
		this.mkdir = new MkdirExecutor(WINSCP, WORKING_DIR, connection, password, timeout);
	}

	public List<LsEntry> ls(String remotePath) throws WinSCPException {
		assertNotNull(remotePath, "ls - remote path can not be null");
		return ls.ls(remoteAbsolutePath(remotePath));
	}

	public List<GetEntry> get(String localFile, String remoteFile) throws WinSCPException {
		assertNotNull(localFile, "get - local file path can not be null");
		assertNotNull(remoteFile, "get - remote file path can not be null");
		File file = new File(localFile);
		if(file.exists()) { file.delete(); }
		return get.get(localFile, remoteAbsolutePath(remoteFile));
	}

	public List<PutEntry> put(String localFile) throws WinSCPException {
		assertNotNull(localFile, "put - local file path can not be null");
		return this.put(new File(localFile), null);
	}

	public List<PutEntry> put(String localFile, String subDirectory) throws WinSCPException {
		assertNotNull(localFile, "put - local file path can not be null");
		return this.put(new File(localFile), subDirectory);
	}

	public List<PutEntry> put(File localFile) throws WinSCPException {
		assertNotNull(localFile, "put - local file path can not be null");
		return this.put(localFile, null);
	}

	public List<PutEntry> put(File localFile, String subDirectory) throws WinSCPException {
		assertNotNull(localFile, "put - local file path can not be null");
		createRemoteDir(subDirectory);
		return put.put(localFile.getAbsolutePath(), remoteAbsolutePath(subDirectory));
	}

	public boolean exists(String remoteFile) {
		assertNotNull(remoteFile, "exists - remote file path can not be null");
		return stat.exists(remoteAbsolutePath(remoteFile));
	}

	public List<RmEntry> rm(String remoteFile) throws WinSCPException {
		assertNotNull(remoteFile, "rm - remote file path can not be null");
		return rm.rm(remoteAbsolutePath(remoteFile));
	}

	public void mv(String fromPath, String toPath) throws WinSCPException {
		assertNotNull(fromPath, "mv - remote from path can not be null");
		assertNotNull(toPath, "mv - remote to path can not be null");
		mv.mv(remoteAbsolutePath(fromPath), remoteAbsolutePath(toPath));
	}

	public List<RmEntry> rmdir(String remotePath) throws WinSCPException {
		assertNotNull(remotePath, "rmdir - remote path can not be null");
		return rm.rm(remoteAbsolutePath(remotePath));
	}

	public List<PutEntry> mput(String localPath, String subDirectory) throws WinSCPException {
		assertNotNull(localPath, "mput - local path can not be null");
		return mput(new File(localPath), subDirectory);
	}

	public List<PutEntry> mput(File localPath, String subDirectory) throws WinSCPException {
		assertNotNull(localPath, "mput - local path can not be null");
		createRemoteDir(subDirectory);
		return put.put(localPath.getAbsolutePath(), remoteAbsolutePath(subDirectory, localPath.getName()));
	}

	private String remoteAbsolutePath(String... relativePaths) {
		StringBuffer buffer = new StringBuffer();
		String parent = StringUtils.stripStart(this.remotePath, this.separator);
		parent = StringUtils.stripEnd(parent, this.separator);
		if(StringUtils.isNotBlank(parent)) {
			buffer.append(this.separator).append(parent);
		}
		int parentLen = buffer.length();
		// append relative paths
		if(relativePaths != null && relativePaths.length > 0) {
			for(String relativePath : relativePaths) {
				relativePath = StringUtils.stripStart(relativePath, this.separator);
				relativePath = StringUtils.stripEnd(relativePath, this.separator);
				if(StringUtils.isNotBlank(relativePath)) {
					buffer.append(this.separator).append(relativePath);
				}
			}
		}
		// if no relative path appended
		if(buffer.length() == parentLen && parentLen > 1) {
			buffer.append(this.separator);
		}
		return buffer.toString();
	}

	private void createRemoteDir(String... relativePaths) throws WinSCPException {
		StringBuffer buffer = new StringBuffer();
		String parent = StringUtils.stripStart(this.remotePath, this.separator);
		parent = StringUtils.stripEnd(parent, this.separator);
		if(StringUtils.isNotBlank(parent)) {
			buffer.append(this.separator).append(parent);
		}
		// append relative paths
		if(relativePaths != null && relativePaths.length > 0) {
			for(String relativePath : relativePaths) {
				relativePath = StringUtils.stripStart(relativePath, this.separator);
				relativePath = StringUtils.stripEnd(relativePath, this.separator);
				if(StringUtils.isNotBlank(relativePath)) {
					buffer.append(this.separator).append(relativePath);
					mkdir.mkdir(buffer.toString());
				}
			}
		}
	}

	private void createRemoteBase() throws WinSCPException {
		StringBuffer buffer = new StringBuffer();
		String parent = StringUtils.stripStart(this.remotePath, this.separator);
		parent = StringUtils.stripEnd(parent, this.separator);
		if(StringUtils.isNotBlank(parent)) {
			buffer.append(this.separator).append(parent);
		}
		// create remote base dir
		StringBuffer path = new StringBuffer();
		for(String dir : buffer.toString().split(this.separator)) {
			if(dir != null && !dir.isEmpty()) {
				path.append(this.separator).append(dir);
				mkdir.mkdir(path.toString());
			}
		}
	}

	private void assertNotNull(Object object, String message) {
		if (object == null) {
			throw new IllegalArgumentException(message);
		}
	}

}
