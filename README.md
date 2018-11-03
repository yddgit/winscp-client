# winscp-client
WinSCP client with Java

## Download WinSCP First

Please download WinSCP [**Portable executables**] from:
https://winscp.net/eng/downloads.php

Then extract the downloaded file to src/main/resources/winscp

## Use This Client

Use `com.my.project.winscp.WinSCPClient` class in your source code which include all supported operations for FTP/SFTP

## WinSCP Model Generate

Use xjc command generate models:

```bash
xjc -d src/main/java -p com.my.project.winscp.model -encoding UTF-8 -verbose https://winscp.net/schema/session/1.0
```

## WinSCP Documentation References

- [Command-line Options](https://winscp.net/eng/docs/commandline "Command-line Options")

- [Scripting and Task Automation](https://winscp.net/eng/docs/scripting "Scripting and Task Automation")