define(['jquery'], function ($) {
    return {
        componentOption: function (url, vuecom) {
            let path="/static/component/"+url
            if (!vuecom) vuecom = {};
            return function (resolve) {
                $.get(path + "/index.html").done(function (r) {
                    require([path + "/index.js"], function (option) {
                        if (option) {
                            for (let a in option) {
                                vuecom[a] = option[a];
                            }
                        }
                        let rl = r.toLowerCase();
                        let style = '';
                        let styleIndexEnd = rl.lastIndexOf('</style>');
                        let styleIndex = rl.lastIndexOf('<style', styleIndexEnd);
                        if (styleIndexEnd !== -1 && styleIndex !== -1) {
                            style = r.substring(styleIndex, styleIndexEnd);
                            style = style.substr(style.indexOf('>') + 1);
                            style = '<component scoped :is="\'style\'">' + style + '</component>';
                        }

                        let template = '';
                        let templateIndexEnd = rl.lastIndexOf('</template>');
                        let templateIndex = rl.indexOf('<template');
                        if (templateIndexEnd !== -1 && templateIndex !== -1) {
                            template = r.substring(templateIndex, templateIndexEnd);
                            template = template.substr(template.indexOf('>') + 1);
                        }
                        if (style) {
                            let index = template.lastIndexOf('</');
                            if (index !== -1) template = template.substr(0, index) + style + template.substr(index);
                        }
                        vuecom.template = template;
                        resolve(vuecom);
                    });
                });
            };
        }
    }
});
