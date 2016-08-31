com.inter.test。TestMain是使用params.properties配置文件的控制台程序
关于配置文件config/params.properties
	配置文件中有appKey、lastTime、interfaceType、sign、appSecret等5个关键词
	其中appKey、lastTime、interfaceType为必填项
	
	sign和appSecret则为二选一的选填项；
		若同时填写，则首先使用appSecret根据MAC算法重新生成sign摘要信息，然后连接服务器请求数据；
		若返回的数据不正确，则根据配置文件中的sign关键字，再次连接服务器。


com.inter.ui。UITestMain为界面版本。只支持appKey、lastTime、interfaceType、sign4个参数
	其中为方便测试，appKey、lastTime、interfaceType、sign的值将会首先从config/params.properties读取到输入框中

【注意】lastTime为yyyy-MM-dd HH:mm:ss格式的日期



