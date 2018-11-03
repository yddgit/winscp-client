package com.my.project.winscp;

import java.util.ArrayList;
import java.util.List;

import com.my.project.winscp.model.Mkdir;

public class MkdirExecutor extends WinSCPExecutor<Mkdir> {

	public MkdirExecutor(String winscp, String workingDir, String connection, String password, int timeoutMs) {
		super(winscp, workingDir, connection, password, timeoutMs);
	}

	public List<MkdirEntry> mkdir(String remoteDir) throws WinSCPException {
		List<MkdirEntry> files = new ArrayList<MkdirEntry>();
		execute("mkdir " + remoteDir, Mkdir.class, list -> {
			for(Mkdir mkdir : list) {
				if(mkdir == null) {
					continue;
				}
				files.add(new MkdirEntry(mkdir));
			}
		});
		return files;
	}

	public static class MkdirEntry {
		private String filename;
		private Boolean result;
		public MkdirEntry(Mkdir mkdir) {
			this.filename = mkdir.getFilename().getValue();
			this.result = mkdir.getResult().isSuccess();
		}
		public String getFilename() {
			return filename;
		}
		public void setFilename(String filename) {
			this.filename = filename;
		}
		public Boolean getResult() {
			return result;
		}
		public void setResult(Boolean result) {
			this.result = result;
		}
		@Override
		public String toString() {
			return "MkdirEntry [filename=" + filename + ", result=" + result + "]";
		}
	}

}
