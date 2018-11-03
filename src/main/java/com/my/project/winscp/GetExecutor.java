package com.my.project.winscp;

import java.util.ArrayList;
import java.util.List;

import com.my.project.winscp.model.Download;

public class GetExecutor extends WinSCPExecutor<Download> {


	public GetExecutor(String winscp, String workingDir, String connection, String password, int timeoutMs) {
		super(winscp, workingDir, connection, password, timeoutMs);
	}

	public List<GetEntry> get(String localFile, String remoteFile) throws WinSCPException {
		List<GetEntry> files = new ArrayList<GetEntry>();
		execute("get -preservetime -transfer=binary " + remoteFile + " " + localFile, Download.class, list -> {
			for(Download download : list) {
				if(download == null) {
					continue;
				}
				files.add(new GetEntry(download));
			}
		});
		return files;
	}

	public static class GetEntry {
		private String filename;
		private String destination;
		private Boolean result;
		public GetEntry(Download download) {
			this.filename = download.getFilename().getValue();
			this.destination = download.getDestination().getValue();
			this.result = download.getResult().isSuccess();
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
			return "GetEntry [filename=" + filename + ", destination=" + destination + ", result=" + result + "]";
		}
	}
}
