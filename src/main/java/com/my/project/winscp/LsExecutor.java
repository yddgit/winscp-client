package com.my.project.winscp;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.my.project.winscp.model.Ls;

public class LsExecutor extends WinSCPExecutor<Ls> {

	public LsExecutor(String winscp, String workingDir, String connection, String password, int timeoutMs) {
		super(winscp, workingDir, connection, password, timeoutMs);
	}

	public List<LsEntry> ls(String dir) throws WinSCPException {

		List<LsEntry> files = new ArrayList<LsEntry>();
		execute("ls " + dir, Ls.class, list -> {
			if(list != null && list.size() > 0) {
				Ls ls = list.get(0);
				if(ls == null || !ls.getResult().isSuccess() || ls.getFiles() == null || ls.getFiles().getFile() == null) {
					return;
				}
				String filename = null;
				for(com.my.project.winscp.model.File f : ls.getFiles().getFile()) {
					filename = f.getFilename().getValue();
					if(".".equals(filename) || "..".equals(filename)) {
						continue;
					}
					files.add(new LsEntry(f));
				}
			}
		});
		return files;
	}

	public static class LsEntry {
		private String filename;
		private Boolean directory;
		private Integer size;
		private Date modification;

		private LsEntry(com.my.project.winscp.model.File file) {
			this.filename = file.getFilename().getValue();
			this.directory = "d".equalsIgnoreCase(file.getType().getValue());
			this.size = file.getSize() == null ? 0 : file.getSize().getValue().intValue();
			this.modification = file.getModification() == null ? null : file.getModification().getValue().toGregorianCalendar().getTime();
		}

		public String getFilename() {
			return filename;
		}
		public void setFilename(String filename) {
			this.filename = filename;
		}
		public Boolean getDirectory() {
			return directory;
		}
		public void setDirectory(Boolean directory) {
			this.directory = directory;
		}
		public Integer getSize() {
			return size;
		}
		public void setSize(Integer size) {
			this.size = size;
		}
		public Date getModification() {
			return modification;
		}
		public void setModification(Date modification) {
			this.modification = modification;
		}
		@Override
		public String toString() {
			return "LsEntry [filename=" + filename + ", directory=" + directory + ", size=" + size + ", modification="
					+ modification + "]";
		}
	}

}
