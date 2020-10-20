define(["request"], function (request) {
    return {
        data: function () {
            return {
                logining: false,
                ruleForm2: {
                    username: 'mengjinyuan',
                    password: 'mjy2020',
                },
                rules2: {
                    username: [{required: true, message: '请输入账号', trigger: 'blur'}],
                    password: [{required: true, message: '请输入密码', trigger: 'blur'}]
                },
                checked: false,
            }
        },
        methods: {
            handleSubmit: function () {
                this.$refs.ruleForm2.validate((valid) => {
                    if (valid) {
                        this.logining = true;
                        let _this = this;
                        this.$http.post('/system/login', this.ruleForm2, {emulateJSON: true}).then(function (res) {
                            _this.logining = false;
                            if (res.body.code === 20000) {
                                _this.$store.commit('$_setToken', res.body.data)
                                _this.$store.commit('$_setLogin', true)
                                _this.$notify({
                                    title: '成功',
                                    message: '登陆成功',
                                    type: 'success'
                                });

                                //获取用户信息
                                request.syncGet("/loginUserInfo", {},
                                    function (result) {
                                        _this.$store.commit('$_setUserInfo', JSON.stringify(result.data))
                                        _this.$router.push({path: '/'});
                                    }, function (e) {
                                        console.error(e.status);
                                        console.error(e.responseText);
                                    })

                                let loginWrap = document.getElementById("loginWrap");
                                loginWrap.style = null;
                                // loginWrap.style.backgroundImage="";
                                // loginWrap.style.backgroundRepeat = "";
                                // loginWrap.style.backgroundSize = "";
                            } else {
                                _this.logining = false;
                                _this.$alert(res.body.info, '提示', {
                                    confirmButtonText: '关闭'
                                })
                            }

                        }, function (res) {
                            _this.logining = false;
                            this.$notify({
                                title: '错误',
                                message: '服务器发生错误',
                                type: 'error'
                            });
                        });
                    } else {
                        console.log('error submit!');
                        return false;
                    }
                })
            },
            setUrl: function () {
                $.getJSON("http://open.iciba.com/dsapi/?callback=?", function (data) {
                    let loginWrap = document.getElementById("loginWrap");
                    // loginWrap.style.backgroundImage="url('"+data.picture4+"')";
                    loginWrap.style.backgroundRepeat = "no-repeat";
                    loginWrap.style.backgroundSize = "cover";
                });
            }
        },

        created: function () {
            this.setUrl();
        }
    }
});
