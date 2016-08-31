# standard-inter-demo
网易对外标准化接口demo

### 1.接入流程

首先必须向相关的技术人员申请appKey和密钥，以便得到接口的访问权限。申请完后，会得到相关数据信息，如appKey、interfaceType、密钥等等，之后可以请求http://{ip}/netease/news/list.html地址获取相关数据。

### 2.对外接口文档
####1. MAC算法
消息认证码算法，兼容了MD和SHA算法优势，并在此基础上增加了密钥支持，是一种相对更加安全的消息摘要算法。

####2. 加密字段
appSecret：合作方密钥值
lastTimeStamp=1466740231&interfaceType=1：原始消息

####3. 验签
利用MAC算法，对传递过来的参数中，由第三方传入的appKey得到唯一对应的appSecret，以appSecret作为密钥，对原始消息（即lastTimeStamp=1466740231&interfaceType=1），做摘要处理得到字符串A，比较字符串A和sign进行比较，若A和sign字符串相等，则视为验签通过。

####4. 请求的URL：http://{ip}/netease/news/list.html

请求方式：POST请求

请求参数如下：

参数名称| 参数类型 | 参数说明 | 是否必传
---|--- | --- | ---|
appKey | string | 合作方唯一key，即对应“合作方接入”功能中的合作方ID | 是
lastTimestamp | string | 上一次更新的linux时间戳，参数传长度为10 | 是
interfaceType | string | 接口类型，1：普通新闻；2：本地新闻；3：图集图片 | 是
sign | string | 签名：用于校验 | 是

（1）lastTimestamp
普通咨询和本地接口：
根据此值查询到[lastTimestamp ~ (lastTimestamp+1)天]时间段内的文章信息。
例如：lastTimestamp='2016-07-04 19:35:39'，则查询条件的时间区间即为：[2016-07-04 19:35:39, 2016-07-05 19:35:39]
图集接口
则查询最近15分钟的图片，与lastTimestamp参数值无关。

（2）请求测试：http://{ip}/netease/news/list.html

参数名称| 参数值
---| ---|
appKey	| B6D767D2F8ED5D21A44B0E5886680CB9
lastTimestamp	| 1369358251
interfaceType	| 1
sign	| hby+MyuL1E7lKjSyO0m7BZ3JEQc=


测试返回数据如下：
```json
{
  "message": "处理完成",
  "data": {
    "site": "http://www.3g.163.com",
    "channel": "网易",
    "channel_url": "http://3g.163.com",
    "items": [
      {
        "title": "34万双色球大奖险被丢进垃圾箱",
        "url": "http://3g.163.com/ntes/special/00340EPA/wapSpecialModule.html?qd=yidong?sid=S1447212645197",
        "digest": "",
        "imgSrc": "",
        "type": "special",
        "category": "推荐",
        "source": "测试媒体3g1",
        "ptime": "2013-05-24 09:24:57.0"
      },
      {
        "title": "司机打盹货车冲下10米高桥",
        "url": "http://3g.163.com/ntes/special/00341FAQ/QQbrowser_article.html?qd=yidong&docid=8VKMUH640402001B",
        "digest": "10米高桥10米高桥\r\n10米高桥",
        "imgSrc": "http://img6.cache.netease.com/lady/2014/6/3/201406031036142ae09.jpg",
        "type": "article",
        "category": "推荐",
        "source": "测试媒体3g1",
        "ptime": "2013-05-24 09:31:01.0"
      },
      {
        "title": "端午假期高速路照常收费",
        "url": "http://3g.163.com/ntes/special/00341FAQ/QQbrowser_article.html?qd=yidong&docid=8VLGEOHB0402001B",
        "digest": "",
        "imgSrc": "",
        "type": "article",
        "category": "推荐",
        "source": "网易",
        "ptime": "2013-05-24 16:56:47.0"
      },
      {
        "title": "大沽河大堤全线基本贯通",
        "url": "http://3g.163.com/ntes/special/00341FAQ/QQbrowser_article.html?qd=yidong&docid=8VN38PVV0402001B",
        "digest": "大沽河大堤全线基本贯通大沽河大堤全线基本贯通。",
        "imgSrc": "http://img5.cache.netease.com/3g/2015/10/26/20151026123056c0d46.jpg",
        "type": "article",
        "category": "推荐",
        "source": "青岛新闻网",
        "ptime": "2013-05-25 07:44:49.0"
      }
    ]
  },
  "code": 200
}
```


