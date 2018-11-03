package com.my.project.winscp;

import java.util.ArrayList;
import java.util.List;

import com.my.project.winscp.model.Stat;

public class StatExecutor extends WinSCPExecutor<Stat> {

	public StatExecutor(String winscp, String workingDir, String connection, String password, int timeoutMs) {
		super(winscp, workingDir, connection, password, timeoutMs);
	}

	public boolean exists(String remotePath) {
		List<StatEntry> files = new ArrayList<StatEntry>();
		try {
			execute("stat " + remotePath, Stat.class, list -> {
				for(Stat stat : list) {
					if(stat == null) {
						continue;
					}
					files.add(new StatEntry(stat));
				}
			});
		} catch (WinSCPException e) { }
		return files.size() > 0;
	}

	public static class StatEntry {
		private String filename;
		private Boolean result;
		public StatEntry(Stat stat) {
			this.filename = stat.getFilename().getValue();
			this.result = stat.getResult().isSuccess();
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
			return "StatEntry [filename=" + filename + ", result=" + result + "]";
		}
	}

}
