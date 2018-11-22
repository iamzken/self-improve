package com.gupaoedu.vip.mongo.explorer.common.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * 加载自定义配置
 * @author tanyongde
 *
 */
@Component
@PropertySource("classpath:explorer-config.properties")
@ConfigurationProperties(prefix = "explorer")
public class ExplorerConfig {

    private String filesDepotDir;
    private String rootDir;
    private String usersRootDir;
    private String tempRootDir;
    private String publicRootDir;
    private String port;

    public String getFilesDepotDir() {
        return filesDepotDir;
    }
    public void setFilesDepotDir(String filesDepotDir) {
        this.filesDepotDir = filesDepotDir;
    }
    public String getRootDir() {
        return rootDir;
    }
    public void setRootDir(String rootDir) {
        this.rootDir = rootDir;
    }
    public String getUsersRootDir() {
        return usersRootDir;
    }
    public void setUsersRootDir(String usersRootDir) {
        this.usersRootDir = usersRootDir;
    }
    public String getTempRootDir() {
        return tempRootDir;
    }
    public void setTempRootDir(String tempRootDir) {
        this.tempRootDir = tempRootDir;
    }
    public String getPublicRootDir() {
        return publicRootDir;
    }
    public void setPublicRootDir(String publicRootDir) {
        this.publicRootDir = publicRootDir;
    }
    public String getPort() {
        return port;
    }
    public void setPort(String port) {
        this.port = port;
    }

}
