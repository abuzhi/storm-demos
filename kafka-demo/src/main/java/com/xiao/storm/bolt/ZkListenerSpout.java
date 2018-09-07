package com.xiao.storm.bolt;

import com.xiao.storm.business.Constants;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheEvent;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheListener;
import org.apache.curator.retry.RetryNTimes;
import org.apache.storm.spout.SpoutOutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichSpout;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Values;

import java.util.Map;

/**
 * Created by xiao on 2018/9/7.
 */
public class ZkListenerSpout extends BaseRichSpout {
    private SpoutOutputCollector _collector;
    private CuratorFramework curator;
    private String zkHost = null;
    private String path = null;

    private boolean isTaskChange = false;
    private boolean isTopicChange = false;

    public ZkListenerSpout(String zkHost, String path) {
        this.zkHost = zkHost;
        this.path = path;
    }

    @Override
    public void ack(Object msgId) {
    }

    @Override
    public void fail(Object msgId) {
    }

    @Override
    public void close() {
    }

    @Override
    public void activate() {
    }

    @Override
    public void deactivate() {
    }


    @Override
    public void open(Map map, TopologyContext topologyContext, SpoutOutputCollector spoutOutputCollector) {
        _collector = spoutOutputCollector;
        curator = CuratorFrameworkFactory
                .builder()
                .connectString(zkHost)
                .namespace(path)
                .retryPolicy(new RetryNTimes(1000, 1000))
                .build();
        curator.start();

        String topicPath = path + "/topic";
        String taskPath = path + "/task";

        final PathChildrenCache topicCache = new PathChildrenCache(curator, topicPath, true);
        final PathChildrenCache taskCache = new PathChildrenCache(curator, taskPath, true);

        PathChildrenCacheListener topicPathChildrenListener = new TopicPathChildrenListener();
        taskCache.getListenable().addListener(topicPathChildrenListener);
        PathChildrenCacheListener taskPathChildrenListener = new TaskPathChildrenListener();
        taskCache.getListenable().addListener(taskPathChildrenListener);

        // TODO: 2018/9/7 这里是否要加异常处理
        try {
            topicCache.start();
            taskCache.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void nextTuple() {
//        try {
//            Thread.sleep(1000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        if(isTaskChange){
            _collector.emit(Constants.ZK_TASK_STREAM,new Values("task"));
            isTaskChange = false;
        }
        if(isTopicChange){
            _collector.emit(Constants.ZK_TOPIC_STREAM,new Values("topic"));
            isTopicChange = false;
        }
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {
        outputFieldsDeclarer.declareStream(Constants.ZK_TOPIC_STREAM,new Fields(Constants.ZK_TOPIC_DATA));
        outputFieldsDeclarer.declareStream(Constants.ZK_TASK_STREAM,new Fields(Constants.ZK_TASK_DATA));
    }

    private class  TaskPathChildrenListener implements PathChildrenCacheListener {
        @Override
        public void childEvent(CuratorFramework curatorFramework, PathChildrenCacheEvent event) throws Exception {
            switch (event.getType()) {
                case CHILD_ADDED:
                case CHILD_UPDATED:
                    isTaskChange = true;

                    //节点新增或修改时会触发，执行task解析并将task添加到taskSet中
                    String configString = new String(event.getData().getData());
//                    log.info("CHILD_CHANGED. config:"+configString);
//                    TaskConfig config = mapper.readValue(configString,TaskConfig.class);
//                    if(config instanceof TaskJsonConfig){
//                        taskSet.add(new JsonTask((TaskJsonConfig)config, emiter));
//                    }else if(config instanceof TaskSqlConfig){
//                        taskSet.add(new SqlTask((TaskSqlConfig) config, emiter, this.jobConfig));
//                    }else {
//                        throw new ArmadaRuntimeException("the task config is illegal.");
//                    }
                    break;
                case CHILD_REMOVED:
                    isTaskChange = true;

                    //节点删除时会触发，执行task删除操作
                    String[] strArr = event.getData().getPath().split("/");
                    String taskId = strArr[strArr.length-1];
//                    log.info("CHILD_REMOVED. taskId:"+taskId);
//                    taskSet.remove(Integer.parseInt(taskId));
                    break;
                case CONNECTION_SUSPENDED:
//                    log.error("CONNECTION_SUSPENDED.");
                    break;
                case CONNECTION_RECONNECTED:
//                    cache.rebuild();
//                    log.error("CONNECTION_RECONNECTED.");
                    break;
                case CONNECTION_LOST:
//                    log.error("CONNECTION_LOST.");
                    break;
                default:
            }
        }
    }

    private class  TopicPathChildrenListener implements PathChildrenCacheListener {
        @Override
        public void childEvent(CuratorFramework curatorFramework, PathChildrenCacheEvent event) throws Exception {
            switch (event.getType()) {
                case CHILD_ADDED:
                case CHILD_UPDATED:
                    isTopicChange = true;

                    String configString = new String(event.getData().getData());
//                    log.info("storm topic 配置更新或添加:{}->{}", event.getData().getPath(), configString);
//                    if (StringUtils.isNotBlank(configString)) {
//                        TopicConfig config = mapper.readValue(configString, TopicConfig.class);
//                        if (null != config
//                                && StringUtils.isNotBlank(config.getTopic())
//                                && StringUtils.isNotBlank(config.getMetricFiled())
//                                && StringUtils.isNotBlank(config.getMetricPattern())) {
//                            Collection<ExecutableTask> tasks = taskSet.getTasks();
//                            for (ExecutableTask task : tasks) {
//                                if (config.getTopic().equals(task.getOutput())) {
//                                    task.setMetricFiled(config.getMetricFiled());
//                                    task.setMetricPattern(config.getMetricPattern());
//                                }
//
//                            }
//                        }
//                        if (null != config
//                                && StringUtils.isNotBlank(config.getTopic())) {
//                            if (StringUtils.isBlank(config.getMetricFiled()) || StringUtils.isBlank(config.getMetricPattern())) {
//                                for (ExecutableTask task : taskSet.getTasks()) {
//                                    if (config.getTopic().equals(task.getOutput())) {
//                                        task.setMetricFiled(null);
//                                        task.setMetricPattern(null);
//                                    }
//
//                                }
//                            }
//                        }
//                    }
                    break;
                case CHILD_REMOVED:
                    isTopicChange = true;

                    String[] strArr = event.getData().getPath().split("/");
                    String outputTopic = strArr[strArr.length - 1];
//                    log.info("storm topic delete config:" + outputTopic);
//                    if (StringUtils.isNotBlank(outputTopic)) {
//                        for (ExecutableTask task : taskSet.getTasks()) {
//                            if (outputTopic.equals(task.getOutput())) {
//                                task.setMetricPattern(null);
//                                task.setMetricFiled(null);
//                            }
//                        }
//                    }
                    break;
                case CONNECTION_SUSPENDED:
//                    log.error("CONNECTION_SUSPENDED.");
                    break;
                case CONNECTION_RECONNECTED:
//                    cache.rebuild();
//                    log.error("CONNECTION_RECONNECTED.");
                    break;
                case CONNECTION_LOST:
//                    log.error("CONNECTION_LOST.");
                    break;
                default:
            }
        }
    }
}
