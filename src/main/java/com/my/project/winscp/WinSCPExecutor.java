package com.my.project.winscp;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.function.Consumer;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.buildobjects.process.ExternalProcessFailureException;
import org.buildobjects.process.ProcBuilder;
import org.buildobjects.process.ProcResult;
import org.buildobjects.process.StartupException;
import org.buildobjects.process.TimeoutException;

import com.my.project.winscp.model.Failure;
import com.my.project.winscp.model.Session;

public abstract class WinSCPExecutor<T> {

	String winscp;
	String workingDir;
	String connection;
	String password;
	int timeoutMs;

	public WinSCPExecutor(String winscp, String workingDir, String connection, String password, int timeoutMs) {
		super();
		this.winscp = winscp;
		this.workingDir = workingDir;
		this.connection = connection;
		this.password = password;
		this.timeoutMs = timeoutMs;
	}

	void execute(String command, Class<T> clazz, Consumer<List<T>> consumer) throws WinSCPException {

		String xmlLog = UUID.randomUUID().toString() + ".xml";
		String xmlLogPath = workingDir + File.separator + xmlLog;
		File xmlLogFile = new File(xmlLogPath);

		try {
			ProcResult result = build(xmlLog, connection, command).run();
			List<T> list = getOutput(xmlLogPath, clazz);
			consumer.accept(list);
			System.out.println("WinSCP ExitValue: " + result.getExitValue());
			System.out.println("WinSCP ProcString: " + result.getProcString());
		} catch (StartupException e) {
			try {
				throw new WinSCPException(getFailure(xmlLogPath, "starting an winscp process failed"), e);
			} catch (IOException | JAXBException ex) {
				throw new WinSCPException("parsing xmllog error when convert xml to session", ex);
			}
		} catch (TimeoutException e) {
			try {
				throw new WinSCPException(getFailure(xmlLogPath, "execute winscp process timeout"), e);
			} catch (IOException | JAXBException ex) {
				throw new WinSCPException("parsing xmllog error when convert xml to session", ex);
			}
		} catch (ExternalProcessFailureException e) {
			try {
				throw new WinSCPException(getFailure(xmlLogPath, "execute winscp process failed (no detail)"), e);
			} catch (IOException | JAXBException ex) {
				throw new WinSCPException("parsing xmllog error when convert xml to session", ex);
			}
		} catch (IOException | JAXBException e) {
			throw new WinSCPException("parsing xmllog error", e);
		} catch (Exception e) {
			throw new WinSCPException("execute winscp process failed", e);
		} finally {
			if(xmlLogFile.exists()) { xmlLogFile.delete(); }
		}

	}

	private ProcBuilder build(String xmlLog, String connect, String command) {
		return new ProcBuilder(winscp)
			.withWorkingDirectory(new File(workingDir))
			.withArgs("/log=winscp.log")
			.withArgs("/loglevel=0")
			.withArgs("/logsize=10M")
			.withArgs("/xmllog=" + xmlLog)
			.withArgs("/ini=nul")
		    .withArgs("/console")
		    .withArgs("/command")
		    .withArgs("option confirm off")
		    .withArgs("option batch continue")
		    .withArgs("option transfer binary")
		    .withArgs("open " + connect)
		    .withArgs(command)
		    .withArgs("exit")
		    .withTimeoutMillis(timeoutMs)
		    .withInputStream(new ByteArrayInputStream(this.password.getBytes()));
	}

	@SuppressWarnings("unchecked")
	private List<T> getOutput(String xmlLog, Class<T> clazz) throws IOException, JAXBException {
		List<T> output = new ArrayList<T>();
		if(new File(xmlLog).exists()) {
			Session session = parseSession(xmlLog);
			if(session == null || session.getGroupOrFailureOrUpload() == null) {
				return output;
			}
			for(Object o : session.getGroupOrFailureOrUpload()) {
				if(o.getClass().equals(clazz)) {
					output.add((T)o);
				}
			}
		}
		return output;
	}

	private String getFailure(String xmlLog, String defaultMessage) throws IOException, JAXBException {
		if(new File(xmlLog).exists()) {
			Session session = parseSession(xmlLog);
			if(session == null || session.getGroupOrFailureOrUpload() == null) {
				return defaultMessage;
			}
			Failure failure = null;
			for(Object o : session.getGroupOrFailureOrUpload()) {
				if(o instanceof Failure) {
					failure = (Failure)o;
					break;
				}
			}
			if(failure != null) { 
				return join(failure.getMessage());
			}
		}
		return defaultMessage;
	}

	private Session parseSession(String xmlLog) throws IOException, JAXBException {
		try (ByteArrayInputStream xml = new ByteArrayInputStream(
				Files.readAllBytes(Paths.get(xmlLog)))) {
			JAXBContext ctx = JAXBContext.newInstance(Session.class);
			Unmarshaller unmarshaller = ctx.createUnmarshaller();
			// 将XML转换为对象
			Session session = (Session)unmarshaller.unmarshal(xml);
			return session;
		}
	}

	private String join(List<String> list) {
		StringBuffer buffer = new StringBuffer();
		for(String s : list) {
			buffer.append("\n").append(s);
		}
		return buffer.length() > 0 ? buffer.substring(1) : "";
	}

	public static String ftp(String host, String user) {
		return ftp(host, -1, user);
	}

	public static String ftp(String host, int port, String user) {
		if(port < 0) port = 21;
		return "ftp://" + user + "@" + host + ":" + port;
	}

	public static String sftp(String host, String user, String hostKey) {
		return sftp(host, -1, user, hostKey);
	}

	public static String sftp(String host, int port, String user, String hostKey) {
		if(port < 0) port = 22;
		return "sftp://" + user + "@" + host + ":" + port + " -hostkey=\"\"" + hostKey + "\"\"";
	}

	public static String sftp(String host, String user, String hostKey, String privateKey, String passphrase) {
		return sftp(host, -1, user, hostKey, privateKey, passphrase);
	}

	public static String sftp(String host, int port, String user, String hostKey, String privateKey, String passphrase) {
		if(port < 0) port = 22;
		return "sftp://" + user + "@" + host + ":" + port + " -hostkey=\"\"" + hostKey + "\"\"" + " -privatekey=" + privateKey + " -passphrase=" + passphrase;
	}

}
