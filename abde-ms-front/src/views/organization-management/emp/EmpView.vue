<template>
  <div>
    <el-container style="height: 500px; border: 1px solid #eee">
      <el-header style="display: flex; justify-content: space-between;">
        <span style="font-size: 40px;font-family: '微软雅黑'; ">账户-品牌-部门-员工智能辅助系统</span>
        <el-button type="primary" icon="el-icon-switch-button" @click="handleLogout()">退出登录</el-button>
      </el-header>
      <el-container>
        <el-aside width="200px" style="background-color: rgb(238, 241, 246)">
          <el-menu  :default-openeds="['1']">
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
          <!--多条件分页查询表单-->
          <el-form :inline="true" :model="queryFormData" class="demo-form-inline">
            <el-form-item label="姓名">
              <el-input v-model="queryFormData.name" placeholder="请输入员工姓名"></el-input>
            </el-form-item>
            <el-form-item label="性别">
              <el-select v-model="queryFormData.gender" placeholder="请选择">
                <el-option
                    v-for="genderObject in genderList"
                    :key="genderObject.id"
                    :label="genderObject.name"
                    :value="genderObject.id">
                </el-option>
              </el-select>
            </el-form-item>
            <el-form-item label="入职日期">
              <el-date-picker
                  :unlink-panels="unlinkPanels"
                  value-format="yyyy-MM-dd"
                  v-model="entrydate"
                  type="daterange"
                  range-separator="至"
                  start-placeholder="开始日期"
                  end-placeholder="结束日期">
              </el-date-picker>
            </el-form-item>
            <el-form-item>
              <el-button type="primary" @click="queryPageByCondition">查询</el-button>
              <el-button type="info" @click="clearQueryForm">清空</el-button>
            </el-form-item>
          </el-form>
          <!--批量删除/新增员工按钮-->
          <el-row>
            <el-button type="danger" @click="deleteByIds" plain>-&nbsp;批量删除</el-button>
            <el-button type="primary" @click="showAddDialog" plain>+&nbsp;新增员工</el-button>
          </el-row>
          <br>
          <!--新增员工对话框表单-->
          <el-dialog
              title="新增员工"
              :visible.sync="addDialogVisible"
              width="30%">

            <el-form ref="form" :model="addFormData" label-width="80px">
              <el-form-item label="用户名">
                <el-input v-model="addFormData.username"></el-input>
              </el-form-item>
              <el-form-item label="员工姓名">
                <el-input v-model="addFormData.name"></el-input>
              </el-form-item>
              <el-form-item label="性别">
                <el-select v-model="addFormData.gender" placeholder="请选择">
                  <el-option
                      v-for="genderObject in genderList"
                      :key="genderObject.id"
                      :label="genderObject.name"
                      :value="genderObject.id">
                  </el-option>
                </el-select>
              </el-form-item>
              <el-form-item label="头像">
                <el-upload
                    class="avatar-uploader"
                    name="uploadFile"
                    action="http://localhost:8080/alioss/upload"
                    :show-file-list="false"
                    :on-success="addHandleAvatarSuccess"
                    :before-upload="addBeforeAvatarUpload">
                  <img v-if="addFormData.image" :src="addFormData.image" class="avatar">
                  <i v-else class="el-icon-plus avatar-uploader-icon"></i>
                </el-upload>
              </el-form-item>
              <el-form-item label="职位">
                <el-select v-model="addFormData.job" placeholder="请选择">
                  <el-option
                      v-for="job in jobList"
                      :key="job.id"
                      :label="job.name"
                      :value="job.id">
                  </el-option>
                </el-select>
              </el-form-item>
              <el-form-item label="入职日期">
                <el-date-picker
                    value-format="yyyy-MM-dd"
                    v-model="addFormData.entrydate"
                    type="date"
                    placeholder="选择日期">
                </el-date-picker>
              </el-form-item>
              <el-form-item label="部门">
                <el-select v-model="addFormData.deptId" placeholder="请选择">
                  <el-option
                      v-for="dept in deptList"
                      :key="dept.id"
                      :label="dept.name"
                      :value="dept.id">
                  </el-option>
                </el-select>
              </el-form-item>
              <el-form-item>
                <el-button type="primary" @click="add">提交</el-button>
                <el-button @click="cancelAdd">取消</el-button>
              </el-form-item>
            </el-form>
          </el-dialog>
          <!--编辑员工对话框表单-->
          <el-dialog
              title="编辑员工"
              :visible.sync="updateDialogVisible"
              width="30%">

            <el-form ref="form" :model="updateFormData" label-width="80px">
              <el-form-item label="用户名">
                <el-input v-model="updateFormData.username"></el-input>
              </el-form-item>
              <el-form-item label="员工姓名">
                <el-input v-model="updateFormData.name"></el-input>
              </el-form-item>
              <el-form-item label="性别">
                <el-select v-model="updateFormData.gender" placeholder="请选择">
                  <el-option
                      v-for="genderObject in genderList"
                      :key="genderObject.id"
                      :label="genderObject.name"
                      :value="genderObject.id">
                  </el-option>
                </el-select>
              </el-form-item>
              <el-form-item label="头像">
                <el-upload
                    class="avatar-uploader"
                    name="uploadFile"
                    action="http://localhost:8080/alioss/upload"
                    :show-file-list="false"
                    :on-success="updateHandleAvatarSuccess"
                    :before-upload="updateBeforeAvatarUpload">
                  <img v-if="updateFormData.image" :src="updateFormData.image" class="avatar">
                  <i v-else class="el-icon-plus avatar-uploader-icon"></i>
                </el-upload>
              </el-form-item>
              <el-form-item label="职位">
                <el-select v-model="updateFormData.job" placeholder="请选择">
                  <el-option
                      v-for="job in jobList"
                      :key="job.id"
                      :label="job.name"
                      :value="job.id">
                  </el-option>
                </el-select>
              </el-form-item>
              <el-form-item label="入职日期">
                <el-date-picker
                    value-format="yyyy-MM-dd"
                    v-model="updateFormData.entrydate"
                    type="date"
                    placeholder="选择日期">
                </el-date-picker>
              </el-form-item>
              <el-form-item label="部门">
                <el-select v-model="updateFormData.deptId" placeholder="请选择">
                  <el-option
                      v-for="dept in deptList"
                      :key="dept.id"
                      :label="dept.name"
                      :value="dept.id">
                  </el-option>
                </el-select>
              </el-form-item>
              <el-form-item>
                <el-button type="primary" @click="update">提交</el-button>
                <el-button @click="cancelUpdate">取消</el-button>
              </el-form-item>
            </el-form>
          </el-dialog>
          <!--表格列表区域-->
          <template>
            <el-table
                :data="tableData"
                border
                style="width: 100%"
                @selection-change="handleSelectionChange">
              <el-table-column
                  type="selection"
                  width="55">
              </el-table-column>
              <el-table-column
                  align="center"
                  prop="name"
                  label="姓名">
              </el-table-column>
              <el-table-column
                  align="center"
                  label="头像">
                <template slot-scope="scope">
                  <el-image
                      style="width: 80px; height: 80px"
                      :src="scope.row.image"
                      :preview-src-list="[scope.row.image]">
                  </el-image>
                </template>
              </el-table-column>
              <el-table-column
                  align="center"
                  label="性别">
                <template slot-scope="scope">
                  <span v-if="scope.row.gender == 1">男</span>
                  <span v-if="scope.row.gender == 2">女</span>
                </template>
              </el-table-column>
              <el-table-column
                  align="center"
                  label="职位">
                <template slot-scope="scope">
                  <span v-if="scope.row.job == 1">班主任</span>
                  <span v-else-if="scope.row.job == 2">讲师</span>
                  <span v-else-if="scope.row.job == 3">学工主管</span>
                  <span v-else-if="scope.row.job == 4">教研主管</span>
                  <span v-else-if="scope.row.job == 5">咨询</span>
                  <span v-else>其它职位</span>
                </template>
              </el-table-column>
              <el-table-column
                  align="center"
                  prop="entrydate"
                  label="入职时间">
              </el-table-column>
              <el-table-column
                  align="center"
                  prop="updateTime"
                  label="最后操作时间">
              </el-table-column>
              <el-table-column
                  align="center"
                  label="操作">
                <template slot-scope="scope">
                  <el-button type="primary" @click="showUpdateDialog(scope.row)">编辑</el-button>
                  <el-button type="danger" @click="deleteById(scope.row.id)">删除</el-button>
                </template>

              </el-table-column>
            </el-table>
          </template>
          <!--分页条区域-->
          <el-pagination
              background
              @size-change="handleSizeChange"
              @current-change="handleCurrentChange"
              :current-page="currentPage"
              :page-sizes="pageSizes"
              :page-size="pageSize"
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
      //多条件分页查询表单数据模型
      queryFormData: {
        name: '',
        gender: '',
        begin: '',//入职日期的开始日期
        end: '',//入职日期的结束日期
      },
      //取消入职日期区域选择的联动效果
      unlinkPanels: true,
      //多条件查询表单日期区域选择数据模型
      entrydate: '',
      //批量删除数据模型(选择的要删除的多条记录的id数组)
      //显示/隐藏新增员工对话框标记
      addDialogVisible: false,
      //新增员工表单数据模型
      addFormData: {
        username: '',
        name: '',
        gender: '',
        image: '',
        job: '',//职位
        entrydate: '',//入职日期
        deptId: ''//部门id
      },
      //显示/隐藏编辑员工对话框标记
      updateDialogVisible: false,
      //编辑员工表单数据模型
      updateFormData: {
        username: '',
        name: '',
        gender: '',
        image: '',
        job: '',//职位
        entrydate: '',//入职日期
        deptId: ''//部门id
      },
      //表格列表数据模型
      tableData: [{
        id: 1,
        username: 'zhaomin',
        name: '赵敏',
        image: '',
        gender: 1,
        job: 1,
        deptId: 1,
        entrydate: '2018-10-10',
        updateTime: '2022-07-22 12:05:20'
      }, {
        id: 2,
        username: 'fantong',
        name: '范统',
        image: '',
        gender: 2,
        job: 2,
        deptId: 2,
        entrydate: '2017-09-09',
        updateTime: '2021-08-23 11:08:21'
      }],
      //新增/编辑员工对话框职位列表
      jobList: [
        {
          id: 1,
          name: "班主任",
        },
        {
          id: 2,
          name: "讲师",
        },
        {
          id: 3,
          name: "学工主管",
        },
        {
          id: 4,
          name: "教研主管",
        },
        {
          id: 5,
          name: "咨询",
        }
      ],
      //性别列表数据模型
      genderList: [
        {
          id: 1,
          name: "男"
        },
        {
          id: 2,
          name: "女"
        }
      ],
      //新增/编辑员工对话框部门列表
      deptList: [
        {
          id: 1,
          name: "学工部"
        },
        {
          id: 2,
          name: "教研部"
        },
        {
          id: 3,
          name: "咨询部"
        },
        {
          id: 4,
          name: "就业部"
        },
        {
          id: 5,
          name: "人事部"
        }
      ],
      //分页条数据模型
      //当前页码数据模型
      currentPage: 1,
      //每页显示记录数数据模型
      pageSize: 5,
      //总记录数数据模型
      totalCount: 30,
      //每页显示记录数列表数据模型
      pageSizes: [5, 10, 15, 20],
      //表格列表数据选定后的数据模型
      multipleSelection: [],
      //表格列表数据选定后的id数据模型
      selectedIds: [],
    }
  },
  //钩子函数
  mounted() {
    //调用无条件分页查询
    //this.queryPage();
    //调用多条件分页查询
    this.queryPageByCondition();
  },
  //指定自定义函数区域
  methods: {
    //无条件分页查询
    queryPage() {
      //axios异步请求
      this.$http({
        method: "get",
        url: "/emps?currentPage=" + this.currentPage + "&pageSize=" + this.pageSize
      }).then(resp => {
        //判断响应成功
        if (resp.data.code == "1") {
          //修改总记录数数据模型
          this.totalCount = resp.data.data.totalCount;
          //把查询结果页面列表数据赋值给表格数据模型
          this.tableData = resp.data.data.dataList;
        }
      });
    },
    //多条件分页查询表单执行查询函数(查询按钮)
    queryPageByCondition() {
      //入职日期区域选择框,选定日期后会被封装到数组数据模型entrydate中,需要拆开
      var entrydate = this.entrydate;
      if (entrydate && Array.isArray(entrydate) && entrydate.length == 2) {
        this.queryFormData.begin = entrydate[0];
        this.queryFormData.end = entrydate[1];
      } else {
        this.queryFormData.begin = '';
        this.queryFormData.end = '';
      }
      //封装查询条件对象
      var queryCondition = {
        name: this.queryFormData.name,
        gender: this.queryFormData.gender,
        begin: this.queryFormData.begin,//入职日期的开始日期
        end: this.queryFormData.end,//入职日期的结束日期
        currentPage: this.currentPage,//当前页码
        pageSize: this.pageSize //每页显示记录数
      };
      //console.log(JSON.stringify(queryCondition));
      //axios异步请求
      this.$http({
        method: "post",
        url: "/emps",
        data: queryCondition
      }).then(resp => {
        //判断响应成功
        if (resp.data.code == "1") {
          //修改总记录数数据模型
          this.totalCount = resp.data.data.totalCount;
          //把查询结果页面列表数据赋值给表格数据模型
          this.tableData = resp.data.data.dataList;
        }
      });
    },
    //多条件分页查询表单执行清空函数(清空按钮)
    clearQueryForm() {
      this.entrydate = '';
      this.queryFormData = {
        name: '',
        gender: '',
        begin: '',//入职日期的开始日期
        end: '',//入职日期的结束日期
      };
    },
    //表格列表数据选定执行函数
    handleSelectionChange(val) {
      this.multipleSelection = val;
      //清空id数组,防止上次操作对这次操作产生影响
      this.selectedIds = [];
      //console.log(JSON.stringify(this.multipleSelection));
      var multipleSelection = this.multipleSelection;
      //遍历选定的列表行数据数组
      for (var i = 0; i < multipleSelection.length; i++) {
        //把选定的当前行的id值存储数组
        this.selectedIds[i] = multipleSelection[i].id;
      }
      //console.log(JSON.stringify(this.selectedIds));
    },
    //批量删除执行函数
    deleteByIds() {
      this.$confirm('此操作将永久删除选定的这些员工信息, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        //js的数组方法join('符号')作用: 把数组元素按照方法参数指定的字符作为分隔符进行字符串的拼接
        //发送ajax异步请求根据多id删除员工(批量删除)
        //alert(this.selectedIds);
        //alert(this.selectedIds.join(","));
        //安全校验
        if (!Array.isArray(this.selectedIds) || this.selectedIds.length == 0) {
          this.$message({
            message: '非常抱歉，请先选定再删除!!!',
            type: 'warning'
          });
          //结束函数
          return;
        }
        //axios异步请求根据多id删除员工
        this.$http({
          method: "delete",
          url: "/emps/" + this.selectedIds.join(",")
        }).then(resp => {
          //判断响应成功
          if (resp.data.code == "1") {
            //弹出删除成功消息提示
            this.$message({
              message: resp.data.msg,
              type: 'success'
            });
            //重新加载页面列表数据
            this.queryPageByCondition();
          } else {
            //弹出删除失败消息提示
            this.$message({
              message: resp.data.msg,
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
    //单个删除执行函数(每一行数据后面的删除按钮)
    deleteById(id) {
      this.$confirm('此操作将永久删除当前员工信息, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        //axios异步请求根据id删除员工
        this.$http({
          method: "delete",
          url: "/emps/" + id
        }).then(resp => {
          //判断响应成功
          if (resp.data.code == "1") {
            //弹出删除成功消息提示
            this.$message({
              message: resp.data.msg,
              type: 'success'
            });
            //重新加载页面列表数据
            this.queryPageByCondition();
          } else {
            //弹出删除失败消息提示
            this.$message({
              message: resp.data.msg,
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
    //显示新增员工对话框表单函数
    showAddDialog() {
      //清空新增数据模型
      this.addFormData = {};
      //修改新增员工对话框显示标记为true(显示)
      this.addDialogVisible = true;
    },
    //执行新增员工函数
    add() {
      //console.log(JSON.stringify(this.addFormData));
      //axios发送异步请求
      this.$http({
        method: "post",
        url: "/emps/save",
        data: this.addFormData
      }).then(resp => {
        //判断响应成功
        if (resp.data.code == "1") {
          //隐藏新增对话框
          this.addDialogVisible = false;
          //清空新增数据模型
          this.addFormData = {};
          //弹出新增成功消息提示
          this.$message({
            message: resp.data.msg,
            type: 'success'
          });
          //重新加载页面列表数据
          this.queryPageByCondition();
        } else {
          //弹出新增失败消息提示
          this.$message({
            message: resp.data.msg,
            type: 'error'
          });
        }
      });
    },
    //新增员工对话框,取消按钮执行函数
    cancelAdd() {
      //是否要清空新增对话框填写的数据
      //this.addFormData = {};
      //隐藏新增对话框
      this.addDialogVisible = false;
    },
    //新增员工上传图片成功回调函数
    addHandleAvatarSuccess(res) {
      //this.addFormData.image = URL.createObjectURL(file.raw);
      // console.log(JSON.stringify(res));
      //判断文件上传到阿里云服务器成功
      if (res.code == "1") {
        //把文件在阿里云服务器上的url访问路径赋值给数据模型
        this.addFormData.image = res.data;
        // 强制组件更新,防止图片回显延迟
        this.$forceUpdate();
      }
      // console.log(res.data);
      // console.log(this.addFormData.image);
    },
    //新增员工上传图片时执行函数(判断图片类型和大小的)
    addBeforeAvatarUpload(file) {
      const isJPG = (file.type === 'image/jpeg') || (file.type === 'image/png');
      const isLt2M = file.size / 1024 / 1024 < 10;

      if (!isJPG) {
        this.$message.error('上传头像图片只能是 JPG或PNG 格式!');
      }
      if (!isLt2M) {
        this.$message.error('上传头像图片大小不能超过 10MB!');
      }
      return isJPG && isLt2M;
    },
    //显示编辑员工对话框表单函数
    showUpdateDialog(row) {
      //根据id查询数据回显数据
      //axios异步请求
      this.$http({
        method: "get",
        url: "/emps/" + row.id
      }).then(resp => {
        //判断响应成功
        if (resp.data.code == "1") {
          //把查询结果对象赋值给编辑对话框的数据模型
          this.updateFormData = resp.data.data;
          //修改编辑员工对话框显示标记为true(显示)
          this.updateDialogVisible = true;
        }
      });
    },
    //执行编辑员工函数
    update() {
      //axios发送异步请求
      this.$http({
        method: "put",
        url: "/emps",
        data: this.updateFormData
      }).then(resp => {
        //判断响应成功
        if (resp.data.code == "1") {
          //隐藏修改对话框
          this.updateDialogVisible = false;
          //清空修改数据模型
          this.updateFormData = {};
          //弹出修改成功消息提示
          this.$message({
            message: resp.data.msg,
            type: 'success'
          });
          //重新加载页面列表数据
          this.queryPageByCondition();
        } else {
          //弹出修改失败消息提示
          this.$message({
            message: resp.data.msg,
            type: 'error'
          });
        }
      });
    },
    //编辑员工对话框,取消按钮执行函数
    cancelUpdate() {
      //是否要清空编辑对话框填写的数据
      //this.updateFormData = {};
      //隐藏编辑对话框
      this.updateDialogVisible = false;
    },
    //编辑员工上传图片成功回调函数
    updateHandleAvatarSuccess(res) {
      //this.updateFormData.image = URL.createObjectURL(file.raw);
      //判断文件上传到阿里云服务器成功
      if (res.code == "1") {
        //把文件在阿里云服务器上的url访问路径赋值给数据模型
        this.updateFormData.image = res.data;
        // 强制组件更新,防止图片回显延迟
        this.$forceUpdate();
      }
    },
    //编辑员工上传图片时执行函数(判断图片类型和大小的)
    updateBeforeAvatarUpload(file) {
      const isJPG = (file.type === 'image/jpeg') || (file.type === 'image/png');
      const isLt2M = file.size / 1024 / 1024 < 10;

      if (!isJPG) {
        this.$message.error('上传头像图片只能是 JPG或PNG 格式!');
      }
      if (!isLt2M) {
        this.$message.error('上传头像图片大小不能超过 10MB!');
      }
      return isJPG && isLt2M;
    },

    //分页条执行函数
    //修改每页显示记录数执行函数
    handleSizeChange(val) {
      //console.log(`每页 ${val} 条`);
      //修改数据模型中的每页显示记录数数据模型
      this.pageSize = val;
      //执行无条件分页查询函数重新加载页面数据
      //this.queryPage();
      //执行多条件分页查询函数重新加载页面数据
      this.queryPageByCondition();
    },
    //修改当前页码执行函数
    handleCurrentChange(val) {
      //console.log(`当前页: ${val}`);
      //修改数据模型中的当前页码数据模型
      this.currentPage = val;
      //执行无条件分页查询函数重新加载页面数据
      //this.queryPage();
      //执行多条件分页查询函数重新加载页面数据
      this.queryPageByCondition();
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
