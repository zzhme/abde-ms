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
          <!--多条件查询表单-->
          <el-form :inline="true" :model="queryFormData" class="demo-form-inline">
            <el-form-item label="当前状态">
              <el-select v-model="queryFormData.status" placeholder="当前状态">
                <el-option label="启用" value="1"></el-option>
                <el-option label="禁用" value="0"></el-option>
              </el-select>
            </el-form-item>
            <el-form-item label="企业名称">
              <el-input v-model="queryFormData.companyName" placeholder="企业名称"></el-input>
            </el-form-item>
            <el-form-item label="品牌名称">
              <el-input v-model="queryFormData.brandName" placeholder="品牌名称"></el-input>
            </el-form-item>
            <el-form-item>
              <el-button type="primary" @click="queryPageByCondition">查询</el-button>
            </el-form-item>
          </el-form>
          <!--批量删除/新增按钮-->
          <el-row>
            <el-button type="danger" plain @click="deleteByIds">批量删除</el-button>
            <el-button type="primary" plain @click="showAddDialog">新增</el-button>
          </el-row>
          <!--新增品牌对话框-->
          <el-dialog
              title="新增品牌"
              :visible.sync="addDialogVisible"
              width="30%">
            <el-form ref="form" :model="addFormData" label-width="80px">
              <el-form-item label="品牌名称">
                <el-input v-model="addFormData.brandName"></el-input>
              </el-form-item>
              <el-form-item label="企业名称">
                <el-input v-model="addFormData.companyName"></el-input>
              </el-form-item>
              <el-form-item label="排序">
                <el-input v-model="addFormData.ordered"></el-input>
              </el-form-item>
              <el-form-item label="品牌图片">
                <el-upload
                    name="uploadFile"
                    class="avatar-uploader"
                    action="http://localhost:8080/alioss/upload"
                    :show-file-list="false"
                    :on-success="handleAddAvatarSuccess"
                    :before-upload="beforeAddAvatarUpload">
                  <img v-if="addFormData.image"
                       :src="addFormData.image" class="avatar">
                  <i v-else class="el-icon-plus avatar-uploader-icon"></i>
                </el-upload>
              </el-form-item>
              <el-form-item label="备注">
                <el-input type="textarea" v-model="addFormData.description"></el-input>
              </el-form-item>
              <el-form-item label="状态">
                <el-switch v-model="addFormData.status"
                           active-value="100"
                           inactive-value="0">

                </el-switch>
              </el-form-item>
              <el-form-item>
                <el-button type="primary" @click="add">提交</el-button>
                <el-button @click="addDialogVisible = false">取消</el-button>
              </el-form-item>
            </el-form>
          </el-dialog>
          <!--修改品牌对话框-->
          <el-dialog
              title="修改品牌"
              :visible.sync="updateDialogVisible"
              width="30%">
            <el-form ref="form" :model="updateFormData" label-width="80px">
              <el-form-item label="品牌名称">
                <el-input v-model="updateFormData.brandName"></el-input>
              </el-form-item>
              <el-form-item label="企业名称">
                <el-input v-model="updateFormData.companyName"></el-input>
              </el-form-item>
              <el-form-item label="排序">
                <el-input v-model="updateFormData.ordered"></el-input>
              </el-form-item>
              <el-form-item label="品牌图片">
                <el-upload
                    name="uploadFile"
                    class="avatar-uploader"
                    action="http://localhost:8080/alioss/upload"
                    :show-file-list="false"
                    :on-success="handleUpdateAvatarSuccess"
                    :before-upload="beforeUpdateAvatarUpload">
                  <img v-if="updateFormData.image"
                       :src="updateFormData.image" class="avatar">
                  <i v-else class="el-icon-plus avatar-uploader-icon"></i>
                </el-upload>
              </el-form-item>
              <el-form-item label="备注">
                <el-input type="textarea" v-model="updateFormData.description"></el-input>
              </el-form-item>
              <el-form-item label="状态">
                <el-switch v-model="updateFormData.status"
                           active-value="100"
                           inactive-value="0">
                </el-switch>
              </el-form-item>
              <el-form-item>
                <el-button type="primary" @click="update">提交</el-button>
                <el-button @click="updateDialogVisible = false">取消</el-button>
              </el-form-item>
            </el-form>
          </el-dialog>
          <!--表格-->
          <template>
            <el-table
                :data="tableData"
                style="width: 100%"
                :row-class-name="tableRowClassName"
                @selection-change="handleSelectionChange">
              <el-table-column
                  type="selection"
                  width="55">
              </el-table-column>
              <el-table-column
                  type="index"
                  width="50">
              </el-table-column>

              <el-table-column
                  prop="image"
                  align="center"
                  label="图片">
                <template slot-scope="scope">
                  <el-image
                      :src="scope.row.image"
                      style="width: auto; height: 50px; border:none;cursor: pointer;"
                      :preview-src-list="[scope.row.image]">
                  </el-image>
                </template>
              </el-table-column>
              <el-table-column
                  prop="brandName"
                  align="center"
                  label="品牌名称">
              </el-table-column>
              <el-table-column
                  prop="companyName"
                  align="center"
                  label="企业名称">
              </el-table-column>
              <el-table-column
                  prop="ordered"
                  align="center"
                  label="排序">
              </el-table-column>
              <el-table-column
                  prop="statusStr"
                  align="center"
                  label="当前状态">
              </el-table-column>
              <el-table-column
                  align="center"
                  label="操作">
                <template slot-scope="scope">
                  <el-row>
                    <el-button type="primary" @click="showUpdateDialog(scope)">修改</el-button>
                    <el-button type="danger" @click="deleteById(scope.row.id)">删除</el-button>
                  </el-row>
                </template>
              </el-table-column>
            </el-table>
          </template>
          <br>
          <!--分页-->
          <el-pagination
              @size-change="handleSizeChange"
              @current-change="handleCurrentChange"
              :current-page="pageNum"
              :page-sizes="pageSizes"
              :page-size="pageSize"
              background
              layout="total, sizes, prev, pager, next, jumper"
              :total="totalCount">
          </el-pagination>
        </el-main>
      </el-container>
    </el-container>
  </div>
</template>

<script>
/*import axios from "axios";
// 设置请求拦截器，自动添加 token 请求头
axios.interceptors.request.use(
    config => {
      const token = localStorage.getItem("ade_token");
      if (token) {
        // 使用服务器期望的 'token' 请求头，而不是标准的 'Authorization'
        config.headers['token'] = token;
      }
      return config;
    },
    error => {
      return Promise.reject(error);
    }
);
// 设置响应拦截器
axios.interceptors.response.use(
    response => {
      // 检查后端返回的业务状态码
      if (response.data && (response.data.code == "0" || response.data.msg == "NOT_LOGIN")) {
        // 业务层面的未认证
        localStorage.removeItem("ade_token");
        //跳转到首页
        window.location.href = '/';
        return Promise.reject(response.data);
      }
      return response;
    },
    error => {
      // 检查HTTP状态码或后端错误响应
      if (error.response) {
        const {status, data} = error.response;
        if (status === 401 || (data.code && data.code === "401")) {
          localStorage.removeItem("ade_token");
          //跳转到首页
          window.location.href = '/';
          // router.push('/');//注意先引入router的js文件,import router from '@/router'; // 导入router实例
        }
      }
      return Promise.reject(error);
    }
);*/
export default {
  data() {
    return {

      //分页操作数据模型
      //当前页码
      pageNum: 1,
      //每页显示记录数
      pageSize: 10,
      //每页显示记录数数组
      pageSizes: [10, 15, 20, 25],
      //总记录数
      totalCount: 400,
      //添加品牌表单数据模型
      addFormData: {
        brandName: '',
        companyName: '',
        ordered: '',
        description: '',
        status: 'false',
        image:''
      },
      //修改品牌表单数据模型
      updateFormData: {
        brandName: '',
        companyName: '',
        ordered: '',
        description: '',
        status: 'false',
        image:''
      },
      //显示/隐藏新增品牌对话框开关
      addDialogVisible: false,
      //显示/隐藏修改品牌对话框开关
      updateDialogVisible: false,
      //多条件查询表单数据模型
      queryFormData: {
        status: '',
        companyName: '',
        brandName: ''
      },
      //表格数据模型
      tableData: [{
        id: 1,
        image: '../upload/huawei-1.jpg',
        brandName: '华为',
        companyName: '企业名称',
        ordered: '100',
        status: 1
      }, {
        id: 2,
        image: '../upload/xiaomi-2.jpg',
        brandName: '华为',
        companyName: '企业名称',
        ordered: '100',
        status: 1
      }, {
        id: 3,
        image: '../upload/geli-3.jpg',
        brandName: '华为',
        companyName: '企业名称',
        ordered: '100',
        status: 1
      }, {
        id: 4,
        image: '../upload/ali-4.jpg',
        brandName: '华为',
        companyName: '企业名称',
        ordered: '100',
        status: 1
      }, {
        id: 5,
        image: '../upload/tengxun-5.jpg',
        brandName: '华为',
        companyName: '企业名称',
        ordered: '100',
        status: 1
      }, {
        id: 6,
        image: '../upload/baidu-6.jpg',
        brandName: '华为',
        companyName: '企业名称',
        ordered: '100',
        status: 1
      }],
      //复选框选中后的数据模型
      multipleSelection: [],
      //复选框选中后的id数据模型
      selectedIds: []
    }
  },
  //钩子函数
  mounted() {
    //调用无条件分页查询
    //this.queryPage();
    //调用多条件分页查询函数
    this.queryPageByCondition();
  },
  //指定自定义函数区域
  methods: {
    tableRowClassName({rowIndex}) {
      if (rowIndex % 4 === 1) {
        return 'warning-row';
      } else if (rowIndex % 4 === 3) {
        return 'success-row';
      }
      return '';
    },
    //复选框选中后执行的方法
    handleSelectionChange(val) {
      this.multipleSelection = val;
      //console.log(this.multipleSelection);
    },
    //多条件查询表单执行的方法
    queryPageByCondition() {
      //发送异步请求加载页面数据
      this.$http({
        method:"post",
        url:"http://localhost:8080/brands/" + this.pageNum + "/" + this.pageSize,
        data:this.queryFormData
      }).then(resp => {
        // console.log(JSON.stringify(resp));
        //查询成功
        if (resp.data.code == "1") {
          //把查询总记录数赋值给数据模型
          this.totalCount = resp.data.data.totalCount;
          //把查询列表数据赋值给数据模型
          this.tableData = resp.data.data.dataList;
        }
      });
    },
    //批量删除按钮执行的方法
    deleteByIds() {
      this.$confirm('此操作将永久删除这些数据, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        for (var i = 0; i < this.multipleSelection.length; i++) {
          var selectedElement = this.multipleSelection[i];
          //数组保存选定的要删除的元素的id值
          this.selectedIds[i] = selectedElement.id;
        }
        //发送异步请求,根据多id删除
        this.$http({
          method:"delete",
          url:"http://localhost:8080/brands",
          data:this.selectedIds
        }).then(resp => {
          //删除成功
          if (resp.data.code == "1") {
            //弹框
            this.$message({
              type: 'success',
              message: '批量删除品牌数据成功!'
            });
            //重新加载列表数据
            this.queryPageByCondition();
          } else {
            this.$message({
              message: '非常抱歉,批量删除品牌数据失败!!',
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
    //显示添加品牌对话框
    showAddDialog() {
      this.addFormData = {
        brandName: '',
        companyName: '',
        ordered: '',
        description: '',
        status: 'false',
        image:''
      };
      this.addDialogVisible = true;
    },
    //添加品牌执行的方法
    add() {
      //alert("hello");
      //console.log(this.addFormData);
      //发送axios请求,添加品牌数据
      this.$http({
        method:"post",
        url:"http://localhost:8080/brands",
        data:this.addFormData
      }).then(resp=>{
        //alert("what problem!");
        //添加成功
        if (resp.data.code == "1") {
          //弹出添加成功消息提示框
          this.$message({
            message: '恭喜你，添加品牌数据成功!!',
            type: 'success'
          });
          //清空对话框表单数据
          this.addFormData = {};
          //隐藏添加对话框
          this.addDialogVisible = false;
          //重新加载页面
          this.queryPageByCondition();
        } else {
          //弹出添加失败消息提示框
          //alert(resp.data.msg);
          this.$message({
            message: resp.data.msg,
            type: 'error'
          });
        }
      });
    },
    //显示修改品牌对话框
    showUpdateDialog(scope) {
      //this.updateFormData = JSON.parse(JSON.stringify(scope.row));
      //数据回显
      //发送axios异步请求
      this.$http({
        method:"get",
        url:"http://localhost:8080/brands/"+scope.row.id
      }).then(resp=>{
        if (resp.data.code == "1") {
          //响应结果数据赋值给修改对话框的数据模型
          this.updateFormData = resp.data.data;
          //显示修改对话框
          this.updateDialogVisible = true;
        }
      });
    },
    //修改品牌执行的方法
    update() {
      // this.updateFormData =
      //console.log(this.updateFormData);
      //发送axios异步请求,修改数据
      this.$http({
        method:"put",
        url:"http://localhost:8080/brands",
        data:this.updateFormData
      }).then(resp=>{
        //修改成功
        if (resp.data.code == "1") {
          //弹出添加成功消息提示框
          this.$message({
            message: '恭喜你，修改品牌数据成功!!',
            type: 'success'
          });
          //清空对话框表单数据
          this.updateFormData = {};
          //隐藏修改对话框
          this.updateDialogVisible = false;
          //重新加载页面
          this.queryPageByCondition();
        } else {
          //弹出修改失败消息提示框
          this.$message({
            message: '非常抱歉,修改品牌数据失败!!',
            type: 'error'
          });
        }
      });
    },
    //根据id删除执行的方法
    deleteById(id) {
      //console.log(id);
      this.$confirm('此操作将永久删除该数据, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        //console.log(id);
        //发起axios异步请求
        this.$http({
          method:"delete",
          "url":"http://localhost:8080/brands/"+id
        }).then( resp => {
          //删除成功
          if (resp.data.code == "1") {
            //弹框
            this.$message({
              type: 'success',
              message: '删除成功!'
            });
            //重新加载列表数据
            this.queryPageByCondition();
          } else {
            this.$message({
              message: '非常抱歉,删除品牌数据失败!!',
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
    //修改每页显示记录数执行的方法
    handleSizeChange(val) {
      //修改每页显示记录数
      this.pageSize = val;
      //console.log(this.pageSize);
      //重新加载页面
      this.queryPageByCondition();
    },
    //修改当前页面执行的方法
    handleCurrentChange(val) {
      //修改当前页码
      this.pageNum = val;
      //console.log(this.pageNum);
      //重新加载页面
      this.queryPageByCondition();
    },
    //新增品牌图片上传成功后执行的方法
    handleAddAvatarSuccess(res) {
      //this.addFormData.image = URL.createObjectURL(file.raw);
      //console.log(JSON.stringify(res));
      //图片上传成功
      if (res.code == "1") {
        //给表单的数据模型中的图片属性赋值
        this.addFormData.image = res.data;
      }
    },
    //新增品牌图片上传前执行的方法
    beforeAddAvatarUpload(file) {
      const isJPG = file.type === 'image/jpeg' || file.type === 'image/png';
      const isLt2M = file.size / 1024 / 1024 < 2;

      if (!isJPG) {
        this.$message.error('上传头像图片只能是 JPG 格式!');
      }
      if (!isLt2M) {
        this.$message.error('上传头像图片大小不能超过 2MB!');
      }
      return isJPG && isLt2M;
    },
    //修改品牌图片上传成功后执行的方法
    handleUpdateAvatarSuccess(res) {
      //this.updateFormData.image = URL.createObjectURL(file.raw);
      //图片上传成功
      if (res.code == "1") {
        //给表单的数据模型中的图片属性赋值
        this.updateFormData.image = res.data;
      }
    },
    //修改品牌图片上传前执行的方法
    beforeUpdateAvatarUpload(file) {
      const isJPG = file.type === 'image/jpeg'  || file.type === 'image/png';
      const isLt2M = file.size / 1024 / 1024 < 2;

      if (!isJPG) {
        this.$message.error('上传头像图片只能是 JPG 格式!');
      }
      if (!isLt2M) {
        this.$message.error('上传头像图片大小不能超过 2MB!');
      }
      return isJPG && isLt2M;
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
<style scoped>
.el-header {
  background-color: #B3C0D1;
  color: #333;
  line-height: 60px;
}

.el-aside {
  color: #333;
}

::v-deep(.avatar-uploader .el-upload) {
  border: 1px dashed #d9d9d9;
  border-radius: 6px;
  cursor: pointer;
  position: relative;
  overflow: hidden;

}

::v-deep(.avatar-uploader .el-upload:hover) {
  border-color: #409EFF;
}

::v-deep(.avatar-uploader-icon) {
  font-size: 28px;
  color: #8c939d;
  width: 178px;
  height: 178px;
  line-height: 178px;
  text-align: center;
}

.avatar {
  width: 178px;
  height: 178px;
  display: block;
}
</style>
