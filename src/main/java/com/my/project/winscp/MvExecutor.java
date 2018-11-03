package com.my.project.winscp;

import java.util.ArrayList;
import java.util.List;

import com.my.project.winscp.model.Mv;

public class MvExecutor extends WinSCPExecutor<Mv> {

	public MvExecutor(String winscp, String workingDir, String connection, String password, int timeoutMs) {
		super(winscp, workingDir, connection, password, timeoutMs);
	}

	public List<MvEntry> mv(String fromPath, String toPath) throws WinSCPException {
		List<MvEntry> files = new ArrayList<MvEntry>();
		execute("mv " + fromPath + " " + toPath, Mv.class, list -> {
			for(Mv mv : list) {
				if(mv == null) {
					continue;
				}
				files.add(new MvEntry(mv));
			}
		});
		return files;
	}

	public static class MvEntry {
		private String filename;
		private String destination;
		private Boolean result;
		public MvEntry(Mv mv) {
			this.filename = mv.getFilename().getValue();
			this.destination = mv.getDestination().getValue();
			this.result = mv.getResult().isSuccess();
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
			return "MvEntry [filename=" + filename + ", destination=" + destination + ", result=" + result + "]";
		}
	}

}
