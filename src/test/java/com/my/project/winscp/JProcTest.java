package com.my.project.winscp;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.buildobjects.process.ExternalProcessFailureException;
import org.buildobjects.process.ProcBuilder;
import org.buildobjects.process.ProcResult;
import org.buildobjects.process.StreamConsumer;
import org.buildobjects.process.TimeoutException;
import org.junit.Test;

public class JProcTest {

	public String bin = "/path/to/git/install/folder/usr/bin/";
	public String bash = "/path/to/git/install/folder/bin/bash";

	/** 基本使用 */
	@Test
	public void testExec01() {
		String output = ProcBuilder.run(bin + "echo", "Hello World!");
		assertEquals("Hello World!\n", output);
	}

	/** 使用命令过滤字符串 */
	@Test
	public void testExec02() {
		String output = ProcBuilder.filter("x y z", bin + "sed", "s/y/a/");
		assertEquals("x a z", output.trim());
	}

	/** 获取命令输出 */
	@Test
	public void testExec03() {
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		new ProcBuilder(bin + "echo")
		    .withArg("Hello World!")
		    .withOutputStream(output)
		    .run();
		assertEquals("Hello World!\n", output.toString());
	}

	/** 使用输入流做为命令输入 */
	@Test
	public void testExec04() {
		ByteArrayInputStream input = new ByteArrayInputStream("Hello cruel World".getBytes());
		ProcResult result = new ProcBuilder(bin + "wc")
		    .withArgs("-w")
		    .withInputStream(input).run();
		assertEquals("3", result.getOutputString().trim());
	}

	/** 从执行结果中获取执行结果、命令返回值和完整命令行 */
	@Test
	public void testExec05() {
		ProcResult result = new ProcBuilder(bin + "echo")
			.withArg("Hello World!")
			.run();
		assertEquals("Hello World!\n", result.getOutputString());
		assertEquals(0, result.getExitValue());
		assertEquals(bin + "echo \"Hello World!\"", result.getProcString());
	}

	/** 设置环境变量 */
	@Test
	public void testExec06() {
		ProcResult result = new ProcBuilder(bash)
			.withArgs("-c", "echo $MYVAR")
			.withVar("MYVAR", "my value").run();
		assertEquals("my value\n", result.getOutputString());
		assertEquals(bash + " -c \"echo $MYVAR\"", result.getProcString());
	}

	/** 设置workingDirectory */
	@Test
	public void testExec07() {
		ProcResult result = new ProcBuilder(bin + "pwd")
			.withWorkingDirectory(new File("/"))
			.run();
		assertEquals("/d\n", result.getOutputString());
	}

	/** 超时异常, 默认5s */
	@Test
	public void testExec08() {
		ProcBuilder builder = new ProcBuilder(bin + "sleep")
		    .withArg("2")
		    .withTimeoutMillis(1000);
		try {
		    builder.run();
		    fail("Should time out");
		} catch (TimeoutException ex) {
		    assertEquals("Process '" + bin + "sleep 2' timed out after 1000ms.", ex.getMessage());
		}
	}

	/** 获取程序执行时间 */
	@Test
	public void testExec09() {
		ProcResult result = new ProcBuilder(bin + "sleep")
			.withArg("0.5")
			.run();
		assertTrue(result.getExecutionTime() > 500 && result.getExecutionTime() < 1000);
	}

	/** 禁用超时 */
	@Test
	public void testExec10() {
		ProcBuilder builder = new ProcBuilder(bin + "sleep")
		    .withArg("7")
		    .withNoTimeout();
		ProcResult result = builder.run();
		assertEquals(result.getExecutionTime(), 7000, 500);
	}

	/** 命令返回值大于0时, 抛出异常 */
	@Test
	public void testExec11() {
		ProcBuilder builder = new ProcBuilder(bin + "ls")
		    .withArg("xyz");
		try {
		    builder.run();
		    fail("Should throw exception");
		} catch (ExternalProcessFailureException ex) {
		    assertEquals("No such file or directory", ex.getStderr().split("\\:")[2].trim());
		    assertTrue(ex.getExitValue() > 0);
		    assertEquals(bin + "ls xyz", ex.getCommand());
		    assertTrue(ex.getTime() > 0);
		}
	}

	/** 忽略命令返回值, 大于0的情况 */
	@Test
	public void testExec12() {
		try {
		    ProcResult result = new ProcBuilder(bash)
		        .withArgs("-c", "echo Hello World!;exit 100")
		        .ignoreExitStatus()
		        .run();

		    assertEquals("Hello World!\n", result.getOutputString());
		    assertEquals(100, result.getExitValue());
		} catch (ExternalProcessFailureException ex) {
		    fail("A process started with ignoreExitStatus should not throw an exception");
		}
	}

	/** 指定预期的命令返回值也以避免抛出异常 */
	@Test
	public void testExec13() {
		try {
		    ProcResult result = new ProcBuilder(bash)
		        .withArgs("-c", "echo Hello World!;exit 100")
		        .withExpectedExitStatuses(0, 100)
		        .run();

		    assertEquals("Hello World!\n", result.getOutputString());
		    assertEquals(100, result.getExitValue());
		} catch (ExternalProcessFailureException ex) {
		    fail("An expected exit status should not lead to an exception");
		}
	}

	/** 返回值不在预期的命令返回值列表里, 仍然会抛出异常 */
	@Test
	public void testExec14() {
		try {
		    new ProcBuilder(bash)
		        .withArgs("-c", "echo Hello World!;exit 99")
		        .withExpectedExitStatuses(0, 100)
		        .run();

		    fail("An exit status that is not part of the expectedExitStatuses should throw");
		} catch (ExternalProcessFailureException ex) {
		    assertEquals(99, ex.getExitValue());
		}
	}

	/** 输入输出也可以是字节数组 */
	@Test
	public void testExec15() {
		int MEGA = 1024 * 1024;
		byte[] data = new byte[4 * MEGA];
		for (int i = 0; i < data.length; i++) {
		    data[i] = (byte) Math.round(Math.random() * 255 - 128);
		}

		ProcResult result = new ProcBuilder(bin + "gzip")
		    .withInputStream(new ByteArrayInputStream(data))
		    .run();

		assertTrue(result.getOutputBytes().length > 2 * MEGA);
	}

	/** 使用管道 */
	@Test
	public void testExec16() {
		new ProcBuilder("echo")
	    .withArgs("line1\nline2")
	    .withOutputConsumer(new StreamConsumer() {
	        public void consume(InputStream stream) throws IOException {
	            BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
	            assertEquals("line1", reader.readLine());
	            assertEquals("line2", reader.readLine());
	            assertNull(reader.readLine());
	        }
	    })
	    .withTimeoutMillis(2000)
	    .run();
	}

	/** 标准错误输出 */
	@Test
	public void testExec17() {
		ProcResult result = new ProcBuilder(bash)
		    .withArgs("-c", ">&2 echo error;>&2 echo error2; echo out;echo out2")
		    .run();

		assertEquals("out\nout2\n", result.getOutputString());
		assertEquals("error\nerror2\n", result.getErrorString());
	}

	/** 也可以使用输入输出流来接收标准输出 */
	@Test
	public void testExec18() {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		ByteArrayOutputStream err = new ByteArrayOutputStream();
		new ProcBuilder(bash)
		    .withArgs("-c", ">&2 echo error;>&2 echo error2; echo out;echo out2")
		    .withOutputStream(out)
		    .withErrorStream(err)
		    .run();

		assertEquals("out\nout2\n", out.toString());
		assertEquals("error\nerror2\n", err.toString());
	}

}
