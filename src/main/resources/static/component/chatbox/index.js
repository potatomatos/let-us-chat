define(['jquery'], function ($) {
    return {
        data: function () {
            return {
                chatBoxHeight: '',
                clientHeight: '',
                chatContent: '',
                message: {
                    /**
                     * 消息类型
                     */
                    type: null,
                    /**
                     * 发送人
                     */
                    fromUser:null,
                    /**
                     * 接收人
                     */
                    toUser: null,
                    /**
                     * 发送消息
                     */
                    msg: null
                },
            }
        },
        props: {
            type:Number,//消息类型
            fromUser:Number,//发送者
            toUser:Number,//接收者
            toUsername:String,//用户名
            chatboxId:String,//容器id
            messageList:Object,//消息列表
        },
        created:function(){
            this.message.type=this.type;
            this.message.fromUser=this.fromUser;
            this.message.toUser=this.toUser;
        },
        mounted: function () {
            // 获取浏览器可视区域高度
            this.clientHeight = document.documentElement.clientHeight;
            $("#"+this.chatboxId).animate({scrollTop: this.clientHeight}, 500);
        },
        watch: {
            // 如果 `clientHeight` 发生改变，这个函数就会运行
           clientHeight(newHeight, oldHeight) {
                console.log("new", newHeight, "old", oldHeight);
                let chatBoxHeaderHeight = document.getElementById("chatBoxHeader").offsetHeight;
                let chatContentHeight = document.getElementById("chatContent").offsetHeight;
                this.chatBoxHeight = "overflow: auto;height:" + (this.clientHeight - chatContentHeight - chatBoxHeaderHeight - 100) + "px";
            }
        },
        methods: {
            /**
             * 发送消息
             */
            sendMsg() {
                if (!this.chatContent){
                    return
                }
                this.message.msg = this.chatContent
                console.log("发送消息", this.message);
                this.$emit('onSend',this.message)
                this.chatContent=''
            }
        }
    }
});
