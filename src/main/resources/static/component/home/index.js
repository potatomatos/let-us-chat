define(['jquery','componentTool','request'], function ($,componentTool,request) {
    return {
        data: function () {
            return {
                isCollapse: false,
                chatBoxHeight: '',
                clientHeight: '',
                chatContent: '',
                loading: false,
                chatboxDatas:[],
                toUserId: null,
                fromUser:null,
                webSocket: null,
                friends: [],//好友列表
                groups: [],//群聊列表
                currentChatBoxId:null
            }
        },
        created:function(){
            let user = JSON.parse(this.$store.getters.getUserInfo)
            if (user){
                this.fromUser=user.id
            }
        },
        mounted: function () {
            // 获取浏览器可视区域高度
            this.clientHeight = document.documentElement.clientHeight;
            $(".chat-box").animate({scrollTop: this.clientHeight}, 500);

            this.initWebSocket();
            this.getFriendList();
        },
        methods: {
            /**
             * 发送消息
             */
            sendMsg(data) {
                this.webSocket.send(JSON.stringify(data))
                this.chatContent=null;
            },
            getFriendList(){
                let _this=this;
                request.get("/friends/list",{userId:this.fromUser},function (res) {
                    if (res.code===20000){
                        _this.friends=res.data;
                    }
                })
            },
            listSelect:function(index,indexPath){
                console.log("index",index,"indexPath",indexPath)
                let type=indexPath[0];
                if (type==='1'){
                    //好友
                    let friend=this.friends[index];
                    let id=indexPath.join("_");
                    id="chatbox_"+id
                    this.currentChatBoxId=id;
                    let chatboxData={}
                    chatboxData.id=id
                    chatboxData.type=type
                    chatboxData.fromUser=this.fromUser
                    chatboxData.toUser=friend.id
                    chatboxData.toUsername=friend.remarkName
                    chatboxData.messageList=[]
                    chatboxData.active=true

                    this.chatboxDatas.forEach(function (value) {
                        value.active=false;
                    })
                    this.chatboxDatas.push(chatboxData);
                    console.log(this.chatboxDatas)
                }else if (type==='2'){
                    //群
                }
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
                let token = this.$store.getters.getToken;
                if (!this.fromUser||!token){
                    this.$message({
                        message: '用户未登录',
                        center: true
                    });
                    return;
                }

                this.loading = true;
                this.webSocket = new WebSocket((document.location.origin + "/chat/" + this.fromUser + "/" + token).replace("http", "ws"));
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
                if (data.fromUser.id===this.fromUser){
                    direction="right msg"
                }else {
                    direction="left msg"
                }
                let message={
                    time:data.timestamp,
                    name:data.fromUser.username,
                    content:data.msg,
                    direction:direction,
                    avatar:data.fromUser.avatar,
                    type:data.type
                }
                this.chatboxDatas.forEach(function (value) {
                    if (value.id==="chatbox_"+data.type+"_"+data.fromUser.id){
                        value.messageList.push(message);
                    }
                })

                let height=document.getElementById("chatBox").scrollHeight
                let select="chatBox_"+data.type+"_"+this.toUserId;
                $("#"+select).animate({scrollTop:height}, 500);
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
        },
        components:{
            "chatbox":componentTool.componentOption('chatbox', {}),
        },
        watch: {

        }
    }
});
