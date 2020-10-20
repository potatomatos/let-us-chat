define(['jquery'], function ($) {
    return {
        data: function () {
            return {
                isCollapse: false,
                chatBoxHeight: '',
                clientHeight: '',
                chatContent: '',
                loading: false,
                message: {
                    /**
                     * 消息类型
                     */
                    type: 2,
                    /**
                     * 发送人
                     */
                    fromUser:null,
                    /**
                     * 接收人
                     */
                    toUser: 1,
                    /**
                     * 发送消息
                     */
                    msg: null
                },
                webSocket: null,
                friends: [],//好友列表
                groups: [],//群聊列表
                privateMessages: [],//私聊消息列表
                groupMessages: [],//群聊消息列表
            }
        },
        mounted: function () {//
            // 获取浏览器可视区域高度
            this.clientHeight = document.documentElement.clientHeight;
            $(".chat-box").animate({scrollTop: this.clientHeight}, 500);
            this.initWebSocket();
        },
        watch: {
            // 如果 `clientHeight` 发生改变，这个函数就会运行
            clientHeight(newHeight, oldHeight) {
                console.log("new", newHeight, "old", oldHeight);
                let chatBoxHeaderHeight = document.getElementById("chatBoxHeader").offsetHeight;
                let chatContentHeight = document.getElementById("chatContent").offsetHeight;
                this.chatBoxHeight = "overflow: auto;height:" + (this.clientHeight - chatContentHeight - chatBoxHeaderHeight - 100) + "px";
            }
        }, methods: {
            /**
             * 发送消息
             */
            sendMsg() {
                console.log("消息", this.chatContent);
                if (!this.chatContent){
                    return
                }
                this.message.msg = this.chatContent
                this.webSocket.send(JSON.stringify(this.message))
                this.chatContent=null;
            },
            /**
             * 初始化websocket
             */
            initWebSocket() {
                if (typeof (WebSocket) == "undefined") {
                    this.$alert("您的浏览器不支持WebSocket", '提示', {
                        confirmButtonText: '关闭'
                    })
                    return;
                }
                let user = JSON.parse(this.$store.getters.getUserInfo)
                let token = this.$store.getters.getToken;
                if (!user||!token){
                    this.$message({
                        message: '用户未登录',
                        center: true
                    });
                    return;
                }
                this.message.fromUser=user.id
                this.loading = true;
                this.webSocket = new WebSocket((document.location.origin + "/chat/" + user.id + "/" + token).replace("http", "ws"));
                this.webSocket.onerror = this.onError;  // 通讯异常
                this.webSocket.onopen = this.onOpen;  // 连接成功
                this.webSocket.onmessage = this.onMessage;  // 收到消息时回调
                this.webSocket.onclose = this.onClose;  // 连接关闭时回调
            },
            /**
             * 通讯异常
             */
            onError() {
                console.log("通讯异常")
                this.loading = false;
                this.$message({
                    duration: 0,
                    message: '服务器连接异常',
                    type: 'error'
                });
            },
            /**
             * 连接成功
             */
            onOpen() {
                console.log("通讯开始");
                this.loading = false;
                this.$message({
                    message: '服务器连接成功',
                    type: 'success'
                });
            },
            /**
             * 收到消息时回调函数
             * @param event
             */
            onMessage(event) {

                let data = JSON.parse(event.data);
                console.log("收到消息", data);
                this.messageDiv(data);
            },
            /**
             * 关闭连接时回调函数
             */
            onClose() {
                console.log("通讯关闭");
                this.loading = false;
                this.$message({
                    duration: 0,
                    message: '服务连接关闭',
                    type: 'error'
                });
            },
            /**
             * 构建消息框
             * @param data
             */
            messageDiv(data) {
                let direction="";
                if (data.fromUser.id===this.message.fromUser){
                    direction="right msg"
                }else {
                    direction="left msg"
                }
                let message={
                    time:data.timestamp,
                    name:data.fromUser.username,
                    content:data.msg,
                    direction:direction,
                    avatar:data.fromUser.avatar
                }
                this.groupMessages.push(message);
                let height=document.getElementById("chatBox").scrollHeight
                $(".chat-box").animate({scrollTop:height}, 500);
            },
            /**
             * 手动关闭连接
             */
            webSocketClose() {
                /*
                * 关闭连接
                * */
                this.webSocket.close();
                localStorage.removeItem("token")
                localStorage.removeItem("userInfo")
                this.$router.push({path: '/login'});
            },
        }
    }
});
