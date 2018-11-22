package com.gupao.course.demo.zk;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * 腾讯课堂搜索 咕泡学院
 * 加群获取视频：608583947
 * 风骚的Michael 老师
 */
public class DistributedLock implements Watcher{

    private static final String GROUP_PATH="/locks"; //分组路径

    private static final String SUB_PATH="/lokcs/sub"; //子路径

    private static final String CONNECTION_STRING="101.37.81.107:2181"; //连接字符串

    private static final int SESSION_TIMEOUT = 10000; //session过期时间

    private ZooKeeper zk=null;

    private static final int THREAD_NUM=10;  //争夺锁的线程数

    private String threadName; //名称

    private String currentPath;  //当前路径

    private String waitPath;  //等待路径

    //确保连接zk成功；
    private CountDownLatch connectedSemaphore = new CountDownLatch(1); //确保zk连接成功

    private static final CountDownLatch threadSemaphore=new CountDownLatch(THREAD_NUM);

    public DistributedLock(int threadId) {
        this.threadName="[第"+threadId+"个线程]";
    }

    public static void main(String[] args) {
        for(int i=0;i<THREAD_NUM;i++){
            final int threadId=i+1;
            new Thread(()->{
                DistributedLock dl=new DistributedLock(threadId);
                try {
                    dl.createConnection(CONNECTION_STRING,SESSION_TIMEOUT);
                    synchronized (threadSemaphore) {
                        dl.createPath(GROUP_PATH, "线程" + threadId, true);
                    }
                    dl.getLock();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }).start();
        }
        try{
            threadSemaphore.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 创建连接
     * @param connectString  连接字符串
     * @param sessionTimeout 超时时间
     */
    private void createConnection(String connectString,int sessionTimeout) throws IOException, InterruptedException {
        zk=new ZooKeeper(connectString,sessionTimeout,this);
        connectedSemaphore.await();
    }

    /**
     * 创建节点
     * @param path
     * @param data
     * @param needWatch
     * @return
     * @throws KeeperException
     * @throws InterruptedException
     */
    private boolean createPath(String path,String data,boolean needWatch) throws KeeperException, InterruptedException {
        if(zk.exists(path,needWatch)==null){
            String rs=this.zk.create(path,data.getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE,CreateMode.PERSISTENT);
            System.out.println(threadName+" 节点创建成功,path:"+rs+";data:"+data);
        }
        return true;
    }

    private void getLock() throws KeeperException, InterruptedException {
        currentPath=zk.create(SUB_PATH,null, ZooDefs.Ids.OPEN_ACL_UNSAFE,CreateMode.EPHEMERAL_SEQUENTIAL);
        System.out.println(threadName+"-创建节点:"+currentPath);
        if(checkMinPath()){
            getLockSuccess();
        }
    }

    public boolean checkMinPath() throws KeeperException, InterruptedException {
        List<String> subNodes=zk.getChildren(GROUP_PATH,false);
        Collections.sort(subNodes);
        int index=subNodes.indexOf(currentPath.substring(GROUP_PATH.length()+1));
        switch(index){
            case -1:
                System.out.println(threadName+"-节点不存在..."+currentPath);
                return false;
            case 0:
                System.out.println(threadName+"-当前节点为最大节点..."+currentPath);
                return true;
            default:
                this.waitPath=GROUP_PATH+"/"+subNodes.get(index-1);
                System.out.println(threadName+"-排在我前面的节点是:"+waitPath);
                try {
                    zk.getData(waitPath, true, new Stat());
                    return false;
                }catch(KeeperException e){
                    if(zk.exists(waitPath,false)==null){
                        System.out.println(threadName+"-排在我前面的节点+"+waitPath+"+因异常消失");
                        return checkMinPath();
                    }else{
                        throw e;
                    }
                }
        }
    }

    private void getLockSuccess() throws KeeperException, InterruptedException {
        if(zk.exists(this.currentPath,false)==null){
            System.out.println(threadName+"-节点已不存在");
            return;
        }
        System.out.println(threadName+"-获取锁成功");
        Thread.sleep(2000);
        System.out.println(threadName+"-删除当前节点");
        zk.delete(this.currentPath,-1);

        releaseConnection();
        threadSemaphore.countDown();
    }

    public void releaseConnection(){
        if(this.zk!=null){
            try {
                this.zk.close();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println(threadName+"-释放连接");
    }


    @Override
    public void process(WatchedEvent watchedEvent) {
        if(watchedEvent == null){
            return;
        }
        Event.KeeperState keeperState = watchedEvent.getState();
        Event.EventType eventType = watchedEvent.getType();
        if ( Event.KeeperState.SyncConnected == keeperState) {
            if ( Event.EventType.None == eventType ) {
                System.out.println(threadName+"-成功连接上ZK服务器");
                connectedSemaphore.countDown();
            }else if (watchedEvent.getType() == Event.EventType.NodeDeleted && watchedEvent.getPath().equals(waitPath)) {
                System.out.println(threadName+"-收到情报，排我前面的家伙已挂，我是不是可以出山了？");
                try {
                    if(checkMinPath()){
                        getLockSuccess();
                    }
                } catch (KeeperException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }else if ( Event.KeeperState.Disconnected == keeperState ) {
            System.out.println(threadName+"-与ZK服务器断开连接");

        } else if ( Event.KeeperState.AuthFailed == keeperState ) {
            System.out.println(threadName+"-权限检查失败");

        } else if ( Event.KeeperState.Expired == keeperState ) {
            System.out.println(threadName+"-会话失效");

        }
    }
}
