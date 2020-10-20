define(['jquery','vue'], function ($,vue) {
    return {
        get: function (url,data,success,error) {
            $.ajax({
                //请求方式
                type : "GET",
                //请求的媒体类型
                contentType: "application/json;charset=UTF-8",
                //请求地址
                url : url+"?token="+localStorage.getItem("token")+"&timestamp="+new Date().getTime(),
                //数据，json字符串
                data : data,
                //请求成功
                success : function(result) {
                    if (result.code===50008||result.code===50014){
                        vue.$alert(result.info, '提示', {
                            confirmButtonText: '关闭'
                        })
                        vue.$router.push({path: '/login'});
                        return;
                    }
                    success(result);
                },
                //请求失败，包含具体的错误信息
                error : function(e){
                    error(e);
                }
            })
        },
        syncGet: function (url,data,success,error) {
            $.ajax({
                //请求方式
                type : "GET",
                //请求的媒体类型
                contentType: "application/json;charset=UTF-8",
                //请求地址
                url : url+"?token="+localStorage.getItem("token")+"&timestamp="+new Date().getTime(),
                async:false,
                //数据，json字符串
                data : data,
                //请求成功
                success : function(result) {
                    if (result.code===50008||result.code===50014){
                        vue.$alert(result.info, '提示', {
                            confirmButtonText: '关闭'
                        })
                        vue.$router.push({path: '/login'});
                        return;
                    }
                    success(result);
                },
                //请求失败，包含具体的错误信息
                error : function(e){
                    error(e);
                }
            })
        },
        post: function (url,data,success,error) {
            $.ajax({
                //请求方式
                type : "POST",
                //请求的媒体类型
                contentType: "application/json;charset=UTF-8",
                //请求地址
                url : url+"?token="+localStorage.getItem("token")+"&timestamp="+new Date().getTime(),
                //数据，json字符串
                data : data,
                //请求成功
                success : function(result) {
                    if (result.code===50008||result.code===50014){
                        vue.$alert(result.info, '提示', {
                            confirmButtonText: '关闭'
                        })
                        vue.$router.push({path: '/login'});
                        return;
                    }
                    success(result);
                },
                //请求失败，包含具体的错误信息
                error : function(e){
                    error(e);
                }
            })
        },
        syncPost: function (url,data,success,error) {
            $.ajax({
                //请求方式
                type : "POST",
                //请求的媒体类型
                contentType: "application/json;charset=UTF-8",
                //请求地址
                url : url+"?token="+localStorage.getItem("token")+"&timestamp="+new Date().getTime(),
                async:false,
                //数据，json字符串
                data : data,
                //请求成功
                success : function(result) {
                    if (result.code===50008||result.code===50014){
                        vue.$alert(result.info, '提示', {
                            confirmButtonText: '关闭'
                        })
                        vue.$router.push({path: '/login'});
                        return;
                    }
                    success(result);
                },
                //请求失败，包含具体的错误信息
                error : function(e){
                    error(e);
                }
            })
        }
    }
});
