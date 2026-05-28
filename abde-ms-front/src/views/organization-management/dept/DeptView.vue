<template>
  <div>
    <el-container style="height: 500px; border: 1px solid #eee">
      <el-header style="display: flex; justify-content: space-between;">
        <span style="font-size: 40px;font-family: '微软雅黑'; ">账户-品牌-部门-员工智能辅助系统</span>
        <el-button type="primary" icon="el-icon-switch-button" @click="handleLogout()">退出登录</el-button>
      </el-header>
      <el-container>
        <el-aside width="200px" style="background-color: rgb(238, 241, 246)">
          <el-menu :default-openeds="['1']">
            <el-submenu index="1">
              <template slot="title"><i class="el-icon-message"></i>系统信息管理</template>
              <el-menu-item-group>
                <el-menu-item index="1-1">
                  <router-link to="/account">账户管理</router-link>
                </el-menu-item>
                <el-menu-item index="1-2">
                  <router-link to="/brand">品牌管理</router-link>
                </el-menu-item>
                <el-menu-item index="1-3">
                  <router-link to="/dept">部门管理</router-link>
                </el-menu-item>
                <el-menu-item index="1-4">
                  <router-link to="/emp">员工管理</router-link>
                </el-menu-item>
                <el-menu-item index="1-5">
                  <router-link to="/product">产品管理</router-link>
                </el-menu-item>
              </el-menu-item-group>
            </el-submenu>
          </el-menu>
        </el-aside>
        <el-main>
          <!--
              新增部门按钮
              style="float: right"  el-button标签中添加此属性可以让按钮靠右对齐
          -->
          <el-row>
            <el-button type="primary" @click="showAddDialog">+&nbsp;&nbsp;&nbsp;新增部门</el-button>
          </el-row>
          <br>
          <!--新增部门对话框-->
          <el-dialog
              title="新增部门"
              :visible.sync="addDialogVisible"
              width="30%">
            <el-form ref="form" :model="addFormData" label-width="80px">
              <el-form-item label="部门名称">
                <el-input v-model="addFormData.name" placeholder="请输入部门名称,长度为2-10位"></el-input>
              </el-form-item>
              <el-form-item>
                <el-button type="primary" @click="add">保 存</el-button>
                <el-button @click="addDialogVisible = false">取 消</el-button>
              </el-form-item>
            </el-form>
          </el-dialog>
          <!--编辑部门对话框-->
          <el-dialog
              title="编辑部门"
              :visible.sync="updateDialogVisible"
              width="30%">
            <el-form ref="form" :model="updateFormData" label-width="80px">
              <el-form-item label="部门名称">
                <el-input v-model="updateFormData.name"></el-input>
              </el-form-item>
              <el-form-item>
                <el-button type="primary" @click="update">保 存</el-button>
                <el-button @click="updateDialogVisible = false">取 消</el-button>
              </el-form-item>
            </el-form>
          </el-dialog>
          <!--表格-->
          <template>
            <el-table
                :data="tableData"
                border
                style="width: 100%">
              <el-table-column
                  type="index"
                  label="序号"
                  align="center"
                  width="100">
              </el-table-column>
              <el-table-column
                  prop="name"
                  align="center"
                  label="部门名称">
              </el-table-column>
              <el-table-column
                  prop="updateTime"
                  align="center"
                  label="最后操作时间">
              </el-table-column>
              <el-table-column
                  align="center"
                  label="操作">
                <template slot-scope="scope">
                  <el-row>
                    <el-button type="primary" plain @click="showUpdateDialog(scope.row)">编辑</el-button>
                    <el-button type="danger" plain @click="deleteById(scope.row.id)">删除</el-button>
                  </el-row>
                </template>
              </el-table-column>
            </el-table>
          </template>
        </el-main>
      </el-container>
    </el-container>
  </div>
</template>

<script>
export default {
  data() {
    return {
      //显示/关闭新增部门对话框开关
      addDialogVisible: false,
      //显示/关闭编辑部门对话框开关
      updateDialogVisible: false,
      //新增部门数据模型
      addFormData: {
        name: ''
      },
      //编辑部门数据模型
      updateFormData: {
        name: ''
      },
      //表格数据模型
      tableData: [{
        id: 1,
        name: '学工部',
        updateTime: '2025-08-19 15:07:28'
      }, {
        id: 2,
        name: '教研部',
        updateTime: '2023-11-19 18:08:20'
      }]
    }
  },
  //钩子函数
  mounted() {
    //调用查询所有函数
    this.selectAll();
  },
  methods: {
    //查询所有
    selectAll() {
      //axios发送异步请求
      this.$http({
        method: "get",
        url: "/depts"
      }).then(resp => {
        //判断响应成功
        if (resp.data.code == "1") {
          //把查询结果列表数据给数据模型赋值
          this.tableData = resp.data.data;
        }
      });
    },
    //显示新增部门对话框
    showAddDialog() {
      this.addFormData = {};
      this.addDialogVisible = true;
    },
    //新增部门执行的方法
    add() {
      //console.log(this.addFormData);
      //axios发送异步请求
      this.$http({
        method: "post",
        url: "/depts",
        data: this.addFormData
      }).then(resp => {
        //判断响应成功
        if (resp.data.code == "1") {
          //清空
          this.addFormData = {};
          //隐藏新增对话框
          this.addDialogVisible = false;
          //弹出新增成功消息提示
          this.$message({
            message: resp.data.msg,
            type: 'success'
          });
          //重新加载数据
          this.selectAll();
        } else {
          //弹出新增失败消息提示
          this.$message({
            message: resp.data.msg,
            type: 'error'
          });
        }
      }).catch(resp => {
        console.log(JSON.stringify(resp));
      });
    },
    //显示修改部门对话框
    showUpdateDialog(row) {
      this.updateDialogVisible = true;
      //this.updateFormData = JSON.parse(JSON.stringify(row));
      //axios发送异步请求
      this.$http({
        method: "get",
        url: "/depts/" + row.id
      }).then(resp => {
        //判断响应成功
        if (resp.data.code == "1") {
          //把查询结果列表数据给数据模型赋值
          this.updateFormData = resp.data.data;
        }
      });
    },
    //编辑部门执行的方法
    update() {
      //console.log(this.updateFormData);
      //axios发送异步请求
      this.$http({
        method: "put",
        url: "/depts",
        data: this.updateFormData
      }).then(resp => {
        //判断响应成功
        if (resp.data.code == "1") {
          //清空
          this.updateFormData = {};
          //隐藏新增对话框
          this.updateDialogVisible = false;
          //弹出编辑成功消息提示
          this.$message({
            message: resp.data.msg,
            type: 'success'
          });
          //重新加载数据
          this.selectAll();
        } else {
          //弹出编辑失败消息提示
          this.$message({
            message: resp.data.msg,
            type: 'error'
          });
        }
      });
    },
    //根据id删除
    deleteById(id) {
      this.$confirm('您确定要删除该部门吗?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        //axios发送异步请求
        this.$http({
          method: "delete",
          url: "/depts/" + id
        }).then(resp => {
          //判断响应成功
          if (resp.data.code == "1") {
            //弹出删除成功消息提示
            this.$message({
              message: resp.data.msg,
              type: 'success'
            });
          } else {
            //弹出删除失败消息提示
            this.$message({
              message: resp.data.msg,
              type: 'error'
            });
          }
          //重新加载数据
          this.selectAll();
        });
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        });
      });
    },
    //退出登录,返回首页
    handleLogout() {
      this.$confirm('确定要退出登录吗？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        // 清空本地存储中的token（根据实际存储的key名调整）
        sessionStorage.removeItem('ade_token');
        // 跳转到登录页面
        this.$router.push('/');
        //重新加载整个项目
        window.location.reload();
        // 显示成功提示
        this.$message({
          message: '退出登录成功！',
          type: 'success'
        });
      }).catch(() => {
        // 用户取消退出
        this.$message({
          type: 'info',
          message: '已取消退出登录'
        });
      });
    }
  }
}
</script>
<style>
.el-header {
  background-color: #B3C0D1;
  color: #333;
  line-height: 60px;
}

.el-aside {
  color: #333;
}
</style>
