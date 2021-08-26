package client.demo.service;

/**
 * @author Qiuxinchao
 * @version 1.0
 * @date 2021/8/26 15:52
 * @describe visit_log 接口
 */
public interface VisitLogService {

    public int insert(long hostId,String url,String status);

}
