package com.my.project.winscp;

import java.util.ArrayList;
import java.util.List;

import com.my.project.winscp.model.Upload;

public class PutExecutor extends WinSCPExecutor<Upload> {

	public PutExecutor(String winscp, String workingDir, String connection, String password, int timeoutMs) {
		super(winscp, workingDir, connection, password, timeoutMs);
	}

	public List<PutEntry> put(String localFile, String remoteFile) throws WinSCPException {
		List<PutEntry> files = new ArrayList<PutEntry>();
		execute("put -preservetime -transfer=binary " + localFile + " " + remoteFile, Upload.class, list -> {
			for(Upload upload : list) {
				if(upload == null) {
					continue;
				}
				files.add(new PutEntry(upload));
			}
		});
		return files;
	}

	public static class PutEntry {
		private String filename;
		private String destination;
		private Boolean result;
		public PutEntry(Upload upload) {
			this.filename = upload.getFilename().getValue();
			this.destination = upload.getDestination().getValue();
			this.result = upload.getResult().isSuccess();
		}
		public String getFilename() {
			return filename;
		}
		public void setFilename(String filename) {
			this.filename = filename;
		}
		public String getDestination() {
			return destination;
		}
		public void setDestination(String destination) {
			this.destination = destination;
		}
		public Boolean getResult() {
			return result;
		}
		public void setResult(Boolean result) {
			this.result = result;
		}
		@Override
		public String toString() {
			return "PutEntry [filename=" + filename + ", destination=" + destination + ", result=" + result + "]";
		}
	}

}
