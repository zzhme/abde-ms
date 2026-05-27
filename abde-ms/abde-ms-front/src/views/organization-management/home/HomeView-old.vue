<template>
  <div>
    <el-container style="height: 500px; border: 1px solid #eee">
      <el-header><span style="font-size: 40px;font-family: '微软雅黑'; ">账户-部门-员工智能辅助系统</span></el-header>
      <el-container>
        <el-aside width="200px" style="background-color: rgb(238, 241, 246)">
          <el-menu>
            <el-submenu index="1">
              <template slot="title"><i class="el-icon-message"></i>系统信息管理</template>
              <el-menu-item-group>
                <el-menu-item index="1-1"><router-link to="/">首页</router-link></el-menu-item>
                <el-menu-item index="1-2"><router-link to="/account">账户管理</router-link></el-menu-item>
                <el-menu-item index="1-3"><router-link to="/dept">部门管理</router-link></el-menu-item>
                <el-menu-item index="1-4"><router-link to="/emp">员工管理</router-link></el-menu-item>
              </el-menu-item-group>
            </el-submenu>
          </el-menu>
        </el-aside>
        <el-main>
          <div class="login-page">
            <!-- 系统标题区域 -->
            <div class="system-title">
              <span class="en-title">ade-ms</span>
              <span class="cn-title">账户-部门-员工-智能管理系统</span>
            </div>

            <!-- 表单区域 -->
            <div class="form-wrapper">
              <!-- 用户名表单项 -->
              <div class="form-item">
                <label class="required-mark">*</label>
                <label class="item-label">用户名</label>
                <input
                    type="text"
                    v-model="emp.username"
                    placeholder="请输入员工用户名"
                    class="form-input"
                >
              </div>

              <!-- 密码表单项 -->
              <div class="form-item">
                <label class="required-mark">*</label>
                <label class="item-label">密码</label>
                <input
                    type="password"
                    v-model="emp.password"
                    placeholder="请输入密码"
                    class="form-input"
                >
              </div>

              <!-- 按钮区域 -->
              <div class="btn-group">
                <button class="login-btn" @click="handleLogin">登录</button>
                <button class="cancel-btn" @click="handleCancel">取消</button>
              </div>
            </div>
          </div>
        </el-main>
      </el-container>
    </el-container>
  </div>
</template>

<script>
import axios from "axios";
// 第一步：全局配置 Axios 请求拦截器（写在 Vue 实例初始化前，或全局入口处）
axios.interceptors.request.use(
    (config) => {
      // 确保 headers 存在（避免个别请求没设置 headers 导致报错）
      config.headers = config.headers || {};

      // 全局添加请求头：operateUser=zhangsan（所有请求自动携带）
      config.headers.operateUser = "zhangsan";

      // 可选：统一添加 Content-Type（POST 请求默认 JSON 格式，后端兼容的话可加）
      config.headers["Content-Type"] = "application/json;charset=utf-8";

      // 后续如果需要加 Token，也可以在这里全局配置（示例）
      // config.headers.Authorization = `Bearer ${localStorage.getItem('token')}`;

      return config; // 必须返回 config，否则请求会中断
    },
    (error) => {
      // 拦截器报错处理（如网络异常）
      return Promise.reject(error);
    }
);
export default {
  data() {
    return {
      emp: {
        username: "", // 绑定用户名输入框
        password: ""  // 绑定密码输入框
      }
    }
  },
  methods: {
    // 登录按钮逻辑
    handleLogin() {
      // 非空验证
      if (!this.emp.username || !this.emp.password) {
        this.$message.error('用户名和密码不能为空哦~');
        return;
      }
      // 这里可以替换为真实接口请求（示例用固定账号）
      if (this.emp.username === "admin" && this.emp.password === "123") {
        // 存储登录状态（前端临时存储）
        localStorage.setItem("isLogin", "true");
        // 跳转到系统首页（需确保路由配置了/home）
        this.$router.push("/account");
      } else {
        this.$message.error('用户名或密码错误，请重试~');
      }
    },
    // 取消按钮逻辑：清空输入框
    handleCancel() {
      this.emp.username = "";
      this.emp.password = "";
    }
  }
}
</script>
<style>
/* 页面容器 */
.login-page {
  width: 420px;
  margin: 100px auto;
  padding: 20px;
  box-sizing: border-box;
}

/* 系统标题 */
.system-title {
  font-size: 26px;
  margin-bottom: 35px;
  color: #333;
}

.en-title {
  margin-right: 8px;
}

.cn-title {
  font-weight: 700;
}

/* 表单容器 */
.form-wrapper {
  width: 100%;
}

/* 表单项 */
.form-item {
  display: flex;
  align-items: center;
  margin-bottom: 22px;
}

.required-mark {
  color: red;
  margin-right: 4px;
}

.item-label {
  width: 80px;
  text-align: right;
  margin-right: 12px;
  color: #666;
}

.form-input {
  flex: 1;
  height: 38px;
  padding: 0 12px;
  border: 1px solid #ccc;
  border-radius: 4px;
  outline: none;
  font-size: 14px;
}

.form-input:focus {
  border-color: #1677ff;
}

/* 按钮组 */
.btn-group {
  margin-top: 30px;
  display: flex;
  gap: 25px;
  justify-content: center;
}

.login-btn {
  width: 120px;
  height: 42px;
  background-color: #1677ff;
  color: #fff;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  font-size: 14px;
}

.login-btn:hover {
  background-color: #3588ff;
}

.cancel-btn {
  width: 120px;
  height: 42px;
  background-color: #f5f5f5;
  color: #666;
  border: 1px solid #ccc;
  border-radius: 4px;
  cursor: pointer;
  font-size: 14px;
}

.cancel-btn:hover {
  background-color: #e8e8e8;
}
</style>
