package com.youedata.dfs;

import com.github.tobato.fastdfs.FdfsClientConfig;

import org.apache.coyote.http11.AbstractHttp11Protocol;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.embedded.tomcat.TomcatConnectorCustomizer;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableMBeanExport;
import org.springframework.context.annotation.Import;
import org.springframework.jmx.support.RegistrationPolicy;


@ServletComponentScan
//导入FdfsClient
@Import(FdfsClientConfig.class)
//解决jmx注册冲突
@EnableMBeanExport(registration = RegistrationPolicy.IGNORE_EXISTING)
@SpringBootApplication
public class RenewUploadApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(RenewUploadApplication.class, args);
	}
	
	@Bean
    public ServletWebServerFactory tomcatEmbedded() {
 
        TomcatServletWebServerFactory tomcat = new TomcatServletWebServerFactory();
 
        tomcat.addConnectorCustomizers((TomcatConnectorCustomizer) connector -> {
 
            // connector other settings...
            // configure maxSwallowSize
            if ((connector.getProtocolHandler() instanceof AbstractHttp11Protocol<?>)) {
                // -1 means unlimited, accept bytes
                ((AbstractHttp11Protocol<?>)
                        connector.getProtocolHandler()).setMaxSwallowSize(-1);
            }
        });
        return tomcat;
    }
}
