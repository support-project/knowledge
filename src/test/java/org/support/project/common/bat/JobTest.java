package org.support.project.common.bat;

import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.MissingResourceException;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

import org.junit.AfterClass;
import org.junit.Test;

public class JobTest {

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
    }

    @Test
    public void testExecute() throws IOException, InterruptedException {
        // Javaコマンド実行
        JobResult result = BatJob.get().addCommand("java", "-version").execute();
        assertEquals(0, result.getResultCode());
        System.out.println(result.getStdout());
        
        // Javaコマンド実行（利用出来ないパラメータ）かつ、コマンドはリストで指定
        List<String> command = new ArrayList<String>();
        command.add("java");
        command.add("-hoge");
        result = BatJob.get().addCommand(command).execute();
        assertEquals(1, result.getResultCode());
        System.out.println(result.getStdout());
    }
    
    @Test
    public void testExecute2() throws IOException, InterruptedException {
        BatJob job = BatJob.get();
        job.addEnvironment("ENV_TEST", "hoge");
        job.addCommand("printenv"); // MAC/Linuxのみ?
//        job.addCommand("echo", "$ENV_TEST"); // MAC/Linuxのみ?
        JobResult result = job.execute();
        assertEquals(0, result.getResultCode());
        String out = result.getStdout();
        assertNotNull(out);
    }
    
    @Test
    public void testExecute4() throws IOException, InterruptedException, URISyntaxException {
        BatJob job = BatJob.get();
        job.setCurrentDirectory(new File("src/test/resources/org/support/project/common/bat/"));
        job.addCommand("./test.sh"); // MAC/Linuxのみ?
        job.setTimeOutMilliSecond(1000);
        JobResult result = job.execute();
        assertEquals(0, result.getResultCode());
    }
    
    @Test
    public void testExecuteTimeout() throws IOException, InterruptedException, URISyntaxException {
        BatJob job = BatJob.get();
        job.setCurrentDirectory(new File("src/test/resources/org/support/project/common/bat/"));
        job.addCommand("./test2.sh"); // MAC/Linuxのみ?
        job.setTimeOutMilliSecond(5);
        JobResult result = job.execute();
        assertEquals(-99, result.getResultCode()); // timeoutした
    }
    
    private boolean asyncEnd = false;
    @Test
    public void testExecuteAsync() throws IOException, InterruptedException, URISyntaxException {
        asyncEnd = false;
        BatListener listener = new BatListener() {
            @Override
            public void finish(JobResult result) {
                assertEquals(0, result.getResultCode()); // timeoutした
                asyncEnd = true;
            }
        };
        
        AsyncJob job = AsyncJob.get();
        job.setCurrentDirectory(new File("src/test/resources/org/support/project/common/bat/"));
        job.addCommand("./test.sh"); // MAC/Linuxのみ?
        job.addListener(listener);
        job.execute();
        while (!asyncEnd) {
            System.out.println("実行中...");
            Thread.sleep(1000);
        }
        System.out.println("終了");
        
        job.removeListener(listener);
        job.execute();
        
    }

    @Test
    public void testJavaExecute() throws Exception {
        JavaJob job = JavaJob.get();
        
        String userHome = System.getProperty("user.home");
        String m2Dir = userHome.concat("/.m2/repository/");
        
        System.out.println(m2Dir);
        
        job.addjarDir(new File(m2Dir));
        job.addClassPathDir(new File("target/test-classes"));
        job.setMainClass(JavaTestBat.class.getName());
        
        JobResult result = job.execute();
        //assertEquals(1, result.getResultCode());
        System.out.println(result.getStdout());
    }

    
    
}
