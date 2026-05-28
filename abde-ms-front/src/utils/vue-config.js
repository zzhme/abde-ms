// src/utils/vue-config.js
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';

// Vue全局配置
export function setupVue(Vue) {
    // 使用ElementUI
    Vue.use(ElementUI);

    // 关闭生产提示
    Vue.config.productionTip = false;

    // Vue组件错误处理
    Vue.config.errorHandler = function (err, vm, info) {
        console.error('🔴 Vue组件内错误:', err, '出错位置:', info);
    };
}

// 全局错误捕获
export function setupGlobalErrorHandlers() {
    // Promise异常处理
    window.addEventListener('unhandledrejection', function (event) {
        console.error('🔴 未处理的Promise异常:', event.reason);
        event.preventDefault();
    });

    // 全局JS错误处理
    window.addEventListener('error', function (event) {
        console.error('🔴 全局JS错误:', event.message, '文件:', event.filename, '行号:', event.lineno);
    });
}
