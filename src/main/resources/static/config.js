console.log("开始配置require");
require.config({
    baseUrl : "/static/",
    paths :{
        jquery:"assets/js/lib/jquery/jquery-3.3.1.min",
        vue:"assets/js/lib/vue/vue.min",
        vueResource:"assets/js/lib/vue/vue-resource.min",
        vueRouter:"assets/js/lib/vue/vue-router",
        vueX:"assets/js/lib/vue/vuex",
        ELEMENT : "assets/js/lib/element-ui/index",
        componentTool:"assets/js/componentTool",
        request:"assets/js/request",
        touch:"assets/js/touch",
    },
    shim : {


    },
    waitSeconds: 15//加载时间为15秒。。。。15秒还加载不到。。可以放弃了
});
console.log("require配置完成");
require(["./main"]);
