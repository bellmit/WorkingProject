package com.ruoyi.homewifi.utils;

import com.jcraft.jsch.*;

import java.io.*;
import java.util.Date;
import java.util.Properties;
import java.util.Vector;

public class SftpUtil {

    private ChannelSftp sftp;

    private Session session;
    /**
     * FTP 登录用户名
     */
    private String username;
    /**
     * FTP 登录密码
     */
    private String password;
    /**
     * 私钥
     */
    private String privateKey;
    /**
     * FTP 服务器地址IP地址
     */
    private String host;
    /**
     * FTP 端口
     */
    private int port;


    public SftpUtil(String username, String password, String host, int port) {
        this.username = username;
        this.password = password;
        this.host = host;
        this.port = port;
    }


    public SftpUtil(String username, String host, int port, String privateKey) {
        this.username = username;
        this.host = host;
        this.port = port;
        this.privateKey = privateKey;
    }

    public SftpUtil() {
    }

    /**
     * 连接sftp服务器
     *
     * @throws Exception
     */
    public void login() throws JSchException {
        try {
            JSch jsch = new JSch();
            if (privateKey != null) {
                jsch.addIdentity(privateKey);// 设置私钥

            }

            session = jsch.getSession(username, host, port);
            if (password != null) {
                session.setPassword(password);
            }
            Properties config = new Properties();
            config.put("StrictHostKeyChecking", "no");

            session.setConfig(config);
            session.connect();

            Channel channel = session.openChannel("sftp");
            channel.connect();
            sftp = (ChannelSftp) channel;
            System.out.println(new Date() + " login success");
        } catch (JSchException e) {
            e.printStackTrace();
            System.out.println(new Date() + " login fail");
        }
    }

    /**
     * 关闭连接 server
     */
    public void logout() {
        if (sftp != null) {
            if (sftp.isConnected()) {
                sftp.disconnect();

            }
        }
        if (session != null) {
            if (session.isConnected()) {
                session.disconnect();

            }
        }
    }

    public void upload(String directory, String sftpFileName, InputStream input) throws SftpException {
        try {
            sftp.cd(directory);
        } catch (SftpException e) {
            sftp.mkdir(directory);
            sftp.cd(directory);
        }
        sftp.put(input, sftpFileName);

    }

    /**
     * 上传单个文件
     *
     * @param directory  上传到sftp目录
     * @param uploadFile 要上传的文件,包括路径
     * @throws FileNotFoundException
     * @throws SftpException
     * @throws Exception
     */
    public void upload(String directory, String uploadFile) throws FileNotFoundException, SftpException {
        File file = new File(uploadFile);
        upload(directory, file.getName(), new FileInputStream(file));
    }

    /**
     * 将byte[]上传到sftp，作为文件。注意:从String生成byte[]是，要指定字符集。
     *
     * @param directory    上传到sftp目录
     * @param sftpFileName 文件在sftp端的命名
     * @param byteArr      要上传的字节数组
     * @throws SftpException
     * @throws Exception
     */
    public void upload(String directory, String sftpFileName, byte[] byteArr) throws SftpException {
        upload(directory, sftpFileName, new ByteArrayInputStream(byteArr));
    }

    /**
     * 将字符串按照指定的字符编码上传到sftp
     *
     * @param directory    上传到sftp目录
     * @param sftpFileName 文件在sftp端的命名
     * @param dataStr      待上传的数据
     * @param charsetName  sftp上的文件，按该字符编码保存
     * @throws UnsupportedEncodingException
     * @throws SftpException
     * @throws Exception
     */
    public void upload(String directory, String sftpFileName, String dataStr, String charsetName) throws UnsupportedEncodingException, SftpException {
        upload(directory, sftpFileName, new ByteArrayInputStream(dataStr.getBytes(charsetName)));
    }

    /**
     * 删除文件
     *
     * @param directory  要删除文件所在目录
     * @param deleteFile 要删除的文件
     * @throws SftpException
     * @throws Exception
     */
    public void delete(String directory, String deleteFile) throws SftpException {
        sftp.cd(directory);
        sftp.rm(deleteFile);
    }

    /**
     * 载单个文件
     *
     * @param directory      ：远程下载目录(以路径符号结束)
     * @param remoteFileName FTP服务器文件名称 如：xxx.txt ||xxx.txt.zip
     * @param localFile      本地文件路径 如 D:\\xxx.txt
     * @return
     */
    public void downloadFile(String directory, String remoteFileName, String localFile) {
        System.out.println(">>>>>>>>FtpUtil-->downloadFile--ftp下载文件:" + remoteFileName + "开始>>>>>>>>>>>>>");
        File file = null;
        OutputStream output = null;
        try {
            file = new File(localFile);
            if (file.exists()) {
                file.delete();
            }
            file.createNewFile();
            sftp.cd(directory);
            output = new FileOutputStream(file);
            sftp.get(remoteFileName, output);
            System.out.println("===DownloadFile:" + remoteFileName + " success from sftp.");
        } catch (IOException e) {
            // TODO Auto-generated catch block
            System.out.println("本地目录异常，请检查" + file.getPath() + e.getMessage());
            e.printStackTrace();
        } catch (SftpException e) {
            System.out.println(">>>>>>>>FtpUtil-->downloadFile--ftp下载文件失败" + directory + remoteFileName + "不存在>>>>>>>>>>>>>");
            e.printStackTrace();
        }
    }

    /**
     * 列出目录下的文件
     *
     * @param directory 要列出的目录
     * @return
     * @throws SftpException
     */
    public Vector<?> listFiles(String directory) throws SftpException {
        return sftp.ls(directory);
    }

    public static void main(String[] args) throws SftpException, IOException {
        SftpUtil sftp = new SftpUtil("admin_sttri", "T8H3aSYKj", "192.168.55.34", 22);
        try {
            sftp.login();
        } catch (JSchException e) {
            e.printStackTrace();
        }

        File file = new File("d:\\101011_TYWG_GATEWAYINFO_20180425_20180424_D_00.DAT.gz");
        InputStream is = new FileInputStream(file);
        sftp.upload("/opt/datatran", "101011_TYWG_GATEWAYINFO_20180425_20180424_D_00.DAT.gz", is);
        sftp.logout();
    }
}

