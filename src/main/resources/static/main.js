define(["jquery", "vue", "vueResource", "vueRouter", "vueX", "ELEMENT", "componentTool"], function ($, Vue, vueResource, vueRouter, vueX, ELEMENT, componentTool) {
    console.log("初始化...");
    Vue.use(vueResource);
    Vue.use(vueRouter);
    Vue.use(vueX);
    Vue.use(ELEMENT);
    Vue.config.debug = true;
    Vue.config.devtools = true;

    const state = {
        isLogin: false,
        token: null,
        userInfo:null
    };
    const getters = {
        getToken: function (state) {
            if (!state.token) {
                state.token = localStorage.getItem("token")
                state.isLogin = localStorage.getItem("isLogin")
            }
            return state.token
        },
        getUserInfo: function (state) {
            if (!state.userInfo) {
                state.userInfo = localStorage.getItem("userInfo")
            }
            return state.userInfo
        },
    }
    const mutations = {
        $_setLogin(state, value) {
            state.isLogin = value
            localStorage.setItem("isLogin", value)
        },
        $_setToken(state, value) {
            state.token = value
            localStorage.setItem("token", value)
        },
        $_setUserInfo(state, value) {
            state.userInfo = value
            localStorage.setItem("userInfo", value)
        },
        $_removeToken(state) {
            state.token = null
            state.userInfo=null
            localStorage.removeItem("token")
            localStorage.removeItem("userInfo")
        }
    };
    const store = new vueX.Store({
        state, getters, mutations
    });

    const routes = [
        {
            path: '/',
            name: 'Home',
            component: componentTool.componentOption('home', {}),
            meta: {
                title: '来聊吧',
                type: 'auth'
            }
        },
        {
            path: '/login',
            name: 'Login',
            component: componentTool.componentOption('login', {}),
            meta: {
                title: '登录',
                type: 'login'
            }
        },
        {
            path: '/404',
            name: '404',
            component: componentTool.componentOption('404', {}),
            meta: {
                title: '404 Not Found',
            }
        }

    ]
    const router = new vueRouter({
        routes: routes,
        crollBehavior(to, from, savedPosition) {
            return {
                x: 0,
                y: 0
            }
        },
        history: true
    })

    router.beforeEach((to, from, next) => {
        if (to.meta.title) {
            document.title = to.meta.title;
        }
        const type = to.meta.type;

        if (to.matched.length === 0) {//匹配前往的路由不存在
            from.name ? next({
                name: from.name
            }) : next('/404'); //判断此跳转路由的来源路由是否存在，存在的情况跳转到来源路由，否则跳转到404页面
        } else if (type === 'auth') {
            if (localStorage.getItem("isLogin")) {
                next();
            } else {
                next('/login')
            }
        } else {
            next(); //如果匹配到正确跳转
        }
    });
    new Vue({
        el: "#app",
        store: store,
        router: router,
        data: {}
    });

    console.log("初始化完成");
});
