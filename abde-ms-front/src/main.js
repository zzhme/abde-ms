// src/main.js
import Vue from 'vue'
import App from './App.vue'
import router from './router'
import request from './utils/request';
import { setupVue, setupGlobalErrorHandlers } from './utils/vue-config';

// 配置Vue实例
setupVue(Vue);
setupGlobalErrorHandlers();

// 给Vue实例添加axios
Vue.prototype.$http = request;

new Vue({
    router,
    render: h => h(App)
}).$mount('#app')
