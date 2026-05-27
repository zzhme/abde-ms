// src/utils/request.js
import axios from 'axios';

// 创建axios实例
const request = axios.create({
    baseURL: 'http://localhost:8080/',
    withCredentials: true,
    timeout: 10000
});

// 请求拦截器
request.interceptors.request.use(
    config => {
        const token = sessionStorage.getItem("ade_token");
        if (token) {
            config.headers['token'] = token;
        }
        return config;
    },
    error => {
        console.error('请求拦截器异常:', error);
        return Promise.reject(error);
    }
);

// 响应拦截器
request.interceptors.response.use(
    response => {
        const { code, msg } = response.data || {};
        if (code === 0 && msg === "NOT_LOGIN") {
            sessionStorage.removeItem("ade_token");
            const targetUrl = window.location.origin + '/#/';
            if (window.location.href !== targetUrl) {
                window.location.replace(targetUrl);
            }
            return Promise.resolve({ data: { code: 0, msg: "已跳转首页" } });
        }
        return response;
    },
    error => {
        if (error.response) {
            const { status, data } = error.response;
            if (status === 401 || (data?.code === 401)) {
                sessionStorage.removeItem("ade_token");
                const targetUrl = window.location.origin + '/#/';
                if (window.location.href !== targetUrl) {
                    window.location.replace(targetUrl);
                }
                return Promise.resolve({ data: { code: 401, msg: "已跳转首页" } });
            }
        }
        console.error('请求错误:', error);
        return Promise.reject(error);
    }
);

/*
request.interceptors.response.use(
    response => {
        const { code, msg } = response.data || {};
        if (code === 0 || msg === "NOT_LOGIN") {
            .removeItem("ade_token");
            const targetUrl = window.location.origin + '/#/';
            if (window.location.href !== targetUrl) {
                window.location.replace(targetUrl);
            }
            return Promise.resolve({ data: { code: 0, msg: "已跳转首页" } });
        }
        return response;
    },
    error => {
        if (error.response) {
            const { status, data } = error.response;
            if (status === 401 || (data?.code === 401)) {
                localStorage.removeItem("ade_token");
                const targetUrl = window.location.origin + '/#/';
                if (window.location.href !== targetUrl) {
                    window.location.replace(targetUrl);
                }
                return Promise.resolve({ data: { code: 401, msg: "已跳转首页" } });
            }
        }
        console.error('请求错误:', error);
        return Promise.reject(error);
    }
);
*/

export default request;
