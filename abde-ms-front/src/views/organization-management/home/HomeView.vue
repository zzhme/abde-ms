<template>
  <div class="login-page">
    <!-- 系统标题区域 -->
    <div class="system-title">
      <span class="en-title">ade-ms</span>
      <span class="cn-title">账户-品牌-部门-员工智能辅助系统</span>
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
</template>

<script>
export default {
  data() {
    return {
      emp: {
        username: "", // 绑定用户名输入框
        password: ""  // 绑定密码输入框
      }
    };
  },
  methods: {
    // 登录按钮逻辑
    handleLogin() {
      //this.$router.push("/account");
      // 非空验证
      if (!this.emp.username.trim() || !this.emp.password.trim()) {
        this.$message.error('用户名和密码不能为空哦~');
        return;
      }
      // 这里可以替换为真实接口请求（示例用固定账号）
      this.$http({
        method: "post",
        url: "/login",
        data: {
          username: this.emp.username,
          password: this.emp.password
        }
      }).then(resp => {
        //登录成功
        if (resp.data.code == "1") {
          //存储登录状态(前端临时存储)
          const token = resp.data.data;
          sessionStorage.setItem("ade_token", token);
          // 设置请求头，确保后续请求携带 token
          this.$http.defaults.headers.common['token'] = token;
          console.log(JSON.stringify(resp));
          // 跳转到系统首页（需确保路由配置了/account）
          this.$router.push("/account");
        } else {
          this.$message.error(resp.data.msg);
          // console.log(JSON.stringify(resp));
        }
      }).catch(resp=>{
        console.log(JSON.stringify(resp));
        this.$message.error('登录失败请,请检查用户名和密码');
      })
    },
    // 取消按钮逻辑：清空输入框
    handleCancel() {
      this.emp.username = "";
      this.emp.password = "";
    }
  }
};
</script>

<style scoped>
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
