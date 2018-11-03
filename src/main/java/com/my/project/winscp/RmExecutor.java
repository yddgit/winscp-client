package com.my.project.winscp;

import java.util.ArrayList;
import java.util.List;

import com.my.project.winscp.model.Rm;

public class RmExecutor extends WinSCPExecutor<Rm> {

	public RmExecutor(String winscp, String workingDir, String connection, String password, int timeoutMs) {
		super(winscp, workingDir, connection, password, timeoutMs);
	}

	public List<RmEntry> rm(String remotePath) throws WinSCPException {
		List<RmEntry> files = new ArrayList<RmEntry>();
		execute("rm " + remotePath, Rm.class, list -> {
			for(Rm rm : list) {
				if(rm == null) {
					continue;
				}
				files.add(new RmEntry(rm));
			}
		});
		return files;
	}

	public static class RmEntry {
		private String filename;
		private Boolean recursive;
		private Boolean result;
		public RmEntry(Rm rm) {
			this.filename = rm.getFilename().getValue();
			this.recursive = rm.isRecursive();
			this.result = rm.getResult().isSuccess();
		}
		public String getFilename() {
			return filename;
		}
		public void setFilename(String filename) {
			this.filename = filename;
		}
		public Boolean getRecursive() {
			return recursive;
		}
		public void setRecursive(Boolean recursive) {
			this.recursive = recursive;
		}
		public Boolean getResult() {
			return result;
		}
		public void setResult(Boolean result) {
			this.result = result;
		}
		@Override
		public String toString() {
			return "RmEntry [filename=" + filename + ", recursive=" + recursive + ", result=" + result + "]";
		}
	}

}
