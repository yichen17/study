回调内容：
    biz.callback.queue为回调topic，消费模式使用手动ACK。
        消息内容必须包含：product，app_id, task_id, notify_event, body四个参数。
    
        product：业务类型，CRED,INSU,YYSD,WECHAT, ALIM等
        app_id：商户id，接入时分配
        task_id：任务号，流水号，批次号。可随机生成，用于回调日志查询，一般为任务唯一号
        notify_event:回调事件，可以根据各自业务自行定义。task.success, task.fail, login.fail, login.success, report等
        body：回调需要必要参数
    
    
回调模型：  
    {
        "batch_no":{
            "fixed":false,
            "pairKey":"batchNo"	,
            "value":"390"
        }
    }

    回调模型为JSON：
        其中key batch_no为回调业务方的字段名称，batchNo为对应runtime端发过来数据key，根据key获取回调内容
        fixed：是否为固定值，为true时，回调内容为value值
    
    回调次数默认为3次，第一次触发失败后，回调。分别在之后10s，20s，40s触发三次，如果三次都失败，则不再继续触发。
    
    
回调配置：
    包括app_id, url, notify_event, headers, partner
    app_id, notify_event, partner上述已描述
    url：商户订阅事件回调地址
    headers：商户订阅事件自定义回调头
    