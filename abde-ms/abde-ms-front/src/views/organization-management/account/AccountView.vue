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
                <el-menu-item index="1-1"><router-link to="/account">账户管理</router-link></el-menu-item>
                <el-menu-item index="1-2"><router-link to="/brand">品牌管理</router-link></el-menu-item>
                <el-menu-item index="1-3"><router-link to="/dept">部门管理</router-link></el-menu-item>
                <el-menu-item index="1-4"><router-link to="/emp">员工管理</router-link></el-menu-item>
                <el-menu-item index="1-5"><router-link to="/product">产品管理</router-link></el-menu-item>
              </el-menu-item-group>
            </el-submenu>
          </el-menu>
        </el-aside>
        <el-main>
          <div class="box">
            <div class="filter-container">
              <el-button type="primary" class="butT" @click="handleCreateDialog()">新增</el-button>
              <el-button type="primary" class="butT" @click="handleTransferDialog()">转账</el-button>
            </div>
            <el-table size="small" current-row-key="id" :data="dataList" stripe highlight-current-row>
              <el-table-column type="index" align="center" label="序号"></el-table-column>
              <el-table-column prop="name" label="账户名称" align="center"></el-table-column>
              <el-table-column prop="money" label="金额" align="center"></el-table-column>
              <el-table-column prop="homeAddress" label="地址" align="center"></el-table-column>
              <el-table-column label="操作" align="center">
                <template slot-scope="scope">
                  <el-button type="primary" size="mini" @click="handleUpdateDialog(scope.row)">编辑</el-button>
                  <el-button size="mini" type="danger" @click="handleDelete(scope.row)">删除</el-button>
                </template>
              </el-table-column>
            </el-table>
            <!-- 新增账户对话框 -->
            <div class="add-form">
              <el-dialog title="新增账户" :visible.sync="addDialogFormVisible">
                <el-form ref="dataAddForm" :model="addFormData" label-position="right" label-width="100px">
                  <el-row>
                    <el-col :span="24">
                      <el-form-item label="账户名称">
                        <el-input v-model="addFormData.name" type="text"></el-input>
                      </el-form-item>
                    </el-col>
                  </el-row>
                  <el-row>
                    <el-col :span="24">
                      <el-form-item label="金额">
                        <el-input v-model="addFormData.money" type="text"></el-input>
                      </el-form-item>
                    </el-col>
                  </el-row>
                  <el-row>
                    <el-col :span="24">
                      <el-form-item label="账户地址">
                        <el-input v-model="addFormData.homeAddress" type="text"></el-input>
                      </el-form-item>
                    </el-col>
                  </el-row>
                </el-form>
                <div slot="footer" class="dialog-footer">
                  <el-button @click="addDialogFormVisible = false">取消</el-button>
                  <el-button type="primary" @click="handleAdd()">确定</el-button>
                </div>
              </el-dialog>
            </div>

            <!--账户转账对话框 -->
            <div class="add-form">
              <el-dialog title="账户转账" :visible.sync="transferDialogFormVisible">
                <el-form ref="dataAddForm" :model="transferFormData" label-position="right" label-width="100px">
                  <el-row>
                    <el-col :span="24">
                      <el-form-item label="转出账户名称">
                        <el-input v-model="transferFormData.outName" type="text"></el-input>
                      </el-form-item>
                    </el-col>
                  </el-row>
                  <el-row>
                    <el-col :span="24">
                      <el-form-item label="转入账户名称">
                        <el-input v-model="transferFormData.inName" type="text"></el-input>
                      </el-form-item>
                    </el-col>
                  </el-row>
                  <el-row>
                    <el-col :span="24">
                      <el-form-item label="转账金额">
                        <el-input v-model="transferFormData.money" type="text"></el-input>
                      </el-form-item>
                    </el-col>
                  </el-row>
                </el-form>
                <div slot="footer" class="dialog-footer">
                  <el-button @click="transferDialogFormVisible = false">取消</el-button>
                  <el-button type="primary" @click="handleTransfer()">确定</el-button>
                </div>
              </el-dialog>
            </div>
            <!-- 编辑账户对话框 -->
            <div class="add-form">
              <el-dialog title="编辑账户" :visible.sync="editDialogFormVisible">
                <el-form ref="dataAddForm" :model="editFormData" label-position="right" label-width="100px">
                  <el-row>
                    <el-col :span="24">
                      <el-form-item label="账户名称">
                        <el-input v-model="editFormData.name" type="text"></el-input>
                      </el-form-item>
                    </el-col>
                  </el-row>
                  <el-row>
                    <el-col :span="24">
                      <el-form-item label="金额">
                        <el-input v-model="editFormData.money" type="text"></el-input>
                      </el-form-item>
                    </el-col>
                  </el-row>
                  <el-row>
                    <el-col :span="24">
                      <el-form-item label="账户地址">
                        <el-input v-model="editFormData.homeAddress" type="text"></el-input>
                      </el-form-item>
                    </el-col>
                  </el-row>
                </el-form>
                <div slot="footer" class="dialog-footer">
                  <el-button @click="editDialogFormVisible = false">取消</el-button>
                  <el-button type="primary" @click="handleEdit()">确定</el-button>
                </div>
              </el-dialog>
            </div>
          </div>
        </el-main>
      </el-container>
    </el-container>
  </div>
</template>

<script>
export default {
  data() {
    return {
      dataList: [
        {id: 1, name: "1234567890", money: 1000, homeAddress: "北京昌平"},
        {id: 2, name: "2345678901", money: 2000, homeAddress: "上海嘉定"},
        {id: 3, name: "3456789012", money: 3000, homeAddress: "山东济南"}
      ],//当前页要展示的分页列表数据
      addFormData: {},//新增表单数据
      editFormData: {},//编辑表单数据
      transferFormData: {}, //转账表单数据
      addDialogFormVisible: false,//增加表单是否可见
      editDialogFormVisible: false,//编辑表单是否可见
      transferDialogFormVisible: false //转账表单是否可见
    }
  },
  //钩子函数
  mounted() {
    //查询所有方法
    this.selectAll();
  },
  methods: {
    //查询所有方法
    selectAll() {
      //axios发送异步请求
      this.$http({
        method: "get",
        url: "/accounts/selectAll"
      }).then(resp => {
        //判断如果查询成功
        if (resp.data.code == "1") {
          //把查询结果的列表数据赋值给数据模型
          this.dataList = resp.data.data;
        }
      });
    },
    //编辑
    handleEdit() {
      //console.log(this.editFormData);
      //axios发送异步请求
      this.$http({
        method: "put",
        url: "/accounts",
        data: this.editFormData
      }).then(resp => {
        //判断如果修改成功
        if (resp.data.code == "1") {
          //清空修改对话框数据模型
          this.editFormData = {};
          //隐藏新增对话框
          this.editDialogFormVisible = false;
          //提示信息
          this.$message({
            message: '恭喜你，编辑账户成功!',
            type: 'success'
          });
          //重新加载所有账户
          this.selectAll();
        } else {
          //提示信息
          this.$message({
            message: '非常抱歉，编辑账户失败!',
            type: 'error'
          });
        }
      });
    },
    //添加
    handleAdd() {
      //console.log(this.addFormData);
      //axios发送异步请求
      this.$http({
        method: "post",
        url: "/accounts",
        data: this.addFormData
      }).then(resp => {
        console.log(JSON.stringify(resp));
        //判断如果新增成功
        if (resp.data.code == "1") {
          //清空新增对话框数据模型
          //this.addFormData = {};
          //隐藏新增对话框
          this.addDialogFormVisible = false;
          //提示信息
          this.$message({
            message: resp.data.msg,
            type: 'success'
          });
          //重新加载所有账户
          this.selectAll();
        } else {
          //提示信息
          this.$message({
            message: resp.data.msg,
            type: 'error'
          });
        }
      });
    },
    //转账
    handleTransfer() {
      //console.log(this.transferFormData);
      //axios发送异步请求
      this.$http({
        method: "get",
        url: "/accounts/transfer?outName=" + this.transferFormData.outName + "&inName=" + this.transferFormData.inName + "&money=" + this.transferFormData.money
      }).then(resp => {
        //清空转账对话框数据模型
        this.transferFormData = {};
        //隐藏转账对话框
        this.transferDialogFormVisible = false;
        //判断如果转账成功
        if (resp.data.code == "1") {
          //提示信息
          this.$message({
            message: resp.data.msg,
            type: 'success'
          });
          //重新加载所有账户
          this.selectAll();
        } else {
          //提示信息
          this.$message({
            message: resp.data.msg,
            type: 'error'
          });
        }
      });
    },
    // 新增弹窗
    handleCreateDialog() {
      this.addFormData = {};
      this.addDialogFormVisible = true;
    },
    //转账弹窗
    handleTransferDialog() {
      this.transferFormData = {};
      this.transferDialogFormVisible = true;
    },
    //编辑弹框
    handleUpdateDialog(row) {
      this.editDialogFormVisible = true;
      //this.editFormData = JSON.parse(JSON.stringify(row));
      //axios发送异步请求根据账户名称查询账户对象
      this.$http({
        method: "get",
        url: "/accounts/selectByName?name=" + row.name
      }).then(resp => {
        //判断如果查询成功
        if (resp.data.code == "1") {
          //把查询结果的对象数据赋值给数据模型
          this.editFormData = resp.data.data;
        }
      });
    },
    // 删除
    handleDelete(row) {
      this.$confirm('此操作将永久删除该文件, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        //axios发送异步请求
        this.$http({
          method: "delete",
          url: "/accounts/" + row.id
        }).then(resp => {
          //判断如果删除成功
          if (resp.data.code == "1") {
            //提示信息
            this.$message({
              message: '恭喜你，删除账户成功!',
              type: 'success'
            });
            //重新加载所有账户
            this.selectAll();
          } else {
            //提示信息
            this.$message({
              message: '非常抱歉，删除账户失败!',
              type: 'error'
            });
          }
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
